package kaiyi.app.xhapp.entity.log;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.curriculum.Course;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.FieldReference;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

import javax.persistence.*;
import java.util.Date;

@Entity(name=CourseBrowse.TABLE_NAME)
@Table(indexes = {
        @Index(name="course_browse_index_account",columnList = "account",unique = false),
        @Index(name="course_browse_index_course",columnList = "course",unique = false),
        @Index(name="course_browse_index_createTime",columnList = "createTime",unique = false)
})
@PageEntity(showName = "课程浏览记录",entityName = "courseBrowse",serviceName = "courseBrowseService")
public class CourseBrowse extends AbstractEntity {
    public static final String TABLE_NAME="course_browse";
    private static final long serialVersionUID = -359300686052405693L;
    @PageField(label = "浏览用户",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "showAccountName")
    private Account account;
    @PageField(label = "课程名称",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "name")
    private Course course;
    @IDate
    @PageField(label = "加入时间",type = FieldType.DATETIME)
    private Date createTime;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="account")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="course")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
