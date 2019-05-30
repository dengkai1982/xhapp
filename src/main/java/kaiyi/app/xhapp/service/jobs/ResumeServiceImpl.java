package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Resume;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("resumeService")
public class ResumeServiceImpl extends InjectDao<Resume> implements ResumeService {
    private static final long serialVersionUID = -3047192250825704891L;
}
