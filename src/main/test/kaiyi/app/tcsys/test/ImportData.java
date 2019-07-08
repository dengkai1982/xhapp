package kaiyi.app.tcsys.test;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.AliyunVodHelper;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.access.VisitorRoleService;
import kaiyi.app.xhapp.service.access.VisitorUserService;
import kaiyi.app.xhapp.service.curriculum.CategoryService;
import kaiyi.app.xhapp.service.examination.ExamQuestionService;
import kaiyi.app.xhapp.service.jobs.PositionService;
import kaiyi.app.xhapp.service.log.ShortMessageSenderNoteService;
import kaiyi.app.xhapp.service.pub.ConfigureService;
import kaiyi.puer.commons.bean.SpringSelector;
import kaiyi.puer.commons.data.*;
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
import java.util.HashMap;

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
}
