package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.Chapter;
import kaiyi.app.xhapp.entity.curriculum.CourseMovie;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("chapterService")
public class ChapterServiceImpl extends InjectDao<Chapter> implements  ChapterService{
    private static final long serialVersionUID = 6492630214464043541L;

    @Override
    public void deleteByEntityId(String entityId) {
        Chapter chapter=new Chapter();
        chapter.setEntityId(entityId);
        em.createQuery("delete from "+getEntityName(CourseMovie.class)+" o where o.chapter=:chapter")
                .setParameter("chapter",chapter).executeUpdate();
        deleteForPrimary(entityId);
    }
}
