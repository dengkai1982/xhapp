package kaiyi.app.tcsys.test;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.VisitorMenu;
import kaiyi.app.xhapp.entity.bus.CourseOrder;
import kaiyi.app.xhapp.entity.curriculum.Category;
import kaiyi.app.xhapp.entity.curriculum.Course;
import kaiyi.app.xhapp.entity.log.ShortMessageSenderNote;
import kaiyi.app.xhapp.entity.pages.ContentText;
import kaiyi.app.xhapp.entity.pub.Configure;
import kaiyi.app.xhapp.entity.pub.Notice;
import kaiyi.puer.commons.bean.BeanSyntacticSugar;
import kaiyi.puer.commons.utils.CoderUtil;
import kaiyi.puer.crypt.cipher.RSACipher;
import kaiyi.puer.crypt.key.KeyGeneratorUtils;
import kaiyi.puer.json.parse.ArrayJsonParser;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class daima {
    @Test
    public void print(){
        String[] dps1="市发改委、市经济和信息化委、市教育体育局、市科技和知识产权局、市民政局、市住房城乡建设局、市交通运输局、市水务局、市农牧业局、市林业局、市商务局、市文广新局、市卫生计生委、市旅游发展委、市国资委、市食品药品监管局、市供销社、市金融工作局、市工商联".split("、");
        String[] dps2="市纪委机关（市监委、市委巡察办、市委巡察组）、市检察院、市委办公室、市人大常委会办公室、市政府办公室、市政协办公室、市委组织部、市委宣传部、市委统战部、市委政法委、市委群工局、市委政研室、市委农工委、市直机关工委、市委编办、市委老干局、市委党校、市公安局、市司法局、市财政局、市人力资源社会保障局、市国土资源局、市规划局、市审计局、市环保局、市统计局、市城乡管理执法局、市安监局、市人防办、市工商局、市质监局、市政务服务中心、市档案局、市防震减灾局、市地方志办、市国安局、市国税局、市地税局、市气象局、市邮政管理局、海关自贡办事处、自贡调查队、市通信办、市总工会、团市委、市妇联、市文联、市科协、市社科联、市残联".split("、");

    }

    @Test
    public void jiami() throws Exception {
        RSACipher cipher=new RSACipher("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOTG2zXlCm7QJ25+331+hBeIAzXbwCadLqjDWb/D7S4ONbYfJrFueQmrG97mZaJwaZ1dhoMsTwVaDu245HOVFks9+UZ36Q1Tr2CcBpHZpWJTXmmYYd+Hfwb6L6uzUc3WvDP5DTgWLFAPA1d4nWRshfZys/0PX3PjJIL4eDU958TNAgMBAAECgYEAqjYpspeOg5PrQpKyxTP0I23WtaOh+xjHNljh1YN4W+PGopHav/hppX/hZJ4W0BzC94o2IjI9OMYghU4i6rvpsPledEjOv0tkjcRy9ucGxL/T3q4/ezGe7P8hUFnYbsHe/2+6pHjJOyCzab45vH6cTmUbXuPkijMvCZ9NCdkunyECQQD3iH39JegMBSTWy934V0XIMmtVehWpRCvZaPmNTT96LOz/5VKwOz8TwcoM7ViTBaeWzNG2Lal34Ll4VrLYlqdDAkEA7JogA8w3qlk/i5lmFSW6ZKcxHT1WnBuysM8D0zp7qKLtWTPi8bF0jqXBaPs3BoXTc2TGdt8kOE9wNrgURtz6rwJAHDuqOxx+uhhAGmvIVpIFuI7fpTE2lUbcRYDurco4ykOjiJBsfQNU73j0BcNwjdxgQBf+d2v/31d3cB1bas7MJwJAB0Gq3bLzuhvGoSdxRBDGKLQgA3+QGnWXA2k1+tJ+XGuyz9uRiEwjAQUAa5HY3DBajd13YMnzOG7nSCZZKNs6LQJBALBnWpiakCDE/d3Nl0JMxD6FqM0l3+hjSlu/8JlFaUYK5QISY4J+g/3cDYS0ZRsDZOBxS7WvyV9Fv1UeKySbqX4=",
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDkxts15Qpu0Cduft99foQXiAM128AmnS6ow1m/w+0uDjW2HyaxbnkJqxve5mWicGmdXYaDLE8FWg7tuORzlRZLPflGd+kNU69gnAaR2aViU15pmGHfh38G+i+rs1HN1rwz+Q04FixQDwNXeJ1kbIX2crP9D19z4ySC+Hg1PefEzQIDAQAB");
        byte[] result=cipher.pubEncrypt("https://outin-630f003c491c11e9b88a00163e1a65b6.oss-cn-shanghai.aliyuncs.com/sv/209784f3-169c8bb38d8/209784f3-169c8bb38d8.mp4?Expires=1554127963&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=Vx1QIxExt37g4R9gNVBYrXCUP%2BE%3D".getBytes("utf-8"));
        System.out.println(CoderUtil.base64Encode(result));
    }
    @Test
    public void key(){
        KeyPair keyPair=KeyGeneratorUtils.generatorRSAKey(2048);
        System.out.println(CoderUtil.base64Encode(keyPair.getPrivate().getEncoded()));
        System.out.println(CoderUtil.base64Encode(keyPair.getPublic().getEncoded()));
    }
    @Test
    public void ttt(){
        BeanSyntacticSugar.printEntity(VisitorMenu.class);
    }

    @Test
    public void parse(){
        String json="[{'field':'phone','condition':'1','value':'1333889982'}]";
        ArrayJsonParser jsonParser=new ArrayJsonParser(json);
        System.out.println(jsonParser);
    }
    @Test
    public void printEntity(){
        BeanSyntacticSugar.printEntity(Account.class);
        BeanSyntacticSugar.printEntity(CourseOrder.class);
        BeanSyntacticSugar.printEntity(Course.class);
        BeanSyntacticSugar.printEntity(Category.class);
        BeanSyntacticSugar.printEntity(Configure.class);
        BeanSyntacticSugar.printEntity(ShortMessageSenderNote.class);
        BeanSyntacticSugar.printEntity(ContentText.class);
        BeanSyntacticSugar.printEntity(Notice.class);
    }
}
