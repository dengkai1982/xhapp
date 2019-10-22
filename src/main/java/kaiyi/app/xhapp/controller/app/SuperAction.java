package kaiyi.app.xhapp.controller.app;

import kaiyi.app.xhapp.service.access.InsideNoticeService;
import kaiyi.puer.commons.collection.ForEach;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.utils.ObjectUtils;
import kaiyi.puer.commons.validate.ValidateResult;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.h5ui.bean.DynamicGridInfo;
import kaiyi.puer.h5ui.bean.PageFieldData;
import kaiyi.puer.h5ui.controller.H5Controller;
import kaiyi.puer.h5ui.entity.DocumentStorageEvent;
import kaiyi.puer.h5ui.ft.PageFieldType;
import kaiyi.puer.h5ui.service.H5UIService;
import kaiyi.puer.h5ui.service.PageElementManager;
import kaiyi.puer.json.JsonParserException;
import kaiyi.puer.json.creator.JsonMessageCreator;
import kaiyi.puer.json.parse.ArrayJsonParser;
import kaiyi.puer.web.query.QueryCondition;
import kaiyi.puer.web.servlet.ServletUtils;
import kaiyi.puer.web.servlet.WebInteractive;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public abstract class SuperAction extends H5Controller {
    public static final String PREFIX = "/app";
    @Resource
    protected H5UIService h5UIService;
    @Resource
    protected InsideNoticeService insideNoticeService;
    @Override
    protected String getTableDataQueryUrl() {
        return null;
    }

    @Override
    protected H5UIService getH5UIService() {
        return h5UIService;
    }

    @Override
    protected String getSearchTemplate(String className, StreamCollection<PageFieldData> searchableFieldData, DynamicGridInfo dynamicGridInfo) {
        return null;
    }

    @Override
    protected String getTableTemplate(DynamicGridInfo dynamicGridInfo, StreamCollection<PageFieldData> tableFieldDatas) {
        return null;
    }
    protected QueryExpress parseQueryExpress(String entityClassName, String queryConditionJson) {
        QueryExpress express = null;
        if (ObjectUtils.notNullString(queryConditionJson)) {
            ArrayJsonParser jsonParser = new ArrayJsonParser(queryConditionJson);
            try {
                StreamCollection<Map<String, JavaDataTyper>> parseResult = jsonParser.doParser();
                int index = 0;
                for (Map<String, JavaDataTyper> map : parseResult) {
                    QueryCondition condition = map.get("condition").enumValueByOrdinal(QueryCondition.class);
                    String fieldName = map.get("field").stringValue();
                    JavaDataTyper value = map.get("value");
                    QueryExpress qe = getSignleQueryExpress(entityClassName, fieldName, condition, value);
                    if (index == 0) {
                        express = qe;
                    } else {
                        LinkQueryExpress.LINK itemLink = map.get("link").enumValueByOrdinal(LinkQueryExpress.LINK.class);
                        express = new LinkQueryExpress(express, itemLink, qe);
                    }
                    index++;
                }
            } catch (JsonParserException e) {
                e.printStackTrace();
            }
        }
        return express;
    }

    protected QueryExpress getSignleQueryExpress(String className, String fieldName,
                                                 QueryCondition condition, JavaDataTyper value) {
        PageElementManager pem = h5UIService.getPageElementManager();
        PageFieldData pageFieldData = pem.getPageFieldData(className, fieldName);
        PageFieldType pageFieldType = pageFieldData.getType();
        return pageFieldType.getQueryExpress(fieldName, condition, value);
    }

    protected String getSendUrl(WebInteractive interactive,String url,String onlyEntityId){
        Map<String,Object> params=new HashMap<>();
        params.put("onlyEntityId",onlyEntityId);
        String sendUrl=ServletUtils.generatorRequestUrl(interactive.getHttpServletRequest(),
                url,params);
        return sendUrl;
    }

    protected void sendInsideMessage(WebInteractive interactive,String content,String url,String onlyEntityId){
        insideNoticeService.sendMessage(content,getSendUrl(interactive,url,onlyEntityId));
    }
    protected <T> JsonMessageCreator executeNewOrUpdate(WebInteractive interactive, DatabaseFastOper oper, Map<String,JavaDataTyper> params, String saveFilePath,
                                                    AtomicReference<T> entityReference){
        JsonMessageCreator msg=getSuccessMessage();
        DatabaseQuery databaseQuery=null;
        if(oper instanceof DatabaseQuery){
            databaseQuery=(DatabaseQuery)oper;
        }
        String entityClassName=databaseQuery.getEntityClassName();
        if(Objects.isNull(databaseQuery)){
            throw new RuntimeException("DatabaseFastOper not instanceof DatabaseQuery");
        }
        try {
            Class<?> clz = Class.forName(entityClassName);
            ForEach.map(params,(k, v)->{
                ValidateResult validateResult=getH5UIService().validateField(clz,k,v);
                if(!validateResult.isPassed()){
                    msg.setError(validateResult.getHint());
                    msg.setBody(ServletUtils.generatorToken(interactive.getHttpServletRequest()));
                    return false;
                }
                return true;
            });
            //表单校验,失败返回
            if(msg.getCode().equals(JsonMessageCreator.FAIL)){
                return msg;
            }
            if(Objects.nonNull(saveFilePath)){
                Object instance=Class.forName(entityClassName).newInstance();
                if(instance instanceof DocumentStorageEvent){
                    DocumentStorageEvent storageEvent=(DocumentStorageEvent)instance;
                    storageEvent.tryStorage(interactive,entityClassName,params,saveFilePath);
                }
            }
            String entityId=interactive.getStringParameter("entityId","");
            if(ObjectUtils.notNullString(entityId)){
                entityReference.set((T)oper.updateObject(entityId,params));
            }else{
                entityReference.set((T)oper.newObject(params));
            }
        }catch(ServiceException e){
            catchServiceException(msg,e);
        } catch (InstantiationException|IllegalAccessException|ClassNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
