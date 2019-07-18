package kaiyi.app.xhapp.entity.examination;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.curriculum.Category;
import kaiyi.app.xhapp.entity.examination.enums.TestPagerType;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity(name= TestPager.TABLE_NAME)
@PageEntity(showName = "试卷",entityName = "testPager",serviceName = "testPagerService")
public class TestPager extends AbstractEntity {
    public static final String TABLE_NAME="test_pager";
    private static final long serialVersionUID = -8765999321414804847L;
    @NotEmpty(hint = "试卷名称必须填写")
    @PageField(label = "试卷名称",tableLength = 200)
    private String name;
    @NotEmpty(hint = "所属课程分类必须选择")
    @PageField(label = "课程分类",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "name")
    private Category category;
    @NotEmpty(hint = "试卷分类必须选择")
    @PageField(label = "试卷分类",type = FieldType.CHOSEN)
    @FieldChosen
    private TestPagerType testPagerType;
    @IDate
    @PageField(label = "创建时间",type = FieldType.DATETIME,showForm = false,tableLength = 160)
    private Date createTime;
    @PageField(label = "单选题数量",type = FieldType.NUMBER,showForm = false)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int singleChoiceNumber;
    @PageField(label = "多选题数量",type = FieldType.NUMBER,showForm = false)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int multipleChoiceNumber;
    @PageField(label = "问答题数量",type = FieldType.NUMBER,showForm = false)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int questionsAndAnswersNumber;
    @PageField(label = "启用状态",type = FieldType.BOOLEAN,showForm = false,showQuery = false,showSearch = false)
    @FieldBoolean(values = {"启用","停用"})
    private boolean enable;
    private Set<TestPagerQuestion> testPagerQuestions;

    @Override
    public StreamArray<String> filterField() {
        return new StreamArray<>(new String[]{"testPagerQuestions"});
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "question")
    public Set<TestPagerQuestion> getTestPagerQuestions() {
        return testPagerQuestions;
    }

    public void setTestPagerQuestions(Set<TestPagerQuestion> testPagerQuestions) {
        this.testPagerQuestions = testPagerQuestions;
    }

    /*@Transient
    public StreamCollection<TestPagerQuestion> getChoiceAnswerStream(){
        if(StreamCollection.assertNotEmpty(testPagerQuestions)){
            List<TestPagerQuestion> testPagerQuestionsList=StreamCollection.setToList(testPagerQuestions);
            Collections.sort(testPagerQuestionsList,(o1, o2)->{
                return Integer.valueOf(o1.getWeight()).compareTo(o2.getWeight());
            });
            return new StreamCollection<>(testPagerQuestionsList);
        }
        return new StreamCollection<>();
    }*/

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="category")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    @Enumerated(EnumType.STRING)
    public TestPagerType getTestPagerType() {
        return testPagerType;
    }

    public void setTestPagerType(TestPagerType testPagerType) {
        this.testPagerType = testPagerType;
    }
}
