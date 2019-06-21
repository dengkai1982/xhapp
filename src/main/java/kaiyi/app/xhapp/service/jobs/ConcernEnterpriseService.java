package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.ConcernEnterprise;
import kaiyi.app.xhapp.service.CustomerPaginationJson;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface ConcernEnterpriseService extends DatabaseOperator<ConcernEnterprise>,
        DatabaseQuery<ConcernEnterprise>,DatabaseFastOper<ConcernEnterprise>,CustomerPaginationJson {
}
