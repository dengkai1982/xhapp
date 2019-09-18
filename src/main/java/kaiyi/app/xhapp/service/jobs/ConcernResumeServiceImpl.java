package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.jobs.ConcernResume;
import kaiyi.app.xhapp.entity.jobs.Enterprise;
import kaiyi.app.xhapp.entity.jobs.Resume;
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

@Service("concernResumeService")
public class ConcernResumeServiceImpl extends InjectDao<ConcernResume> implements ConcernResumeService {
    private static final long serialVersionUID = 2618843499288448960L;

    @Override
    public ObjectJsonCreator<Pagination> getCustomerCreator(Pagination<? extends JsonBuilder> pagination) {
        ObjectJsonCreator<Pagination> creator=new ObjectJsonCreator<Pagination>(pagination, new String[]{
                "totalPage", "currentPage", "dataList","first", "maxResult", "total",
        }, new JsonValuePolicy<Pagination>() {
            @Override
            public JsonCreator getCreator(Pagination entity, String field, Object fieldValue) {
                if(field.equals("dataList")&& StreamCollection.assertNotEmpty(entity.getDataList())){
                    StreamCollection<ConcernResume> stream=entity.getDataList();
                    ConcernResume concernResume=stream.get(0);
                    String[] showFieldArray=BeanSyntacticSugar.getFieldStringNotStatic(concernResume.getClass(),
                            concernResume.filterField().getArray());
                    CollectionJsonCreator<ConcernResume> streamCreate=new CollectionJsonCreator<>(stream,
                            showFieldArray, concernResume.jsonFieldReplacePolicy(), new JsonValuePolicy<ConcernResume>() {
                        @Override
                        public JsonCreator getCreator(ConcernResume entity, String field, Object fieldValue) {
                            if(field.equals("createTime")){
                                return new StringJsonCreator(DateTimeUtil.yyyyMMddHHmmss.format(entity.getCreateTime()));
                            }else if(field.equals("enterprise")){
                                Enterprise enterprise=entity.getEnterprise();
                                String fields[]= BeanSyntacticSugar.getFieldStringNotStatic(enterprise.getClass(),
                                        enterprise.filterField().getArray());
                                ObjectJsonCreator<Account> ojc = new ObjectJsonCreator(enterprise,fields,
                                        enterprise.jsonFieldReplacePolicy(),new DefaultJsonValuePolicy(null));
                                return ojc;
                            }else if(field.equals("resume")){
                                Resume resume=entity.getResume();
                                String fields[]=BeanSyntacticSugar.getFieldStringNotStatic(resume.getClass(),
                                        resume.filterField().getArray());
                                ObjectJsonCreator<Resume> ojc = new ObjectJsonCreator(resume,fields,
                                        resume.jsonFieldReplacePolicy(),new DefaultJsonValuePolicy(null));
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
    protected void objectBeforePersistHandler(ConcernResume concernResume, Map<String, JavaDataTyper> params) throws ServiceException {
        concernResume.setCreateTime(new Date());
        QueryExpress query=new CompareQueryExpress("enterprise",CompareQueryExpress.Compare.EQUAL,
                concernResume.getEnterprise());
        query=new LinkQueryExpress(query, LinkQueryExpress.LINK.AND,new CompareQueryExpress("resume",
                CompareQueryExpress.Compare.EQUAL,concernResume.getResume()));
        if(exist(query)){
            throw ServiceExceptionDefine.resumeConcerned;
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
