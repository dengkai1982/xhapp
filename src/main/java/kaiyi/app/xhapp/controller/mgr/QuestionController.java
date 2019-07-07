package kaiyi.app.xhapp.controller.mgr;


import kaiyi.app.xhapp.entity.curriculum.Category;
import kaiyi.app.xhapp.entity.examination.TestPager;
import kaiyi.app.xhapp.entity.examination.TestPagerQuestion;
import kaiyi.app.xhapp.service.curriculum.CategoryService;
import kaiyi.app.xhapp.service.examination.ExamQuestionService;
import kaiyi.app.xhapp.service.examination.QuestionService;
import kaiyi.app.xhapp.service.examination.TestPagerService;
import kaiyi.puer.commons.access.AccessControl;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.data.StringEditor;
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
    private TestPagerService testPagerService;

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
    public String questionNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,questionService,3);
        setDefaultPage(interactive,rootPath+"/question");
        return rootPath+"/questionForm";
    }
    @RequestMapping("/question/modify")
    @AccessControl(name = "编辑试题", weight = 5.12f, detail = "编辑试题",
            code = rootPath+ "/question/modify", parent = rootPath+"/question")
    public String questionModify(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,questionService,3);
        setDefaultPage(interactive,rootPath+"/question");
        return rootPath+"/questionForm";
    }
    @RequestMapping("/question/detail")
    @AccessControl(name = "试题详情", weight = 5.13f, detail = "试题详情",
            code = rootPath+ "/question/detail" +
                    "", parent = rootPath+"/question")
    public String questionDelete(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,questionService,3);
        setDefaultPage(interactive,rootPath+"/question");
        return rootPath+"/questionDetail";
    }


    @PostMapping("/question/commit")
    public void visitorRoleCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        if(StringEditor.notEmpty(entityId)){
            questionService.removeChoiceAnswer(entityId);
        }
        Map<String, JavaDataTyper> params=interactive.getRequestParameterMap();
        String choiceAnswer=interactive.getHttpServletRequest().getParameter("choiceAnswer");
        params.put("choiceAnswer",new JavaDataTyper(choiceAnswer));
        JsonMessageCreator msg=executeNewOrUpdate(interactive,questionService,params);
        interactive.writeUTF8Text(msg.build());
    }
    @PostMapping("/question/deleteAnswer")
    public void deleteAnswer(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        questionService.deleteChoiceAnswer(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }

    @RequestMapping("/testPager")
    @AccessControl(name = "试卷管理", weight = 5.2f, detail = "管理考试是全", code = rootPath+ "/testPager", parent = rootPath)
    public String testPager(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/testPager");
        mainTablePage(interactive,testPagerService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/testPager";
    }

    @RequestMapping("/testPager/new")
    @AccessControl(name = "新增试卷", weight = 5.21f, detail = "添加新的试卷",
            code = rootPath+ "/testPager/new", parent = rootPath+"/testPager")
    public String testPagerNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,testPagerService,3);
        setDefaultPage(interactive,rootPath+"/testPager");
        return rootPath+"/testPagerForm";
    }
    @RequestMapping("/testPager/modify")
    @AccessControl(name = "修改试卷", weight = 5.22f, detail = "编辑试卷",
            code = rootPath+ "/testPager/modify", parent = rootPath+"/testPager")
    public String testPagerModify(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        TestPager testPager=newOrEditPage(interactive,testPagerService,3);
        setDefaultPage(interactive,rootPath+"/testPager");
        StreamCollection<TestPagerQuestion> testPagerQuestions = testPagerService.getTestPagerQuestion(testPager.getEntityId());
        interactive.setRequestAttribute("testPagerQuestions",testPagerQuestions);
        return rootPath+"/testPagerForm";
    }
    @RequestMapping("/testPager/detail")
    @AccessControl(name = "试卷详情", weight = 5.23f, detail = "试卷详情",
            code = rootPath+ "/testPager/detail" +
                    "", parent = rootPath+"/testPager")
    public String testPagerDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,testPagerService,3);
        setDefaultPage(interactive,rootPath+"/testPager");
        return rootPath+"/testPagerDetail";
    }



    //TODO 添加试题库，试卷的启用停用属性
}
