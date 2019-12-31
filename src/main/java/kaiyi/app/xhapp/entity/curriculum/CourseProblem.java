package kaiyi.app.xhapp.entity.curriculum;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name=CourseProblem.TABLE_NAME)
@PageEntity(showName = "课程问题",entityName = "courseProblem",serviceName = "courseProblemService")
public class CourseProblem extends AbstractEntity {
    public static final String TABLE_NAME="course_problem";
    private static final long serialVersionUID = -4278346481297773802L;
    @PageField(label = "提交人",type = FieldType.REFERENCE,tableLength = 120)
    @FieldReference(fieldName = "showAccountName",only=false)
    private Account submitter;
    @PageField(label = "课程名称",type = FieldType.REFERENCE,tableLength = 200)
    @FieldReference(fieldName = "name",only = false)
    private Course course;
    @IDate
    @PageField(label = "提交时间",type = FieldType.DATETIME,tableLength = 160)
    private Date commitTime;
    @PageField(label = "问题内容",tableLength = 280,formColumnLength = 3)
    private String content;
    @PageField(label = "是否回复",type = FieldType.BOOLEAN)
    @FieldBoolean(values={"已回复","未回复"})
    private boolean answer;
    @PageField(label = "回复人",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "realName")
    private VisitorUser replier;
    @IDate
    @PageField(label = "回复时间",type = FieldType.DATETIME,tableLength = 160)
    private Date replyTime;
    @PageField(label = "回复内容",tableLength = 300,formColumnLength = 3)
    private String reply;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="submitter")
    public Account getSubmitter() {
        return submitter;
    }

    private String headerImage;

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
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="replier")
    public VisitorUser getReplier() {
        return replier;
    }

    public void setReplier(VisitorUser replier) {
        this.replier = replier;
    }

    @Transient
    public String getHeaderImage() {
        return submitter.getHeaderImage();
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}
