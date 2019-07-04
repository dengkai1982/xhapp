package kaiyi.app.xhapp.entity.examination;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.h5ui.annotations.FieldReference;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

import javax.persistence.*;

@Entity(name= ExamChoiceAnswer.TABLE_NAME)
@PageEntity(showName = "考试答案",entityName = "choiceAnswer",serviceName = "choiceAnswerService")
public class ExamChoiceAnswer extends AbstractEntity {
    private static final long serialVersionUID = 8954908025941101896L;
    public static final String TABLE_NAME="exam_choice_answer";
    @PageField(label = "选项名称")
    private String optionName;
    @PageField(label = "选择值")
    private String detailValue;
    @PageField(label = "所属问题",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "detail")
    private ExamQuestionItem examQuestionItem;

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }
    @Lob
    public String getDetailValue() {
        return detailValue;
    }

    public void setDetailValue(String detailValue) {
        this.detailValue = detailValue;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name="examQuestionItem")
    public ExamQuestionItem getExamQuestionItem() {
        return examQuestionItem;
    }

    public void setExamQuestionItem(ExamQuestionItem examQuestionItem) {
        this.examQuestionItem = examQuestionItem;
    }
}
