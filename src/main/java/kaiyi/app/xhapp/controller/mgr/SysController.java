package kaiyi.app.xhapp.controller.mgr;

import kaiyi.app.xhapp.entity.pub.Configure;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.pub.ConfigureService;
import kaiyi.app.xhapp.service.sys.ConsultationService;
import kaiyi.app.xhapp.service.sys.QNumberManagerService;
import kaiyi.puer.commons.access.AccessControl;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.utils.EnumUtils;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.h5ui.bean.DynamicGridInfo;
import kaiyi.puer.json.creator.JsonMessageCreator;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping(SysController.rootPath)
@AccessControl(name = "系统", weight = 7f, detail = "系统其它功能管理", code = SysController.rootPath)
public class SysController extends ManagerController {
    public static final String rootPath=prefix+"/sys";
    @Resource
    private ConsultationService consultationService;
    @Resource
    private QNumberManagerService qNumberManagerService;
    @Resource
    private ConfigureService configureService;
    @RequestMapping("/consultation")
    @AccessControl(name = "留言咨询", weight = 7.1f, detail = "查看留言咨询", code = rootPath+ "/consultation", parent = rootPath)
    public String consultation(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/courseComment");
        mainTablePage(interactive,consultationService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/consultation";
    }

    @RequestMapping("/consultation/reply")
    @AccessControl(name = "留言回复", weight = 7.11f, detail = "回复留言",
            code = rootPath+ "/consultation/reply", parent = rootPath+"/consultation")
    public String consultationReply(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,consultationService,3);
        setDefaultPage(interactive,rootPath+"/consultation");
        interactive.getWebPage().setPageTitle("留言咨询回复");
        return rootPath+"/consultationReply";
    }

    @RequestMapping("/consultation/detail")
    @AccessControl(name = "留言详情详情", weight = 7.12f, detail = "查看评论详情",
            code = rootPath+ "/consultation/detail", parent = rootPath+"/consultation",defaultAuthor = true)
    public String consultationDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,consultationService,3);
        setDefaultPage(interactive,rootPath+"/consultation");
        interactive.getWebPage().setPageTitle("留言咨询详情");
        return rootPath+"/consultationDetail";
    }

    @PostMapping("/consultation/doReply")
    public void consultationDoReply(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String replyContent=interactive.getStringParameter("replyContent","");
        String entityId=interactive.getStringParameter("entityId","");
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            consultationService.reply(entityId,getLoginedUser(interactive).getEntityId(),replyContent);
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Text(jmc.build());
    }

    @RequestMapping("/qNumberManager")
    @AccessControl(name = "QQ号码管理", weight = 7.2f, detail = "QQ号码管理", code = rootPath+ "/qNumberManager", parent = rootPath)
    public String qNumberManager(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/qNumberManager");
        mainTablePage(interactive,qNumberManagerService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/qNumberManager";
    }

    @RequestMapping("/qNumberManager/new")
    @AccessControl(name = "新增QQ号码", weight = 7.21f, detail = "新增QQ号码",
            code = rootPath+ "/qNumberManager/new", parent = rootPath+"/qNumberManager")
    public String qNumberManagerNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,qNumberManagerService,3);
        setDefaultPage(interactive,rootPath+"/qNumberManager");
        return rootPath+"/qNumberManagerForm";
    }
    @PostMapping("/qNumberManager/commit")
    public void qNumberManagerCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=executeNewOrUpdate(interactive,qNumberManagerService);
        interactive.writeUTF8Text(msg.build());
    }
    @AccessControl(name = "删除QQ号码", weight = 7.22f, detail = "删除QQ号码", code = rootPath
            + "/qNumberManager/delete", parent = rootPath+"/qNumberManager")
    @RequestMapping("/qNumberManager/delete")
    public void visitorUserDelete(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=getSuccessMessage();
        String entityId=interactive.getStringParameter("entityId","");
        qNumberManagerService.deleteForPrimary(entityId);
        interactive.writeUTF8Text(msg.build());
    }
    @RequestMapping("/businessConfigure")
    @AccessControl(name = "业务配置", weight = 7.3f, detail = "业务配置", code = rootPath+ "/businessConfigure", parent = rootPath)
    public String businessConfigure(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        StreamCollection<Configure> configures = configureService.getEntitys();
        Map<String,String> configureMap=new HashMap<>();
        configures.forEach(c->{
            configureMap.put(c.getItem().name(),c.getValue());
        });
        interactive.setRequestAttribute("configureMap",configureMap);
        return rootPath+"/businessConfigure";
    }
    @PostMapping("/commitConfigure")
    public void commitConfigure(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        Map<String,JavaDataTyper> params=interactive.getRequestParameterMap();
        params.forEach((k,v)->{
            ConfigureItem item=EnumUtils.getEnumValueByName(ConfigureItem.class,k);
            if(Objects.nonNull(item)){
                configureService.updateConfig(item,v.stringValue());
            }
        });
        interactive.writeUTF8Text(getSuccessMessage().build());
    }
}
