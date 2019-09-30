package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Position;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.db.query.*;
import kaiyi.puer.db.query.CompareQueryExpress.Compare;
import kaiyi.puer.db.query.LinkQueryExpress.LINK;
import kaiyi.puer.db.query.NullQueryExpress.NullCondition;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service("positionService")
public class PositionServiceImpl extends InjectDao<Position> implements PositionService {
    private static final long serialVersionUID = -974487672441226990L;


    @Override
    public QueryExpress getCustomerQuery(Map<String, JavaDataTyper> params) {
        QueryExpress queryExpress = super.getCustomerQuery(params);
        if(existParameter(params,"parent")){
            Position parent=new Position();
            parent.setEntityId(params.get("parent").stringValue());
            queryExpress=new LinkQueryExpress(queryExpress,LINK.AND,new CompareQueryExpress("parent",
                    Compare.EQUAL,parent));
        }
        if(existParameter(params,"topParent")){
            queryExpress=new LinkQueryExpress(queryExpress,LINK.AND,new NullQueryExpress("parent",
                    NullCondition.IS_NULL));
        }
        return queryExpress;

    }

    @Override
    public StreamCollection<Position> getChild(String districtId) {
        Position parent=new Position();
        parent.setEntityId(districtId);
        QueryExpress query=new CompareQueryExpress("parent",Compare.EQUAL,parent);
        OrderBy order=new OrderBy(query.getPrefix(),"entityId");
        return getEntitys(query,order);
    }

    @Override
    public StreamCollection<Position> getSameLevel(String districtId) {
        Position position=findForPrimary(districtId);
        if(Objects.nonNull(position)){
            return getChild(position.getParent().getEntityId());
        }
        return new StreamCollection<>();
    }

    @Override
    public void newJobs(String name, String parentName) {
        Position parent=signleQuery("name",parentName);
        Position newJobs=new Position();
        newJobs.setName(name);
        if(Objects.nonNull(parent)){
            newJobs.setParent(parent);
            newJobs.setLevel(parent.getLevel()+1);
        }else{
            newJobs.setLevel(0);
        }
        saveObject(newJobs);
    }

    @Override
    public void changeShowable(String entityId) {
        Position position=findForPrimary(entityId);
        if(Objects.nonNull(position)){
            position.setShowable(!position.isShowable());
        }
    }

    @Override
    public void batchShowOrHidden(StreamArray<String> entityIdArray, boolean showable) {
        StreamCollection<String> positions=new StreamCollection<>();
        entityIdArray.forEach(h->{
            positions.add(h);
        });
        em.createQuery("update "+getEntityName(entityClass)+" o set o.showable=:showable where " +
                "o.entityId in(:entityIdArray)").setParameter("showable",showable)
                .setParameter("entityIdArray",positions.toList()).executeUpdate();
    }


    @Override

    protected String[] getFormElementHiddenParams() {
        return new String[]{"parent","topParent"};
    }
}
