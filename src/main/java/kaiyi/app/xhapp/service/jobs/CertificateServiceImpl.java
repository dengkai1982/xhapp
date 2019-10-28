package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Certificate;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.db.orm.ServiceException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service("certificateService")
public class CertificateServiceImpl extends InjectDao<Certificate> implements CertificateService {
    private static final long serialVersionUID = -4425492314039265166L;

    @Override
    public void deleteCertificate(String entityId) {
        deleteForPrimary(entityId);
    }

    @Override
    protected void objectBeforePersistHandler(Certificate certificate, Map<String, JavaDataTyper> params) throws ServiceException {
        certificate.setCreateTime(new Date());
    }
}
