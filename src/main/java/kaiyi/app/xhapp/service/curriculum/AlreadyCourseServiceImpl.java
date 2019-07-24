package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.AlreadyCourse;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("alreadyCourseService")
public class AlreadyCourseServiceImpl extends InjectDao<AlreadyCourse> implements AlreadyCourseService {
    private static final long serialVersionUID = 4384858728942557241L;
}
