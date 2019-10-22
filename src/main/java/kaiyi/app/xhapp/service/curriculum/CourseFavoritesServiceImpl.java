package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.curriculum.Course;
import kaiyi.app.xhapp.entity.curriculum.CourseFavorites;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.CompareQueryExpress.Compare;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress.LINK;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Service("courseFavoritesService")
public class CourseFavoritesServiceImpl extends InjectDao<CourseFavorites> implements CourseFavoritesService {
    @Resource
    private AccountService accountService;
    @Resource
    private CourseService courseService;
    @Override
    public CourseFavorites addFavorites(String accountId, String courseId)throws ServiceException {
        Account account=accountService.findForPrimary(accountId);
        Course course=courseService.findForPrimary(courseId);
        if(Objects.isNull(account)||Objects.isNull(course)){
            throw ServiceExceptionDefine.entityNotExist;
        }
        QueryExpress query=new CompareQueryExpress("account",Compare.EQUAL,account);
        query=new LinkQueryExpress(query,LINK.AND,new CompareQueryExpress("course",Compare.EQUAL,course));
        CourseFavorites favorites=signleQuery(query);
        if(Objects.nonNull(favorites)){
            favorites.setCreateTime(new Date());
            updateObject(favorites);
        }else{
            favorites=new CourseFavorites();
            favorites.setAccount(account);
            favorites.setCourse(course);
            favorites.setCreateTime(new Date());
            saveObject(favorites);
        }
        return favorites;
    }
}
