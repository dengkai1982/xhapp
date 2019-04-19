package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.curriculum.Course;
import kaiyi.app.xhapp.entity.curriculum.ShopCar;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.CompareQueryExpress.*;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress.*;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Service("shopCarService")
public class ShopCarServiceImpl extends InjectDao<ShopCar> implements ShopCarService{
    private static final long serialVersionUID = 8624569957198206779L;
    @Resource
    private CourseService courseService;
    @Resource
    private AccountService accountService;
    @Override
    public ShopCar joinToShopCar(String courseId, String accountId)throws ServiceException {
        Course course=courseService.findForPrimary(courseId);
        Account account=accountService.findForPrimary(accountId);
        if(Objects.isNull(course)||Objects.isNull(account)){
            throw ServiceExceptionDefine.userNotExist;
        }
        QueryExpress query=new CompareQueryExpress("course",Compare.EQUAL,course);
        query=new LinkQueryExpress(query,LINK.AND,new CompareQueryExpress("account",Compare.EQUAL,account));
        ShopCar exist=signleQuery(query);
        if(!exist(query)){
            ShopCar shopCar=new ShopCar();
            shopCar.setCourse(course);
            shopCar.setAccount(account);
            shopCar.setJoinTime(new Date());
            saveObject(shopCar);
            return shopCar;
        }
        return exist;
    }
}
