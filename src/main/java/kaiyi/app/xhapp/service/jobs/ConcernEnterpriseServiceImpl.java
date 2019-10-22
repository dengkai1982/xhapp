package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.jobs.ConcernEnterprise;
import kaiyi.app.xhapp.entity.jobs.Enterprise;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.bean.BeanSyntacticSugar;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.time.DateTimeUtil;
import kaiyi.puer.db.Pagination;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.json.DefaultJsonValuePolicy;
import kaiyi.puer.json.JsonCreator;
import kaiyi.puer.json.JsonValuePolicy;
import kaiyi.puer.json.creator.CollectionJsonCreator;
import kaiyi.puer.json.creator.JsonBuilder;
import kaiyi.puer.json.creator.ObjectJsonCreator;
import kaiyi.puer.json.creator.StringJsonCreator;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service("concernEnterpriseService")
public class ConcernEnterpriseServiceImpl extends InjectDao<ConcernEnterprise> implements ConcernEnterpriseService {
    private static final long serialVersionUID = -7208672509966810737L;

    @Override
    protected void objectBeforePersistHandler(ConcernEnterprise concernEnterprise, Map<String, JavaDataTyper> params) throws ServiceException {
        concernEnterprise.setCreateTime(new Date());
        QueryExpress query=new CompareQueryExpress("account",CompareQueryExpress.Compare.EQUAL,
                concernEnterprise.getAccount());
        query=new LinkQueryExpress(query, LinkQueryExpress.LINK.AND,new CompareQueryExpress("enterprise",
                CompareQueryExpress.Compare.EQUAL,concernEnterprise.getEnterprise()));
        if(exist(query)){
            throw ServiceExceptionDefine.enterpriseConcerned;
        }
    }

    @Override
    public ObjectJsonCreator<Pagination> getCustomerCreator(Pagination<? extends JsonBuilder> pagination) {
        ObjectJsonCreator<Pagination> creator=new ObjectJsonCreator<Pagination>(pagination, new String[]{
                "totalPage", "currentPage", "dataList","first", "maxResult", "total",
        }, new JsonValuePolicy<Pagination>() {
            @Override
            public JsonCreator getCreator(Pagination entity, String field, Object fieldValue) {
                if(field.equals("dataList")&&StreamCollection.assertNotEmpty(entity.getDataList())){
                    StreamCollection<ConcernEnterprise> stream=entity.getDataList();
                    ConcernEnterprise concernEnterprise=stream.get(0);
                    String[] showFieldArray=BeanSyntacticSugar.getFieldStringNotStatic(concernEnterprise.getClass(),
                            concernEnterprise.filterField().getArray());
                    CollectionJsonCreator<ConcernEnterprise> streamCreate=new CollectionJsonCreator<>(stream,
                            showFieldArray, concernEnterprise.jsonFieldReplacePolicy(), new JsonValuePolicy<ConcernEnterprise>() {
                        @Override
                        public JsonCreator getCreator(ConcernEnterprise entity, String field, Object fieldValue) {
                            if(field.equals("createTime")){
                                return new StringJsonCreator(DateTimeUtil.yyyyMMddHHmmss.format(entity.getCreateTime()));
                            }else if(field.equals("account")){
                                Account account=entity.getAccount();
                                String fields[]=BeanSyntacticSugar.getFieldStringNotStatic(account.getClass(),
                                        account.filterField().getArray());
                                ObjectJsonCreator<Account> ojc = new ObjectJsonCreator(account,fields,
                                        account.jsonFieldReplacePolicy(),new DefaultJsonValuePolicy(null));
                                return ojc;
                            }else if(field.equals("enterprise")){
                                Enterprise enterprise=entity.getEnterprise();
                                String fields[]=BeanSyntacticSugar.getFieldStringNotStatic(enterprise.getClass(),
                                        enterprise.filterField().getArray());
                                ObjectJsonCreator<Enterprise> ojc = new ObjectJsonCreator(enterprise,fields,
                                        enterprise.jsonFieldReplacePolicy(),new DefaultJsonValuePolicy(null));
                                return ojc;
                            }
                            return null;
                        }
                    });
                    return streamCreate;
                }
                return null;
            }
        });
        return creator;
    }
}
