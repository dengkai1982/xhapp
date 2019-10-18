package kaiyi.app.xhapp.entity.curriculum;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name=CourseComment.TABLE_NAME)
@PageEntity(showName = "课程评论",entityName = "courseComment",serviceName = "courseCommentService")
public class CourseComment extends AbstractEntity {
    public static final String TABLE_NAME="course_comment";
    private static final long serialVersionUID = 8869194154150782526L;
    @PageField(label = "评论人",type = FieldType.REFERENCE,tableLength = 140)
    @FieldReference(fieldName = "showAccountName")
    private Account commentator;
    @PageField(label = "课程名称",type = FieldType.REFERENCE,tableLength = 200)
    @FieldReference(fieldName = "name")
    private Course course;
    @PageField(label = "课程评分",type = FieldType.NUMBER,tableLength = 120)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int score;
    @IDate
    @PageField(label = "评论时间",type = FieldType.DATETIME,tableLength = 160)
    private Date commentTime;
    @PageField(label = "评论内容",tableLength = 280)
    private String content;
    @PageField(label = "是否回复",type = FieldType.BOOLEAN,tableLength = 120)
    @FieldBoolean(values={"已回复","未回复"})
    private boolean answer;
    @PageField(label = "回复人",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "realName")
    private VisitorUser replier;
    @IDate
    @PageField(label = "回复时间",type = FieldType.DATETIME,tableLength = 160)
    private Date replyTime;
    @PageField(label = "回复内容",tableLength = 400,formColumnLength = 3)
    private String reply;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="commentator")
    public Account getCommentator() {
        return commentator;
    }

    private String headerImage;

    public void setCommentator(Account commentator) {
        this.commentator = commentator;
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
    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    @Transient
    public String getHeaderImage() {
        return commentator.getHeaderImage();
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
