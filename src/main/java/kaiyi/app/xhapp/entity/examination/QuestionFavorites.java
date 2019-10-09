package kaiyi.app.xhapp.entity.examination;

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

@Entity(name=QuestionFavorites.TABLE_NAME)
@PageEntity(showName = "习题收藏",entityName = "questionFavorites",serviceName = "questionFavoritesService")
public class QuestionFavorites extends AbstractEntity {
    public static final String TABLE_NAME="question_favorites";
    private static final long serialVersionUID = -6584425607698737099L;
    @PageField(label = "收藏用户",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "showAccountName")
    private Account account;
    @PageField(label = "课程名称",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "detail")
    private ExamQuestionItem question;
    @IDate
    @PageField(label = "收藏时间",type = FieldType.DATETIME)
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
    @JoinColumn(name="question")
    public ExamQuestionItem getQuestion() {
        return question;
    }

    public void setQuestion(ExamQuestionItem question) {
        this.question = question;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
