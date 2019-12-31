package kaiyi.app.xhapp.entity.curriculum;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.curriculum.enums.CourseOrderStatus;
import kaiyi.puer.commons.data.ICurrency;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name=OrderItem.TABLE_NAME)
@PageEntity(showName = "订单子项",entityName = "orderItem",serviceName = "orderItemService")
public class OrderItem extends AbstractEntity {
    public static final String TABLE_NAME="order_item";
    private static final long serialVersionUID = -4660419065673027565L;
    @PageField(label = "所属订单",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "orderId")
    private CourseOrder courseOrder;
    @IDate
    @PageField(label = "订单时间")
    private Date orderTime;
    @PageField(label = "所属订单",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "name")
    private Course course;
    @ICurrency
    @PageField(label = "课程单价",type = FieldType.NUMBER)
    @FieldNumber(type=FieldNumber.TYPE.INT)
    private int price;
    @PageField(label = "购买人",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "phone")
    private Account account;
    @PageField(label = "订单状态",type = FieldType.CHOSEN)
    @FieldChosen
    private CourseOrderStatus status;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="courseOrder")
    public CourseOrder getCourseOrder() {
        return courseOrder;
    }

    public void setCourseOrder(CourseOrder courseOrder) {
        this.courseOrder = courseOrder;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="course")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="account")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    @Enumerated(EnumType.STRING)
    public CourseOrderStatus getStatus() {
        return status;
    }

    public void setStatus(CourseOrderStatus status) {
        this.status = status;
    }
}
