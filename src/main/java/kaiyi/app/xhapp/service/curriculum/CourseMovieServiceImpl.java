package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.CourseMovie;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("courseMovieService")
public class CourseMovieServiceImpl extends InjectDao<CourseMovie> implements CourseMovieService {
    private static final long serialVersionUID = 2145669329618026485L;
}
