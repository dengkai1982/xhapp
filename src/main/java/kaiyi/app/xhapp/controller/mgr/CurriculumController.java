package kaiyi.app.xhapp.controller.mgr;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import kaiyi.app.xhapp.entity.curriculum.Category;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.AliyunVodHelper;
import kaiyi.app.xhapp.service.curriculum.CourseService;
import kaiyi.app.xhapp.service.curriculum.MediaLibraryService;
import kaiyi.app.xhapp.service.curriculum.TeacherService;
import kaiyi.app.xhapp.service.curriculum.CategoryService;
import kaiyi.app.xhapp.service.pub.ConfigureService;
import kaiyi.puer.commons.access.AccessControl;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.StringEditor;
import kaiyi.puer.db.query.NullQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.h5ui.bean.DynamicGridInfo;
import kaiyi.puer.json.JsonCreator;
import kaiyi.puer.json.creator.JsonMessageCreator;
import kaiyi.puer.json.creator.MapJsonCreator;
import kaiyi.puer.json.creator.StringJsonCreator;
import kaiyi.puer.web.elements.FormElementHidden;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(CurriculumController.rootPath)
@AccessControl(name = "课程管理", weight = 3f, detail = "管理课程相关内容", code = CurriculumController.rootPath)
public class CurriculumController extends ManagerController{
    public static final String rootPath=prefix+"/curriculum";

    @Resource
    private CategoryService categoryService;
    @Resource
    private TeacherService studentService;
    @Resource
    private CourseService courseService;
    @Resource
    private MediaLibraryService mediaLibraryService;
    @Resource
    private ConfigureService configureService;
    @RequestMapping("/teacher")
    @AccessControl(name = "授课教师", weight = 3.1f, detail = "管理授课教师", code = rootPath+ "/teacher", parent = rootPath)
    public String teacher(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/teacher");
        mainTablePage(interactive,studentService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/teacher";
    }
    @RequestMapping("/teacher/new")
    @AccessControl(name = "新增授课教师", weight = 3.11f, detail = "添加新的授课教师",
            code = rootPath+ "/teacher/new", parent = rootPath+"/teacher")
    public String teacherNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,studentService,3);
        setDefaultPage(interactive,rootPath+"/teacher");
        return rootPath+"/teacherForm";
    }
    @RequestMapping("/teacher/modify")
    @AccessControl(name = "编辑授课教师", weight = 3.12f, detail = "编辑授课教师信息",
            code = rootPath+ "/teacher/modify", parent = rootPath+"/teacher")
    public String teacherModify(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,studentService,3);
        setDefaultPage(interactive,rootPath+"/teacher");
        return rootPath+"/teacherForm";
    }
    @RequestMapping("/teacher/detail")
    @AccessControl(name = "授课教师详情", weight = 3.13f, detail = "授课教师详情",
            code = rootPath+ "/teacher/detail", parent = rootPath+"/teacher")
    public String teacherDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,studentService,3);
        setDefaultPage(interactive,rootPath+"/teacher");
        return rootPath+"/teacherDetail";
    }
    @PostMapping("/teacher/commit")
    public void teacherCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=executeNewOrUpdate(interactive,studentService);
        interactive.writeUTF8Text(msg.build());
    }

    @RequestMapping("/course")
    @AccessControl(name = "课程管理", weight = 3.2f, detail = "管理发布的课程", code = rootPath+ "/course", parent = rootPath)
    public String course(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/course");
        QueryExpress queryExpress=new NullQueryExpress("category",NullQueryExpress.NullCondition.IS_NULL);
        mainTablePage(interactive,courseService,queryExpress,new FormElementHidden[]{
                    new FormElementHidden("limitCategory","true")
                },
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        StreamCollection<Category> categories=categoryService.getRootCategory();
        String treeData=categoryService.toJsonTree(categories,"categoryTreeAction.ftlh");
        if(StringEditor.isEmpty(treeData)){
            treeData=JsonCreator.EMPTY_JSON;
        }
        interactive.setRequestAttribute("treeData",treeData);
        return rootPath+"/course";
    }
    @RequestMapping("/course/new")
    @AccessControl(name = "新增课程", weight = 3.21f, detail = "添加新的课程",
            code = rootPath+ "/course/new", parent = rootPath+"/course")
    public String courseNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,studentService,3);
        setDefaultPage(interactive,rootPath+"/course");
        return rootPath+"/courseForm";
    }
    @RequestMapping("/course/modify")
    @AccessControl(name = "修改课程", weight = 3.22f, detail = "修改已有课程信息",
            code = rootPath+ "/course/modify", parent = rootPath+"/course")
    public String courseModify(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,studentService,3);
        setDefaultPage(interactive,rootPath+"/teacher");
        return rootPath+"/courseForm";
    }

    @RequestMapping("/mediaLibrary")
    @AccessControl(name = "媒体库", weight = 3.9f, detail = "管理上传媒体文件", code = rootPath+ "/mediaLibrary", parent = rootPath)
    public String mediaLibrary(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/mediaLibrary");
        mainTablePage(interactive,mediaLibraryService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/mediaLibrary";
    }
    @RequestMapping("/mediaLibrary/new")
    @AccessControl(name = "新增媒体库", weight = 3.91f, detail = "新增媒体库",
            code = rootPath+ "/mediaLibrary/new", parent = rootPath+"/mediaLibrary")
    public String mediaLibraryNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,mediaLibraryService,3);
        setDefaultPage(interactive,rootPath+"/mediaLibrary");
        interactive.setRequestAttribute("aliyunUserId",configureService.getStringValue(ConfigureItem.ALIYUN_USER_ID));
        return rootPath+"/mediaLibraryForm";
    }
    @RequestMapping("/mediaLibrary/modify")
    @AccessControl(name = "修改媒体库", weight = 3.92f, detail = "修改已有课程信息",
            code = rootPath+ "/mediaLibrary/modify", parent = rootPath+"/mediaLibrary")
    public String mediaLibraryModify(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,mediaLibraryService,3);
        setDefaultPage(interactive,rootPath+"/mediaLibrary");
        interactive.setRequestAttribute("aliyunUserId",configureService.getStringValue(ConfigureItem.ALIYUN_USER_ID));
        return rootPath+"/mediaLibraryForm";
    }
    @RequestMapping("/uploadVideoRequest")
    public void uploadVideoRequest(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws Exception {
        DefaultAcsClient client=AliyunVodHelper.initVodClient(
                configureService.getStringValue(ConfigureItem.ALIYUN_VOD_ACCESS_KEY_ID),
                configureService.getStringValue(ConfigureItem.ALIYUN_VOD_ACCESS_KEY_SECRET));
        CreateUploadVideoResponse resp=AliyunVodHelper.createUploadVideo(client);
        MapJsonCreator jsonCreator=new MapJsonCreator();
        jsonCreator.put("VideoId",new StringJsonCreator(resp.getVideoId()));
        jsonCreator.put("UploadAddress",new StringJsonCreator(resp.getUploadAddress()));
        jsonCreator.put("UploadAuth",new StringJsonCreator(resp.getUploadAuth()));
        interactive.writeUTF8Json(jsonCreator);
    }

}
