package kaiyi.app.xhapp.entity.bus;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.enums.CapitalType;
import kaiyi.app.xhapp.entity.bus.enums.CourseOrderStatus;
import kaiyi.app.xhapp.entity.curriculum.Course;
import kaiyi.puer.commons.data.ICurrency;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name=CourseOrder.TABLE_NAME)
@PageEntity(showName = "课程订单",entityName = "courseOrder",serviceName = "courseOrderService")
public class CourseOrder extends AbstractEntity {
    public static final String TABLE_NAME="course_order";
    private static final long serialVersionUID = -6133783542944108049L;
    @PageField(label = "课程名称",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "name")
    private Course course;
    @PageField(label = "下单会员",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "phone")
    private Account account;
    @ICurrency
    @PageField(label = "订单金额",type = FieldType.NUMBER,tableLength = 120)
    @FieldNumber(type=FieldNumber.TYPE.INT)
    private int amount;
    @PageField(label = "支付方式",type = FieldType.CHOSEN)
    @FieldChosen
    private CapitalType capitalType;
    @IDate
    @PageField(label = "订单时间",type = FieldType.DATETIME,tableLength = 160)
    private Date orderTime;
    @PageField(label = "订单状态",type = FieldType.CHOSEN)
    @FieldChosen
    private CourseOrderStatus status;
    @IDate
    @PageField(label = "付款时间",type = FieldType.REFERENCE)
    private Date paymentDate;
    @PageField(label = "支付单号",tableLength = 160)
    private String platformOrderId;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="course")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="account")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    @Enumerated(EnumType.STRING)
    public CapitalType getCapitalType() {
        return capitalType;
    }

    public void setCapitalType(CapitalType capitalType) {
        this.capitalType = capitalType;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
    @Enumerated(EnumType.STRING)
    public CourseOrderStatus getStatus() {
        return status;
    }

    public void setStatus(CourseOrderStatus status) {
        this.status = status;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPlatformOrderId() {
        return platformOrderId;
    }

    public void setPlatformOrderId(String platformOrderId) {
        this.platformOrderId = platformOrderId;
    }


}
