package kaiyi.app.xhapp.entity.examination;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.examination.enums.AscriptionType;
import kaiyi.app.xhapp.entity.examination.enums.QuestionType;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.commons.validate.Max;
import kaiyi.puer.commons.validate.Min;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity(name= Question.TABLE_NAME)
@Table(indexes = {
        @Index(name="question_index_category",columnList = "category",unique = false),
        @Index(name="question_index_simulation_category",columnList = "simulationCategory",unique = false),
        @Index(name="question_index_simulation_question_type",columnList = "questionType",unique = false),
        @Index(name="question_index_simulation_enable",columnList = "enable",unique = false),
        @Index(name="question_index_simulation_update_time",columnList = "updateTime",unique = false)
})
@PageEntity(showName = "试题",entityName = "question",serviceName = "questionService")
public class Question extends AbstractEntity {

    public static final String TABLE_NAME="question";
    private static final long serialVersionUID = -3446794725915281424L;
    @NotEmpty(hint = "题目必须填写")
    @PageField(label = "试题题目",type = FieldType.AREATEXT,formColumnLength = 3,tableLength = 500,showForm = false)
    private String detail;
    @NotEmpty(hint = "所属类别名称必须选择")
    @PageField(label = "所属类别",type = FieldType.REFERENCE,tableLength = 140)
    @FieldReference(fieldName = "name")
    private QuestionCategory category;
    @NotEmpty(hint = "模拟考试类别必须选择")
    @PageField(label = "模拟考试类别",type = FieldType.REFERENCE,tableLength = 140)
    @FieldReference(fieldName = "name")
    private SimulationCategory simulationCategory;
    @IDate
    @PageField(label = "上传时间",type = FieldType.DATETIME,tableLength = 160)
    private Date updateTime;
    @NotEmpty(hint = "题目类型必须选择")
    @PageField(label = "题目类型",type = FieldType.CHOSEN,tableLength = 120)
    @FieldChosen
    private QuestionType questionType;
    @NotEmpty(hint = "归属分类必须选择")
    @PageField(label = "归属分类",type = FieldType.CHOSEN,tableLength = 120)
    @FieldChosen
    private AscriptionType ascriptionType;
    @NotEmpty(hint = "问题分值必须设置")
    @Min(val = 1,hint = "问题分值最少需要指定1分")
    @Max(val = 99,hint = "问题分值不能超过99分")
    @PageField(label = "试题分值",type = FieldType.NUMBER,tableLength = 120)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int score;
    @PageField(label = "问题解析",type = FieldType.AREATEXT,formColumnLength = 3,tableLength = 500,showForm = false,
    showQuery = false)
    private String analysis;
    @PageField(label = "参考答案",showForm = false,showQuery = false,showSearch = false,tableLength = 120)
    private String answer;
    @PageField(label = "启用状态",type = FieldType.BOOLEAN,showForm = false,showQuery = false,showSearch = false,tableLength = 120)
    @FieldBoolean(values = {"启用","停用"})
    private boolean enable;


    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    private Set<ChoiceAnswer> choiceAnswers;

    @Override
    public StreamArray<String> filterField() {
        return new StreamArray<>(new String[]{"choiceAnswers"});
    }

    @Lob
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="category")
    public QuestionCategory getCategory() {
        return category;
    }

    public void setCategory(QuestionCategory category) {
        this.category = category;
    }
    @Lob
    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
    @Enumerated(EnumType.STRING)
    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
    @Lob
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "question")
    public Set<ChoiceAnswer> getChoiceAnswers() {
        return choiceAnswers;
    }

    public void setChoiceAnswers(Set<ChoiceAnswer> choiceAnswers) {
        this.choiceAnswers = choiceAnswers;
    }

    @Transient
    public StreamCollection<ChoiceAnswer> getChoiceAnswerStream(){
        if(StreamCollection.assertNotEmpty(choiceAnswers)){
            List<ChoiceAnswer> choiceAnswerList=StreamCollection.setToList(choiceAnswers);
            Collections.sort(choiceAnswerList,(o1,o2)->{
                return o1.getOptionName().compareTo(o2.getOptionName());
            });
            return new StreamCollection<>(choiceAnswerList);
        }
        return new StreamCollection<>();
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="simulationCategory")
    public SimulationCategory getSimulationCategory() {
        return simulationCategory;
    }

    public void setSimulationCategory(SimulationCategory simulationCategory) {
        this.simulationCategory = simulationCategory;
    }
    @Enumerated(EnumType.STRING)
    public AscriptionType getAscriptionType() {
        return ascriptionType;
    }

    public void setAscriptionType(AscriptionType ascriptionType) {
        this.ascriptionType = ascriptionType;
    }
}

