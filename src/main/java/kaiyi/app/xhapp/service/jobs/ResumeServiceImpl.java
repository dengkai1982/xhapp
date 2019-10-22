package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.ConcernResume;
import kaiyi.app.xhapp.entity.jobs.Position;
import kaiyi.app.xhapp.entity.jobs.Resume;
import kaiyi.app.xhapp.entity.jobs.WorkExperience;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.ContainQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

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
        StreamCollection<String> positions=new StreamCollection<>();
        entityIdArray.forEach(h->{
            positions.add(h);
        });
        em.createQuery("update "+getEntityName(entityClass)+" o set o.infoUpper=:infoUpper where " +
                "o.entityId in(:entityIdArray)").setParameter("infoUpper",infoUpper)
                .setParameter("entityIdArray",positions.toList()).executeUpdate();
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
}
