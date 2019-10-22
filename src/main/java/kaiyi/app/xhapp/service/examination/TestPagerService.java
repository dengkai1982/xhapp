package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.entity.examination.TestPager;
import kaiyi.app.xhapp.entity.examination.TestPagerQuestion;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface TestPagerService extends DatabaseQuery<TestPager> , DatabaseFastOper<TestPager>, DatabaseOperator<TestPager> {
    /**
     * 获取试卷中包含的试题内容
     * @param testPagerId  试卷ID
     * @return
     */
    StreamCollection<TestPagerQuestion> getTestPagerQuestion(String testPagerId);

    /**
     * 移除试卷中的试题
     * @param testPagerQuestionId
     */
    void removeQuestion(String testPagerQuestionId);
    /**
     * 移除试卷中的试题
     * @param testPagerId
     */
    void clearQuestion(String testPagerId);
    /**
     * 启用或停用试卷
     * @param entityId
     */
    void changeEnable(String entityId);

}
