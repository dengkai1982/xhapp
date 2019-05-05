package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.app.xhapp.entity.curriculum.Course;
import kaiyi.app.xhapp.entity.curriculum.CourseComment;
import kaiyi.app.xhapp.entity.curriculum.CourseProblem;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.access.VisitorUserService;
import kaiyi.puer.db.orm.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Service("courseProblemService")
public class CourseProblemServiceImpl extends InjectDao<CourseProblem> implements CourseProblemService {
    private static final long serialVersionUID = 1216980888639683538L;
    @Resource
    private CourseService courseService;
    @Resource
    private AccountService accountService;
    @Resource
    private VisitorUserService visitorUserSerivce;
    @Override
    public void problem(String courseId, String commentatorId, String content) throws ServiceException {
        Course course=courseService.findForPrimary(courseId);
        Account commentator=accountService.findForPrimary(commentatorId);
        if(Objects.isNull(course)||Objects.isNull(commentator)){
            throw ServiceExceptionDefine.entityNotExist;
        }
        CourseProblem problem=new CourseProblem();
        problem.setSubmitter(commentator);
        problem.setCommitTime(new Date());
        problem.setContent(content);
        problem.setCourse(course);
        saveObject(problem);
    }

    @Override
    public void reply(String entityId, String replierId, String replyContent) throws ServiceException {
        CourseProblem problem=findForPrimary(entityId);
        VisitorUser replier=visitorUserSerivce.findForPrimary(replierId);
        if(Objects.isNull(problem)){
            throw ServiceExceptionDefine.entityNotExist;
        }
        problem.setReplier(replier);
        problem.setReplyTime(new Date());
        problem.setReply(replyContent);
    }
}