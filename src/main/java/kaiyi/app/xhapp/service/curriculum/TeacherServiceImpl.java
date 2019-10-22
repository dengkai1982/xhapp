package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.Teacher;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("teacherService")
public class TeacherServiceImpl extends InjectDao<Teacher> implements TeacherService {
    private static final long serialVersionUID = 5396233466199455527L;
}
