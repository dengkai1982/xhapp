package kaiyi.app.xhapp.controller.mgr;

import kaiyi.app.xhapp.entity.pages.enums.LinkType;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.pages.DisplayMapService;
import kaiyi.app.xhapp.service.pub.ConfigureService;
import kaiyi.app.xhapp.service.pub.NoticeService;
import kaiyi.puer.commons.access.AccessControl;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.time.DateTimeUtil;
import kaiyi.puer.h5ui.bean.DynamicGridInfo;
import kaiyi.puer.h5ui.entity.DocumentStorageEvent;
import kaiyi.puer.h5ui.service.DocumentService;
import kaiyi.puer.json.creator.JsonMessageCreator;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping(PageController.rootPath)
@AccessControl(name = "页面展示", weight = 2f, detail = "配置页面相关信息", code = PageController.rootPath)
public class PageController extends ManagerController {
    public static final String rootPath=prefix+"/pages";
    @Resource
    private DisplayMapService displayMapService;
    @Resource
    private ConfigureService configureService;
    @Resource
    private NoticeService noticeService;
    @RequestMapping("/displayMap")
    @AccessControl(name = "展示图片", weight = 2.1f, detail = "设置展示图片", code = rootPath+ "/displayMap", parent = rootPath)
    public String displayMap(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/displayMap");
        mainTablePage(interactive,displayMapService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/displayMap";
    }
    @RequestMapping("/displayMap/new")
    @AccessControl(name = "新增展示图片", weight = 2.11f,code = rootPath+ "/displayMap/new",
            parent = rootPath+"/displayMap")
    public String displayMapNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,displayMapService,3);
        setDefaultPage(interactive,rootPath+"/displayMap");
        return rootPath+"/displayMapForm";
    }
    @RequestMapping("/displayMap/modify")
    @AccessControl(name = "修改展示图片", weight = 2.12f,code = rootPath+ "/displayMap/modify",
            parent = rootPath+"/displayMap")
    public String displayMapModify(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,displayMapService,3);
        setDefaultPage(interactive,rootPath+"/displayMap");
        return rootPath+"/displayMapForm";
    }
    @PostMapping("/displayMap/commit")
    public void displayMapCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        Map<String,JavaDataTyper> params=interactive.getRequestParameterMap();
        String saveFileHex=DocumentStorageEvent.storageHexFileToPath(configureService.getStringValue(ConfigureItem.DOC_SAVE_PATH),
                params.get("imagePath").stringValue());
        params.put("imagePath",new JavaDataTyper(saveFileHex));
        LinkType linkType=params.get("linkType").enumValueByOrdinal(LinkType.class);
        if(linkType.equals(LinkType.content)){
            String storagePath=configureService.getStringValue(ConfigureItem.DOC_SAVE_PATH);
            String serverPath=configureService.getStringValue(ConfigureItem.DOC_SERVER_PREFIX);
            String content=params.get("content").stringValue();
            content=DocumentService.replaceImageSrc(content,AccessController.getAccessTempFilePathPrefix(interactive),
                    storagePath,serverPath);
            params.put("content",new JavaDataTyper(content));
        }else{
            params.remove("content");
        }
        JsonMessageCreator msg=executeNewOrUpdate(interactive,displayMapService,params);
        interactive.writeUTF8Text(msg.build());
    }

    @RequestMapping("/notice")
    @AccessControl(name = "系统公告", weight = 2.2f, code = rootPath+ "/notice", parent = rootPath)
    public String notice(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/notice");
        mainTablePage(interactive,noticeService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/notice";
    }
    @RequestMapping("/notice/new")
    @AccessControl(name = "发布公告", weight = 2.21f,code = rootPath+ "/notice/new",
            parent = rootPath+"/notice")
    public String noticeNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,noticeService,3);
        setDefaultPage(interactive,rootPath+"/notice");
        return rootPath+"/noticeForm";
    }
    @RequestMapping("/notice/modify")
    @AccessControl(name = "修改公告", weight = 2.22f,code = rootPath+ "/notice/modify",
            parent = rootPath+"/notice")
    public String noticeModify(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,noticeService,3);
        setDefaultPage(interactive,rootPath+"/notice");
        return rootPath+"/noticeForm";
    }
    @RequestMapping("/notice/delete")
    @AccessControl(name = "删除公告", weight = 2.23f,code = rootPath+ "/notice/delete",
            parent = rootPath+"/notice")
    public void noticeDelete(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        noticeService.deleteById(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }
    @PostMapping("/notice/commit")
    public void noticeCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        Map<String,JavaDataTyper> params=interactive.getRequestParameterMap();
        String storagePath=configureService.getStringValue(ConfigureItem.DOC_SAVE_PATH);
        String serverPath=configureService.getStringValue(ConfigureItem.DOC_SERVER_PREFIX);
        String content=params.get("content").stringValue();
        content=DocumentService.replaceImageSrc(content,AccessController.getAccessTempFilePathPrefix(interactive),
                storagePath,serverPath);
        params.put("content",new JavaDataTyper(content));
        params.put("publishDate",new JavaDataTyper(DateTimeUtil.yyyyMMddHHmmss.format(new Date())));
        JsonMessageCreator msg=executeNewOrUpdate(interactive,noticeService,params,storagePath);
        interactive.writeUTF8Text(msg.build());
    }
}
