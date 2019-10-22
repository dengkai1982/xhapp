package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.app.xhapp.entity.curriculum.Course;
import kaiyi.app.xhapp.entity.curriculum.CourseProblem;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.access.VisitorMenuService;
import kaiyi.app.xhapp.service.access.VisitorUserService;
import kaiyi.app.xhapp.service.log.MenuTooltipService;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
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
    @Resource
    private MenuTooltipService menuTooltipService;
    @Resource
    private VisitorMenuService visitorMenuService;
    @Override
    public CourseProblem problem(String courseId, String commentatorId, String content) throws ServiceException {
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
        String menuId="/mgr/curriculum/courseProblem";
        String parentId=visitorMenuService.findForPrimary(menuId).getParent().getEntityId();
        menuTooltipService.addMenuNotice(parentId);
        menuTooltipService.addMenuNotice(menuId);
        saveObject(problem);
        return problem;
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
        problem.setAnswer(true);
    }

    @Override
    public QueryExpress getCustomerQuery(Map<String, JavaDataTyper> params) {
        QueryExpress query = super.getCustomerQuery(params);
        if(Objects.nonNull(params.get("onlyEntityId"))){
            query=new LinkQueryExpress(query, LinkQueryExpress.LINK.AND,
                    new CompareQueryExpress("entityId",CompareQueryExpress.Compare.EQUAL,
                            params.get("onlyEntityId").stringValue()));
        }
        return query;
    }
}
