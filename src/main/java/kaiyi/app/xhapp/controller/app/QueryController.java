package kaiyi.app.xhapp.controller.app;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.service.CustomerPaginationJson;
import kaiyi.puer.commons.bean.BeanSyntacticSugar;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.db.Pagination;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.json.DefaultJsonValuePolicy;
import kaiyi.puer.json.JsonCreator;
import kaiyi.puer.json.JsonUtils;
import kaiyi.puer.json.JsonValuePolicy;
import kaiyi.puer.json.creator.CollectionJsonCreator;
import kaiyi.puer.json.creator.JsonBuilder;
import kaiyi.puer.json.creator.ObjectJsonCreator;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping(QueryController.rootPath)
public class QueryController extends SuperAction {
    public static final String rootPath=PREFIX+"/query";
    //分页查询
    @RequestMapping("/pagination")
    public void pagination(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        String serviceName=interactive.getStringParameter("serviceName","");
        if(Objects.nonNull(serviceName)){
            String queryCondition=interactive.getHttpServletRequest().getParameter("queryCondition");
            DatabaseQuery databaseQuery = h5UIService.getSpringContextHolder().getBean(serviceName);
            QueryExpress query=null;
            if(!JsonUtils.isEmptyJson(queryCondition)){
                query=parseQueryExpress(databaseQuery.getEntityClassName(),queryCondition);
            }
            query=super.defaultQuery(interactive,databaseQuery,query);
            executeConditionQuery(interactive,databaseQuery,query);
        }
    }
    //通过ID查询
    @RequestMapping("/findByEntityId")
    public void findByEntityId(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String serviceName=interactive.getStringParameter("serviceName","");
        String entityId=interactive.getStringParameter("entityId","");
        if(Objects.nonNull(serviceName)&&Objects.nonNull(entityId)){
            DatabaseQuery databaseQuery = h5UIService.getSpringContextHolder().getBean(serviceName);
            AbstractEntity abstractEntity  =(AbstractEntity)databaseQuery.findForPrimary(entityId);
            if(Objects.nonNull(abstractEntity)){
                writeObject(interactive,abstractEntity);
            }else{
                interactive.writeUTF8Text(JsonCreator.EMPTY_JSON);
            }
        }
    }
    @Override
    protected void executeConditionQuery(WebInteractive interactive,
                                         DatabaseQuery<? extends JsonBuilder> databaseQuery,
                                         QueryExpress query) {
        executeConditionQuery(interactive,databaseQuery,query,pagination->{
            ObjectJsonCreator<Pagination> creator=null;
            if(databaseQuery instanceof CustomerPaginationJson){
                creator=((CustomerPaginationJson)databaseQuery).getCustomerCreator(pagination);
            }else{
                //creator=getDefaultObjectCreator(pagination);
                creator=customerPaginationJsonCreate(pagination);
            }
            try {
                interactive.writeUTF8Text(creator.build());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private ObjectJsonCreator<Pagination> customerPaginationJsonCreate(Pagination<? extends JsonBuilder> pagination){
        ObjectJsonCreator<Pagination> creator=new ObjectJsonCreator<Pagination>(pagination, new String[]{
                "totalPage", "currentPage", "dataList","first", "maxResult", "total",
        }, new JsonValuePolicy<Pagination>() {
            @Override
            public JsonCreator getCreator(Pagination entity, String field, Object fieldValue) {
                if(field.equals("dataList")&& StreamCollection.assertNotEmpty(entity.getDataList())){
                    StreamCollection<JsonBuilder> stream=entity.getDataList();
                    JsonBuilder jsonBuilder=stream.get(0);
                    String[] showFieldArray= BeanSyntacticSugar.getFieldStringNotStatic(jsonBuilder.getClass(),
                            jsonBuilder.filterField().getArray());
                    CollectionJsonCreator<JsonBuilder> streamCreate=new CollectionJsonCreator<>(stream,
                            showFieldArray,jsonBuilder.jsonFieldReplacePolicy(),new OtherJsonValuePolicy<>());
                    return streamCreate;
                }
                return null;
            }
        });
        return creator;
    }
}
