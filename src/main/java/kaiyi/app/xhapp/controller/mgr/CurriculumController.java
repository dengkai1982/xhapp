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
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.data.StringEditor;
import kaiyi.puer.db.orm.ORMException;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.NullQueryExpress;
import kaiyi.puer.db.query.OrderBy;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.h5ui.bean.DynamicGridInfo;
import kaiyi.puer.h5ui.entity.DocumentStorageEvent;
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
import java.util.Map;

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
    @Resource
    private CourseCommentService courseCommentService;
    @Resource
    private CourseProblemService courseProblemService;
    @Resource
    private FaceToFaceService faceToFaceService;
    @Resource
    private CourseOrderService courseOrderService;
    @Resource
    private AlreadyCourseService alreadyCourseService;
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
        Map<String, JavaDataTyper> params=interactive.getRequestParameterMap();
        String saveFileHex= DocumentStorageEvent.storageHexFileToPath(configureService.getStringValue(ConfigureItem.DOC_SAVE_PATH),
                params.get("photo").stringValue());
        params.put("photo",new JavaDataTyper(saveFileHex));
        JsonMessageCreator msg=executeNewOrUpdate(interactive,studentService,params);
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
    @AccessControl(name = "课程详情", weight = 3.26f, detail = "课程详情",
            code = rootPath+ "/course/detail", parent = rootPath+"/course",defaultAuthor = true)
    public String courseDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        Course course=detailPage(interactive,courseService,3);
        setDefaultPage(interactive,rootPath+"/course");
        QueryExpress queryExpress=new CompareQueryExpress("course",CompareQueryExpress.Compare.EQUAL,course);
        StreamCollection<Chapter> chapters=chapterService.getEntitys(queryExpress,new OrderBy(queryExpress.getPrefix(),"weight"));
        interactive.setRequestAttribute("chapters",chapters);
        interactive.getWebPage().setPageTitle("课程详情");
        interactive.setRequestAttribute("category",course.getCategory());
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

    @RequestMapping("/courseComment")
    @AccessControl(name = "课程评论", weight = 3.3f, detail = "查看课程评论内容", code = rootPath+ "/courseComment", parent = rootPath)
    public String courseComment(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/courseComment");
        mainTablePage(interactive,courseCommentService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/courseComment";
    }

    @RequestMapping("/courseComment/reply")
    @AccessControl(name = "回复评论", weight = 3.31f, detail = "回复课程评论",
            code = rootPath+ "/courseComment/reply", parent = rootPath+"/courseComment")
    public String courseCommentReply(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,courseCommentService,3);
        setDefaultPage(interactive,rootPath+"/courseComment");
        interactive.getWebPage().setPageTitle("课程评论回复");
        return rootPath+"/courseCommentReply";
    }

    @RequestMapping("/courseComment/detail")
    @AccessControl(name = "评论详情", weight = 3.32f, detail = "查看评论详情",
            code = rootPath+ "/courseComment/detail", parent = rootPath+"/courseComment",defaultAuthor = true)
    public String courseCommentDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,courseCommentService,3);
        setDefaultPage(interactive,rootPath+"/courseComment");
        interactive.getWebPage().setPageTitle("课程评论详情");
        return rootPath+"/courseCommentDetail";
    }

    @PostMapping("/courseComment/delete")
    @AccessControl(name = "删除评论", weight = 3.33f, detail = "删除评论",
            code = rootPath+ "/courseComment/delete", parent = rootPath+"/courseComment")
    public void courseCommentDelete(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        JsonMessageCreator jmc=getSuccessMessage();
        courseCommentService.deleteForPrimary(entityId);
        interactive.writeUTF8Text(jmc.build());
    }

    @PostMapping("/courseComment/doReply")
    public void CourseCommentDoReply(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        doReplay(interactive,courseCommentService);
    }
    //课程提问
    @RequestMapping("/courseProblem")
    @AccessControl(name = "课程问题", weight = 3.4f, detail = "查看课程问题列表", code = rootPath+ "/courseProblem", parent = rootPath)
    public String courseProblem(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/courseProblem");
        FormElementHidden[] formElementHiddens=null;
        String onlyEntityId=interactive.getStringParameter("onlyEntityId","");
        if(StringEditor.notEmpty(onlyEntityId)){
            formElementHiddens=new FormElementHidden[]{
                    new FormElementHidden("onlyEntityId",onlyEntityId)
            };
        }
        mainTablePage(interactive,courseProblemService,null,formElementHiddens,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/courseProblem";
    }

    @RequestMapping("/courseProblem/reply")
    @AccessControl(name = "问题回复", weight = 3.41f, detail = "回复课程为题",
            code = rootPath+ "/courseProblem/reply", parent = rootPath+"/courseProblem")
    public String courseProblemReply(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,courseProblemService,3);
        setDefaultPage(interactive,rootPath+"/courseProblem");
        interactive.getWebPage().setPageTitle("回复课程问题");
        return rootPath+"/courseProblemReply";
    }

    @RequestMapping("/courseProblem/detail")
    @AccessControl(name = "问题详情", weight = 3.42f, detail = "查看课程问题详情",
            code = rootPath+ "/courseProblem/detail", parent = rootPath+"/courseProblem",defaultAuthor = true)
    public String courseProblemDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,courseProblemService,3);
        setDefaultPage(interactive,rootPath+"/courseProblem");
        interactive.getWebPage().setPageTitle("课程问题详情");
        return rootPath+"/courseProblemDetail";
    }

    @PostMapping("/courseProblem/doReply")
    public void courseProblemDoReply(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException{
        doReplay(interactive,courseProblemService);
    }
    //面授预约
    @RequestMapping("/faceToFace")
    @AccessControl(name = "面授预约", weight = 3.5f, detail = "查看面授预约请求", code = rootPath+ "/faceToFace", parent = rootPath)
    public String faceToFace(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/faceToFace");
        FormElementHidden[] formElementHiddens=null;
        String onlyEntityId=interactive.getStringParameter("onlyEntityId","");
        if(StringEditor.notEmpty(onlyEntityId)){
            formElementHiddens=new FormElementHidden[]{
                    new FormElementHidden("onlyEntityId",onlyEntityId)
            };
        }
        mainTablePage(interactive,faceToFaceService,null,formElementHiddens,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/faceToFace";
    }

    @RequestMapping("/faceToFace/reply")
    @AccessControl(name = "预约回复", weight = 3.51f, detail = "回复面试预约",
            code = rootPath+ "/faceToFace/reply", parent = rootPath+"/faceToFace")
    public String faceToFaceReply(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,faceToFaceService,3);
        setDefaultPage(interactive,rootPath+"/courseProblem");
        interactive.getWebPage().setPageTitle("预约面授回复");
        return rootPath+"/faceToFaceReply";
    }

    @RequestMapping("/faceToFace/detail")
    @AccessControl(name = "预约面授详情", weight = 3.52f, detail = "查看预约面授详情",
            code = rootPath+ "/faceToFace/detail", parent = rootPath+"/faceToFace",defaultAuthor = true)
    public String faceToFaceDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,faceToFaceService,3);
        setDefaultPage(interactive,rootPath+"/faceToFace");
        interactive.getWebPage().setPageTitle("课程问题详情");
        return rootPath+"/faceToFaceDetail";
    }

    @PostMapping("/faceToFace/doReply")
    public void faceToFaceDoReply(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException{
        doReplay(interactive,faceToFaceService);
    }

    private void doReplay(@IWebInteractive WebInteractive interactive,ReplyService replyService) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        String replyContent=interactive.getStringParameter("replyContent","");
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            replyService.reply(entityId,getLoginedUser(interactive).getEntityId(),replyContent);
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Text(jmc.build());
    }

    @RequestMapping("/mediaLibrary")
    @AccessControl(name = "媒体库", weight = 3.6f, detail = "管理上传媒体文件", code = rootPath+ "/mediaLibrary", parent = rootPath)
    public String mediaLibrary(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/mediaLibrary");
        mainTablePage(interactive,mediaLibraryService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/mediaLibrary";
    }
    @RequestMapping("/mediaLibrary/new")
    @AccessControl(name = "新增媒体库", weight = 3.61f, detail = "新增媒体库",
            code = rootPath+ "/mediaLibrary/new", parent = rootPath+"/mediaLibrary")
    public String mediaLibraryNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,mediaLibraryService,3);
        setDefaultPage(interactive,rootPath+"/mediaLibrary");
        interactive.setRequestAttribute("aliyunUserId",configureService.getStringValue(ConfigureItem.ALIYUN_USER_ID));
        return rootPath+"/mediaLibraryForm";
    }
    @RequestMapping("/mediaLibrary/delete")
    @AccessControl(name = "删除媒体库", weight = 3.62f, detail = "删除媒体库文件",
            code = rootPath+ "/mediaLibrary/delete", parent = rootPath+"/mediaLibrary")
    public void mediaLibraryDelete(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        JsonMessageCreator jmc=getSuccessMessage();
        try{
            mediaLibraryService.deleteForPrimary(entityId);
        }catch(ORMException e){
            jmc.setMsg(e.getMessage());
            jmc.setCode(JsonMessageCreator.FAIL);
        }
        interactive.writeUTF8Text(jmc.build());
    }

    @PostMapping("/getPlayAddress")
    public void getPlayAddress(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        MediaLibrary mediaLibrary=mediaLibraryService.findForPrimary(entityId);
        JsonMessageCreator jmc=getSuccessMessage();
        DefaultAcsClient client = null;
        try {
            if(mediaLibrary.isOnline()){
                client = AliyunVodHelper.initVodClient(
                        configureService.getStringValue(ConfigureItem.ALIYUN_VOD_ACCESS_KEY_ID),
                        configureService.getStringValue(ConfigureItem.ALIYUN_VOD_ACCESS_KEY_SECRET));
                String url=AliyunVodHelper.getPlayUrl(client,mediaLibrary.getVideoId());
                jmc.setBody(url);
            }else{
                jmc.setBody(mediaLibrary.getUrl());
            }
        } catch (ClientException e) {
            jmc.setCode(JsonMessageCreator.FAIL);
            jmc.setMsg(e.getMessage());
        }
        interactive.writeUTF8Text(jmc.build());
    }

    @PostMapping("/commitMediaLibrary")
    public void commitMediaLibrary(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String title=interactive.getStringParameter("title","");
        boolean online=interactive.getBoolean("online","true",false);
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            if(online){
                String videoId=interactive.getStringParameter("videoId","");
                mediaLibraryService.newOnlineMediaLibrary(title,videoId);
            }else{
                String url=interactive.getStringParameter("url","");
                mediaLibraryService.newUrlMedialibrary(title,url);
            }
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


    @RequestMapping("/categoryEnableQuery")
    public String categoryEnableQuery(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        StreamCollection<Category> categories=categoryService.getEnableRootCategory();
        String treeData=categoryService.toJsonTree(categories,"categoryQueryTree.ftlh");
        if(StringEditor.isEmpty(treeData)){
            treeData=JsonCreator.EMPTY_JSON;
        }
        interactive.setRequestAttribute("referenceQueryName",interactive.getStringParameter("referenceQueryName",""));
        interactive.setRequestAttribute("referenceQueryId",interactive.getStringParameter("referenceQueryId",""));
        interactive.setRequestAttribute("treeData",treeData);
        return rootPath+"/categoryQuery";
    }

    @RequestMapping("/courseOrder")
    @AccessControl(name = "课程订单", weight = 3.7f, detail = "查看课程订单", code = rootPath+ "/courseOrder", parent = rootPath)
    public String courseOrder(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/courseOrder");
        mainTablePage(interactive,courseOrderService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/courseOrder";
    }
    @RequestMapping("/courseOrder/detail")
    @AccessControl(name = "订单详情", weight = 3.71f, detail = "查看课程订单详情",
            code = rootPath+ "/courseOrder/detail", parent = rootPath+"/faceToFace",defaultAuthor = true)
    public String courseOrderDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,courseOrderService,3);
        setDefaultPage(interactive,rootPath+"/courseOrder");
        interactive.getWebPage().setPageTitle("查看课程订单详情");
        return rootPath+"/courseOrderDetail";
    }
    @RequestMapping("/alreadyCourse")
    @AccessControl(name = "已购课程", weight = 3.8f, detail = "查看课程订单", code = rootPath+ "/alreadyCourse", parent = rootPath)
    public String alreadyCourse(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/alreadyCourse");
        mainTablePage(interactive,alreadyCourseService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/alreadyCourse";
    }
    @RequestMapping("/alreadyCourse/detail")
    @AccessControl(name = "已购课程详情", weight = 3.81f, detail = "查看课程订单详情",
            code = rootPath+ "/alreadyCourse/detail", parent = rootPath+"/alreadyCourse",defaultAuthor = true)
    public String alreadyCourseDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,alreadyCourseService,3);
        setDefaultPage(interactive,rootPath+"/alreadyCourse");
        interactive.getWebPage().setPageTitle("查看课程订单详情");
        return rootPath+"/alreadyCourseDetail";
    }


}
