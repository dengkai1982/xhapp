package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Recruitment;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("recruitmentService")
public class RecruitmentServiceImpl extends InjectDao<Recruitment> implements RecruitmentService {
    private static final long serialVersionUID = -3756552231797515616L;
}
