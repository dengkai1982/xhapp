package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.Course;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("courseService")
public class CourseServiceImpl extends InjectDao<Course> implements CourseService {
    private static final long serialVersionUID = 6202595141917981754L;
}
