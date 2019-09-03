package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.examination.*;
import kaiyi.app.xhapp.entity.examination.enums.QuestionType;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.pub.ConfigureService;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.ContainQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("examQuestionService")
public class ExamQuestionServiceImpl extends InjectDao<ExamQuestion> implements ExamQuestionService {

    private static final long serialVersionUID = -5053728156600061289L;

    @Resource
    private AccountService accountService;
    @Resource
    private TestPagerService testPagerService;
    @Resource
    private QuestionCategoryService questionCategoryService;
    @Resource
    private QuestionService questionService;
    @Resource
    private ExamQuestionItemService examQuestionItemService;
    @Resource
    private SimulationCategoryService simulationCategoryService;
    @Override
    public ExamQuestion generatorByTestPager(String accountId, String testPagerId) throws ServiceException {
        TestPager testPager=testPagerService.findForPrimary(testPagerId);
        Account account=accountService.findForPrimary(accountId);
        if(Objects.isNull(account)||Objects.isNull(testPager)){
            throw ServiceExceptionDefine.entityNotExist;
        }
        String name=testPager.getName();
        checkExamQuestionExist(name,account);
        ExamQuestion exam=new ExamQuestion(testPager.getName(),account,testPager.getSingleChoiceNumber(),
                testPager.getMultipleChoiceNumber(),testPager.getQuestionsAndAnswersNumber());
        StreamCollection<TestPagerQuestion> testPagerQuestions=testPagerService.getTestPagerQuestion(testPager.getEntityId());
        Set<ExamQuestionItem> items=new HashSet<>();
        for(TestPagerQuestion tpq:testPagerQuestions){
            Question question=tpq.getQuestion();
            ExamQuestionItem item=createExamQuestionItem(exam,question,tpq.getScore(),tpq.getWeight(),account);
            items.add(item);
        }
        exam.setQuestionItems(items);
        saveObject(exam);
        return exam;
    }

    private ExamQuestionItem createExamQuestionItem(ExamQuestion exam,Question question,int score,int weight,Account owner){
        ExamQuestionItem item=new ExamQuestionItem();
        item.setEntityId(randomIdentifier());
        item.setAnalysis(question.getAnalysis());
        item.setDetail(question.getDetail());
        item.setExamQuestion(exam);
        item.setQuestionType(question.getQuestionType());
        item.setScore(score);
        item.setStandardAnswer(question.getAnswer());
        item.setWeight(weight);
        item.setOwner(owner);
        Set<ChoiceAnswer> choiceAnswers = question.getChoiceAnswers();
        Set<ExamChoiceAnswer> examChoiceAnswers=new HashSet<>();
        for(ChoiceAnswer answer:choiceAnswers){
            ExamChoiceAnswer examChoiceAnswer=new ExamChoiceAnswer();
            examChoiceAnswer.setOptionName(answer.getOptionName());
            examChoiceAnswer.setDetailValue(answer.getDetailValue());
            examChoiceAnswer.setEntityId(randomIdentifier());
            examChoiceAnswer.setExamQuestionItem(item);
            examChoiceAnswers.add(examChoiceAnswer);
        }
        item.setChoiceAnswers(examChoiceAnswers);
        return item;
    }
    @Override
    public ExamQuestion generatorBySimulationCategory(String accountId, String questionCategoryId) throws ServiceException {
        StreamCollection<SimulationCategory> categorys=simulationCategoryService.getCategoryAndChildren(questionCategoryId);
        Account account=accountService.findForPrimary(accountId);
        if(Objects.isNull(account)|| categorys.assertEmpty()){
            throw ServiceExceptionDefine.entityNotExist;
        }
        SimulationCategory firstCategory=categorys.get(0);
        String name=firstCategory.getName()+"随机练习题";
        checkExamQuestionExist(name,account);
        int singleNumber=firstCategory.getSingleNumber();
        int multipleNumber=firstCategory.getMultipleNumber();
        int answerNumber=firstCategory.getAnswerNumber();
        StreamCollection<Question> singleQuestion=getRandomQuestionForSimulationCategory(categorys,QuestionType.SingleChoice,singleNumber);
        StreamCollection<Question> multipleQuestion=getRandomQuestionForSimulationCategory(categorys,QuestionType.MultipleChoice,multipleNumber);
        StreamCollection<Question> answerQuestion=getRandomQuestionForSimulationCategory(categorys,QuestionType.QuestionsAndAnswers,answerNumber);
        int weight=singleQuestion.size()+multipleQuestion.size()+answerQuestion.size();
        ExamQuestion exam=new ExamQuestion(name,account,singleQuestion.size(),
                multipleQuestion.size(),answerQuestion.size());
        Set<ExamQuestionItem> items=new HashSet<>();
        for(Question question:singleQuestion){
            ExamQuestionItem item=createExamQuestionItem(exam,question,question.getScore(),weight,account);
            weight--;
            items.add(item);
        }
        for(Question question:multipleQuestion){
            ExamQuestionItem item=createExamQuestionItem(exam,question,question.getScore(),weight,account);
            weight--;
            items.add(item);
        }
        for(Question question:answerQuestion){
            ExamQuestionItem item=createExamQuestionItem(exam,question,question.getScore(),weight,account);
            weight--;
            items.add(item);
        }
        exam.setQuestionItems(items);
        saveObject(exam);
        return exam;
    }
    @Override
    public ExamQuestion generatorByCategory(String accountId, String categoryId) throws ServiceException {
        StreamCollection<QuestionCategory> categorys=questionCategoryService.getCategoryAndChildren(categoryId);
        Account account=accountService.findForPrimary(accountId);
        if(Objects.isNull(account)|| categorys.assertEmpty()){
            throw ServiceExceptionDefine.entityNotExist;
        }
        QuestionCategory firstCategory=categorys.get(0);
        String name=firstCategory.getName()+"随机练习题";
        checkExamQuestionExist(name,account);
        int singleNumber=firstCategory.getSingleNumber();
        int multipleNumber=firstCategory.getMultipleNumber();
        int answerNumber=firstCategory.getAnswerNumber();
        StreamCollection<Question> singleQuestion=getRandomQuestion(categorys,QuestionType.SingleChoice,singleNumber);
        StreamCollection<Question> multipleQuestion=getRandomQuestion(categorys,QuestionType.MultipleChoice,multipleNumber);
        StreamCollection<Question> answerQuestion=getRandomQuestion(categorys,QuestionType.QuestionsAndAnswers,answerNumber);
        int weight=singleQuestion.size()+multipleQuestion.size()+answerQuestion.size();
        ExamQuestion exam=new ExamQuestion(name,account,singleQuestion.size(),
                multipleQuestion.size(),answerQuestion.size());
        Set<ExamQuestionItem> items=new HashSet<>();
        for(Question question:singleQuestion){
            ExamQuestionItem item=createExamQuestionItem(exam,question,question.getScore(),weight,account);
            weight--;
            items.add(item);
        }
        for(Question question:multipleQuestion){
            ExamQuestionItem item=createExamQuestionItem(exam,question,question.getScore(),weight,account);
            weight--;
            items.add(item);
        }
        for(Question question:answerQuestion){
            ExamQuestionItem item=createExamQuestionItem(exam,question,question.getScore(),weight,account);
            weight--;
            items.add(item);
        }
        exam.setQuestionItems(items);
        saveObject(exam);
        return exam;
    }



    @Override
    public void answerQuestion(String examQuestionItemId, String answer) {
        ExamQuestionItem item=examQuestionItemService.finishAnswer(examQuestionItemId,answer);
        if(Objects.nonNull(item)){
            String examQuestionId=item.getExamQuestion().getEntityId();
            ExamQuestion examQuestion=findForPrimary(examQuestionId);
            examQuestion.setFinishNumber(examQuestion.getFinishNumber()+1);
            if(item.isResult()){
                examQuestion.setScore(examQuestion.getScore()+item.getScore());
            }
            int totalNumber=examQuestion.getSingleChoiceNumber()+examQuestion.getMultipleChoiceNumber()
                    +examQuestion.getQuestionsAndAnswersNumber();
            examQuestion.setFinished(examQuestionItemService.isFinished(totalNumber,examQuestionId));
            updateObject(examQuestion);
        }
    }

    private void checkExamQuestionExist(String name,Account account)throws ServiceException{
        QueryExpress existQuery=new CompareQueryExpress("name", CompareQueryExpress.Compare.EQUAL,name);
        existQuery=new LinkQueryExpress(existQuery, LinkQueryExpress.LINK.AND,new CompareQueryExpress("account", CompareQueryExpress.Compare.EQUAL,
                account));
        existQuery=new LinkQueryExpress(existQuery, LinkQueryExpress.LINK.AND,new CompareQueryExpress("finished", CompareQueryExpress.Compare.EQUAL,
                Boolean.FALSE));
        if(exist(existQuery)){
            throw ServiceExceptionDefine.examQuestionExist;
        }
    }
    /**
     * 随机获取练习题
     * @param categorys 类别ID
     * @param questionType 试题类型
     * @param totalScore 总分值
     * @return
     */
    private StreamCollection<Question> getRandomQuestionForSimulationCategory(StreamCollection<SimulationCategory> categorys, QuestionType questionType,int totalScore){
        StreamCollection<Question> result=new StreamCollection<>();
        QueryExpress query=new ContainQueryExpress("simulationCategory",ContainQueryExpress.CONTAINER.IN,categorys.toList());
        query=new LinkQueryExpress(query, LinkQueryExpress.LINK.AND,new CompareQueryExpress("questionType", CompareQueryExpress.Compare.EQUAL,
                questionType));
        StreamCollection<Question> questions=questionService.getEntitys(query);
        Random random=new Random();
        getQuestionByRandom(result,questions,random,totalScore);
        return result;
    }
    /**
     * 随机获取练习题
     * @param categorys 类别ID
     * @param questionType 试题类型
     * @param totalScore 总分值
     * @return
     */
    private StreamCollection<Question> getRandomQuestion(StreamCollection<QuestionCategory> categorys, QuestionType questionType,int totalScore){
        StreamCollection<Question> result=new StreamCollection<>();
        QueryExpress query=new ContainQueryExpress<QuestionCategory>("category",ContainQueryExpress.CONTAINER.IN,categorys.toList());
        query=new LinkQueryExpress(query, LinkQueryExpress.LINK.AND,new CompareQueryExpress("questionType", CompareQueryExpress.Compare.EQUAL,
                questionType));
        StreamCollection<Question> questions=questionService.getEntitys(query);
        Random random=new Random();
        getQuestionByRandom(result,questions,random,totalScore);
        return result;
    }

    private void getQuestionByRandom(StreamCollection<Question> result,StreamCollection<Question> questions,Random random,
                                     int totalScore){
        int currentScore=getTotalScore(result);
        if(currentScore==totalScore||StreamCollection.assertEmpty(questions)){
            return;
        }
        int surplusScore=totalScore-currentScore;
        int number=random.nextInt(questions.size());
        Question question=questions.get(number);
        if(surplusScore>=question.getScore()){
            result.add(question);
            questions.removeIf(p->{
                return p.getEntityId().equals(question.getEntityId());
            });
            getQuestionByRandom(result,questions,random,totalScore);
        }else{
            for(Question q:questions){
                if(q.getScore()==surplusScore){
                    result.add(q);
                    return;
                }
            }
        }
    }

    private int getTotalScore(StreamCollection<Question> result){
        int totalScore=0;
        for(Question question:result){
            totalScore+=question.getScore();
        }
        return totalScore;
    }

}
