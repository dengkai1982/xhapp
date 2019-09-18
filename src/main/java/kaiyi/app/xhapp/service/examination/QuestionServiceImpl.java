package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.examination.*;
import kaiyi.app.xhapp.entity.examination.enums.QuestionType;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.pub.ConfigureService;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.data.StringEditor;
import kaiyi.puer.commons.fs.DiskFile;
import kaiyi.puer.commons.poi.ExcelData;
import kaiyi.puer.commons.utils.CoderUtil;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.ContainQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.OrderBy;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.h5ui.service.DocumentService;
import kaiyi.puer.json.JsonParserException;
import kaiyi.puer.json.JsonUtils;
import kaiyi.puer.json.parse.ArrayJsonParser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("questionService")
public class QuestionServiceImpl extends InjectDao<Question> implements QuestionService {
    private static final long serialVersionUID = 5129246458030105241L;

    @Resource
    private ConfigureService configureService;
    @Resource
    private QuestionCategoryService questionCategoryService;
    @Resource
    private SimulationCategoryService simulationCategoryService;

    @Override
    public void deleteChoiceAnswer(String choiceAnswerId) {
        em.createQuery("delete from "+getEntityName(ChoiceAnswer.class)+" o where o.entityId=:entityId")
                .setParameter("entityId",choiceAnswerId).executeUpdate();
    }

    @Override
    public void removeChoiceAnswer(String questionId) {
        Question question=new Question();
        question.setEntityId(questionId);
        em.createQuery("delete from "+getEntityName(ChoiceAnswer.class)+" o where o.question=:question")
                .setParameter("question",question).executeUpdate();
    }

    @Override
    public void deleteOneQuestion() {
        List<Object> questions=em.createNativeQuery("select question from choice_answer group by question having count(*)=1")
                .getResultList();
        for(Object question:questions){
            em.createNativeQuery("delete from choice_answer where question="+question.toString()).executeUpdate();
            em.createNativeQuery("delete from question where entityId="+question.toString()).executeUpdate();
        }
    }

    @Override
    public void deleteById(String entityId) {
        Question question=new Question();
        question.setEntityId(entityId);
        em.createQuery("delete from "+getEntityName(ChoiceAnswer.class)
                +" o where o.question=:question").setParameter("question",question)
                .executeUpdate();
        em.createQuery("delete from "+getEntityName(TestPagerQuestion.class)
                +" o where o.question=:question").setParameter("question",question)
                .executeUpdate();
        deleteForPrimary(entityId);
    }

    @Override
    public void changeEnable(String entityId) {
        Question question=findForPrimary(entityId);
        if(Objects.nonNull(question)){
            question.setEnable(!question.isEnable());
            updateObject(question);
        }
    }

    @Override
    public boolean isQuestion(List<ExcelData> line) {
        return !line.get(0).getData().stringValue().trim().equalsIgnoreCase("0");
    }

    @Override
    public Question parseQuestion(List<ExcelData> line, StreamCollection<QuestionCategory> categories) throws ServiceException {
        Question question=new Question();
        String detail = line.get(0).getData().stringValue();
        String questionCategoryName= line.get(1).getData().stringValue();
        String simulationCategoryName= line.get(2).getData().stringValue();
        QuestionCategory questionCategory=questionCategoryService.signleQuery("name",questionCategoryName);
        SimulationCategory simulationCategory=simulationCategoryService.signleQuery("name",simulationCategoryName);
        if(Objects.isNull(categories)){
            throw ServiceExceptionDefine.categoryError;
        }
        String typeName=line.get(3).getData().stringValue();
        QuestionType questionType=QuestionType.getByName(typeName);
        if(Objects.isNull(questionType)){
            throw ServiceExceptionDefine.questionType;
        }
        int score=line.get(4).getData().integerValue(0);
        question.setDetail(detail);
        question.setCategory(questionCategory);
        question.setQuestionType(questionType);
        question.setScore(score);
        question.setEnable(true);
        question.setSimulationCategory(simulationCategory);
        question.setUpdateTime(new Date());
        String analysis=line.get(5).getData().stringValue();
        String answer=line.get(6).getData().stringValue();
        if(analysis.equalsIgnoreCase("0")){
            analysis="";
        }
        if(answer.equalsIgnoreCase("0")){
            answer="";
        }
        question.setAnalysis(analysis);
        question.setAnswer(answer);
        if(!question.getQuestionType().equals(QuestionType.QuestionsAndAnswers)){
            question.setChoiceAnswers(new HashSet<>());
            String optionName=line.get(7).getData().stringValue();
            String detailValue=line.get(8).getData().stringValue();
            parseChoiceAnswer(question,optionName,detailValue);
        }
        return question;
    }

    private void parseChoiceAnswer(Question question,String optionName,String detailValue){
        ChoiceAnswer choiceAnswer=new ChoiceAnswer();
        choiceAnswer.setQuestion(question);
        choiceAnswer.setEntityId(randomIdentifier());
        choiceAnswer.setOptionName(optionName);
        choiceAnswer.setDetailValue(detailValue);
        choiceAnswer.setChecked(false);
        if(!question.getQuestionType().equals(QuestionType.QuestionsAndAnswers)){
            String answer=question.getAnswer();
            if(question.getQuestionType().equals(QuestionType.SingleChoice)&&answer.equals(optionName)){
                choiceAnswer.setChecked(true);
            }else{
                StreamArray<String> answerArray=new StreamArray<>(answer.split(";"));
                if(answerArray.contain(optionName)){
                    choiceAnswer.setChecked(true);
                }
            }
        }
        question.getChoiceAnswers().add(choiceAnswer);
    }

    @Override
    public void parseChoiceAnswer(Question question,List<ExcelData> line) throws ServiceException {
        String optionName=line.get(7).getData().stringValue();
        String detailValue=line.get(8).getData().stringValue();
        parseChoiceAnswer(question,optionName,detailValue);
    }

    @Override
    public void batchEnable(StreamArray<String> entityIdArray, boolean enable) {
        StreamCollection<String> questions=new StreamCollection<>();
        entityIdArray.forEach(h->{
            questions.add(h);
        });
        em.createQuery("update "+getEntityName(entityClass)+" o set o.enable=:enable where " +
                "o.entityId in(:entityIdArray)").setParameter("enable",enable)
                .setParameter("entityIdArray",questions.toList()).executeUpdate();
    }


    @Override
    protected void objectBeforePersistHandler(Question question, Map<String, JavaDataTyper> params) throws ServiceException {
        question.setEnable(true);
        updateChoiceAnswer(question,params);
    }

    @Override
    protected void objectBeforeUpdateHandler(Question question, Map<String, JavaDataTyper> params) throws ServiceException {
        updateChoiceAnswer(question,params);
    }

    private void updateChoiceAnswer(Question question, Map<String, JavaDataTyper> params){
        question.setUpdateTime(new Date());
        Set<ChoiceAnswer> choiceAnswers = parseChoiceAnswer(params);
        choiceAnswers.forEach(c->{
            c.setEntityId(randomIdentifier());
            c.setQuestion(question);
        });
        question.setChoiceAnswers(choiceAnswers);
    }

    private Set<ChoiceAnswer> parseChoiceAnswer(Map<String,JavaDataTyper> params){
        StreamArray<String> answerArray=new StreamArray<>(new String[0]);
        if(Objects.nonNull(params.get("answer"))){
            String[] split=params.get("answer").stringValue().split(",");
            for(String s:split){
                answerArray.insertToLast(s);
            }
        }
        Set<ChoiceAnswer> choiceAnswers=new HashSet<>();
        if(Objects.nonNull(params.get("choiceAnswer"))){
            String choiceAnswerJson=params.get("choiceAnswer").stringValue();
            if(!JsonUtils.isEmptyJson(choiceAnswerJson)){
                ArrayJsonParser jsonParser=new ArrayJsonParser(choiceAnswerJson);
                try {
                    StreamCollection<Map<String,JavaDataTyper>> jsonArray = jsonParser.doParser();
                    jsonArray.forEachByOrder((i,d)->{
                        ChoiceAnswer answer=new ChoiceAnswer();
                        answer.setEntityId(d.get("entityId").stringValue());
                        answer.setOptionName(d.get("optionName").stringValue().trim());
                        String detailValue=d.get("detailValue").stringValue();
                        String edit=d.get("edit").stringValue();
                        answer.setImageType(false);
                        if(d.get("isImage").stringValue().equalsIgnoreCase("true")){
                            answer.setImageType(true);
                            if(!edit.equalsIgnoreCase("true")){
                                String accessPath=CoderUtil.hexToString(detailValue,StringEditor.DEFAULT_CHARSET.displayName());
                                DiskFile diskFile=DocumentService.filePathToStorage(configureService.getStringValue(ConfigureItem.DOC_SAVE_PATH),accessPath);
                                detailValue=CoderUtil.stringToHex(diskFile.getFile().getAbsolutePath());
                            }
                        }
                        answer.setDetailValue(detailValue);
                        answer.setChecked(false);
                        if(Objects.nonNull(answerArray.find(h->{
                            return h.equals(answer.getOptionName());
                        }))){
                            answer.setChecked(true);
                        }
                        choiceAnswers.add(answer);
                    });
                } catch (JsonParserException e) {
                    e.printStackTrace();
                }
            }
        }
        return choiceAnswers;
    }

    @Override
    public QueryExpress getCustomerQuery(Map<String, JavaDataTyper> params) {
        QueryExpress queryExpress = super.getCustomerQuery(params);
        if(Objects.nonNull(params.get("existEntityIdArray"))){
            String existEntityIdArray=params.get("existEntityIdArray").stringValue();
            String[] entityIdArray=existEntityIdArray.split("_");
            List<String> entityIdList=new ArrayList<>();
            for(String id:entityIdArray){
                entityIdList.add(id);
            }
            queryExpress=new LinkQueryExpress(queryExpress, LinkQueryExpress.LINK.AND,
                    new ContainQueryExpress<>("entityId", ContainQueryExpress.CONTAINER.NOT_IN,entityIdList));
        }
        return queryExpress;
    }

    @Override
    public OrderBy getDefaultOrderBy(String prefix) {
        return new OrderBy(prefix,"updateTime",OrderBy.TYPE.DESC);
    }

    /*
    public static final boolean isQuestion(List<ExcelData> line){
        return StringEditor.notEmpty(line.get(0).getData().stringValue());
    }
    public static final Question parseQuestion(List<ExcelData> line)throws ParseException {

    }
     */
}
