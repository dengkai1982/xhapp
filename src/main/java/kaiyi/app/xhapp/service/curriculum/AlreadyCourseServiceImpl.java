package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.curriculum.AlreadyCourse;
import kaiyi.app.xhapp.entity.curriculum.Course;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Service("alreadyCourseService")
public class AlreadyCourseServiceImpl extends InjectDao<AlreadyCourse> implements AlreadyCourseService {
    private static final long serialVersionUID = 4384858728942557241L;
    @Resource
    private CourseService courseService;
    @Resource
    private AccountService accountService;
    @Override
    public void generator(String accountId,String courseId)throws ServiceException {
        Account account=accountService.findForPrimary(accountId);
        Course course=courseService.findForPrimary(courseId);
        if(Objects.isNull(account)||Objects.isNull(course)){
            throw ServiceExceptionDefine.entityNotExist;
        }
        QueryExpress query=new CompareQueryExpress("owner",CompareQueryExpress.Compare.EQUAL,account);
        query=new LinkQueryExpress(query,LinkQueryExpress.LINK.AND,
                new CompareQueryExpress("course",CompareQueryExpress.Compare.EQUAL,course));
        if(exist(query)){
            throw ServiceExceptionDefine.alreadyCourseExist;
        }
        if(course.computerCoursePrice(account)>0){
            throw ServiceExceptionDefine.courseNotFree;
        }
        AlreadyCourse alreadyCourse=new AlreadyCourse();
        alreadyCourse.setCreateTime(new Date());
        alreadyCourse.setCourse(course);
        alreadyCourse.setOwner(account);
        alreadyCourse.setFreeCourse(true);
        alreadyCourse.setPrice(0);
        saveObject(alreadyCourse);
        courseService.addBuyVolume(course.getEntityId());
    }
}
