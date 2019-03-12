package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.Category;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("categoryService")
public class CategoryServiceImpl extends InjectDao<Category> implements CategoryService{
    private static final long serialVersionUID = -3065253025659764543L;
}
