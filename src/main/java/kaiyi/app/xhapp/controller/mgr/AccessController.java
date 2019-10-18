package kaiyi.app.xhapp.controller.mgr;

import kaiyi.app.xhapp.entity.access.VisitorMenu;
import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.app.xhapp.entity.log.MenuTooltip;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.access.VisitorUserService;
import kaiyi.app.xhapp.service.log.MenuTooltipService;
import kaiyi.app.xhapp.service.pub.ConfigureService;
import kaiyi.puer.commons.collection.Cascadeable;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.StringEditor;
import kaiyi.puer.db.orm.CustomQueryExpress;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.h5ui.bean.DynamicGridInfo;
import kaiyi.puer.h5ui.service.DocumentService;
import kaiyi.puer.json.creator.CollectionJsonCreator;
import kaiyi.puer.json.creator.JsonMessageCreator;
import kaiyi.puer.json.creator.MutilJsonCreator;
import kaiyi.puer.web.elements.FormElementHidden;
import kaiyi.puer.web.service.EntityQueryService;
import kaiyi.puer.web.servlet.ServletUtils;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(AccessController.rootPath)
public class AccessController extends ManagerController {
    public static final String rootPath=prefix+"/access";
    @Resource
    private VisitorUserService visitorUserService;
    @Resource
    private ConfigureService configureService;
    @Resource
    private MenuTooltipService menuTooltipService;
    public static final String getAccessTempFilePathPrefix(WebInteractive interactive){
        String contextPath=interactive.getHttpServletRequest().getContextPath();
        return contextPath+rootPath+"/accessTempFile"+ServletUtils.getRequestSuffix(interactive.getHttpServletRequest())
                +"?hex=";
    }
    @RequestMapping("/tempUpload")
    public void tempUpload(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String hex=DocumentService.tempFileUpload(interactive,response,configureService.getStringValue(ConfigureItem.DOC_TEMP_PATH),
                applicationService);
        JsonMessageCreator msg = getSuccessMessage();
        msg.setBody(hex);
        interactive.writeUTF8Text(msg.build());
    }
    @RequestMapping("/accessTempFile")
    public void accessTempFile(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        DocumentService.accessStorageFile(interactive,response,configureService.getStringValue(ConfigureItem.DOC_TEMP_PATH),
                configureService.getStringValue(ConfigureItem.DOC_SERVER_PREFIX),"hex");
    }
    @RequestMapping("/accessStorageFile")
    public void accessStorageFile(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        DocumentService.accessStorageFile(interactive,response,configureService.getStringValue(ConfigureItem.DOC_SAVE_PATH),
                configureService.getStringValue(ConfigureItem.DOC_SERVER_PREFIX),"hex");
    }

    @RequestMapping("/getMenuTooltip")
    public void getMenuTooltip(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        QueryExpress query=new CompareQueryExpress("number",CompareQueryExpress.Compare.GT,0);
        StreamCollection<MenuTooltip> tooltips = menuTooltipService.getEntitys(query);
        CollectionJsonCreator<MenuTooltip> jsonCreator=new CollectionJsonCreator<>(tooltips,new String[]{
                "menuId","number"
        });
        interactive.writeUTF8Text(jsonCreator.build());
    }

    //获取菜单跳转
    @RequestMapping("/chooseFirstMenu")
    public void chooseFirstMenu(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        StreamCollection<VisitorMenu> menus=getMenuBySession(interactive.getHttpServletRequest());
        String entityId=interactive.getStringParameter("entityId","");
        VisitorMenu finder=(VisitorMenu)Cascadeable.findById(menus,entityId);
        if(finder==null||finder.getChildrenList().isEmpty()){
            interactive.sendRedict(interactive.getWebPage().getContextPath()+"/login.jsp");
        }else{
            menuTooltipService.clearMenuNotice(finder.getEntityId());
            List<? extends Cascadeable> cascadeList=finder.getChildrenList();
            for(Cascadeable cascade:cascadeList) {
                VisitorMenu menu=(VisitorMenu)cascade;
                if(menu.isShowable()) {
                    interactive.sendRedict(interactive.generatorRequestUrl(menu.getActionFlag(),null));
                    menuTooltipService.clearMenuNotice(menu.getEntityId());
                    break;
                }
            }
        }
    }
    //执行登录
    @PostMapping(value = "/login")
    public void login(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=getSuccessMessage();
        String username=interactive.getStringParameter("username","");
        String password=interactive.getStringParameter("password","");
        try {
            VisitorUser user=visitorUserService.doLogin(username,password,
                    ServletUtils.getRequestIpaddr(interactive.getHttpServletRequest()));
            addUserToSession(interactive,user);
            VisitorMenu visitorMenu=addMenuToSession(interactive);
            String actionFlalg=visitorMenu.getActionFlag();
            String url=interactive.generatorRequestUrl(actionFlalg,null);
            msg.setBody(url);
        } catch (ServiceException e) {
            catchServiceException(msg,e);
        }
        interactive.writeUTF8Text(msg.build());
    }
    //改变搜索框的内容
    @RequestMapping("/changeSearchBox")
    public void changeSearchBox(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String dataEntity=interactive.getStringParameter("dataEntity","");
        String chosenField=interactive.getStringParameter("chosenField","");
        String position=interactive.getStringParameter("position","");
        MutilJsonCreator mutilJsonCreator = getSearchBoxJson(dataEntity, chosenField, position,
                "searchCompareTemplate.ftlh","searchValueTemplate.ftlh");
        interactive.writeUTF8Json(mutilJsonCreator);
    }

    //执行条件查询
    @RequestMapping("/querySearchAction")
    public void querySearchAction(final @IWebInteractive WebInteractive interactive, HttpServletResponse response){
        String serviceName=interactive.getStringParameter("serviceName","");
        if(Objects.nonNull(serviceName)){
            String queryCondition=interactive.getHttpServletRequest().getParameter("queryCondition");
            DatabaseQuery databaseQuery = h5UIService.getSpringContextHolder().getBean(serviceName);
            QueryExpress query=parseQueryExpress(databaseQuery,databaseQuery.getEntityClassName(),queryCondition);
            query=defaultQuery(interactive,databaseQuery,query);
            executeConditionQuery(interactive,databaseQuery,query);
        }
    }
    //弹出多选框
    @RequestMapping("/popupMultipleChoose")
    public String popupMultipleChoose(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        popupChoose(interactive,true,DynamicGridInfo.OperMenuType.none);
        String contextName=interactive.getStringParameter("contextName","");
        interactive.setRequestAttribute("contextName",contextName);
        return rootPath+"/popupMultipleChoose";
    }

    //弹出窗单选
    @RequestMapping("/popupSingleChoose")
    public String popupSingleChoose(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        popupChoose(interactive,false,DynamicGridInfo.OperMenuType.single);
        return rootPath+"/popupSingleChoose";
    }

    public void popupChoose(WebInteractive interactive, boolean supportMultiple, DynamicGridInfo.OperMenuType operMenuType){
        String serviceName=interactive.getStringParameter("serviceName","");
        String actionButtonName=interactive.getStringParameter("actionButtonName","");
        String fieldName=interactive.getStringParameter("fieldName","");
        DatabaseQuery databaseQuery = h5UIService.getSpringContextHolder().getBean(serviceName);
        QueryExpress queryExpress=null;
        FormElementHidden[] hiddens=null;
        if(databaseQuery instanceof CustomQueryExpress){
            queryExpress=((CustomQueryExpress)databaseQuery).getCustomerQuery(interactive.getRequestParameterMap());
        }
        if(databaseQuery instanceof EntityQueryService){
            hiddens=((EntityQueryService)databaseQuery).gerenatorHiddenElement(interactive);
        }
        interactive.setRequestAttribute("actionButtonName",actionButtonName);
        interactive.setRequestAttribute("fieldName",fieldName);
        queryTablePage(interactive,databaseQuery,queryExpress,hiddens,new DynamicGridInfo(supportMultiple,operMenuType));
    }


    @RequestMapping(value="/changePassword",method = RequestMethod.POST)
    public void changePassword(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=getSuccessMessage();
        String userId=getLoginedUser(interactive).getEntityId();
        if(StringEditor.notEmpty(userId)){
            String oldPassword=interactive.getStringParameter("oldPassword","");
            String newPassword=interactive.getStringParameter("newPassword","");
            try {
                visitorUserService.changePassword(userId,oldPassword,newPassword);
            } catch (ServiceException e) {
                catchServiceException(msg,e);
            }
        }
        interactive.writeUTF8Text(msg.build());
    }
    //执行退出
    @RequestMapping("/exit")
    public void exit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        removeUserBySession(interactive);
        removeMenuBySession(interactive);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }
}
