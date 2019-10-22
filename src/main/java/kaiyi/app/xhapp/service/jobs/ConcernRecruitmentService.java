package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.ConcernRecruitment;
import kaiyi.app.xhapp.service.CustomerPaginationJson;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface ConcernRecruitmentService extends DatabaseOperator<ConcernRecruitment>, DatabaseFastOper<ConcernRecruitment>,
        DatabaseQuery<ConcernRecruitment>, CustomerPaginationJson {
}
