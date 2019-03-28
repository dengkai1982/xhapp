package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.Category;
import kaiyi.app.xhapp.entity.curriculum.Course;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.CompareQueryExpress.*;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress.*;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service("courseService")
public class CourseServiceImpl extends InjectDao<Course> implements CourseService {
    private static final long serialVersionUID = 6202595141917981754L;

    @Override
    public QueryExpress getCustomerQuery(Map<String, JavaDataTyper> params) {
        QueryExpress query = super.getCustomerQuery(params);
        if(Objects.nonNull(params.get("limitCategory"))&&Objects.nonNull(params.get("currentCategory"))){
            String categoryId=params.get("currentCategory").stringValue();
            Category category=new Category();
            category.setEntityId(categoryId);
            query=new LinkQueryExpress(query,LINK.AND,new CompareQueryExpress("category",Compare.EQUAL,category));
        }
        return query;
    }
}
