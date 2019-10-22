package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.CourseMovie;
import kaiyi.app.xhapp.entity.curriculum.MediaLibrary;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.db.orm.ORMException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.CompareQueryExpress.Compare;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service("courseMovieService")
public class CourseMovieServiceImpl extends InjectDao<CourseMovie> implements CourseMovieService {
    private static final long serialVersionUID = 2145669329618026485L;
    @Resource
    private MediaLibraryService mediaLibraryService;

    @PostConstruct
    public void postConstruct(){
        mediaLibraryService.registDatasourceStatusNotify(this);
    }

    @Override
    public void beforeDelete(Object entity) throws ORMException {
        if(entity instanceof MediaLibrary){
            if(exist(new CompareQueryExpress("mediaLibrary",Compare.EQUAL,(MediaLibrary)entity))){
                throw new ORMException("视频文件已被引用,不能删除");
            }
        }
    }
}
