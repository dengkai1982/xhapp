package kaiyi.app.xhapp.entity.examination;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.h5ui.annotations.PageEntity;

import javax.persistence.*;


@Entity(name= TestPagerQuestion.TABLE_NAME)
@PageEntity(showName = "试卷引用",entityName = "testPagerQuestion",serviceName = "testPagerQuestionService")
public class TestPagerQuestion extends AbstractEntity{
    private static final long serialVersionUID = -2546911817046264316L;
    public static final String TABLE_NAME="test_pager_question";
    //所属试卷
    private TestPager testPager;
    //问题
    private Question question;
    //得分
    private int score;
    //权重
    private int weight;

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name="testPager")
    public TestPager getTestPager() {
        return testPager;
    }

    public void setTestPager(TestPager testPager) {
        this.testPager = testPager;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="question")
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
