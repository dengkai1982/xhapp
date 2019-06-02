package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Enterprise;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface EnterpriseService extends DatabaseFastOper<Enterprise>,DatabaseOperator<Enterprise>,
        DatabaseQuery<Enterprise> {
}
