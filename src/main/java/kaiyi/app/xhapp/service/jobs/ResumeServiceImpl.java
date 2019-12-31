package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.*;
import kaiyi.app.xhapp.entity.pojo.ResumeAndRecruitment;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.time.DateTimeUtil;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.ContainQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Query;
import java.util.*;

@Service("resumeService")
public class ResumeServiceImpl extends InjectDao<Resume> implements ResumeService {
    private static final long serialVersionUID = -3047192250825704891L;
    @Resource
    private PositionService positionService;

    @Override
    protected void objectBeforePersistHandler(Resume resume, Map<String, JavaDataTyper> params) throws ServiceException {
        resume.setCreateTime(new Date());
        resume.setUpdateTime(new Date());
    }

    @Override
    protected void objectBeforeUpdateHandler(Resume resume, Map<String, JavaDataTyper> data) throws ServiceException {
        resume.setUpdateTime(new Date());
    }

    @Override
    public QueryExpress getCustomerQuery(Map<String, JavaDataTyper> params) {
        QueryExpress query=super.getCustomerQuery(params);
        if(Objects.nonNull(params.get("parentPositionId"))){
            String parentPositionId=params.get("parentPositionId").stringValue();
            StreamCollection<Position> positions = positionService.getChild(parentPositionId);
            if(StreamCollection.assertNotEmpty(positions)){
                query=new LinkQueryExpress(query, LinkQueryExpress.LINK.AND,
                        new ContainQueryExpress("position",ContainQueryExpress.CONTAINER.IN,
                                positions.toList()));
            }
        }
        return query;
    }

    @Override
    public void changeUpper(String entityId) {
        Resume resume=findForPrimary(entityId);
        if(Objects.nonNull(resume)){
            resume.setInfoUpper(!resume.isInfoUpper());
            if(!resume.isInfoUpper()){
                em.createQuery("delete from "+getEntityName(ConcernResume.class)+" o " +
                        "where o.resume=:resume").setParameter("resume",resume).executeUpdate();
            }
        }
    }

    @Override
    public void deleteResume(String entityId) {
        Resume resume=new Resume();
        resume.setEntityId(entityId);
        em.createQuery("delete from "+getEntityName(WorkExperience.class)+" o " +
                "where o.resume=:resume").setParameter("resume",resume).executeUpdate();
        em.createQuery("delete from "+getEntityName(ConcernResume.class)+" o " +
                "where o.resume=:resume").setParameter("resume",resume).executeUpdate();
        deleteForPrimary(entityId);
    }

    @Override
    public void batchInfoUpper(StreamArray<String> entityIdArray, boolean infoUpper) {
        StreamCollection<String> entityIdStream=new StreamCollection<>();
        entityIdArray.forEach(h->{
            entityIdStream.add(h);
        });
        em.createQuery("update "+getEntityName(entityClass)+" o set o.infoUpper=:infoUpper where " +
                "o.entityId in(:entityIdArray)").setParameter("infoUpper",infoUpper)
                .setParameter("entityIdArray",entityIdStream.toList()).executeUpdate();
        em.createQuery("delete from "+getEntityName(ConcernResume.class)+" o " +
                "where o.resume in(:resume)").setParameter("resume",entityIdStream.toList()).executeUpdate();
    }
    @Override
    public void batchFrozen(StreamArray<String> entityIdArray, boolean frozen) {
        StreamCollection<String> positions=new StreamCollection<>();
        entityIdArray.forEach(h->{
            positions.add(h);
        });
        em.createQuery("update "+getEntityName(entityClass)+" o set o.frozen=:frozen,o.infoUpper=:infoUpper where " +
                "o.entityId in(:entityIdArray)").setParameter("frozen",frozen)
                .setParameter("infoUpper",!frozen)
                .setParameter("entityIdArray",positions.toList()).executeUpdate();

    }

    @Override
    public ResumeAndRecruitment resumeAndRecruitment(Date startTime, Date endTime) {
        String resumeSql="select p.name ,count(r.position) from "+Resume.TABLE_NAME+" r,"+Position.TABLE_NAME
                +" p where r.position=p.entityId";
        String recruitmentSql="select p.name ,count(r.position) from "+ Recruitment.TABLE_NAME +" r,"+Position.TABLE_NAME
                +" p where r.position=p.entityId ";
        if(Objects.nonNull(startTime)) {
            DateTimeUtil.setStartDay(startTime);
            resumeSql+=" and r.createTime>=:startTime";
            recruitmentSql+=" and r.publishTime>=:startTime";
        }
        if(Objects.nonNull(endTime)){
            DateTimeUtil.setEndDay(endTime);
            resumeSql+=" and r.createTime<=:endTime";
            recruitmentSql+=" and r.publishTime<=:endTime";
        }
        resumeSql+=" group by r.position";
        recruitmentSql+=" group by r.position";
        Query resumeQuery=em.createNativeQuery(resumeSql);
        Query recruitmentQuery=em.createNativeQuery(recruitmentSql);
        if(Objects.nonNull(startTime)) {
            resumeQuery.setParameter("startTime",startTime);
            recruitmentQuery.setParameter("startTime",startTime);
        }
        if(Objects.nonNull(endTime)){
            resumeQuery.setParameter("endTime",endTime);
            recruitmentQuery.setParameter("endTime",endTime);
        }
        ResumeAndRecruitment pojo=new ResumeAndRecruitment();
        Map<String,Integer> resumeMap=new HashMap<>();
        Map<String,Integer> recruitmentMap=new HashMap<>();
        pojo.setResume(resumeMap);
        pojo.setRecruitment(recruitmentMap);
        List<Object[]> results=resumeQuery.getResultList();
        for(Object[] result:results){
            resumeMap.put(result[0].toString(),Integer.parseInt(result[1].toString()));
        }
        results=recruitmentQuery.getResultList();
        for(Object[] result:results){
            recruitmentMap.put(result[0].toString(),Integer.parseInt(result[1].toString()));
        }
        return pojo;
    }
}
