package kaiyi.app.xhapp.service.log;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.curriculum.Course;
import kaiyi.app.xhapp.entity.log.CourseBrowse;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.curriculum.CourseService;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.CompareQueryExpress.Compare;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress.LINK;
import kaiyi.puer.db.query.OrderBy;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Service("courseBrowseService")
public class CourseBrowseServiceImpl extends InjectDao<CourseBrowse> implements CourseBrowseService {
    private static final long serialVersionUID = 4031382280481632475L;
    @Resource
    private AccountService accountService;
    @Resource
    private CourseService courseService;
    @Override
    public void addCourseBrowse(String accountId, String courseId)throws ServiceException {
        Account account=accountService.findForPrimary(accountId);
        Course course=courseService.findForPrimary(courseId);
        if(Objects.isNull(account)||Objects.isNull(course)){
            throw ServiceExceptionDefine.entityNotExist;
        }
        CourseBrowse browse=new CourseBrowse();
        browse.setAccount(account);
        browse.setCourse(course);
        browse.setCreateTime(new Date());
        courseService.addBrowseVolume(courseId);
        saveObject(browse);
        /*QueryExpress query=new CompareQueryExpress("account",Compare.EQUAL,account);
        query=new LinkQueryExpress(query,LINK.AND,new CompareQueryExpress("course",Compare.EQUAL,course));
        CourseBrowse browse=signleQuery(query);
        if(Objects.nonNull(browse)){
            browse.setCreateTime(new Date());
            updateObject(browse);
        }else{
            browse=new CourseBrowse();
            browse.setAccount(account);
            browse.setCourse(course);
            browse.setCreateTime(new Date());
            saveObject(browse);
        }*/
    }
    @Override
    public OrderBy getDefaultOrderBy(String prefix) {
        return new OrderBy(prefix,"createTime",OrderBy.TYPE.DESC);
    }
}
