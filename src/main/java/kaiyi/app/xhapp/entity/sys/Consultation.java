package kaiyi.app.xhapp.entity.sys;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name=Consultation.TABLE_NAME)
@PageEntity(showName = "留言咨询",entityName = "consultation",serviceName = "consultationService")
public class Consultation extends AbstractEntity {
    public static final String TABLE_NAME="consultation";
    @PageField(label = "提问人",type = FieldType.REFERENCE,tableLength = 140)
    @FieldReference(fieldName = "showAccountName")
    private Account commentator;
    @IDate
    @PageField(label = "提问时间",type = FieldType.DATETIME,tableLength = 160)
    private Date commentTime;
    @PageField(label = "提问内容",tableLength = 280)
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
    private String headerImage;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="commentator")
    public Account getCommentator() {
        return commentator;
    }

    public void setCommentator(Account commentator) {
        this.commentator = commentator;
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

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="replier")
    public VisitorUser getReplier() {
        return replier;
    }

    public void setReplier(VisitorUser replier) {
        this.replier = replier;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
    @Transient
    public String getHeaderImage() {
        return commentator.getHeaderImage();
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }
}
