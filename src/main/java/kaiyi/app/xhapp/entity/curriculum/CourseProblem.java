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

@Entity(name=CourseComment.TABLE_NAME)
@PageEntity(showName = "课程问题",entityName = "courseProblem",serviceName = "courseProblemService")
public class CourseProblem extends AbstractEntity {
    public static final String TABLE_NAME="course_problem";
    private static final long serialVersionUID = -4278346481297773802L;
    @PageField(label = "提交人",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "phone")
    private Account submitter;
    @PageField(label = "课程名称",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "name")
    private Course course;
    @IDate
    @PageField(label = "提交时间",type = FieldType.DATETIME)
    private Date commitTime;
    @PageField(label = "评论内容")
    private String content;
    @PageField(label = "回复内容")
    private String reply;
    @IDate
    @PageField(label = "回复时间",type = FieldType.DATETIME)
    private Date replyTime;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="submitter")
    public Account getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Account submitter) {
        this.submitter = submitter;
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
    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }
    @Lob
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @Lob
    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }
}
