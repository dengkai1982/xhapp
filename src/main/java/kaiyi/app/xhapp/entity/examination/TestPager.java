package kaiyi.app.xhapp.entity.examination;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.FieldNumber;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

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
    @PageField(label = "试卷名称",tableLength = 200)
    private String name;
    @IDate
    @PageField(label = "创建时间",type = FieldType.DATETIME,showForm = false,tableLength = 160)
    private Date createTime;
    @PageField(label = "单选题数量",type = FieldType.NUMBER,tableLength = 200)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int singleChoiceNumber;
    @PageField(label = "多选题数量",type = FieldType.NUMBER,tableLength = 200)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int multipleChoiceNumber;
    @PageField(label = "问答题数量",type = FieldType.NUMBER,tableLength = 200)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int questionsAndAnswersNumber;

    private Set<TestPagerQuestion> testPagerQuestions;



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
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "question")
    public Set<TestPagerQuestion> getTestPagerQuestions() {
        return testPagerQuestions;
    }

    public void setTestPagerQuestions(Set<TestPagerQuestion> testPagerQuestions) {
        this.testPagerQuestions = testPagerQuestions;
    }

    @Transient
    public StreamCollection<TestPagerQuestion> getChoiceAnswerStream(){
        if(StreamCollection.assertNotEmpty(testPagerQuestions)){
            List<TestPagerQuestion> testPagerQuestionsList=StreamCollection.setToList(testPagerQuestions);
            Collections.sort(testPagerQuestionsList,(o1, o2)->{
                return Integer.valueOf(o1.getWeight()).compareTo(o2.getWeight());
            });
            return new StreamCollection<>(testPagerQuestionsList);
        }
        return new StreamCollection<>();
    }
}
