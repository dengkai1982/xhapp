package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Enterprise;
import kaiyi.app.xhapp.entity.jobs.Recruitment;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public void batchVerifyed(StreamArray<String> entityIdArray, boolean verifyed) {
        StreamCollection<String> positions=new StreamCollection<>();
        entityIdArray.forEach(h->{
            positions.add(h);
        });
        em.createQuery("update "+getEntityName(entityClass)+" o set o.verifyed=:verifyed where " +
                "o.entityId in(:entityIdArray)").setParameter("verifyed",verifyed)
                .setParameter("entityIdArray",positions.toList()).executeUpdate();
    }

    @Override
    public void batchRecommend(StreamArray<String> entityIdArray, boolean recommend) {
        StreamCollection<String> positions=new StreamCollection<>();
        entityIdArray.forEach(h->{
            positions.add(h);
        });
        em.createQuery("update "+getEntityName(entityClass)+" o set o.recommend=:recommend where " +
                "o.entityId in(:entityIdArray)").setParameter("recommend",recommend)
                .setParameter("entityIdArray",positions.toList()).executeUpdate();
    }

    @Override
    public void batchFrozen(StreamArray<String> entityIdArray, boolean frozen) {
        StreamCollection<String> entityIdArrayStream=new StreamCollection<>();
        List<Enterprise> enterprises=new ArrayList<>();
        entityIdArray.forEach(h->{
            entityIdArrayStream.add(h);
            Enterprise enterprise=new Enterprise();
            enterprise.setEntityId(h);
            enterprises.add(enterprise);
        });
        em.createQuery("update "+getEntityName(entityClass)+" o set o.frozen=:frozen ,o.verifyed=:verifyed where " +
                "o.entityId in(:entityIdArray)").setParameter("frozen",frozen).setParameter("verifyed",!frozen)
                .setParameter("entityIdArray",entityIdArrayStream.toList()).executeUpdate();


        em.createQuery("update "+getEntityName(Recruitment.class)+" o set o.infoUpper=:infoUpper where " +
                "o.enterprise in(:enterprises)").setParameter("infoUpper",!frozen)
                .setParameter("enterprises",enterprises).executeUpdate();

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
