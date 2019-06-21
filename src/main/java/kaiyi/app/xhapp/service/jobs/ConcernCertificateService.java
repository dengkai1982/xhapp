package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.ConcernCertificate;
import kaiyi.app.xhapp.service.CustomerPaginationJson;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface ConcernCertificateService extends DatabaseQuery<ConcernCertificate>,
        DatabaseFastOper<ConcernCertificate>,DatabaseOperator<ConcernCertificate>,CustomerPaginationJson {
}
