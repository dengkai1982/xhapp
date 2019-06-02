package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Enterprise;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("enterpriseService")
public class EnterpriseServiceImpl extends InjectDao<Enterprise> implements EnterpriseService {
    private static final long serialVersionUID = 916196901732959147L;
}
