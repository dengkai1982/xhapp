package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.Student;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("studentService")
public class StudentServiceImpl extends InjectDao<Student> implements StudentService {
    private static final long serialVersionUID = 5396233466199455527L;
}
