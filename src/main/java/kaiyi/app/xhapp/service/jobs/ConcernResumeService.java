package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.ConcernResume;
import kaiyi.app.xhapp.service.CustomerPaginationJson;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface ConcernResumeService extends DatabaseQuery<ConcernResume>,
        DatabaseFastOper<ConcernResume>, DatabaseOperator<ConcernResume>, CustomerPaginationJson {
}
