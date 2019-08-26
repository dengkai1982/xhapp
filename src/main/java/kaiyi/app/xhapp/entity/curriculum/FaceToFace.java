package kaiyi.app.xhapp.entity.curriculum;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name=FaceToFace.TABLE_NAME)
@PageEntity(showName = "预约面授",entityName = "faceToFace",serviceName = "faceToFaceService")
public class FaceToFace extends AbstractEntity {

    public static final String TABLE_NAME="face_to_face";
    private static final long serialVersionUID = 382207949594296164L;
    @PageField(label = "提交人",type = FieldType.REFERENCE,tableLength = 140)
    @FieldReference(fieldName = "showAccountName")
    private Account account;
    @PageField(label = "预约人")
    private String name;
    @PageField(label = "联系电话")
    private String phone;
    @PageField(label = "预约课程")
    private String course;
    @IDate(pattern = "yyyy-MM-dd")
    @PageField(label = "预约时间",type = FieldType.DATE,tableLength = 160)
    private Date faceTime;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFaceTime() {
        return faceTime;
    }

    public void setFaceTime(Date faceTime) {
        this.faceTime = faceTime;
    }

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="replier")
    public VisitorUser getReplier() {
        return replier;
    }

    public void setReplier(VisitorUser replier) {
        this.replier = replier;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }
    @Lob
    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public FaceToFace() {
    }

    public FaceToFace(Account account,String name, String phone, String course, Date faceTime) {
        this.name = name;
        this.phone = phone;
        this.course = course;
        this.faceTime = faceTime;
        this.account=account;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="account")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
