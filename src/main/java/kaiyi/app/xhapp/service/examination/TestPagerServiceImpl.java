package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.entity.examination.TestPager;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("testPagerService")
public class TestPagerServiceImpl extends InjectDao<TestPager> implements TestPagerService {
    private static final long serialVersionUID = 1768606717840651039L;
}
