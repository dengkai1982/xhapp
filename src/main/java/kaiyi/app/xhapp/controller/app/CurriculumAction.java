package kaiyi.app.xhapp.controller.app;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import kaiyi.app.xhapp.entity.curriculum.*;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.AliyunVodHelper;
import kaiyi.app.xhapp.service.curriculum.ChapterService;
import kaiyi.app.xhapp.service.curriculum.CourseService;
import kaiyi.app.xhapp.service.curriculum.MediaLibraryService;
import kaiyi.app.xhapp.service.curriculum.ShopCarService;
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
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
                                StreamCollection<Chapter> chapterSet=new StreamCollection<>(entity.getChapters());
                                chapterSet.sort((c1,c2)->{
                                    return Integer.valueOf(c1.getWeight()).compareTo(c2.getWeight());
                                });
                                Chapter chapter=new Chapter();
                                String[] showFieldArray=BeanSyntacticSugar.getFieldStringNotStatic(chapter.getClass(),new String[]{});
                                return new CollectionJsonCreator<Chapter>(chapterSet,showFieldArray,new DefaultJsonValuePolicy<Chapter>(new JsonValuePolicy<Chapter>() {
                                    @Override
                                    public JsonCreator getCreator(Chapter entity, String field, Object fieldValue) {
                                        if(field.equals("courseMovies")){
                                            StreamCollection<CourseMovie> movies=entity.getCourseMovieList();
                                            CourseMovie courseMovie=new CourseMovie();
                                            String[] showFieldArray=BeanSyntacticSugar.getFieldStringNotStatic(chapter.getClass(),new String[]{});
                                            return new CollectionJsonCreator<CourseMovie>(movies,showFieldArray);
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
     * 媒体播放地址
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
}
