package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.Category;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.collection.Cascadeable;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.NullQueryExpress;
import kaiyi.puer.db.query.OrderBy;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.h5ui.service.H5UIService;
import kaiyi.puer.json.JsonFieldReplacePolicy;
import kaiyi.puer.json.JsonValuePolicy;
import kaiyi.puer.json.creator.CollectionJsonCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("categoryService")
public class CategoryServiceImpl extends InjectDao<Category> implements CategoryService{
    private static final long serialVersionUID = -3065253025659764543L;
    @Resource
    private H5UIService h5UIService;
    @Override
    public StreamCollection<Category> getRootCategory() {
        QueryExpress query=new NullQueryExpress("entityId",NullQueryExpress.NullCondition.IS_NOT_NULL);
        OrderBy orderby=new OrderBy(query.getPrefix(),"level",OrderBy.TYPE.ASC);
        orderby.add(query.getPrefix(),"weight",OrderBy.TYPE.DESC);
        StreamCollection<Category> categories=getEntitys(query,orderby);
        List<Category> result=new ArrayList<>();
        Cascadeable.cascade(categories.toList(),result);
        Collections.sort(result);
        return new StreamCollection<>(result);
    }

    @Override
    public StreamCollection<Category> getEnableRootCategory() {
        QueryExpress query=new CompareQueryExpress("enable",CompareQueryExpress.Compare.EQUAL,Boolean.TRUE);
        OrderBy orderby=new OrderBy(query.getPrefix(),"level",OrderBy.TYPE.ASC);
        orderby.add(query.getPrefix(),"weight",OrderBy.TYPE.DESC);
        StreamCollection<Category> categories=getEntitys(query,orderby);
        List<Category> result=new ArrayList<>();
        Cascadeable.cascade(categories.toList(),result);
        Collections.sort(result);
        return new StreamCollection<>(result);
    }

    private JsonFieldReplacePolicy categoryJsonFieldReplacePolicy=new JsonFieldReplacePolicy() {
        @Override
        public String replace(String field) {
            if (field.equals("name")) {
                return "title";
            } else if(field.equals("childrenList")){
                return "children";
            }
            return field;
        }
    };

    @Override
    public String toJsonTree(StreamCollection<Category> categories, String templateName) {
        JsonValuePolicy<Category> jsonValuePolicy=new CategoryTreeJsonValuePolicy(templateName,
                categoryJsonFieldReplacePolicy,h5UIService);
        CollectionJsonCreator<Category> json=new CollectionJsonCreator<Category>(categories, new String[]{
                "entityId", "name", "childrenList","weight","enable","level"
        },categoryJsonFieldReplacePolicy,jsonValuePolicy);
        return json.build();
    }

    @Override
    public void enableOrDisable(String entityId) {
        Category category=findForPrimary(entityId);
        category.setEnable(!category.isEnable());
        em.createQuery("update "+getEntityName(Category.class)+" o set o.enable=:enable " +
                "where o.parent=:parent").setParameter("enable",category.isEnable())
        .setParameter("parent",category).executeUpdate();
    }

    @Override
    protected void objectBeforeUpdateHandler(Category category, Map<String, JavaDataTyper> data) throws ServiceException {
        if(Objects.nonNull(data.get("parent"))){
            Category parent=findForPrimary(data.get("parent").stringValue());
            category.setLevel(parent.getLevel()+1);
        }
    }

    @Override
    protected void objectBeforePersistHandler(Category category, Map<String, JavaDataTyper> params) throws ServiceException {
        if(Objects.nonNull(params.get("parent"))){
            Category parent=findForPrimary(params.get("parent").stringValue());
            category.setLevel(parent.getLevel()+1);
        }
        category.setEnable(true);
    }

    @Override
    public StreamCollection<Category> getCategoryAndChildren(String categoryId) {
        Category category=findForPrimary(categoryId);
        StreamCollection<Category> questionCategories=new StreamCollection<>();
        if(Objects.nonNull(category)){
            questionCategories.add(category);
            findChildQuestion(category,questionCategories);
        }
        return questionCategories;
    }

    private void findChildQuestion(Category parent, StreamCollection<Category> questionCategories) {
        CompareQueryExpress query=new CompareQueryExpress("parent",CompareQueryExpress.Compare.EQUAL,parent);
        StreamCollection<Category> categories=getEntitys(query);
        categories.forEachByOrder((i,d)->{
            questionCategories.add(d);
            findChildQuestion(d,questionCategories);
        });
    }
}
