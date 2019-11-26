package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.jobs.*;
import kaiyi.app.xhapp.entity.pojo.JobStatisticsPojo;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.access.VisitorMenuService;
import kaiyi.app.xhapp.service.log.MenuTooltipService;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.time.DateTimeUtil;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("enterpriseService")
public class EnterpriseServiceImpl extends InjectDao<Enterprise> implements EnterpriseService {
    private static final long serialVersionUID = 916196901732959147L;
    @Resource
    private MenuTooltipService menuTooltipService;
    @Resource
    private VisitorMenuService visitorMenuService;
    @Override
    protected void objectBeforePersistHandler(Enterprise enterprise, Map<String, JavaDataTyper> params) throws ServiceException {
        checkEnterpriseExist(enterprise);
        String menuId="/mgr/personnel/enterprise";
        enterprise.setCreateTime(new Date());
        String parentId=visitorMenuService.findForPrimary(menuId).getParent().getEntityId();
        menuTooltipService.addMenuNotice(parentId);
        menuTooltipService.addMenuNotice(menuId);

    }



    private void checkEnterpriseExist(Enterprise enterprise) throws ServiceException {
        Account owner=enterprise.getOwner();
        String code=enterprise.getCode();
        QueryExpress query=new CompareQueryExpress("owner", CompareQueryExpress.Compare.EQUAL,owner);
        query=new LinkQueryExpress(query, LinkQueryExpress.LINK.AND,new CompareQueryExpress("code", CompareQueryExpress.Compare.EQUAL,code));
        if(exist(query)){
            throw ServiceExceptionDefine.enterpriseExist;
        }
    }

    @Override
    protected void objectBeforeUpdateHandler(Enterprise enterprise, Map<String, JavaDataTyper> data) throws ServiceException {
        Account owner=enterprise.getOwner();
        String code=enterprise.getCode();
        QueryExpress query=new CompareQueryExpress("owner", CompareQueryExpress.Compare.EQUAL,owner);
        query=new LinkQueryExpress(query, LinkQueryExpress.LINK.AND,new CompareQueryExpress("code", CompareQueryExpress.Compare.EQUAL,code));
        StreamCollection<Enterprise> exists=getEntitys(query);
        for(Enterprise exist:exists){
            if(!exist.getEntityId().equals(enterprise.getEntityId())){
                throw ServiceExceptionDefine.enterpriseExist;
            }
        }
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
    //TODO 证书和简历需要增加createTime字段值
    @Override
    public JobStatisticsPojo jobStatistics(Date startTime, Date endTime) {
        //企业信息
        String enterpriseSql="select count(1),count(o.verifyed=true or null),count(o.recommend=true or null),count(o.frozen=true or null) from "
                +getEntityName(Enterprise.class)+" o where o.entityId is not null";
        //简历信息
        String resumeSql="select count(1),count(infoUpper=true or null),count(frozen=true or null) from "
                +getEntityName(Resume.class)+" o where o.entityId is not null";
        //招聘信息
        String recruitmentSql="select count(1),count(infoUpper=true or null),count(recommend=true or null) from "
                +getEntityName(Recruitment.class)+" o where o.entityId is not null";
        //新增证书
        String certSql="select count(1) from "+getEntityName(Certificate.class)+" o where o.entityId is not null";
        //新增招聘信息关注
        String concernRecruitmentSql="select count(1) from "+getEntityName(ConcernRecruitment.class)+" o where o.entityId is not null";
        //新增简历关注
        String concernResumeSql="select count(1) from "+getEntityName(ConcernResume.class)+" o where o.entityId is not null";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(Objects.nonNull(startTime)) {
            DateTimeUtil.setStartDay(startTime);
            enterpriseSql+=" and o.createTime>=:startTime";
            resumeSql+=" and o.createTime>=:startTime";
            recruitmentSql+=" and o.publishTime>=:startTime";
            certSql+=" and o.createTime>=:startTime";
            concernRecruitmentSql+=" and o.createTime>=:startTime";
            concernResumeSql+=" and o.createTime>=:startTime";
        }
        if(Objects.nonNull(endTime)){
            DateTimeUtil.setEndDay(endTime);
            enterpriseSql+=" and o.createTime<=:endTime";
            resumeSql+=" and o.createTime<=:endTime";
            recruitmentSql+=" and o.publishTime<=:endTime";
            certSql+=" and o.createTime<=:endTime";
            concernRecruitmentSql+=" and o.createTime<=:endTime";
            concernResumeSql+=" and o.createTime<=:endTime";
        }
        Query enterpriseQuery=em.createNativeQuery(enterpriseSql);
        Query resumeQuery=em.createNativeQuery(resumeSql);
        Query recruitmentQuery=em.createNativeQuery(recruitmentSql);
        Query certQuery=em.createNativeQuery(certSql);
        Query concernRecruitmentQuery=em.createNativeQuery(concernRecruitmentSql);
        Query concernResumeQuery=em.createNativeQuery(concernResumeSql);
        if(Objects.nonNull(startTime)) {
            enterpriseQuery.setParameter("startTime",startTime);
            resumeQuery.setParameter("startTime",startTime);
            recruitmentQuery.setParameter("startTime",startTime);
            certQuery.setParameter("startTime",startTime);
            concernRecruitmentQuery.setParameter("startTime",startTime);
            concernResumeQuery.setParameter("startTime",startTime);
        }
        if(Objects.nonNull(endTime)){
            enterpriseQuery.setParameter("endTime",endTime);
            resumeQuery.setParameter("endTime",endTime);
            recruitmentQuery.setParameter("endTime",endTime);
            certQuery.setParameter("endTime",endTime);
            concernRecruitmentQuery.setParameter("endTime",endTime);
            concernResumeQuery.setParameter("endTime",endTime);
        }
        JobStatisticsPojo pojo=new JobStatisticsPojo();
        Object[] result=(Object[])enterpriseQuery.getSingleResult();
        pojo.setEnterpriseNumber(Integer.parseInt(result[0].toString()));
        pojo.setVerifyEnterpriseNumber(Integer.parseInt(result[1].toString()));
        pojo.setRecommendEnterpriseNumber(Integer.parseInt(result[2].toString()));
        pojo.setFrozenEnterpriseNumber(Integer.parseInt(result[3].toString()));
        result=(Object[])resumeQuery.getSingleResult();
        pojo.setResumeNumber(Integer.parseInt(result[0].toString()));
        pojo.setPublishResumeNumber(Integer.parseInt(result[1].toString()));
        pojo.setFrozenResumeNumber(Integer.parseInt(result[2].toString()));
        result=(Object[])recruitmentQuery.getSingleResult();
        pojo.setRecruitmentNumber(Integer.parseInt(result[0].toString()));
        pojo.setPublishRecruitmentNumber(Integer.parseInt(result[1].toString()));
        pojo.setRecommendRecruitmentNumber(Integer.parseInt(result[2].toString()));
        Object count=certQuery.getSingleResult();
        pojo.setCertNumber(Objects.isNull(count) ? ZERO:Integer.parseInt(count.toString()));
        count=concernRecruitmentQuery.getSingleResult();
        pojo.setConcernRecruitmentNumber(Objects.isNull(count) ? ZERO:Integer.parseInt(count.toString()));
        count=concernResumeQuery.getSingleResult();
        pojo.setConcernResume(Objects.isNull(count) ? ZERO:Integer.parseInt(count.toString()));
        return pojo;
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
