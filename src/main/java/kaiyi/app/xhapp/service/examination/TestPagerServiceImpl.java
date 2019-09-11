package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.entity.curriculum.Category;
import kaiyi.app.xhapp.entity.examination.*;
import kaiyi.app.xhapp.entity.examination.enums.QuestionType;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.ContainQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.json.JsonParserException;
import kaiyi.puer.json.JsonUtils;
import kaiyi.puer.json.parse.ArrayJsonParser;
import org.aspectj.weaver.ast.Test;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.*;

@Service("testPagerService")
public class TestPagerServiceImpl extends InjectDao<TestPager> implements TestPagerService {
    private static final long serialVersionUID = 1768606717840651039L;
    @Resource
    private QuestionCategoryService questionCategoryService;
    @Resource
    private QuestionService questionService;
    @Override
    protected void objectBeforeUpdateHandler(TestPager testPager, Map<String, JavaDataTyper> params) throws ServiceException {
        parseTestPagerQuestion(testPager,params);
        setTestPagerNumber(testPager);

    }
    @Override
    protected void objectBeforePersistHandler(TestPager testPager, Map<String, JavaDataTyper> params) throws ServiceException {
        testPager.setCreateTime(new Date());
        parseTestPagerQuestion(testPager,params);
        setTestPagerNumber(testPager);
    }

    private void setTestPagerNumber(TestPager testPager){
        Set<TestPagerQuestion> questions = testPager.getTestPagerQuestions();
        List<String> questionIdList=new ArrayList<>();
        for(TestPagerQuestion question:questions){
            questionIdList.add(question.getQuestion().getEntityId());
        }
        testPager.setQuestionsAndAnswersNumber(0);
        testPager.setSingleChoiceNumber(0);
        testPager.setMultipleChoiceNumber(0);
        if(StreamCollection.assertNotEmpty(questionIdList)){
            QueryExpress queryExpress=new ContainQueryExpress<>("entityId", ContainQueryExpress.CONTAINER.IN,questionIdList);
            StreamCollection<Question> questionStream=questionService.getEntitys(queryExpress);
            questionStream.forEachByOrder((i,d)->{
                QuestionType type=d.getQuestionType();
                if(type.equals(QuestionType.MultipleChoice)){
                    testPager.setMultipleChoiceNumber(testPager.getMultipleChoiceNumber()+1);
                }else if(type.equals(QuestionType.SingleChoice)){
                    testPager.setSingleChoiceNumber(testPager.getSingleChoiceNumber()+1);
                }else if(type.equals(QuestionType.QuestionsAndAnswers)){
                    testPager.setQuestionsAndAnswersNumber(testPager.getQuestionsAndAnswersNumber()+1);
                }
            });
        }
    }

    private void parseTestPagerQuestion(TestPager testPager, Map<String, JavaDataTyper> params){
        Set<TestPagerQuestion> questions=new HashSet<>();
        if(Objects.nonNull(params.get("testPagerQuestion"))){
            String testPagerQuestion=params.get("testPagerQuestion").stringValue();
            if(!JsonUtils.isEmptyJson(testPagerQuestion)){
                ArrayJsonParser jsonParser=new ArrayJsonParser(testPagerQuestion);
                try {
                    StreamCollection<Map<String,JavaDataTyper>> jsonArray = jsonParser.doParser();
                    jsonArray.forEachByOrder((i,d)->{
                        int score=d.get("score").integerValue(0);
                        int weight=d.get("weight").integerValue(1);
                        String questionId=d.get("questionId").stringValue();
                        Question question=new Question();
                        question.setEntityId(questionId);
                        TestPagerQuestion pagerQuestion=new TestPagerQuestion();
                        pagerQuestion.setQuestion(question);
                        pagerQuestion.setScore(score);
                        pagerQuestion.setWeight(weight);
                        pagerQuestion.setTestPager(testPager);
                        pagerQuestion.setEntityId(randomIdentifier());
                        questions.add(pagerQuestion);
                    });
                } catch (JsonParserException e) {
                    e.printStackTrace();
                }
            }
        }
        testPager.setTestPagerQuestions(questions);
    }

    @Override
    public StreamCollection<TestPagerQuestion> getTestPagerQuestion(String testPagerId) {
        TestPager testPager=new TestPager();
        testPager.setEntityId(testPagerId);
        List<TestPagerQuestion> questions=em.createQuery("select o from "+getEntityName(TestPagerQuestion.class)+" o where " +
                "o.testPager=:testPager order by o.weight desc").setParameter("testPager",testPager).getResultList();
        return new StreamCollection<>(questions);
    }

    @Override
    public void removeQuestion(String entityId) {
        em.createQuery("delete from "+getEntityName(TestPagerQuestion.class)+" o where " +
                "o.entityId=:entityId").setParameter("entityId",entityId).executeUpdate();
    }

    @Override
    public void clearQuestion(String testPagerId) {
        TestPager testPager=new TestPager();
        testPager.setEntityId(testPagerId);
        em.createQuery("delete from "+getEntityName(TestPagerQuestion.class)+" o where " +
                "o.testPager=:testPager").setParameter("testPager",testPager).executeUpdate();
    }

    @Override
    public void changeEnable(String entityId) {
        TestPager testPager=findForPrimary(entityId);
        if(Objects.nonNull(testPager)){
            testPager.setEnable(!testPager.isEnable());
            updateObject(testPager);
        }
    }
    @Override
    public QueryExpress getCustomerQuery(Map<String, JavaDataTyper> params) {
        QueryExpress query = super.getCustomerQuery(params);
        if(Objects.nonNull(params.get("category"))){
            String categoryId=params.get("category").stringValue();
            StreamCollection<QuestionCategory> categories=questionCategoryService.getCategoryAndChildren(categoryId);
            query=new LinkQueryExpress(query,LinkQueryExpress.LINK.AND,new ContainQueryExpress("category",ContainQueryExpress.CONTAINER.IN,
                    categories.toList()));
        }
        return query;
    }
}
