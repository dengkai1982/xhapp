package kaiyi.app.tcsys.test;

import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.access.VisitorRoleService;
import kaiyi.app.xhapp.service.access.VisitorUserService;
import kaiyi.app.xhapp.service.curriculum.CategoryService;
import kaiyi.app.xhapp.service.pub.ConfigureService;
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
}
