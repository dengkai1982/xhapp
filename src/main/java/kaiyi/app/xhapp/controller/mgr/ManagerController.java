package kaiyi.app.xhapp.controller.mgr;
import kaiyi.app.xhapp.entity.access.RoleAuthorizationMenu;
import kaiyi.app.xhapp.entity.access.VisitorMenu;
import kaiyi.app.xhapp.entity.access.VisitorRole;
import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.access.VisitorMenuService;
import kaiyi.app.xhapp.service.pub.ConfigureService;
import kaiyi.puer.commons.collection.Cascadeable;
import kaiyi.puer.commons.collection.ProcessCascadeEachHandler;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.time.DateTimeUtil;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.h5ui.bean.DynamicGridInfo;
import kaiyi.puer.h5ui.bean.PageFieldData;
import kaiyi.puer.h5ui.controller.H5Controller;
import kaiyi.puer.h5ui.service.ApplicationService;
import kaiyi.puer.h5ui.service.DocumentService;
import kaiyi.puer.h5ui.service.H5UIService;
import kaiyi.puer.json.creator.JsonMessageCreator;
import kaiyi.puer.web.servlet.PageNavigation;
import kaiyi.puer.web.servlet.ServletUtils;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.servlet.WebPage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public abstract class ManagerController extends H5Controller {
    public static final String prefix="/mgr";
    @Resource
    protected VisitorMenuService visitorMenuService;
    @Resource
    protected ApplicationService applicationService;
    @Resource
    protected H5UIService h5UIService;
    @Resource
    private ConfigureService configureService;
    /********************* 页面菜单相关  ***************************/
    //添加菜单到session
    protected VisitorMenu addMenuToSession(WebInteractive interactive){
        StreamCollection<VisitorMenu> menus=visitorMenuService.getRootMenus();
        //List<VisitorMenu> menus = visitorMenuService.getRootMenus();
        VisitorUser user=(VisitorUser) getLoginedUser(interactive.getHttpServletRequest());
        VisitorRole visitorRole=user.getVisitorRole();
        final Set<RoleAuthorizationMenu> authors = visitorRole.getAuthors();
        interactive.addSessionAttribute(NAME_SESSION_AUTHORS, authors);
        Cascadeable.forEachCascade(menus.toList(), new ProcessCascadeEachHandler<Cascadeable>() {
            @Override
            public void beforeEach(Cascadeable cascadeable) {

            }

            @Override
            public void beforeEachChild(Cascadeable cascadeable, Collection<? extends Cascadeable> collection) {

            }

            @Override
            public void afterEachChild(Cascadeable cascadeable, Collection<? extends Cascadeable> collection) {

            }

            @Override
            public void atferEach(Cascadeable cascadeable) {

            }

            @Override
            public boolean each(int i, Cascadeable cascadeable) {
                VisitorMenu menu=(VisitorMenu)cascadeable;
                for(RoleAuthorizationMenu author:authors){
                    if(author.getMenu().getEntityId().equals(menu.getEntityId())){
                        if(author.isVisit()){
                            menu.setShowable(true);
                        }else{
                            menu.setShowable(false);
                        }
                    }
                }
                return true;
            }
        });
        VisitorMenu firstMenu=null;
        if(!menus.isEmpty()){
            VisitorMenu first=menus.get(0);
            first.setActive(true);
            if(!first.getChildrenList().isEmpty()){
                firstMenu=first.getChildrenList().get(0);
                firstMenu.setActive(true);
            }
        }
        interactive.addSessionAttribute(NAME_SESSION_MENU,menus);
        return firstMenu;
    }
    protected void removeMenuBySession(WebInteractive interactive) {
        HttpServletRequest request=interactive.getHttpServletRequest();
        HttpSession session=request.getSession();
        session.removeAttribute(NAME_SESSION_MENU);
    }
    public static final StreamCollection<VisitorMenu> getMenuBySession(HttpServletRequest request){
        return (StreamCollection<VisitorMenu>)request.getSession().getAttribute(NAME_SESSION_MENU);
    }
    //设置菜单为激活
    private void setParentMenuActive(VisitorMenu menu){
        VisitorMenu parent=menu.getParent();
        if (parent != null) {
            parent.setActive(true);
            setParentMenuActive(parent);
        }
    }
    //设置导航栏
    private void setNavigation(WebPage webPage, VisitorMenu menu, boolean active){
        PageNavigation nav=new PageNavigation();
        nav.setAccessId(menu.getEntityId());
        nav.setActive(active);
        nav.setName(menu.getName());
        nav.setPath(menu.getActionFlag());
        nav.setShow(true);
        webPage.addNavigation(nav);
        if(menu.getParent()!=null){
            setNavigation(webPage,menu.getParent(),false);
        }
    }
    protected void perfectPage(WebInteractive interactive){
        WebPage webPage=interactive.getWebPage();
        String requestPrefix=ServletUtils.getRequestPrefix(interactive.getHttpServletRequest(),false);
        StreamCollection<VisitorMenu> sessionMenus=getMenuBySession(interactive.getHttpServletRequest());
        if(sessionMenus==null)return;
        VisitorMenu menu= (VisitorMenu)Cascadeable.findById(sessionMenus,requestPrefix);
        if(menu!=null) {
            webPage.setPageTitle(menu.getName());
            setNavigation(webPage, menu, true);
            List<PageNavigation> navs = webPage.getNavigations();
            Collections.reverse(navs);
            webPage.setNavigations(navs);
            final String currentMenuId = menu.getEntityId();
            Cascadeable.forEachCascade(sessionMenus.toList(), new ProcessCascadeEachHandler<Cascadeable>() {
                @Override
                public void beforeEach(Cascadeable cascadeable) {

                }

                @Override
                public void beforeEachChild(Cascadeable cascadeable, Collection<? extends Cascadeable> collection) {

                }

                @Override
                public void afterEachChild(Cascadeable cascadeable, Collection<? extends Cascadeable> collection) {

                }

                @Override
                public void atferEach(Cascadeable cascadeable) {

                }

                @Override
                public boolean each(int i, Cascadeable cascadeable) {
                    VisitorMenu menu = (VisitorMenu) cascadeable;
                    if (menu.getEntityId().equals(currentMenuId)) {
                        menu.setActive(true);
                    } else {
                        menu.setActive(false);
                    }
                    return true;
                }
            });
            String url = webPage.getRequestUrlAndParameter(clearParameterName);
            webPage.setPaginationQueryUrl(url);
            webPage.setConditionQueryUrl(webPage.getFullRequestUrl());
            setParentMenuActive(menu);
        }
        //设置limitPage
        int currentPage=interactive.getPaginationCurrentPage();
        interactive.setRequestAttribute("currentPage",currentPage);
    }

    @Override
    protected String getTableDataQueryUrl() {
        return AccessController.rootPath+"/querySearchAction";
    }
    @Override
    protected H5UIService getH5UIService() {
        return h5UIService;
    }

    @Override
    protected String getTableTemplate(DynamicGridInfo dynamicGridInfo, StreamCollection<PageFieldData> tableFieldDatas) {
        filterTableFieldData(dynamicGridInfo.getClassName(),tableFieldDatas);
        DynamicGridInfo.OperMenuType operMenuType=dynamicGridInfo.getOperMenuType();
        if(operMenuType.equals(DynamicGridInfo.OperMenuType.popup)){
            //显示弹窗菜单
            return "popupOperGridTemplate.ftlh";
        }else if(operMenuType.equals(DynamicGridInfo.OperMenuType.single)){
            //显示常规菜单
            return "normalOperGridTemplate.ftlh";
        }else{
            //不显示任何菜单
            return "noneOperGridTemplate.ftlh";
        }
    }
    //过滤表格中的字段
    protected void filterTableFieldData(String className,StreamCollection<PageFieldData> tableFieldDatas){

    }

    protected void filterSearchFieldData(String className, StreamCollection<PageFieldData> searchableFieldData){

    }

    @Override
    protected String getSearchTemplate(String className, StreamCollection<PageFieldData> searchableFieldData, DynamicGridInfo dynamicGridInfo) {
        filterSearchFieldData(className,searchableFieldData);
        return "searchPage.ftlh";
    }
    //判断用户是否具备权限
    public static boolean hasAuthor(HttpServletRequest request, String url) {
        Set<RoleAuthorizationMenu>  authors= (Set<RoleAuthorizationMenu>) request.getSession(true).getAttribute(NAME_SESSION_AUTHORS);
        if(StreamCollection.assertNotEmpty(authors)){
            RoleAuthorizationMenu pam=new StreamCollection<RoleAuthorizationMenu>(authors).find(m->{
                return url.equals(m.getMenu().getActionFlag());
            });
            return pam!=null&&pam.isVisit();
        }
        return false;
    }
    /**********含有上下级***********/
    protected StreamArray<Integer> getPageNumberStack(WebInteractive interactive){
        StreamArray<Integer> pageNumbers=interactive.getIntegerArrayParameter("pageNumber",",");
        boolean isback=interactive.getBoolean("isback","true",false);
        if(!isback){
            pageNumbers.insertToLast(interactive.getInteger(WebInteractive.PAGINATION_PARAMETER_CURRENT_PAGE));
        }
        return pageNumbers;
    }

    protected void contextCommit(WebInteractive interactive, DatabaseFastOper<?> service) throws IOException {
        Map<String,JavaDataTyper> params=interactive.getRequestParameterMap();
        String storagePath=configureService.getStringValue(ConfigureItem.DOC_SAVE_PATH);
        String serverPath=configureService.getStringValue(ConfigureItem.DOC_SERVER_PREFIX);
        String content=params.get("content").stringValue();
        content=DocumentService.replaceImageSrc(content,AccessController.getAccessTempFilePathPrefix(interactive),
                storagePath,serverPath);
        params.put("content",new JavaDataTyper(content));
        params.put("publishDate",new JavaDataTyper(DateTimeUtil.yyyyMMddHHmmss.format(new Date())));
        JsonMessageCreator msg=executeNewOrUpdate(interactive,service,params,storagePath);
        interactive.writeUTF8Text(msg.build());
    }
}
