package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.entity.examination.Question;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("questionService")
public class QuestionServiceImpl extends InjectDao<Question> implements QuestionService {
    private static final long serialVersionUID = 5129246458030105241L;
}
