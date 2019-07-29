package kaiyi.app.xhapp.service.examination;

import kaiyi.app.xhapp.entity.curriculum.Category;
import kaiyi.app.xhapp.entity.examination.QuestionCategory;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.curriculum.CategoryTreeJsonValuePolicy;
import kaiyi.puer.commons.collection.Cascadeable;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.*;
import kaiyi.puer.h5ui.service.H5UIService;
import kaiyi.puer.json.JsonFieldReplacePolicy;
import kaiyi.puer.json.JsonValuePolicy;
import kaiyi.puer.json.creator.CollectionJsonCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("questionCategoryService")
public class QuestionCategoryServiceImpl extends InjectDao<QuestionCategory> implements QuestionCategoryService {
    @Resource
    private H5UIService h5UIService;
    @Override
    public QueryExpress getCustomerQuery(Map<String, JavaDataTyper> params) {
        QueryExpress queryExpress = super.getCustomerQuery(params);
        if(existParameter(params,"parent")){
            QuestionCategory parent=new QuestionCategory();
            parent.setEntityId(params.get("parent").stringValue());
            queryExpress=new LinkQueryExpress(queryExpress,LinkQueryExpress.LINK.AND,new CompareQueryExpress("parent",
                    CompareQueryExpress.Compare.EQUAL,parent));
        }
        if(existParameter(params,"topParent")){
            queryExpress=new LinkQueryExpress(queryExpress,LinkQueryExpress.LINK.AND,new NullQueryExpress("parent",
                    NullQueryExpress.NullCondition.IS_NULL));
        }
        return queryExpress;

    }

    @Override
    public StreamCollection<QuestionCategory> getChild(String districtId) {
        QuestionCategory parent=new QuestionCategory();
        parent.setEntityId(districtId);
        QueryExpress query=new CompareQueryExpress("parent",CompareQueryExpress.Compare.EQUAL,parent);
        OrderBy order=new OrderBy(query.getPrefix(),"entityId");
        return getEntitys(query,order);
    }

    @Override
    public StreamCollection<QuestionCategory> getSameLevel(String districtId) {
        QuestionCategory questionCategory=findForPrimary(districtId);
        if(Objects.nonNull(questionCategory)){
            return getChild(questionCategory.getParent().getEntityId());
        }
        return new StreamCollection<>();
    }

    @Override
    protected void objectBeforePersistHandler(QuestionCategory questionCategory, Map<String, JavaDataTyper> params) throws ServiceException {
        QuestionCategory parent=questionCategory.getParent();
        if(Objects.nonNull(parent)){
            parent=findForPrimary(parent.getEntityId());
            questionCategory.setLevel(parent.getLevel()+1);
        }
    }

    @Override
    public void newQuestionCategory(String name, String parentName) {
        QuestionCategory parent=signleQuery("name",parentName);
        QuestionCategory newCategory=new QuestionCategory();
        newCategory.setName(name);
        if(Objects.nonNull(parent)){
            newCategory.setParent(parent);
            newCategory.setLevel(parent.getLevel()+1);
        }else{
            newCategory.setLevel(0);
        }
        saveObject(newCategory);
    }


    @Override

    protected String[] getFormElementHiddenParams() {
        return new String[]{"parent","topParent"};
    }

    @Override
    public StreamCollection<QuestionCategory> getEnableRootCategory() {
        QueryExpress query=new CompareQueryExpress("enable",CompareQueryExpress.Compare.EQUAL,Boolean.TRUE);
        OrderBy orderby=new OrderBy(query.getPrefix(),"level",OrderBy.TYPE.ASC);
        orderby.add(query.getPrefix(),"weight",OrderBy.TYPE.DESC);
        StreamCollection<QuestionCategory> categories=getEntitys(query,orderby);
        List<QuestionCategory> result=new ArrayList<>();
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
    public String toJsonTree(StreamCollection<QuestionCategory> categories, String templateName) {
        JsonValuePolicy<QuestionCategory> jsonValuePolicy=new QuestionCategoryTreeJsonValuePolicy(templateName,
                categoryJsonFieldReplacePolicy,h5UIService);
        CollectionJsonCreator<QuestionCategory> json=new CollectionJsonCreator<QuestionCategory>(categories, new String[]{
                "entityId", "name", "childrenList","weight","enable","level"
        },categoryJsonFieldReplacePolicy,jsonValuePolicy);
        return json.build();
    }
}
