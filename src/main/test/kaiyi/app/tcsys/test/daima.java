package kaiyi.app.tcsys.test;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.VisitorMenu;
import kaiyi.app.xhapp.entity.curriculum.Course;
import kaiyi.app.xhapp.entity.log.ShortMessageSenderNote;
import kaiyi.app.xhapp.entity.pages.ContentText;
import kaiyi.app.xhapp.entity.pub.Configure;
import kaiyi.puer.commons.bean.BeanSyntacticSugar;
import kaiyi.puer.json.parse.ArrayJsonParser;
import org.junit.Test;

public class daima {
    @Test
    public void print(){
        String[] dps1="市发改委、市经济和信息化委、市教育体育局、市科技和知识产权局、市民政局、市住房城乡建设局、市交通运输局、市水务局、市农牧业局、市林业局、市商务局、市文广新局、市卫生计生委、市旅游发展委、市国资委、市食品药品监管局、市供销社、市金融工作局、市工商联".split("、");
        String[] dps2="市纪委机关（市监委、市委巡察办、市委巡察组）、市检察院、市委办公室、市人大常委会办公室、市政府办公室、市政协办公室、市委组织部、市委宣传部、市委统战部、市委政法委、市委群工局、市委政研室、市委农工委、市直机关工委、市委编办、市委老干局、市委党校、市公安局、市司法局、市财政局、市人力资源社会保障局、市国土资源局、市规划局、市审计局、市环保局、市统计局、市城乡管理执法局、市安监局、市人防办、市工商局、市质监局、市政务服务中心、市档案局、市防震减灾局、市地方志办、市国安局、市国税局、市地税局、市气象局、市邮政管理局、海关自贡办事处、自贡调查队、市通信办、市总工会、团市委、市妇联、市文联、市科协、市社科联、市残联".split("、");

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
        BeanSyntacticSugar.printEntity(Course.class);
        BeanSyntacticSugar.printEntity(Configure.class);
        BeanSyntacticSugar.printEntity(ShortMessageSenderNote.class);
        BeanSyntacticSugar.printEntity(ContentText.class);
        BeanSyntacticSugar.printEntity(ShortMessageSenderNote.class);
    }
}
