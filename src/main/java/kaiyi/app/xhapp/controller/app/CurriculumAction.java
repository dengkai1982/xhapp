package kaiyi.app.xhapp.controller.app;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import kaiyi.app.xhapp.entity.curriculum.*;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.AliyunVodHelper;
import kaiyi.app.xhapp.service.curriculum.*;
import kaiyi.app.xhapp.service.log.CourseBrowseService;
import kaiyi.app.xhapp.service.pub.ConfigureService;
import kaiyi.puer.commons.bean.BeanSyntacticSugar;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.OrderBy;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.json.DefaultJsonValuePolicy;
import kaiyi.puer.json.JsonCreator;
import kaiyi.puer.json.JsonValuePolicy;
import kaiyi.puer.json.creator.CollectionJsonCreator;
import kaiyi.puer.json.creator.JsonMessageCreator;
import kaiyi.puer.json.creator.ObjectJsonCreator;
import kaiyi.puer.json.creator.StringJsonCreator;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Controller
@RequestMapping(CurriculumAction.rootPath)
public class CurriculumAction extends SuperAction {
    public static final String rootPath=PREFIX+"/curriculum";

    @Resource
    private CourseService courseService;
    @Resource
    private ChapterService chapterService;
    @Resource
    private MediaLibraryService mediaLibraryService;
    @Resource
    private ConfigureService configureService;
    @Resource
    private ShopCarService shopCarService;
    @Resource
    private CourseFavoritesService courseFavoritesService;
    @Resource
    private CourseBrowseService courseBrowseService;
    @Resource
    private CourseCommentService courseCommentService;
    @Resource
    private CourseProblemService courseProblemService;
    @Resource
    private FaceToFaceService faceToFaceService;
    /**
     * 根据ID获取课程信息
     * @param interactive
     * @param response
     * @throws IOException
     */
    @RequestMapping("/queryCourseById")
    public void queryCourseById(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        Course course=courseService.findForPrimary(entityId);
        if(Objects.nonNull(course)){
            QueryExpress queryExpress=new CompareQueryExpress("course",CompareQueryExpress.Compare.EQUAL,course);
            StreamCollection<Chapter> chapters=chapterService.getEntitys(queryExpress,new OrderBy(queryExpress.getPrefix(),"weight"));
            course.setChapters(chapters.toSet());
            String[] showFieldArray=BeanSyntacticSugar.getFieldStringNotStatic(course.getClass(),new String[]{});
            ObjectJsonCreator<Course> jsonCreator=new ObjectJsonCreator<>(course,showFieldArray,new DefaultJsonValuePolicy<Course>(
                    new JsonValuePolicy<Course>() {
                        @Override
                        public JsonCreator getCreator(Course entity, String field, Object fieldValue) {
                            if(field.equals("chapters")){
                                StreamCollection<Chapter> chapterSet=entity.getChapterList();
                                Chapter chapter=new Chapter();
                                String[] showFieldArray=BeanSyntacticSugar.getFieldStringNotStatic(chapter.getClass(),new String[]{});
                                return new CollectionJsonCreator<Chapter>(chapterSet,showFieldArray,new DefaultJsonValuePolicy<Chapter>(new JsonValuePolicy<Chapter>() {
                                    @Override
                                    public JsonCreator getCreator(Chapter entity, String field, Object fieldValue) {
                                        if(field.equals("courseMovies")){
                                            StreamCollection<CourseMovie> movies=entity.getCourseMovieList();
                                            return new CollectionJsonCreator<CourseMovie>(movies, new String[]{
                                                    "name", "longTime", "mediaLibrary", "weight"
                                            }, new JsonValuePolicy<CourseMovie>() {
                                                @Override
                                                public JsonCreator getCreator(CourseMovie entity, String field, Object fieldValue) {
                                                    if (field.equals("mediaLibrary")) {
                                                        return new StringJsonCreator(entity.getMediaLibrary().getEntityId());
                                                    }
                                                    return null;
                                                }
                                            });
                                        }
                                        return null;
                                    }
                                }));
                            }else if(field.equals("privileges")){
                                StreamCollection<CourseBuyerPrivilege> privileges=entity.getPrivilegeStream();
                                return new CollectionJsonCreator<>(privileges,new String[]{
                                        "entityId","memberShip","free","price"
                                },new DefaultJsonValuePolicy<>(null));
                            }
                            return null;
                        }
                    }
            ));
            interactive.writeUTF8Text(jsonCreator.build());
        }else{
            interactive.writeUTF8Text(JsonCreator.EMPTY_JSON);
        }
    }

    /**
     * 获取媒体播放地址
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/mediaLibraryPlayAddress")
    public void mediaLibraryPlayAddress(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        MediaLibrary mediaLibrary=mediaLibraryService.findForPrimary(entityId);
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            DefaultAcsClient client = AliyunVodHelper.initVodClient(
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

    /**
     * 将课程加入购物车
     */
    @PostMapping("/joinToShopCar")
    public void joinToShopCar(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String courseId=interactive.getStringParameter("courseId","");
        String accountId=interactive.getStringParameter("accountId","");
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            shopCarService.joinToShopCar(courseId,accountId);
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Text(jmc.build());
    }

    /**
     * 删除购物车
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/deleteShopCar")
    public void deleteShopCar(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        shopCarService.deleteForPrimary(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }

    /**
     * 课程提问、考试咨询
     * @param interactive
     * @param response
     */
    @PostMapping("/courseProblem")
    public void courseProblem(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String accountId=interactive.getStringParameter("accountId","");
        String courseId=interactive.getStringParameter("courseId","");
        String content=interactive.getStringParameter("content","");
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            courseProblemService.problem(courseId,accountId,content);
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Text(jmc.build());
    }
    /**
     * 课程评论
     * @param interactive
     * @param response
     */
    @PostMapping("/courseComment")
    public void courseComment(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String accountId=interactive.getStringParameter("accountId","");
        String courseId=interactive.getStringParameter("courseId","");
        String content=interactive.getStringParameter("content","");
        int score=interactive.getInteger("score",1);
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            courseCommentService.comment(courseId,accountId,content,score);
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Text(jmc.build());
    }
    /**
     * 添加课程到收藏夹
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/joinFavorites")
    public void joinFavorites(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String accountId=interactive.getStringParameter("accountId","");
        String courseId=interactive.getStringParameter("courseId","");
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            courseFavoritesService.addFavorites(accountId,courseId);
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Text(jmc.build());
    }
    /**
     * 删除课程浏览记录
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/deleteFavorites")
    public void deleteFavorites(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        courseFavoritesService.deleteForPrimary(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }
    /**
     * 添加课程浏览记录
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/addCorseBrowse")
    public void addCorseBrowse(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String accountId=interactive.getStringParameter("accountId","");
        String courseId=interactive.getStringParameter("courseId","");
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            courseBrowseService.addCourseBrowse(accountId,courseId);
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Text(jmc.build());
    }
    /**
     * 删除课程浏览记录
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/deleteCorseBrowse")
    public void deleteCorseBrowse(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        courseBrowseService.deleteForPrimary(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }

    /**
     * 面授预约
     * @param interactive
     * @param response
     */
    @PostMapping("/faceToFace")
    public void faceToFace(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        String name=interactive.getStringParameter("name","");
        String phone=interactive.getStringParameter("phone","");
        String course=interactive.getStringParameter("course","");
        Date faceTime=interactive.getDateParameter("faceTime",new SimpleDateFormat("yyyy-MM-dd"));
        faceToFaceService.make(name,phone,course,faceTime);
    }
}
