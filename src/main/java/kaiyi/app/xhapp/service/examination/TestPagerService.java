package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.entity.examination.TestPager;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface TestPagerService extends DatabaseQuery<TestPager> , DatabaseFastOper<TestPager>, DatabaseOperator<TestPager> {
}
