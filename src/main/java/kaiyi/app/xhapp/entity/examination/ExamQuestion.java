package kaiyi.app.xhapp.entity.examination;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name= ExamQuestion.TABLE_NAME)
@PageEntity(showName = "考试试题",entityName = "examQuestion",serviceName = "examQuestionService")
public class ExamQuestion extends AbstractEntity {
    private static final long serialVersionUID = 1629387960711915781L;
    public static final String TABLE_NAME="exam_question";
    @PageField(label = "试卷名称")
    private String name;
    @PageField(label = "用户账户",type = FieldType.REFERENCE,showForm = false,
            showTable = false,showQuery = false,showDetail = false,showSearch = false)
    @FieldReference(fieldName = "phone")
    private Account account;
    @IDate
    @PageField(label = "创建时间",type = FieldType.DATETIME)
    private Date createTime;
    @PageField(label = "单选题数量",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int singleChoiceNumber;
    @PageField(label = "多选题数量",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int multipleChoiceNumber;
    @PageField(label = "问答题数量",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int questionsAndAnswersNumber;
    @PageField(label = "完成数量",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int finishNumber;
    @PageField(label = "得分",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int score;

    private Set<ExamQuestionItem> questionItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getSingleChoiceNumber() {
        return singleChoiceNumber;
    }

    public void setSingleChoiceNumber(int singleChoiceNumber) {
        this.singleChoiceNumber = singleChoiceNumber;
    }

    public int getMultipleChoiceNumber() {
        return multipleChoiceNumber;
    }

    public void setMultipleChoiceNumber(int multipleChoiceNumber) {
        this.multipleChoiceNumber = multipleChoiceNumber;
    }

    public int getQuestionsAndAnswersNumber() {
        return questionsAndAnswersNumber;
    }

    public void setQuestionsAndAnswersNumber(int questionsAndAnswersNumber) {
        this.questionsAndAnswersNumber = questionsAndAnswersNumber;
    }

    public int getFinishNumber() {
        return finishNumber;
    }

    public void setFinishNumber(int finishNumber) {
        this.finishNumber = finishNumber;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "examQuestion")
    public Set<ExamQuestionItem> getQuestionItems() {
        return questionItems;
    }

    public void setQuestionItems(Set<ExamQuestionItem> questionItems) {
        this.questionItems = questionItems;
    }
}
