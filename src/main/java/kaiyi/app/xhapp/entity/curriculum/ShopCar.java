package kaiyi.app.xhapp.entity.curriculum;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.puer.h5ui.annotations.PageEntity;

import javax.persistence.*;
import java.util.Date;

@Entity(name=ShopCar.TABLE_NAME)
@PageEntity(showName = "购物车",entityName = "chapter",serviceName = "chapterService")
public class ShopCar extends AbstractEntity {
    public static final String TABLE_NAME="shop_car";
    private static final long serialVersionUID = 4815479801201203926L;

    private Course course;

    private Account account;

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
}
