package kaiyi.app.xhapp.entity.curriculum;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.FieldReference;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name=ShopCar.TABLE_NAME)
@PageEntity(showName = "购物车",entityName = "shopCar",serviceName = "shopCarService")
public class ShopCar extends AbstractEntity {
    public static final String TABLE_NAME="shop_car";
    private static final long serialVersionUID = 4815479801201203926L;
    @PageField(label = "课程名称",type = FieldType.REFERENCE,tableLength =100)
    @FieldReference(fieldName = "name")
    private Course course;
    @PageField(label = "所属用户",type = FieldType.REFERENCE,tableLength =100)
    @FieldReference(fieldName = "name")
    private Account account;
    @PageField(label = "课程ID")
    private String courseEntityId;
    @IDate
    @PageField(label = "加入时间",type = FieldType.DATETIME,tableLength =100)
    private Date joinTime;
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
    @Temporal(TemporalType.TIMESTAMP)
    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }
    @Transient
    public String getCourseEntityId() {
        if(Objects.nonNull(course)){
            return course.getEntityId();
        }
        return "";
    }

    public void setCourseEntityId(String courseEntityId) {
        this.courseEntityId = courseEntityId;
    }
}
