package kaiyi.app.tcsys.test;

import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.access.VisitorRoleService;
import kaiyi.app.xhapp.service.access.VisitorUserService;
import kaiyi.puer.commons.bean.SpringSelector;
import kaiyi.puer.commons.data.*;
import kaiyi.puer.db.orm.ServiceException;
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
            put("visitorRole",new JavaDataTyper("15507507370770001"));
        }});
    }
    @Test
    public void registUser() throws ServiceException {
        AccountService accountService=sel.getBean(AccountService.class);
        accountService.register("13789009900","123456");
        accountService.register("13890088998","123456");
    }
}
