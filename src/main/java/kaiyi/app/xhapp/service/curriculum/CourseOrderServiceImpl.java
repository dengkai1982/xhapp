package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.enums.CapitalType;
import kaiyi.app.xhapp.entity.curriculum.CourseOrder;
import kaiyi.app.xhapp.entity.curriculum.OrderItem;
import kaiyi.app.xhapp.entity.curriculum.PaymentNotify;
import kaiyi.app.xhapp.entity.curriculum.enums.CourseOrderStatus;
import kaiyi.app.xhapp.entity.curriculum.Course;
import kaiyi.app.xhapp.entity.curriculum.enums.PayPlatform;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.log.AmountFlowService;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.ContainQueryExpress;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class CourseOrderServiceImpl extends InjectDao<CourseOrder> implements CourseOrderService {
    private static final long serialVersionUID = -2772504046446165661L;

    @Resource
    private CourseService courseService;
    @Resource
    private AccountService accountService;
    @Resource
    private AmountFlowService amountFlowService;
    @Resource
    private PaymentNotifyService paymentNotifyService;
    @Override
    public CourseOrder generatorOrder(StreamCollection<String> courseIdStream, String accountId, CapitalType capitalType)throws ServiceException {
        String orderId=randomIdentifier();
        /*Course course=courseService.findForPrimary(courseId);
        if(Objects.isNull(course)){
            throw ServiceExceptionDefine.entityNotExist;
        }*/
        Account account=accountService.findForPrimary(accountId);
        if(Objects.isNull(account)){
            throw ServiceExceptionDefine.entityNotExist;
        }
        //int price=course.computerCoursePrice(account);
        CourseOrder order=new CourseOrder();
        Set<OrderItem> items=new HashSet<>();
        order.setOrderItems(items);
        int price=0;
        Date now=new Date();
        StreamCollection<Course> courses=courseService.getEntitys(new ContainQueryExpress<String>("entityId",ContainQueryExpress.CONTAINER.IN,
                courseIdStream.toList()));
        for(Course course:courses){
            OrderItem orderItem=new OrderItem();
            orderItem.setAccount(account);
            orderItem.setCourse(course);
            orderItem.setCourseOrder(order);
            orderItem.setEntityId(randomIdentifier());
            orderItem.setOrderTime(now);
            orderItem.setPrice(course.computerCoursePrice(account));
            orderItem.setCourseOrderStatus(CourseOrderStatus.WAIT_PAYMENT);
            price+=orderItem.getPrice();
            items.add(orderItem);
        }
        order.setStatus(CourseOrderStatus.WAIT_PAYMENT);
        order.setOrderId(orderId);
        order.setAccount(account);
        order.setAmount(price);
        order.setCapitalType(capitalType);
        order.setOrderTime(now);
        saveObject(order);
        return order;
    }

    @Override
    public CourseOrder paymentSaleOrder(PaymentNotify paymentNotify) {
        String orderId=paymentNotify.getOrderId();
        CourseOrder courseOrder=signleQuery("orderId",orderId);
        if(Objects.nonNull(courseOrder)&&courseOrder.getStatus().equals(CourseOrderStatus.WAIT_PAYMENT)){
            if(paymentNotify.getPlatform().equals(PayPlatform.INSIDE)){
                paymentNotify.setThirdPartOrderId(randomIdentifier());
            }
            paymentNotifyService.saveObject(paymentNotify);
            if(paymentNotify.isSuccess()){
                courseOrder.setStatus(CourseOrderStatus.PAYMENTED);
                courseOrder.setPaymentDate(new Date());
                em.createQuery("update "+getEntityName(OrderItem.class)+" o set o.orderStatus=:orderStatus " +
                        "where o.courseOrder=:courseOrder").setParameter("courseOrderStatus",courseOrder.getStatus())
                        .setParameter("courseOrder",courseOrder).executeUpdate();
            }
            courseOrder.setPlatformOrderId(paymentNotify.getThirdPartOrderId());
            updateObject(courseOrder);
            em.flush();
        }
        return courseOrder;
    }
}
