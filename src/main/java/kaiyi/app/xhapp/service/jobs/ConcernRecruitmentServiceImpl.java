package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.jobs.ConcernRecruitment;
import kaiyi.app.xhapp.entity.jobs.Recruitment;
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
import java.util.Objects;

@Service("concernRecruitmentService")
public class ConcernRecruitmentServiceImpl extends InjectDao<ConcernRecruitment> implements ConcernRecruitmentService{
    @Override
    protected void objectBeforePersistHandler(ConcernRecruitment concernRecruitment, Map<String, JavaDataTyper> params) throws ServiceException {
        concernRecruitment.setCreateTime(new Date());
        QueryExpress query=new CompareQueryExpress("account",CompareQueryExpress.Compare.EQUAL,
                concernRecruitment.getAccount());
        query=new LinkQueryExpress(query, LinkQueryExpress.LINK.AND,new CompareQueryExpress("recruitment",
                CompareQueryExpress.Compare.EQUAL,concernRecruitment.getRecruitment()));
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
                if(field.equals("dataList")&& StreamCollection.assertNotEmpty(entity.getDataList())){
                    StreamCollection<ConcernRecruitment> stream=entity.getDataList();
                    ConcernRecruitment concernRecruitment=stream.get(0);
                    String[] showFieldArray=BeanSyntacticSugar.getFieldStringNotStatic(concernRecruitment.getClass(),
                            concernRecruitment.filterField().getArray());
                    CollectionJsonCreator<ConcernRecruitment> streamCreate=new CollectionJsonCreator<>(stream,
                            showFieldArray, concernRecruitment.jsonFieldReplacePolicy(), new JsonValuePolicy<ConcernRecruitment>() {
                        @Override
                        public JsonCreator getCreator(ConcernRecruitment entity, String field, Object fieldValue) {
                            if(field.equals("createTime")){
                                return new StringJsonCreator(DateTimeUtil.yyyyMMddHHmmss.format(entity.getCreateTime()));
                            }else if(field.equals("account")){
                                Account account=entity.getAccount();
                                String fields[]= BeanSyntacticSugar.getFieldStringNotStatic(account.getClass(),
                                        account.filterField().getArray());
                                ObjectJsonCreator<Account> ojc = new ObjectJsonCreator(account,fields,
                                        account.jsonFieldReplacePolicy(),new DefaultJsonValuePolicy(null));
                                return ojc;
                            }else if(field.equals("recruitment")){
                                Recruitment recruitment=entity.getRecruitment();
                                String fields[]=BeanSyntacticSugar.getFieldStringNotStatic(recruitment.getClass(),
                                        recruitment.filterField().getArray());
                                ObjectJsonCreator<Recruitment> ojc = new ObjectJsonCreator(recruitment,fields,
                                        recruitment.jsonFieldReplacePolicy(),new DefaultJsonValuePolicy(null));
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
