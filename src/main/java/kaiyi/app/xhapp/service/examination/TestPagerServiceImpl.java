package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.entity.examination.TestPager;
import kaiyi.app.xhapp.entity.examination.TestPagerQuestion;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("testPagerService")
public class TestPagerServiceImpl extends InjectDao<TestPager> implements TestPagerService {
    private static final long serialVersionUID = 1768606717840651039L;

    @Override
    public StreamCollection<TestPagerQuestion> getTestPagerQuestion(String testPagerId) {
        TestPager testPager=new TestPager();
        testPager.setEntityId(testPagerId);
        List<TestPagerQuestion> questions=em.createQuery("select o from "+getEntityName(TestPagerQuestion.class)+" o where " +
                "o.question=:question order by o.weight desc").setParameter("testPager",testPager).getResultList();
        return new StreamCollection<>(questions);
    }
}
