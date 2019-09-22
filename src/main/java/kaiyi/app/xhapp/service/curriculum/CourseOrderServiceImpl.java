package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.enums.CapitalType;
import kaiyi.app.xhapp.entity.curriculum.*;
import kaiyi.app.xhapp.entity.curriculum.enums.CourseOrderStatus;
import kaiyi.app.xhapp.entity.curriculum.enums.PayPlatform;
import kaiyi.app.xhapp.entity.distribution.RoyaltySettlement;
import kaiyi.app.xhapp.entity.log.AmountFlow;
import kaiyi.app.xhapp.entity.log.enums.BorrowLend;
import kaiyi.app.xhapp.entity.log.enums.TradeCourse;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.pub.ConfigureService;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.Currency;
import kaiyi.puer.commons.time.ChangeCalendar;
import kaiyi.puer.commons.time.DateTimeRange;
import kaiyi.puer.commons.time.DateTimeUtil;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.ContainQueryExpress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
@Service("courseOrderService")
public class CourseOrderServiceImpl extends InjectDao<CourseOrder> implements CourseOrderService {
    private static final long serialVersionUID = -2772504046446165661L;

    @Resource
    private CourseService courseService;
    @Resource
    private AccountService accountService;
    @Resource
    private PaymentNotifyService paymentNotifyService;
    @Resource
    private AlreadyCourseService alreadyCourseService;
    @Resource
    private ConfigureService configureService;
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
        if(capitalType.equals(CapitalType.GOLD)&&account.getGold()<price){
            throw ServiceExceptionDefine.goldInsufficient;
        }
        order.setStatus(CourseOrderStatus.WAIT_PAYMENT);
        order.setOrderId(orderId);
        order.setAccount(account);
        order.setAmount(price);
        order.setCapitalType(capitalType);
        order.setOrderTime(now);
        saveObject(order);
        if(capitalType.equals(CapitalType.GOLD)){
            accountService.usageGoldPayment(order);
        }else if(capitalType.equals(CapitalType.INTEGRAL)){
            accountService.usageIntegralPayment(order);
        }
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
                em.createQuery("update "+getEntityName(OrderItem.class)+" o set o.courseOrderStatus=:courseOrderStatus " +
                        "where o.courseOrder=:courseOrder").setParameter("courseOrderStatus",courseOrder.getStatus())
                        .setParameter("courseOrder",courseOrder).executeUpdate();
            }
            courseOrder.setPlatformOrderId(paymentNotify.getThirdPartOrderId());
            updateObject(courseOrder);
            em.flush();
            Set<OrderItem> orderItems=courseOrder.getOrderItems();
            Date date=new Date();
            for(OrderItem orderItem:orderItems){
                AlreadyCourse alreadyCourse=new AlreadyCourse();
                alreadyCourse.setCreateTime(date);
                alreadyCourse.setCourse(orderItem.getCourse());
                alreadyCourse.setOwner(courseOrder.getAccount());
                alreadyCourseService.saveObject(alreadyCourse);
                courseService.addBuyVolume(courseOrder.getEntityId());
            }
            //提成结算
            int saleAmount=courseOrder.getAmount();
            Account account=courseOrder.getAccount();
            Account recommend1=account.getRecommend();
            account.addingPersonSaleAmount(saleAmount);
            accountService.updateObject(account);
            Currency amount=Currency.noDecimalBuild(courseOrder.getAmount(),2);
            int royalty=0;
            if(Objects.nonNull(recommend1)){
                if(!recommend1.isInsideMember()){
                    int commissionRate1=configureService.getIntegerValue(ConfigureItem.SALE_LEVEL_COMMISSION_1);
                    royalty=Currency.computerPercentage(commissionRate1,amount.doubleValue()).getNoDecimalPointToInteger();
                    accountService.grantRoyalty(recommend1.getEntityId(),courseOrder.getOrderId(),
                            TradeCourse.SETTLEMENT_ROYALTY,royalty);
                    recommend1.addingTeamSaleAmount(saleAmount);
                    accountService.updateObject(recommend1);
                }
                Account recommend2=recommend1.getRecommend();
                if(Objects.nonNull(recommend2)){
                    if(!recommend2.isInsideMember()){
                        int commissionRate2=configureService.getIntegerValue(ConfigureItem.SALE_LEVEL_COMMISSION_2);
                        royalty=Currency.computerPercentage(commissionRate2,amount.doubleValue()).getNoDecimalPointToInteger();
                        accountService.grantRoyalty(recommend2.getEntityId(),courseOrder.getOrderId(),
                                TradeCourse.SETTLEMENT_ROYALTY,royalty);
                        recommend2.addingTeamSaleAmount(saleAmount);
                        accountService.updateObject(recommend2);
                    }
                }

            }
            //内部员工提成结算
            Account insideMember=accountService.findParentInsideMember(account.getEntityId());
            if(Objects.nonNull(insideMember)){
                int insideRate=configureService.getIntegerValue(ConfigureItem.INSIDE_MEMBER_COMMISSION);
                royalty=Currency.computerPercentage(insideRate,amount.doubleValue()).getNoDecimalPointToInteger();
                accountService.grantRoyalty(insideMember.getEntityId(),courseOrder.getOrderId(),
                        TradeCourse.INSIDE_SETTLEMENT_ROYALTY,royalty);
                accountService.updateObject(insideMember);
            }
        }
        return courseOrder;
    }

    @Override
    public Currency totalPersonSale(String entityId, String date) {
        Account account=new Account();
        account.setEntityId(entityId);
        try {
            DateTimeRange dateRange=parseDateRange(date);
            Object result=em.createQuery("select coalesce(sum(o.amount),0) from "+getEntityName(entityClass)+" o "+
                    "where o.status=:status and o.account=:account and o.paymentDate>=:startDate and " +
                    "o.paymentDate<=:endDate").setParameter("status",CourseOrderStatus.PAYMENTED)
            .setParameter("account",account).setParameter("startDate",dateRange.getStartDate())
            .setParameter("endDate",dateRange.getEndDate()).getSingleResult();
            Long courseOrderCurrenty=Long.valueOf(result.toString());
            result=em.createQuery("select coalesce(sum(o.amount),0) from "+getEntityName(AmountFlow.class)+" o where " +
                    "o.borrowLend=:borrowLend and o.account=:account and o.createTime>=:startDate and " +
                    "o.createTime<=:endDate").setParameter("borrowLend",BorrowLend.income)
                    .setParameter("account",account).setParameter("startDate",dateRange.getStartDate())
                    .setParameter("endDate",dateRange.getEndDate()).getSingleResult();
            courseOrderCurrenty+=Long.valueOf(result.toString());
            return Currency.noDecimalBuild(courseOrderCurrenty,2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Currency.noDecimalBuild(0,2);
    }

    @Override
    public Currency totalTeamSale(String entityId, String date) {
        StreamCollection<Account> accounts=accountService.getTeams(entityId);
        System.out.println(accounts.joinString(m->{
            return m.getEntityId();
        },"\",\""));
        if(accounts.assertNotEmpty()){
            try {
                DateTimeRange dateRange=parseDateRange(date);
                System.out.println(DateTimeUtil.yyyyMMddHHmmss.format(dateRange.getDayStartDate()) +"&"+DateTimeUtil.yyyyMMddHHmmss.format(dateRange.getDayEndDate()));
                Object result=em.createQuery("select coalesce(sum(o.amount),0) from "+getEntityName(entityClass)+" o "+
                        "where o.status=:status and o.account in(:accounts) and o.paymentDate>=:startDate and " +
                        "o.paymentDate<=:endDate").setParameter("status",CourseOrderStatus.PAYMENTED)
                        .setParameter("accounts",accounts).setParameter("startDate",dateRange.getDayStartDate())
                        .setParameter("endDate",dateRange.getDayEndDate()).getSingleResult();
                Long courseOrderCurrenty=Long.valueOf(result.toString());
                //RoyaltySettlement
                result=em.createQuery("select coalesce(sum(o.price),0) from "+getEntityName(RoyaltySettlement.class)+" o where "+
                "o.grant=:grant and o.grantTime>=:startDate and o.grantTime<=:endDate and o.account in(:accounts)")
                .setParameter("grant",Boolean.TRUE).setParameter("startDate",dateRange.getDayStartDate())
                .setParameter("endDate",dateRange.getDayEndDate()).setParameter("accounts",accounts).getSingleResult();

                /*result=em.createQuery("select coalesce(sum(o.amount),0) from "+getEntityName(AmountFlow.class)+" o where " +
                        "o.borrowLend=:borrowLend and o.account in(:accounts) and o.createTime>=:startDate and " +
                        "o.createTime<=:endDate").setParameter("borrowLend",BorrowLend.income)
                        .setParameter("accounts",accounts).setParameter("startDate",dateRange.getDayStartDate())
                        .setParameter("endDate",dateRange.getDayEndDate()).getSingleResult();*/
                courseOrderCurrenty+=Long.valueOf(result.toString());
                return Currency.noDecimalBuild(courseOrderCurrenty,2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return Currency.noDecimalBuild(0,2);
    }

    private DateTimeRange parseDateRange(String date) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
        Date queryDate=sdf.parse(date);
        DateTimeRange range=new DateTimeRange();
        Calendar start=Calendar.getInstance();
        start.setTime(queryDate);
        start.set(Calendar.DAY_OF_MONTH,start.getActualMinimum(Calendar.DAY_OF_MONTH ));
        range.setStartDate(start.getTime());
        Calendar end=Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH,end.getActualMaximum(Calendar.DAY_OF_MONTH ));
        range.setEndDate(end.getTime());
        return range;
        /*
        return getDateTimeRange(s->{
            s.set(Calendar.YEAR, queryDate.getYear());
            s.set(Calendar.MONTH, queryDate.getMonth());
            s.set(Calendar.DAY_OF_MONTH,s.getActualMinimum(Calendar.DAY_OF_MONTH ));
        },e->{
            e.set(Calendar.DAY_OF_MONTH,e.getActualMaximum(Calendar.DAY_OF_MONTH ));
        });*/
    }

    private static DateTimeRange getDateTimeRange(ChangeCalendar start, ChangeCalendar end){
        Calendar c=Calendar.getInstance(Locale.getDefault());
        start.doChange(c);
        DateTimeRange range=new DateTimeRange();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        range.setStartDate(c.getTime());
        end.doChange(c);
        c.set(Calendar.HOUR_OF_DAY,23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        range.setEndDate(c.getTime());
        return range;
    }

    @Override
    public void deleteOrderById(String entityId) {
        CourseOrder order=findForPrimary(entityId);
        if(!order.getStatus().equals(CourseOrderStatus.PAYMENTED)){
            order.setStatus(CourseOrderStatus.DELETED);
            updateObject(order);
        }
    }



    /*
    支付宝回调通知地址
    http://www.xinhongapp.cn/xhapp/app/curriculum/alipaySyncNotify.xhtml
     */
}
