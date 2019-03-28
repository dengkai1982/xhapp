package kaiyi.app.xhapp.controller.mgr;

import kaiyi.app.xhapp.entity.access.RoleAuthorizationMenu;
import kaiyi.app.xhapp.entity.access.VisitorMenu;
import kaiyi.app.xhapp.entity.access.VisitorRole;
import kaiyi.app.xhapp.service.access.RoleAuthorizationMenuService;
import kaiyi.app.xhapp.service.curriculum.TeacherService;
import kaiyi.app.xhapp.service.access.VisitorRoleService;
import kaiyi.app.xhapp.service.access.VisitorUserService;
import kaiyi.puer.commons.access.AccessControl;
import kaiyi.puer.commons.collection.Cascadeable;
import kaiyi.puer.commons.collection.ProcessCascadeEachHandler;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.utils.CoderUtil;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.h5ui.bean.DynamicGridInfo;
import kaiyi.puer.json.JsonParserException;
import kaiyi.puer.json.creator.JsonMessageCreator;
import kaiyi.puer.json.parse.ArrayJsonParser;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(AccountController.rootPath)
@AccessControl(name = "用户账户", weight = 1f, detail = "管理系统中用户账户", code = AccountController.rootPath)
public class AccountController extends ManagerController {
    public static final String rootPath=prefix+"/account";
    @Resource
    private VisitorRoleService visitorRoleService;
    @Resource
    private RoleAuthorizationMenuService roleAuthorizationMenuService;
    @Resource
    private VisitorUserService visitorUserService;
    @Resource
    private TeacherService studentService;
    @RequestMapping("/visitorRole")
    @AccessControl(name = "角色管理", weight = 1.1f, detail = "管理系统中的访问角色", code = rootPath+ "/visitorRole", parent = rootPath)
    public String visitorRole(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/visitorRole");
        mainTablePage(interactive,visitorRoleService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/visitorRole";
    }

    @RequestMapping("/visitorRole/new")
    @AccessControl(name = "新增角色", weight = 1.11f, detail = "添加新的角色",
            code = rootPath+ "/visitorRole/new", parent = rootPath+"/visitorRole")
    public String visitorRoleNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,visitorRoleService,3);
        setDefaultPage(interactive,rootPath+"/visitorRole");
        return rootPath+"/visitorRoleForm";
    }
    @RequestMapping("/visitorRole/modify")
    @AccessControl(name = "编辑角色", weight = 1.12f, detail = "编辑角色",
            code = rootPath+ "/visitorRole/modify", parent = rootPath+"/visitorRole")
    public String visitorRoleModify(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,visitorRoleService,3);
        setDefaultPage(interactive,rootPath+"/visitorRole");
        return rootPath+"/visitorRoleForm";
    }
    @RequestMapping("/visitorRole/detail")
    @AccessControl(name = "角色详情", weight = 1.13f, detail = "角色详情",
            code = rootPath+ "/visitorRole/detail", parent = rootPath+"/visitorRole")
    public String visitorRoleDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,visitorRoleService,3);
        setDefaultPage(interactive,rootPath+"/visitorRole");
        return rootPath+"/visitorRoleDetail";
    }
    @PostMapping("/visitorRole/commit")
    public void visitorRoleCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=executeNewOrUpdate(interactive,visitorRoleService);
        interactive.writeUTF8Text(msg.build());
    }
    @AccessControl(name = "访问权限", weight = 1.14f, detail = "修改部门后台操作访问权限", code = rootPath
            + "/visitorRole/privilegeDatas", parent = rootPath+"/visitorRole")
    @RequestMapping("/visitorRole/privilegeDatas")
    public String rolePrilivigeDatas(@IWebInteractive WebInteractive interactive){
        String entityId=interactive.getStringParameter("entityId","");
        VisitorRole role=visitorRoleService.findForPrimary(entityId);
        final StreamCollection<VisitorMenu> rootMenus=visitorMenuService.getRootMenus();
        final Set<RoleAuthorizationMenu> authors=role.getAuthors();
        final StringBuilder builder=new StringBuilder();
        builder.append("<ul class='tree tree-lines' data-animate='true' data-ride='tree'>");
        Cascadeable.forEachCascade(rootMenus.toList(), new ProcessCascadeEachHandler<Cascadeable>() {
            @Override
            public void beforeEach(Cascadeable t) {
                //if(t.getLevel()>1)return;
                builder.append("<li>");
            }

            @Override
            public void beforeEachChild(Cascadeable t, Collection<? extends Cascadeable> collection) {
                //if(t.getLevel()==1)return;
                builder.append("<ul>");
            }

            @Override
            public void afterEachChild(Cascadeable t, Collection<? extends Cascadeable> collection) {
                //if(t.getLevel()==1)return;
                builder.append("</ul>");
            }

            @Override
            public void atferEach(Cascadeable t) {
                //if(t.getLevel()>1)return;
                builder.append("</li>");
            }

            @Override
            public boolean each(int i, Cascadeable t) {
                //if(t.getLevel()>1)return true;
                VisitorMenu menu=(VisitorMenu)t;
                boolean hasAuth=false;
                for(RoleAuthorizationMenu auth:authors){
                    if(auth.getMenu().getEntityId().equals(menu.getEntityId())){
                        if(auth.isVisit()){
                            hasAuth=auth.isVisit();
                            break;
                        }
                    }
                }
                String parentId="";
                if(menu.getParent()!=null){
                    parentId=menu.getParent().getEntityId();
                }
                if(hasAuth){
                    builder.append("<input class='changePrivilege' type='checkbox' id='"
                            +CoderUtil.stringToHex(menu.getEntityId(),"utf-8",true)+"' parent='"+
                            CoderUtil.stringToHex(parentId,"utf-8",true)
                            +"' checked='checked'><span href='#' title='"+menu.getDetail()+"' >"+menu.getName()+"</span>");
                }else{
                    builder.append("<input class='changePrivilege' type='checkbox' id='"
                            +CoderUtil.stringToHex(menu.getEntityId(),"utf-8",true)+"' parent='"+
                            CoderUtil.stringToHex(parentId,"utf-8",true)
                            +"'><span href='#' title='"+menu.getDetail()+"' >"+menu.getName()+"</span>");
                }
                return true;
            }
        });
        builder.append("</ul>");
        interactive.setRequestAttribute("privilegeList", builder.toString());
        interactive.setRequestAttribute("roleId",entityId);
        return rootPath+"/prilivigeDatas";
    }
    @RequestMapping(value = "/visitorRole/changePrivilege",method = RequestMethod.POST)
    public void changePrivilege(@IWebInteractive WebInteractive interactive,HttpServletResponse response) throws IOException, JsonParserException {
        JsonMessageCreator msg=getSuccessMessage();
        String roleId=interactive.getStringParameter("roleid", "");
        String privileges=interactive.getHttpServletRequest().getParameter("privileges");//interactive.getStringParameter("privileges", "");
        ArrayJsonParser jsonParsr=new ArrayJsonParser(privileges);
        StreamCollection<Map<String, JavaDataTyper>> maplist=jsonParsr.doParser();
        List<VisitorMenu> trueMenus=new ArrayList<VisitorMenu>();
        List<VisitorMenu> falseMenus=new ArrayList<VisitorMenu>();
        maplist.forEachByOrder((i,d)->{
            VisitorMenu menu=new VisitorMenu();
            String menuId=d.get("menuid").stringValue();
            menu.setEntityId(CoderUtil.hexToString(menuId,"utf-8"));
            if(d.get("auth").booleanValue(false)){
                trueMenus.add(menu);
            }else{
                falseMenus.add(menu);
            }
        });
        roleAuthorizationMenuService.updateRoleAuthroity(roleId, trueMenus, true);
        roleAuthorizationMenuService.updateRoleAuthroity(roleId, falseMenus, false);
        interactive.writeUTF8Text(msg.build());
    }
    @RequestMapping("/visitorUser")
    @AccessControl(name = "用户管理", weight = 1.2f, detail = "管理系统中的用户", code = rootPath
            + "/visitorUser", parent = rootPath)
    public String visitorUser(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/visitorUser");
        mainTablePage(interactive,visitorUserService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/visitorUser";
    }

    @RequestMapping("/visitorUser/new")
    @AccessControl(name = "新增用户", weight = 1.21f, detail = "新增用户", code = rootPath
            + "/visitorUser/new", parent = rootPath+"/visitorUser")
    public String visitorUserNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,visitorUserService,3);
        setDefaultPage(interactive,rootPath+"/visitorUser");
        return rootPath+"/visitorUserForm";
    }
    @AccessControl(name = "修改用户", weight = 1.22f, detail = "修改用户", code = rootPath
            + "/visitorUser/modify", parent = rootPath+"/visitorUser")
    @RequestMapping("/visitorUser/modify")
    public String visitorUserModify(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,visitorUserService,3);
        setDefaultPage(interactive,rootPath+"/visitorUser");
        return rootPath+"/visitorUserForm";
    }

    @AccessControl(name = "删除用户", weight = 1.23f, detail = "删除用户", code = rootPath
            + "/visitorUser/delete", parent = rootPath+"/visitorUser")
    @RequestMapping("/visitorUser/delete")
    public void visitorUserDelete(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=getSuccessMessage();
        String entityId=interactive.getStringParameter("entityId","");
        try {
            visitorUserService.deleteById(entityId);
        } catch (ServiceException e) {
            catchServiceException(msg,e);
        }
        interactive.writeUTF8Text(msg.build());
    }
    @AccessControl(name = "重置密码", weight = 1.24f, detail = "重置用户密码", code = rootPath
            + "/visitorUser/resetPasswd", parent = rootPath+"/visitorUser")
    @RequestMapping("/visitorUser/resetPasswd")
    public void visitorUserResetPasswd(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        visitorUserService.resetPassword(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }

    @RequestMapping(value="/visitorUser/commit",method = RequestMethod.POST)
    public void visitorUserCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=executeNewOrUpdate(interactive,visitorUserService);
        interactive.writeUTF8Text(msg.build());
    }

}
