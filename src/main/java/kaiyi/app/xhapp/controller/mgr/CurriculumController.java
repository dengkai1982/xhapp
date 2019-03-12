package kaiyi.app.xhapp.controller.mgr;

import kaiyi.app.xhapp.service.curriculum.StudentService;
import kaiyi.app.xhapp.service.curriculum.CategoryService;
import kaiyi.puer.commons.access.AccessControl;
import kaiyi.puer.h5ui.bean.DynamicGridInfo;
import kaiyi.puer.json.creator.JsonMessageCreator;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(CurriculumController.rootPath)
@AccessControl(name = "课程管理", weight = 3f, detail = "管理课程相关内容", code = CurriculumController.rootPath)
public class CurriculumController extends ManagerController{
    public static final String rootPath=prefix+"/curriculum";

    @Resource
    private CategoryService categoryService;
    @Resource
    private StudentService studentService;
    @RequestMapping("/student")
    @AccessControl(name = "授课教师", weight = 3.1f, detail = "管理授课教师", code = rootPath+ "/student", parent = rootPath)
    public String student(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/student");
        mainTablePage(interactive,studentService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/student";
    }
    @RequestMapping("/student/new")
    @AccessControl(name = "新增授课教师", weight = 3.11f, detail = "添加新的授课教师",
            code = rootPath+ "/student/new", parent = rootPath+"/student")
    public String studentNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,studentService,3);
        setDefaultPage(interactive,rootPath+"/student");
        return rootPath+"/studentForm";
    }
    @RequestMapping("/student/modify")
    @AccessControl(name = "编辑授课教师", weight = 3.12f, detail = "编辑授课教师",
            code = rootPath+ "/student/modify", parent = rootPath+"/student")
    public String studentModify(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,studentService,3);
        setDefaultPage(interactive,rootPath+"/student");
        return rootPath+"/studentForm";
    }
    @RequestMapping("/student/detail")
    @AccessControl(name = "授课教师详情", weight = 3.13f, detail = "角色详情",
            code = rootPath+ "/visitorRole/detail", parent = rootPath+"/student")
    public String studentDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,studentService,3);
        setDefaultPage(interactive,rootPath+"/student");
        return rootPath+"/studentDetail";
    }
    @PostMapping("/student/commit")
    public void studentCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=executeNewOrUpdate(interactive,studentService);
        interactive.writeUTF8Text(msg.build());
    }

}
