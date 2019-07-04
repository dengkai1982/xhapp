package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.entity.examination.ExamQuestion;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.curriculum.CategoryService;
import kaiyi.puer.db.orm.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("examQuestionService")
public class ExamQuestionServiceImpl extends InjectDao<ExamQuestion> implements ExamQuestionService {

    private static final long serialVersionUID = -5053728156600061289L;

    @Resource
    private AccountService accountService;
    @Resource
    private TestPagerService testPagerService;
    @Resource
    private CategoryService categoryService;


    @Override
    public void generatorByTestPager(String accountId, String testPagerId) throws ServiceException {
        //TODO
    }

    @Override
    public void generatorByCategory(String account, String categoryId) throws ServiceException {
        //TODO
    }
}
