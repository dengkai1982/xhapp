package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.curriculum.Category;
import kaiyi.app.xhapp.entity.examination.ChoiceAnswer;
import kaiyi.app.xhapp.entity.examination.Question;
import kaiyi.app.xhapp.entity.examination.QuestionCategory;
import kaiyi.app.xhapp.entity.examination.enums.QuestionType;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.data.StringEditor;
import kaiyi.puer.commons.poi.ExcelData;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.ContainQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.json.JsonParserException;
import kaiyi.puer.json.JsonUtils;
import kaiyi.puer.json.parse.ArrayJsonParser;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("questionService")
public class QuestionServiceImpl extends InjectDao<Question> implements QuestionService {
    private static final long serialVersionUID = 5129246458030105241L;




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
    public void changeEnable(String entityId) {
        Question question=findForPrimary(entityId);
        if(Objects.nonNull(question)){
            question.setEnable(!question.isEnable());
            updateObject(question);
        }
    }

    @Override
    public boolean isQuestion(List<ExcelData> line) {
        return line.size()>=4;
    }

    private QuestionCategory findCategory(StreamCollection<QuestionCategory> categories,String categoryLevel1,
                                          String categoryLevel2,String categoryLevel3,String categoryLevel4){
        for(QuestionCategory c1:categories){
            if(c1.getName().equals(categoryLevel1)){
                Set<QuestionCategory> c2s=c1.getChildren();
                for(QuestionCategory c2:c2s){
                    if(c2.getName().equals(categoryLevel2)){
                        Set<QuestionCategory> c3s=c2.getChildren();
                        for(QuestionCategory c3:c3s){
                            if(c3.getName().equals(categoryLevel3)){
                                Set<QuestionCategory> c4s=c3.getChildren();
                                for(QuestionCategory c4:c4s){
                                    if(c4.getName().equals(categoryLevel4)){
                                        return c4;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Question parseQuestion(List<ExcelData> line, StreamCollection<QuestionCategory> categories) throws ServiceException {
        Question question=new Question();
        String detail = line.get(0).getData().stringValue();
        String categoryLevel1= line.get(1).getData().stringValue();
        String categoryLevel2= line.get(2).getData().stringValue();
        String categoryLevel3= line.get(3).getData().stringValue();
        String categoryLevel4= line.get(4).getData().stringValue();
        QuestionCategory questionCategory=findCategory(categories,categoryLevel1,categoryLevel2,categoryLevel3,categoryLevel4);
        if(Objects.isNull(categories)){
            throw ServiceExceptionDefine.categoryError;
        }
        String typeName=line.get(5).getData().stringValue();
        QuestionType questionType=QuestionType.getByName(typeName);
        if(Objects.isNull(questionType)){
            throw ServiceExceptionDefine.questionType;
        }
        int score=line.get(6).getData().integerValue(0);
        question.setDetail(detail);
        question.setCategory(questionCategory);
        question.setQuestionType(questionType);
        question.setScore(score);
        question.setEnable(true);
        String analysis=null;
        String answer=null;
        if(questionType.equals(QuestionType.QuestionsAndAnswers)){
            if(line.size()==8){
                analysis=line.get(7).getData().stringValue();
            }
            if(line.size()==9){
                answer=line.get(8).getData().stringValue();
            }
        }else{
            analysis=line.get(7).getData().stringValue();
            answer=line.get(8).getData().stringValue();
        }
        question.setAnalysis(analysis);
        question.setAnswer(answer);
        if(!question.getQuestionType().equals(QuestionType.QuestionsAndAnswers)){
            question.setChoiceAnswers(new HashSet<>());
            String optionName=line.get(9).getData().stringValue();
            String detailValue=line.get(10).getData().stringValue();
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
        String optionName=line.get(0).getData().stringValue();
        String detailValue=line.get(1).getData().stringValue();
        parseChoiceAnswer(question,optionName,detailValue);
    }


    @Override
    protected void objectBeforePersistHandler(Question question, Map<String, JavaDataTyper> params) throws ServiceException {
        updateChoiceAnswer(question,params);
    }

    @Override
    protected void objectBeforeUpdateHandler(Question question, Map<String, JavaDataTyper> params) throws ServiceException {
        updateChoiceAnswer(question,params);
    }

    private void updateChoiceAnswer(Question question, Map<String, JavaDataTyper> params){
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
                        answer.setDetailValue(d.get("detailValue").stringValue());
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


    /*
    public static final boolean isQuestion(List<ExcelData> line){
        return StringEditor.notEmpty(line.get(0).getData().stringValue());
    }
    public static final Question parseQuestion(List<ExcelData> line)throws ParseException {

    }
     */
}
