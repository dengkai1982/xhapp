package kaiyi.app.tcsys.test;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.examination.Question;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.executor.DayTimer;
import kaiyi.app.xhapp.service.AliyunVodHelper;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.access.VisitorRoleService;
import kaiyi.app.xhapp.service.access.VisitorUserService;
import kaiyi.app.xhapp.service.curriculum.CategoryService;
import kaiyi.app.xhapp.service.curriculum.CourseOrderService;
import kaiyi.app.xhapp.service.examination.ExamQuestionService;
import kaiyi.app.xhapp.service.examination.QuestionFavoritesService;
import kaiyi.app.xhapp.service.examination.QuestionService;
import kaiyi.app.xhapp.service.jobs.PositionService;
import kaiyi.app.xhapp.service.log.ShortMessageSenderNoteService;
import kaiyi.app.xhapp.service.pub.ConfigureService;
import kaiyi.app.xhapp.service.sys.ConsultationService;
import kaiyi.puer.commons.bean.SpringSelector;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.Currency;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.poi.ExcelUtils;
import kaiyi.puer.commons.utils.CoderUtil;
import kaiyi.puer.crypt.cipher.CipherOperator;
import kaiyi.puer.crypt.cipher.RSACipher;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.h5ui.service.ApplicationService;
import kaiyi.puer.http.HttpException;
import kaiyi.puer.http.HttpResponse;
import kaiyi.puer.http.connect.HttpConnector;
import kaiyi.puer.http.connect.OKHttpConnection;
import kaiyi.puer.http.parse.TextParser;
import kaiyi.puer.http.request.HttpGetRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ImportData {
    static ApplicationContext ctx;
    static SpringSelector sel;
    @Before
    public void before(){
        if(ctx==null){
            ctx=new ClassPathXmlApplicationContext("spring-context.xml");
            sel=new SpringSelector(ctx);
        }
    }
    @Test
    public void clearPath(){
        DayTimer dayTimer=sel.getBean(DayTimer.class);
        dayTimer.dispose();
    }
    @Test
    public void questionFav(){
        QuestionFavoritesService favoritesService=sel.getBean(QuestionFavoritesService.class);
        favoritesService.favorites("15688503905390085","15689496813820040");
    }

    /*@Test
    public void getSale(){
        CourseOrderService courseOrderService=sel.getBean(CourseOrderService.class);
        Currency currency=courseOrderService.totalTeamSale("15688598106186917","201909");
        System.out.println(currency);
    }*/

    /**
     * 要写程序来删除Question
     delete from exam_choice_answer

     delete from exam_question_item

     delete from exam_question

     delete from test_pager_question

     delete from test_pager

     select * from question

     update question set deleted=false
     //这句执行不了
     delete from choice_answer where question in (select question from choice_answer group by question having count(*)=1)
    
     update question set deleted=true where entityId in(select question from choice_answer group by question having count(*)=1)

     * @throws IOException
     */

    @Test
    public void deleteQuestion(){
        QuestionService questionService=sel.getBean(QuestionService.class);
        questionService.deleteOneQuestion();
    }
    @Test
    public void excelImport() throws IOException {
        QuestionService questionService=sel.getBean(QuestionService.class);
        File file=new File("/Users/dengkai/Documents/鑫鸿app/最新导入/导入/二级建造师(机电工程)考前模拟题及答案解析.xls");
        AtomicReference<Question> questionReference=new AtomicReference<>();
        //StreamCollection<QuestionCategory> categories=categoryService.getEntitys();
        if(Objects.nonNull(file)&&file.exists()){
            AtomicInteger index=new AtomicInteger(0);
            ExcelUtils.readExcel(file, line->{
                if(!line.get(0).getData().stringValue().equals("试题题目")){
                    //表头不做处理
                    System.out.println("index:"+index.getAndAdd(1));
                    try {
                        if(questionService.isQuestion(line)){
                            Question existQuestion=questionReference.get();
                            if(Objects.nonNull(existQuestion)){
                                questionService.saveObject(existQuestion);
                                questionReference.set(null);
                            }
                            questionReference.set(questionService.parseQuestion(line));
                        }else{
                            Question question=questionReference.get();
                            if(Objects.nonNull(question)){
                                questionService.parseChoiceAnswer(question,line);
                            }
                        }
                    } catch (ServiceException e) {
                        questionReference.set(null);
                    }
                }
            });
        }
        if(Objects.nonNull(questionReference.get())){
            System.out.println(questionReference.get());
            //questionService.saveObject(questionReference.get());
        }
    }
    @Test
    public void excelBatchImport() throws IOException {
        QuestionService questionService=sel.getBean(QuestionService.class);
        File dir=new File("/Users/dengkai/金红/daoru/");
        File[] files=dir.listFiles();
        for(File file:files){
            AtomicReference<Question> questionReference=new AtomicReference<>();
            //StreamCollection<QuestionCategory> categories=categoryService.getEntitys();
            if(Objects.nonNull(file)&&file.exists()){
                AtomicInteger index=new AtomicInteger(0);
                ExcelUtils.readExcel(file, line->{
                    if(!line.get(0).getData().stringValue().equals("试题题目")){
                        //表头不做处理
                        System.out.println("index:"+index.getAndAdd(1));
                        try {
                            if(questionService.isQuestion(line)){
                                Question existQuestion=questionReference.get();
                                if(Objects.nonNull(existQuestion)){
                                    questionService.saveObject(existQuestion);
                                    questionReference.set(null);
                                }
                                questionReference.set(questionService.parseQuestion(line));
                            }else{
                                Question question=questionReference.get();
                                if(Objects.nonNull(question)){
                                    questionService.parseChoiceAnswer(question,line);
                                }
                            }
                        } catch (ServiceException e) {
                            questionReference.set(null);
                        }
                    }
                });
            }
            if(Objects.nonNull(questionReference.get())){
                System.out.println(questionReference.get());
                //questionService.saveObject(questionReference.get());
            }
        }
    }

    @Test
    public void commitConsultation() throws ServiceException {
        ConsultationService consultationService=sel.getBean(ConsultationService.class);
        consultationService.commitConsultation("15562127039610033","颠覆了计算机发牢骚");
    }

    @Test
    public void generatorExam() throws ServiceException {
        String testPagerId="15625950396140092";
        String accountId="15575605038650042";
        ExamQuestionService examQuestionService=sel.getBean(ExamQuestionService.class);
        examQuestionService.generatorByTestPager(accountId,testPagerId);
    }

    @Test
    public void generatorExamByCategory() throws ServiceException {
        String categoryId="15518854151500024";
        String accountId="15575605038650042";
        ExamQuestionService examQuestionService=sel.getBean(ExamQuestionService.class);
        examQuestionService.generatorByCategory(accountId,categoryId);
    }

    @Test
    public void testAnswer(){
        ExamQuestionService examQuestionService=sel.getBean(ExamQuestionService.class);
        examQuestionService.answerQuestion("15626016943550081","B");
    }

    @Test
    public void sendTest() throws ServiceException {
        ShortMessageSenderNoteService smsn=sel.getBean(ShortMessageSenderNoteService.class);
        smsn.sendValidateCode("13350106164","短信测试");
    }

    @Test
    public void jiemi() throws Exception {
        String data="RPg4a2PbWiRGEkzrqpzFVyhKy+34C5Z0R0SDzaM6IHnhfOzzPjRwQzh/ODLuuz0rl7VQbzWt/aTAq+CX3oNx1ocGyCU1k2kuBoVyQCrFO76PdYKjtCQu8DwkYFgtQ60z10P5Sqlg7qSUiKJDcNVYM1qWh7xzBFSaP5omFHGvDlq0g7ETk03yLfM35ngiFjzC1OM/wSE362GSa6gKGt/rDoTLDb82GZpm02h1k7Bj5GO9fut/kCzUWGYdYZt+P3lDRuh+K1BEFjpqdlDuikE2Cg9TzIMpzBBt7syjdwlxrJYemfeg0XNDiVOV7UCDUDbV9nitsW3EE7UvhnzV23v43A==";
        ApplicationService as=sel.getBean(ApplicationService.class);
        CipherOperator cipherOperator = as.getCipherOperator();
        RSACipher cipher=cipherOperator.getRSACipher();
        byte[] bytes=cipher.priDencrypt(CoderUtil.base64DecodeToBytes(data));
        System.out.println(new String(bytes));
    }
    @Test
    public void jiami() throws Exception {
        ApplicationService as=sel.getBean(ApplicationService.class);
        CipherOperator cipherOperator = as.getCipherOperator();
        RSACipher cipher=cipherOperator.getRSACipher();
        byte[] result=cipher.pubEncrypt("https://outin-630f003c491c11e9b88a00163e1a65b6.oss-cn-shanghai.aliyuncs.com/sv/209784f3-169c8bb38d8/209784f3-169c8bb38d8.mp4?Expires=1554127963&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=Vx1QIxExt37g4R9gNVBYrXCUP%2BE%3D".getBytes("utf-8"));
        System.out.println(CoderUtil.base64Encode(result));
    }
    @Test
    public void importRole() throws ServiceException {
        VisitorRoleService roleService=sel.getBean(VisitorRoleService.class);
        roleService.newObject(new HashMap<String,JavaDataTyper>(){{
            put("name",new JavaDataTyper("administrators"));
            put("descript",new JavaDataTyper("系统超级管理员组"));
        }});
    }
    @Test
    public void importUser() throws ServiceException {
        VisitorUserService userService=sel.getBean(VisitorUserService.class);
        userService.newObject(new HashMap<String,JavaDataTyper>(){{
            put("loginName",new JavaDataTyper("admin"));
            put("realName",new JavaDataTyper("系统管理员"));
            put("password",new JavaDataTyper("123456"));
            put("visitorRole",new JavaDataTyper("15592239223100055"));
        }});
    }
    @Test
    public void registUser() throws ServiceException {
        AccountService accountService=sel.getBean(AccountService.class);
        //accountService.register("13789009900","123456");
        //accountService.register("13890088998","123456");
    }

    @Test
    public void insertCategory() throws ServiceException {
        CategoryService categoryService=sel.getBean(CategoryService.class);
        String[] names={"一级建造师","一级消费工程师","安全工程师","造价工程师","二级建造师","环境影响评价师"};
        float weight=1;
        for(String name:names){
            defaultAddCateogry(categoryService,name,weight++,"15518835256660016");
        }
        names=new String[]{"教师资格证","教师招聘","特岗教师"};
        weight=1;
        for(String name:names){
            defaultAddCateogry(categoryService,name,weight++,"15518835256660017");
        }
        names=new String[]{"证券从业师","基金从业师","中级会计师"};
        weight=1;
        for(String name:names){
            defaultAddCateogry(categoryService,name,weight++,"15518835256660018");
        }
        names=new String[]{"初级消防员","中级消防员","高级消防员"};
        weight=1;
        for(String name:names){
            defaultAddCateogry(categoryService,name,weight++,"15518835256660019");
        }
        names=new String[]{"执业医师","执业护士"};
        weight=1;
        for(String name:names){
            defaultAddCateogry(categoryService,name,weight++,"15518835256660020");
        }
    }

    private void defaultAddCateogry(CategoryService categoryService,String name,float weight,String parent) throws ServiceException {
        categoryService.newObject(new HashMap<String,JavaDataTyper>(){{
            put("name",new JavaDataTyper(name));
            put("weight",new JavaDataTyper(weight));
            put("parent",new JavaDataTyper(parent));
        }});
    }

    @Test
    public void impt() throws ClientException {
        ConfigureService configureService=sel.getBean(ConfigureService.class);
        DefaultAcsClient client = AliyunVodHelper.initVodClient(
                configureService.getStringValue(ConfigureItem.ALIYUN_VOD_ACCESS_KEY_ID),
                configureService.getStringValue(ConfigureItem.ALIYUN_VOD_ACCESS_KEY_SECRET));
        String url=AliyunVodHelper.getPlayUrl(client,"69491d9f378c43e89c8dfcafc7dae823");
        System.out.println(url);
    }
    @Test
    public void importJobInfo() throws HttpException {
        PositionService positionService=sel.getBean(PositionService.class);
        HttpGetRequest get=new HttpGetRequest("https://zg.58.com/job.shtml?PGTID=0d100000-01a5-92f9-197f-5cfded7a15e1&ClickID=2");
        HttpConnector<String> http=new OKHttpConnection<>();
        HttpResponse<String> resp=http.doRequest(get,new TextParser());
        String html=resp.getResponseData().getData();
        Document doc= Jsoup.parse(html);
        Element sidebar=doc.getElementById("sidebar-right");
        //List<Node> nodes=sidebar.child(0).childNodes();
        Elements elements=sidebar.getElementsByTag("li");
        elements.forEach(a->{
            Elements atag=a.getElementsByTag("a");
            System.out.println("=============");
            String parentName=null;
            for(int i=0;i<atag.size();i++){
                String name=atag.get(i).html();
                if(i==0){
                    positionService.newJobs(name,null);
                    parentName=name;
                }else{
                    positionService.newJobs(name,parentName);
                }
            }
            System.out.println("=============");
        });
    }
    @Test
    public void testGetTeam(){
        AccountService accountService=sel.getBean(AccountService.class);
        StreamCollection<Account> accounts=accountService.getTeams("15524007346910022");
        accounts.forEach(c->{
            System.out.println(c.getEntityId());
        });
    }
    /*@Test
    public void saleCount(){
        CourseOrderService courseOrderService=sel.getBean(CourseOrderService.class);
        Currency currency=courseOrderService.totalPersonSale("15524007346910022","201908");
        System.out.println(currency.toString());
        currency=courseOrderService.totalTeamSale("15524007346910022","201908");
        System.out.println(currency.toString());
    }*/

    @Test
    public void callAlipay(){
        ConfigureService configureService=sel.getBean(ConfigureService.class);
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
        model.setOutTradeNo("328049809849230");
        model.setSubject("鑫鸿教育课程支付订单");
        model.setTotalAmount(Currency.noDecimalBuild(3000,2).toString());
        model.setBody("课程支付");
        model.setTimeoutExpress("3m");
        model.setProductCode("product_code");
        alipay_request.setBizModel(model);
        String prefix="http://http://www.xinhongapp.cn/xhapp";//ServletUtils.getRequestHostContainerProtolAndPort(interactive.getHttpServletRequest());
        alipay_request.setReturnUrl(prefix+"/alipayNotify.xhtml");
        alipay_request.setNotifyUrl(prefix+"/alipaySyncNotify");
        // form表单生产
        try {
            // 调用SDK生成表单
            String body = client.pageExecute(alipay_request).getBody();
            System.out.println(body);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void staticsticCourse(){
        CourseOrderService courseOrderService=sel.getBean(CourseOrderService.class);
        courseOrderService.courseSaleStatistics(null,null);
    }
}
