package kaiyi.app.xhapp.entity.curriculum;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.puer.h5ui.annotations.PageEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 已购课程
 */
@Entity(name=Course.TABLE_NAME)
@PageEntity(showName = "已购课程",entityName = "alreadyCourse",serviceName = "alreadyCourseService")
public class AlreadyCourse extends AbstractEntity {
    private static final long serialVersionUID = -3842555833237366231L;

    private Account owner;

    private Course course;

    private Date createTime;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="owner")
    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="course")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    @Temporal(TemporalType.TIME)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
