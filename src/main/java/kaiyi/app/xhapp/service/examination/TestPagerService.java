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
}
