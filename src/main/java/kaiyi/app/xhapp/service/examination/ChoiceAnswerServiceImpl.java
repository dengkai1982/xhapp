package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.entity.examination.ChoiceAnswer;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("choiceAnswerService")
public class ChoiceAnswerServiceImpl extends InjectDao<ChoiceAnswer> implements ChoiceAnswerService {
    private static final long serialVersionUID = 4773483210234869853L;
}
