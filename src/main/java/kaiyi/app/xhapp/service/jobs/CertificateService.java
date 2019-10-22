package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Certificate;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface CertificateService extends DatabaseQuery<Certificate>,
        DatabaseFastOper<Certificate>,DatabaseOperator<Certificate> {

    void deleteCertificate(String entityId);
}
