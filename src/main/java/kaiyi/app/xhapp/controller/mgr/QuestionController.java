package kaiyi.app.xhapp.controller.mgr;


import kaiyi.app.xhapp.service.examination.ExamQuestionService;
import kaiyi.app.xhapp.service.examination.QuestionService;
import kaiyi.puer.commons.access.AccessControl;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
    @AccessControl(name = "试题库", weight = 4.1f, detail = "管理试题库内容", code = rootPath+ "/question", parent = rootPath)
    public String question(@IWebInteractive WebInteractive interactive, HttpServletResponse response){

        return rootPath+"/question";
    }
}
