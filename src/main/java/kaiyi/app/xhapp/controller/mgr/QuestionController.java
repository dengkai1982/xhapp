package kaiyi.app.xhapp.controller.mgr;


import kaiyi.app.xhapp.entity.curriculum.Category;
import kaiyi.app.xhapp.service.curriculum.CategoryService;
import kaiyi.app.xhapp.service.examination.ExamQuestionService;
import kaiyi.app.xhapp.service.examination.QuestionService;
import kaiyi.puer.commons.access.AccessControl;
import kaiyi.puer.commons.data.JavaDataTyper;
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
import java.util.Map;

@Controller
@RequestMapping(QuestionController.rootPath)
@AccessControl(name = "考试管理", weight = 5f, detail = "管理考试试题与相关考试信息", code = QuestionController.rootPath)
public class QuestionController extends ManagerController {
    public static final String rootPath=prefix+"/examination";
    @Resource
    private QuestionService questionService;
    @Resource
    private ExamQuestionService examQuestionService;

    @RequestMapping("/question")
    @AccessControl(name = "试题库", weight = 5.1f, detail = "管理试题库内容", code = rootPath+ "/question", parent = rootPath)
    public String question(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/question");
        mainTablePage(interactive,questionService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/question";
    }
    @RequestMapping("/question/new")
    @AccessControl(name = "新增试题", weight = 5.11f, detail = "添加新的试题",
            code = rootPath+ "/question/new", parent = rootPath+"/question")
    public String visitorRoleNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,questionService,3);
        setDefaultPage(interactive,rootPath+"/question");
        return rootPath+"/questionForm";
    }
    @RequestMapping("/question/modify")
    @AccessControl(name = "编辑试题", weight = 5.12f, detail = "编辑试题",
            code = rootPath+ "/question/modify", parent = rootPath+"/question")
    public String visitorRoleModify(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,questionService,3);
        setDefaultPage(interactive,rootPath+"/question");
        return rootPath+"/questionForm";
    }

    @PostMapping("/question/commit")
    public void visitorRoleCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        Map<String, JavaDataTyper> params=interactive.getRequestParameterMap();
        String choiceAnswer=interactive.getHttpServletRequest().getParameter("choiceAnswer");
        params.put("params",new JavaDataTyper(choiceAnswer));
        JsonMessageCreator msg=executeNewOrUpdate(interactive,questionService,params);
        interactive.writeUTF8Text(msg.build());
    }
    @PostMapping("/question/deleteAnswer")
    public void deleteAnswer(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        questionService.deleteChoiceAnswer(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }
}
