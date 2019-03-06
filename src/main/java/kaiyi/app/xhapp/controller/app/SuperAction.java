package kaiyi.app.xhapp.controller.app;

import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.utils.ObjectUtils;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.h5ui.bean.DynamicGridInfo;
import kaiyi.puer.h5ui.bean.PageFieldData;
import kaiyi.puer.h5ui.controller.H5Controller;
import kaiyi.puer.h5ui.ft.PageFieldType;
import kaiyi.puer.h5ui.service.H5UIService;
import kaiyi.puer.h5ui.service.PageElementManager;
import kaiyi.puer.json.JsonParserException;
import kaiyi.puer.json.parse.ArrayJsonParser;
import kaiyi.puer.web.query.QueryCondition;

import javax.annotation.Resource;
import java.util.Map;

public abstract class SuperAction extends H5Controller {
    public static final String PREFIX = "/app";
    @Resource
    protected H5UIService h5UIService;

    @Override
    protected String getTableDataQueryUrl() {
        return null;
    }

    @Override
    protected H5UIService getH5UIService() {
        return null;
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
}
