package kaiyi.app.xhapp.entity.examination;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.curriculum.Category;
import kaiyi.app.xhapp.entity.examination.enums.QuestionType;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.validate.Max;
import kaiyi.puer.commons.validate.Min;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;
import org.apache.poi.ss.formula.functions.T;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity(name= Question.TABLE_NAME)
@PageEntity(showName = "试题",entityName = "question",serviceName = "questionService")
public class Question extends AbstractEntity {

    public static final String TABLE_NAME="question";
    private static final long serialVersionUID = -3446794725915281424L;
    @NotEmpty(hint = "题目必须填写")
    @PageField(label = "试题题目",type = FieldType.AREATEXT,formColumnLength = 3,tableLength = 500)
    private String detail;
    @NotEmpty(hint = "题目必须填写")
    @PageField(label = "所属类别",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "name")
    private Category category;
    @PageField(label = "题目类型",type = FieldType.CHOSEN)
    @FieldChosen
    private QuestionType questionType;
    @Min(val = 1,hint = "问题得分最少需要指定1分")
    @Max(val = 99,hint = "问题得分不能超过99分")
    @PageField(label = "问题得分",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int score;
    @PageField(label = "问题解析",type = FieldType.AREATEXT,formColumnLength = 3,tableLength = 500)
    private String analysis;
    @PageField(label = "参考答案",type = FieldType.CHOSEN,tableLength = 300,showForm = false,showQuery = false,showSearch = false)
    private String answer;

    private Set<ChoiceAnswer> choiceAnswers;

    @Lob
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="category")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    @Lob
    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

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
}