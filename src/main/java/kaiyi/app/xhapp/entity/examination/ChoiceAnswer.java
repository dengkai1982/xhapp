package kaiyi.app.xhapp.entity.examination;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;

@Entity(name= ChoiceAnswer.TABLE_NAME)
@PageEntity(showName = "选择题答案",entityName = "choiceAnswer",serviceName = "choiceAnswerService")
public class ChoiceAnswer extends AbstractEntity {
    private static final long serialVersionUID = 8954908025941101896L;
    public static final String TABLE_NAME="choice_answer";
    @PageField(label = "选项名称")
    private String optionName;
    @PageField(label = "选择值")
    private String detailValue;
    @PageField(label = "所属问题",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "detail")
    private Question question;
    @PageField(label = "已选答案",type = FieldType.BOOLEAN)
    @FieldBoolean(values = {"已选","未选"})
    private boolean checked;

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
    @JoinColumn(name="question")
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
