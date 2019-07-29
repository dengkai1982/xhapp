package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.entity.examination.ExamQuestion;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

public interface ExamQuestionService extends DatabaseQuery<ExamQuestion> , DatabaseFastOper<ExamQuestion>{
    /**
     * 根据试卷构建考试题目
     * @param accountId
     * @param testPagerId
     * @throws ServiceException
     */
    ExamQuestion generatorByTestPager(String accountId,String testPagerId)throws ServiceException;

    /**
     * 根据试题类别构建考试题目
     * @param accountId
     * @param categoryId
     * @throws ServiceException
     */
    ExamQuestion generatorByCategory(String accountId,String categoryId)throws ServiceException;

    /**
     * 完成答题
     * @param examQuestionItemId
     * @param answer
     */
    void answerQuestion(String examQuestionItemId,String answer);

}