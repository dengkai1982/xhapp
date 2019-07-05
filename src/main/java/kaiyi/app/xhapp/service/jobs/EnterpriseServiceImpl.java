package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Enterprise;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("enterpriseService")
public class EnterpriseServiceImpl extends InjectDao<Enterprise> implements EnterpriseService {
    private static final long serialVersionUID = 916196901732959147L;

    @Override
    public void changeRecommend(String entityId) {
        Enterprise enterprise=findForPrimary(entityId);
        if(Objects.nonNull(enterprise)){
            enterprise.setRecommend(!enterprise.isRecommend());
        }
    }

    @Override
    public void changeVerifyed(String entityId) {
        Enterprise enterprise=findForPrimary(entityId);
        if(Objects.nonNull(enterprise)){
            enterprise.setVerifyed(!enterprise.isVerifyed());
        }
    }
}
