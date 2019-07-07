package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.entity.examination.ChoiceAnswer;
import kaiyi.app.xhapp.entity.examination.Question;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.db.orm.ServiceException;
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
}
