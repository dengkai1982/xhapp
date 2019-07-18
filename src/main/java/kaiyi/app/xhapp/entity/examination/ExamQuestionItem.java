package kaiyi.app.xhapp.entity.examination;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.examination.enums.QuestionType;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.validate.Max;
import kaiyi.puer.commons.validate.Min;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity(name= ExamQuestionItem.TABLE_NAME)
@PageEntity(showName = "考试试题项",entityName = "examQuestionItem",serviceName = "examQuestionItemService")
public class ExamQuestionItem extends AbstractEntity {

    public static final String TABLE_NAME="exam_question_item";
    private static final long serialVersionUID = -8189415348554828536L;
    @PageField(label = "所属考试名称",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "name")
    private ExamQuestion examQuestion;
    @PageField(label = "试题题目",type = FieldType.AREATEXT,formColumnLength = 3,tableLength = 500)
    private String detail;
    @PageField(label = "题目类型",type = FieldType.CHOSEN)
    @FieldChosen
    private QuestionType questionType;
    @PageField(label = "显示权重",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int weight;
    @Min(val = 1,hint = "问题分值最少需要指定1分")
    @Max(val = 99,hint = "问题分值不能超过99分")
    @PageField(label = "问题分值",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int score;
    @PageField(label = "问题解析",type = FieldType.AREATEXT,formColumnLength = 3,tableLength = 500)
    private String analysis;
    @PageField(label = "参考答案",type = FieldType.CHOSEN,tableLength = 300,showForm = false,showQuery = false,showSearch = false)
    private String standardAnswer ;
    @PageField(label = "回答答案",type = FieldType.CHOSEN,tableLength = 300,showForm = false,showQuery = false,showSearch = false)
    private String resultAnswer;
    @PageField(label = "答题结果",type = FieldType.BOOLEAN)
    @FieldBoolean(values = {"正确","错误"})
    private boolean result;

    @PageField(label = "完成答题",type = FieldType.BOOLEAN)
    @FieldBoolean(values = {"已完成","未完成"})
    private boolean finished;

    private Account owner;


    private Set<ExamChoiceAnswer> choiceAnswers;

    @Override
    public StreamArray<String> filterField() {
        return new StreamArray<>(new String[]{"examQuestion","owner"});
    }

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name="examQuestion")
    public ExamQuestion getExamQuestion() {
        return examQuestion;
    }

    public void setExamQuestion(ExamQuestion examQuestion) {
        this.examQuestion = examQuestion;
    }
    @Lob
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    @Enumerated(EnumType.STRING)
    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    @Lob
    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
    @Lob
    public String getStandardAnswer() {
        return standardAnswer;
    }

    public void setStandardAnswer(String standardAnswer) {
        this.standardAnswer = standardAnswer;
    }
    @Lob
    public String getResultAnswer() {
        return resultAnswer;
    }

    public void setResultAnswer(String resultAnswer) {
        this.resultAnswer = resultAnswer;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "examQuestionItem")
    public Set<ExamChoiceAnswer> getChoiceAnswers() {
        return choiceAnswers;
    }

    public void setChoiceAnswers(Set<ExamChoiceAnswer> choiceAnswers) {
        this.choiceAnswers = choiceAnswers;
    }

    @Transient
    public StreamCollection<ExamChoiceAnswer> getChoiceAnswerStream(){
        if(StreamCollection.assertNotEmpty(choiceAnswers)){
            List<ExamChoiceAnswer> choiceAnswerList=StreamCollection.setToList(choiceAnswers);
            Collections.sort(choiceAnswerList,(o1, o2)->{
                return o1.getOptionName().compareTo(o2.getOptionName());
            });
            return new StreamCollection<>(choiceAnswerList);
        }
        return new StreamCollection<>();
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name="owner")
    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }
}
