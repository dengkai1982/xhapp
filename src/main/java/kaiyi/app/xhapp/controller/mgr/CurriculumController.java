package kaiyi.app.xhapp.controller.mgr;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import kaiyi.app.xhapp.entity.access.enums.MemberShip;
import kaiyi.app.xhapp.entity.curriculum.Category;
import kaiyi.app.xhapp.entity.curriculum.Chapter;
import kaiyi.app.xhapp.entity.curriculum.Course;
import kaiyi.app.xhapp.entity.curriculum.MediaLibrary;
import kaiyi.app.xhapp.entity.pojo.CreateUploadVideoRequestInfo;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.AliyunVodHelper;
import kaiyi.app.xhapp.service.curriculum.*;
import kaiyi.app.xhapp.service.pub.ConfigureService;
import kaiyi.puer.commons.access.AccessControl;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.StringEditor;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.NullQueryExpress;
import kaiyi.puer.db.query.OrderBy;
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
    @Resource
    private ChapterService chapterService;
    @Resource
    private CourseMovieService courseMovieService;
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
                    new FormElementHidden("limitCategory","true"),
                    new FormElementHidden("currentCategory","")
                },
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        StreamCollection<Category> categories=categoryService.getRootCategory();
        String treeData=categoryService.toJsonTree(categories,"categoryTreeAction.ftlh");
        if(StringEditor.isEmpty(treeData)){
            treeData=JsonCreator.EMPTY_JSON;
        }
        interactive.setRequestAttribute("treeData",treeData);
        String expandId=interactive.getStringParameter("expandId","");
        if(StringEditor.notEmpty(expandId)){
            interactive.setRequestAttribute("expandId",expandId);
        }
        return rootPath+"/course";
    }
    //TODO 新增课程和新增章节放开
    @RequestMapping("/course/new")
    @AccessControl(name = "新增课程", weight = 3.21f, detail = "添加新的课程",
            code = rootPath+ "/course/new", parent = rootPath+"/course")
    public String courseNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,courseService,3);
        setDefaultPage(interactive,rootPath+"/course");
        String categoryId=interactive.getStringParameter("categoryId","");
        Category category=categoryService.findForPrimary(categoryId);
        interactive.setRequestAttribute("category",category);
        interactive.setRequestAttribute("memberShips",MemberShip.values());
        return rootPath+"/courseForm";
    }
    @RequestMapping("/course/modify")
    @AccessControl(name = "修改课程", weight = 3.22f, detail = "修改已有课程信息",
            code = rootPath+ "/course/modify", parent = rootPath+"/course")
    public String courseModify(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        Course course=newOrEditPage(interactive,courseService,3);
        setDefaultPage(interactive,rootPath+"/course");
        interactive.setRequestAttribute("category",course.getCategory());
        return rootPath+"/courseForm";
    }

    @PostMapping("/course/commit")
    public void courseCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=executeNewOrUpdate(interactive,courseService,configureService.getStringValue(ConfigureItem.DOC_SAVE_PATH));
        interactive.writeUTF8Text(msg.build());
    }

    @RequestMapping("/course/category/newOrEdit")
    @AccessControl(name = "新增修改类别", weight = 3.23f, detail = "新增修改课程类别",
            code = rootPath+ "/course/category/newOrEdit", parent = rootPath+"/course")
    public void courseCategoryNewOrEdit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=executeNewOrUpdate(interactive,categoryService);
        interactive.writeUTF8Text(msg.build());
    }

    @RequestMapping("/course/category/enableOrDisable")
    @AccessControl(name = "启用停用类别", weight = 3.23f, detail = "新增修改课程类别",
            code = rootPath+ "/course/category/enableOrDisable", parent = rootPath+"/course")
    public void courseCategoryEnableOrDisable(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        categoryService.enableOrDisable(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }

    @RequestMapping("/course/chapter/editor")
    @AccessControl(name = "编辑章节", weight = 3.24f, detail = "编辑课程章节",
            code = rootPath+ "/course/chapter/editor", parent = rootPath+"/course")
    public String courseChapterEditor(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/course");
        String entityId=interactive.getStringParameter("entityId","");
        Course course=courseService.findForPrimary(entityId);
        interactive.setRequestAttribute("course",course);
        QueryExpress queryExpress=new CompareQueryExpress("course",CompareQueryExpress.Compare.EQUAL,course);
        StreamCollection<Chapter> chapters=chapterService.getEntitys(queryExpress,new OrderBy(queryExpress.getPrefix(),"weight"));
        interactive.setRequestAttribute("chapters",chapters);
        return rootPath+"/chapterEditor";
    }
    @RequestMapping("/course/chapter/changeSale")
    @AccessControl(name = "课程上下架", weight = 3.25f, detail = "处理章节上下架",
            code = rootPath+ "/course/chapter/changeSale", parent = rootPath+"/course")
    public void changeSale(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        courseService.changeSale(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }

    @RequestMapping("/course/detail")
    @AccessControl(name = "课程详情", weight = 3.26f, detail = "处理章节上下架",
            code = rootPath+ "/course/detail", parent = rootPath+"/course",defaultAuthor = true)
    public String courseDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        Course course=detailPage(interactive,courseService,3);
        setDefaultPage(interactive,rootPath+"/course");
        QueryExpress queryExpress=new CompareQueryExpress("course",CompareQueryExpress.Compare.EQUAL,course);
        StreamCollection<Chapter> chapters=chapterService.getEntitys(queryExpress,new OrderBy(queryExpress.getPrefix(),"weight"));
        interactive.setRequestAttribute("chapters",chapters);
        interactive.getWebPage().setPageTitle("课程详情");
        return rootPath+"/courseDetail";
    }

    @PostMapping("/course/commitChapter")
    public void commitChapter(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=executeNewOrUpdate(interactive,chapterService);
        interactive.writeUTF8Text(msg.build());
    }
    @PostMapping("/course/commitCourseMovie")
    public void commitCourseMovie(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=executeNewOrUpdate(interactive,courseMovieService);
        interactive.writeUTF8Text(msg.build());
    }

    @PostMapping("/course/deleteChapter")
    public void deleteChapter(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        chapterService.deleteByEntityId(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }

    @PostMapping("/course/deleteCourseMovie")
    public void deleteCourseMovie(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        courseMovieService.deleteForPrimary(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
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
    @RequestMapping("/mediaLibrary/delete")
    @AccessControl(name = "删除媒体库", weight = 3.92f, detail = "删除媒体库文件",
            code = rootPath+ "/mediaLibrary/delete", parent = rootPath+"/mediaLibrary")
    public void mediaLibraryDelete(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        String entityId=interactive.getStringParameter("entityId","");
        //TODO 有关联后再来做

    }

    @PostMapping("/getPlayAddress")
    public void getPlayAddress(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        MediaLibrary mediaLibrary=mediaLibraryService.findForPrimary(entityId);
        JsonMessageCreator jmc=getSuccessMessage();
        DefaultAcsClient client = null;
        try {
            client = AliyunVodHelper.initVodClient(
                    configureService.getStringValue(ConfigureItem.ALIYUN_VOD_ACCESS_KEY_ID),
                    configureService.getStringValue(ConfigureItem.ALIYUN_VOD_ACCESS_KEY_SECRET));
            String url=AliyunVodHelper.getPlayUrl(client,mediaLibrary.getVideoId());
            jmc.setBody(url);
        } catch (ClientException e) {
            jmc.setCode(JsonMessageCreator.FAIL);
            jmc.setMsg(e.getMessage());
        }
        interactive.writeUTF8Text(jmc.build());
    }

    @PostMapping("/commitMediaLibrary")
    public void commitMediaLibrary(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String title=interactive.getStringParameter("title","");
        String videoId=interactive.getStringParameter("videoId","");
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            mediaLibraryService.newMediaLibrary(title,videoId,"");
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Json(jmc);
    }

    @PostMapping("/refreshUploadVideoRequest")
    public void refreshUploadVideoRequest(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws Exception {
        DefaultAcsClient client=AliyunVodHelper.initVodClient(
                configureService.getStringValue(ConfigureItem.ALIYUN_VOD_ACCESS_KEY_ID),
                configureService.getStringValue(ConfigureItem.ALIYUN_VOD_ACCESS_KEY_SECRET));
        String videoId=interactive.getStringParameter("videoId","");
        RefreshUploadVideoResponse resp=AliyunVodHelper.refreshUploadVideo(client,videoId);
        MapJsonCreator jsonCreator=new MapJsonCreator();
        jsonCreator.put("VideoId",new StringJsonCreator(resp.getVideoId()));
        jsonCreator.put("UploadAddress",new StringJsonCreator(resp.getUploadAddress()));
        jsonCreator.put("UploadAuth",new StringJsonCreator(resp.getUploadAuth()));
        interactive.writeUTF8Json(jsonCreator);
    }
    @PostMapping("/uploadVideoRequest")
    public void uploadVideoRequest(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws Exception {
        DefaultAcsClient client=AliyunVodHelper.initVodClient(
                configureService.getStringValue(ConfigureItem.ALIYUN_VOD_ACCESS_KEY_ID),
                configureService.getStringValue(ConfigureItem.ALIYUN_VOD_ACCESS_KEY_SECRET));
        String title=interactive.getStringParameter("title","");
        String fileName=interactive.getStringParameter("fileName","");
        CreateUploadVideoRequestInfo info=new CreateUploadVideoRequestInfo();
        info.setTitle(title);
        info.setFileName(fileName);
        info.setCateId(configureService.getStringValue(ConfigureItem.VOD_UPLOAD_CATE_ID));
        info.setTemplateId(configureService.getStringValue(ConfigureItem.VOD_UPLOAD_TEMPLATE_ID));
        CreateUploadVideoResponse resp=AliyunVodHelper.createUploadVideo(client,info);
        MapJsonCreator jsonCreator=new MapJsonCreator();
        jsonCreator.put("VideoId",new StringJsonCreator(resp.getVideoId()));
        jsonCreator.put("UploadAddress",new StringJsonCreator(resp.getUploadAddress()));
        jsonCreator.put("UploadAuth",new StringJsonCreator(resp.getUploadAuth()));
        interactive.writeUTF8Json(jsonCreator);
    }

}
