package kaiyi.app.xhapp.controller.app;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import kaiyi.app.xhapp.entity.access.enums.CapitalType;
import kaiyi.app.xhapp.entity.curriculum.*;
import kaiyi.app.xhapp.entity.curriculum.enums.PayPlatform;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.executor.NotifyCourseOrder;
import kaiyi.app.xhapp.service.AliyunVodHelper;
import kaiyi.app.xhapp.service.curriculum.*;
import kaiyi.app.xhapp.service.log.CourseBrowseService;
import kaiyi.app.xhapp.service.pages.ExamInfoService;
import kaiyi.app.xhapp.service.pub.ConfigureService;
import kaiyi.puer.commons.bean.BeanSyntacticSugar;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.Currency;
import kaiyi.puer.commons.log.Logger;
import kaiyi.puer.commons.utils.RandomUtils;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.OrderBy;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.h5ui.service.ApplicationService;
import kaiyi.puer.json.DefaultJsonValuePolicy;
import kaiyi.puer.json.JsonCreator;
import kaiyi.puer.json.JsonObjectCreator;
import kaiyi.puer.json.JsonValuePolicy;
import kaiyi.puer.json.creator.*;
import kaiyi.puer.web.servlet.ServletUtils;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Resource
    private ExamInfoService examInfoService;
    @Resource
    private CourseOrderService courseOrderService;
    @Resource
    private ApplicationService applicationService;
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
        String accountId=interactive.getStringParameter("accountId","");
        String name=interactive.getStringParameter("name","");
        String phone=interactive.getStringParameter("phone","");
        String course=interactive.getStringParameter("course","");
        Date faceTime=interactive.getDateParameter("faceTime",new SimpleDateFormat("yyyy-MM-dd"));
        faceToFaceService.make(accountId,name,phone,course,faceTime);
    }

    /**
     * 读取咨询，将阅读量加1
     * */
    @PostMapping("/readExaminfo")
    public void readExaminfo(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        examInfoService.readExamInfo(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }

    /**
     * 构建课程购买订单
     * courseIdArray 课程ID、数组
     * accountId 用户ID
     * capitalType 付款方式
     */
    @PostMapping("/generatorOrder")
    public void generatorOrder(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        StreamArray<String> courseIdArray=interactive.getStringStreamArray("courseIdArray",";");
        String accountId=interactive.getStringParameter("accountId","");
        CapitalType capitalType=interactive.getEnumParameterByOrdinal(CapitalType.class,"capitalType");
        JsonMessageCreator jmc=getSuccessMessage();
        MutilJsonCreator mjc=new MutilJsonCreator();
        try {
            CourseOrder courseOrder=courseOrderService.generatorOrder(new StreamCollection<>(courseIdArray.toList()),accountId, capitalType);
            JsonCreator jsonCreator=defaultWriteObject(courseOrder);
            mjc.addJsonCreator(jsonCreator);
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Text(mjc.build());
    }
    /**
     * 调用支付宝支付
     * @param interactive
     * @param response
     * @return
     */
    @PostMapping("/generatorWebAlipayPayment")
    public void generatorWebAlipayPayment(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String orderId=interactive.getStringParameter("orderId","");
        CourseOrder courseOrder=courseOrderService.findForPrimary(orderId);
        if(Objects.nonNull(courseOrder)){
            String gatewayUrl=configureService.getStringValue(ConfigureItem.ALIPAY_GATEWAY_URL);
            String appid=configureService.getStringValue(ConfigureItem.ALIPAY_APPID);
            String privateKey=configureService.getStringValue(ConfigureItem.MY_PRIVATE_KEY);
            String format=configureService.getStringValue(ConfigureItem.ALIPAY_FORMAT);
            String charset=configureService.getStringValue(ConfigureItem.CHARSET);
            String alypayPublicKey=configureService.getStringValue(ConfigureItem.ALIPAY_PUBLIC_KEY);
            String signtype=configureService.getStringValue(ConfigureItem.ALIPAY_SIGNTYPE);
            AlipayClient client = new DefaultAlipayClient(gatewayUrl, appid,
                    privateKey, format, charset,
                    alypayPublicKey,signtype);
            AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();
            AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
            model.setOutTradeNo(courseOrder.getOrderId());
            model.setSubject("鑫鸿教育课程支付订单");
            model.setTotalAmount(Currency.noDecimalBuild(courseOrder.getAmount(),2).toString());
            model.setBody("课程支付");
            model.setTimeoutExpress("3m");
            model.setProductCode("product_code");
            alipay_request.setBizModel(model);
            String prefix=ServletUtils.getRequestHostContainerProtolAndPort(interactive.getHttpServletRequest());
            alipay_request.setReturnUrl(prefix+interactive.generatorRequestUrl(rootPath+"/alipayNotify",null));
            alipay_request.setNotifyUrl(prefix+interactive.generatorRequestUrl(rootPath+"/alipaySyncNotify",null));
            // form表单生产
            try {
                // 调用SDK生成表单
                String body = client.pageExecute(alipay_request).getBody();
                interactive.writeUTF8Text(body);
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 完成站内支付
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/finishGoldPayment")
    public void finishGoldPayment(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String orderId=interactive.getStringParameter("orderId","");
        CourseOrder courseOrder=courseOrderService.signleQuery("orderId",orderId);
        JsonMessageCreator jmc=getSuccessMessage();
        if(Objects.nonNull(courseOrder)){
            Currency actualAmount=Currency.noDecimalBuild(courseOrder.getAmount(),2);
            PaymentNotify paymentNotify=new PaymentNotify(courseOrder.getOrderId(),PayPlatform.INSIDE,
                    "完成订单","使用金币支付","金币支付",actualAmount.toString(),
                    "none",RandomUtils.getRandomString(),new Date(),true);
            applicationService.syncExecute(new NotifyCourseOrder(applicationService,paymentNotify));
        }else{
            jmc.setMsg("支付订单不存在");
            jmc.setCode(JsonMessageCreator.FAIL);
        }
        interactive.writeUTF8Text(jmc.build());
    }

    /**
     * 支付宝异步回调
     * @param interactive
     * @param response
     * @throws AlipayApiException
     * @throws IOException
     */
    @RequestMapping("/alipaySyncNotify")
    public void alipaySyncNotify(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws AlipayApiException, IOException {
        Map<String,String> params = getAlipayNotifyParams(interactive);
        String alypayPublicKey=configureService.getStringValue(ConfigureItem.ALIPAY_PUBLIC_KEY);
        String charset=configureService.getStringValue(ConfigureItem.CHARSET);
        String signtype=configureService.getStringValue(ConfigureItem.ALIPAY_SIGNTYPE);
        boolean verify_result = AlipaySignature.rsaCheckV1(params,alypayPublicKey, charset, signtype);
        if(verify_result){
            String out_trade_no = new String(interactive.getHttpServletRequest().getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String trade_no = new String(interactive.getHttpServletRequest().getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //交易状态
            String trade_status = new String(interactive.getHttpServletRequest().getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
            if (trade_status.equals("TRADE_SUCCESS")){
                CourseOrder courseOrder=courseOrderService.signleQuery("orderId",out_trade_no);
                if(Objects.nonNull(courseOrder)){
                    Currency actualAmount=Currency.noDecimalBuild(courseOrder.getAmount(),2);
                    PaymentNotify paymentNotify=new PaymentNotify(courseOrder.getOrderId(),PayPlatform.ALIPAY,
                            "完成订单","在线支付","在线支付",actualAmount.toString(),
                            "none",trade_no,new Date(),true);
                    applicationService.syncExecute(new NotifyCourseOrder(applicationService,paymentNotify));
                    //courseOrderService.paymentSaleOrder(paymentNotify);
                    interactive.writeUTF8Text("success");
                }
            }
        }
        interactive.writeUTF8Text("fail");
    }

    private Map<String,String> getAlipayNotifyParams(WebInteractive interactive){
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = interactive.getHttpServletRequest().getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }
}
