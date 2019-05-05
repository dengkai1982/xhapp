package kaiyi.app.xhapp.service.bus;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.enums.CapitalType;
import kaiyi.app.xhapp.entity.bus.CourseOrder;
import kaiyi.app.xhapp.entity.bus.enums.CourseOrderStatus;
import kaiyi.app.xhapp.entity.curriculum.Course;
import kaiyi.app.xhapp.entity.log.enums.AmountType;
import kaiyi.app.xhapp.entity.log.enums.BorrowLend;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.curriculum.CourseService;
import kaiyi.app.xhapp.service.log.AmountFlowService;
import kaiyi.puer.db.orm.ServiceException;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

public class CourseOrderServiceImpl extends InjectDao<CourseOrder> implements CourseOrderService {
    private static final long serialVersionUID = -2772504046446165661L;

    @Resource
    private CourseService courseService;
    @Resource
    private AccountService accountService;
    @Resource
    private AmountFlowService amountFlowService;

    @Override
    public CourseOrder generatorOrder(String courseId, String accountId, CapitalType capitalType)throws ServiceException {
        String orderId=randomIdentifier();
        Course course=courseService.findForPrimary(courseId);
        if(Objects.isNull(course)){
            throw ServiceExceptionDefine.entityNotExist;
        }
        Account account=accountService.findForPrimary(accountId);
        if(Objects.isNull(account)){
            throw ServiceExceptionDefine.entityNotExist;
        }
        int price=course.computerCoursePrice(account);
        CourseOrder order=new CourseOrder();
        order.setStatus(CourseOrderStatus.WAIT_PAYMENT);
        order.setAccount(account);
        order.setAmount(price);
        order.setCapitalType(capitalType);
        order.setCourse(course);
        order.setOrderTime(new Date());
        saveObject(order);
        /*if(capitalType.equals(CapitalType.GOLD)){
            if(account.getGold()<price){
                throw ServiceExceptionDefine.goldInsufficient;
            }
            account.setGold(account.getGold()-price);
            amountFlowService.saveNote(account,AmountType.GOLD,orderId,price,BorrowLend.expenditure);
        }*/
        return order;
    }
}
