package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Enterprise;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service("enterpriseService")
public class EnterpriseServiceImpl extends InjectDao<Enterprise> implements EnterpriseService {
    private static final long serialVersionUID = 916196901732959147L;

    @Override
    protected void objectBeforeUpdateHandler(Enterprise enterprise, Map<String, JavaDataTyper> data) throws ServiceException {
        enterprise.setVerifyed(false);
    }

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

    @Override
    public QueryExpress getCustomerQuery(Map<String, JavaDataTyper> params) {
        QueryExpress query = super.getCustomerQuery(params);
        if(Objects.nonNull(params.get("onlyEntityId"))){
            query=new LinkQueryExpress(query, LinkQueryExpress.LINK.AND,
                    new CompareQueryExpress("entityId",CompareQueryExpress.Compare.EQUAL,
                            params.get("onlyEntityId").stringValue()));
        }
        return query;
    }
}
