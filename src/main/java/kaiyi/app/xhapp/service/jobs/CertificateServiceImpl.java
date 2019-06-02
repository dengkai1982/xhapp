package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Certificate;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("certificateService")
public class CertificateServiceImpl extends InjectDao<Certificate> implements CertificateService {
    private static final long serialVersionUID = -4425492314039265166L;
}
