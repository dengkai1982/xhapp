package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.Category;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.h5ui.service.H5UIService;
import kaiyi.puer.json.JsonCreator;
import kaiyi.puer.json.JsonFieldReplacePolicy;
import kaiyi.puer.json.JsonValuePolicy;
import kaiyi.puer.json.creator.CollectionJsonCreator;
import kaiyi.puer.json.creator.StringJsonCreator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryTreeJsonValuePolicy implements JsonValuePolicy<Category> {
    private String templateName;
    private JsonFieldReplacePolicy categoryJsonFieldReplacePolicy;
    private H5UIService h5UIService;

    public CategoryTreeJsonValuePolicy(String templateName, JsonFieldReplacePolicy categoryJsonFieldReplacePolicy,
                                       H5UIService h5UIService) {
        this.templateName = templateName;
        this.categoryJsonFieldReplacePolicy = categoryJsonFieldReplacePolicy;
        this.h5UIService = h5UIService;
    }

    @Override
    public JsonCreator getCreator(Category entity, String field, Object fieldValue) {
        if(field.equals("childrenList")){
            List<Category> categories= (List<Category>) entity.getChildrenList();
            return new CollectionJsonCreator<Category>(new StreamCollection<>(categories),new String[]{
                    "entityId", "name", "childrenList","weight","enable","level"
            },categoryJsonFieldReplacePolicy,this);
        }else if(field.equals("name")){
            Map<String,Object> params=new HashMap<>();
            params.put("id",entity.getEntityId());
            params.put("name",entity.getName());
            params.put("weight",entity.getWeight());
            params.put("level",entity.getLevel());
            params.put("enable",entity.isEnable());
            params.put("enableStr",entity.isEnable()?"停用":"启用");
            String content=h5UIService.writeTemplate(templateName,params);
            return new StringJsonCreator(content);
        }
        return null;
    }
}
