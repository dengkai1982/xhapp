package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.WorkExperience;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("workExperienceService")
public class WorkExperienceServiceImpl extends InjectDao<WorkExperience> implements WorkExperienceService {
    private static final long serialVersionUID = -4603348633668880163L;
}
