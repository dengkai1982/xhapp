package kaiyi.app.xhapp.controller.mgr;


import kaiyi.app.xhapp.entity.curriculum.Category;
import kaiyi.app.xhapp.entity.examination.Question;
import kaiyi.app.xhapp.entity.examination.QuestionCategory;
import kaiyi.app.xhapp.entity.examination.TestPager;
import kaiyi.app.xhapp.entity.examination.TestPagerQuestion;
import kaiyi.app.xhapp.service.curriculum.CategoryService;
import kaiyi.app.xhapp.service.examination.ExamQuestionService;
import kaiyi.app.xhapp.service.examination.QuestionCategoryService;
import kaiyi.app.xhapp.service.examination.QuestionService;
import kaiyi.app.xhapp.service.examination.TestPagerService;
import kaiyi.puer.commons.access.AccessControl;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.data.StringEditor;
import kaiyi.puer.commons.poi.ExcelUtils;
import kaiyi.puer.commons.utils.CoderUtil;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.NullQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.h5ui.bean.DynamicGridInfo;
import kaiyi.puer.json.JsonCreator;
import kaiyi.puer.json.creator.JsonMessageCreator;
import kaiyi.puer.web.elements.FormElementHidden;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping(QuestionController.rootPath)
@AccessControl(name = "考试管理", weight = 5f, detail = "管理考试试题与相关考试信息", code = QuestionController.rootPath)
public class QuestionController extends ManagerController {
    public static final String rootPath=prefix+"/examination";
    @Resource
    private QuestionService questionService;
    @Resource
    private TestPagerService testPagerService;
    @Resource
    private QuestionCategoryService questionCategoryService;
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
    @RequestMapping("/question/enable")
    @AccessControl(name = "启用停用试题", weight = 5.14f, detail = "启用停用试题",
            code = rootPath+ "/question/enable" +
                    "", parent = rootPath+"/question")
    public void questionEnable(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        questionService.changeEnable(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }
    //批量导入实体
    @PostMapping("/question/import")
    public void importQuestion(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String hex=interactive.getStringParameter("hex","");
        String path=CoderUtil.hexToString(hex,StringEditor.DEFAULT_CHARSET.displayName());
        File file=new File(path);
        JsonMessageCreator jmc=getSuccessMessage();
        AtomicReference<Question> questionReference=new AtomicReference<>();
        StreamCollection<QuestionCategory> categories=questionCategoryService.getEntitys();
        if(Objects.nonNull(file)&&file.exists()){
            ExcelUtils.readExcel(file, line->{
                if(!line.get(0).getData().stringValue().equals("试题题目")){
                    //表头不做处理
                    try {
                        if(questionService.isQuestion(line)){
                            Question existQuestion=questionReference.get();
                            if(Objects.nonNull(existQuestion)){
                                questionService.saveObject(existQuestion);
                                questionReference.set(null);
                            }
                            questionReference.set(questionService.parseQuestion(line,categories));
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
            questionService.saveObject(questionReference.get());
        }
        interactive.writeUTF8Text(jmc.build());
        file.delete();

    }

    @PostMapping("/question/commit")
    public void questionCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
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
        TestPager testPager=detailPage(interactive,testPagerService,3);
        setDefaultPage(interactive,rootPath+"/testPager");
        StreamCollection<TestPagerQuestion> testPagerQuestions = testPagerService.getTestPagerQuestion(testPager.getEntityId());
        interactive.setRequestAttribute("testPagerQuestions",testPagerQuestions);
        return rootPath+"/testPagerDetail";
    }
    @RequestMapping("/testPager/enable")
    @AccessControl(name = "启用停用试卷", weight = 5.24f, detail = "启用停用试卷",
            code = rootPath+ "/testPager/enable" +
                    "", parent = rootPath+"/testPager")
    public void testPagerEnable(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        testPagerService.changeEnable(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }
    @PostMapping("/testPager/commit")
    public void testPagerCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        if(StringEditor.notEmpty(entityId)){
            testPagerService.clearQuestion(entityId);
        }
        Map<String, JavaDataTyper> params=interactive.getRequestParameterMap();
        String testPagerQuestion=interactive.getHttpServletRequest().getParameter("testPagerQuestion");
        params.put("testPagerQuestion",new JavaDataTyper(testPagerQuestion));
        JsonMessageCreator msg=executeNewOrUpdate(interactive,testPagerService,params);
        interactive.writeUTF8Text(msg.build());
    }
    /**
     * 移除试卷中的试题
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/testPager/removeTestPagerQuestion")
    public void removeTestPagerQuestion(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        testPagerService.removeQuestion(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }

    @RequestMapping("/questionCategory")
    @AccessControl(name = "试题分类", weight = 5.3f, detail = "试题分类", code = rootPath+ "/questionCategory", parent = rootPath)
    public String questionCategory(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/questionCategory");
        String parent=interactive.getStringParameter("parent","");
        interactive.setRequestAttribute("parent",parent);
        QueryExpress queryExpress=null;
        FormElementHidden[] hiddens=null;
        if(StringEditor.notEmpty(parent)){
            QuestionCategory questionCategory=questionCategoryService.findForPrimary(parent);
            hiddens=new FormElementHidden[]{
                    new FormElementHidden("parent",parent)
            };
            queryExpress=new CompareQueryExpress("parent",CompareQueryExpress.Compare.EQUAL,questionCategory);
            if(Objects.nonNull(questionCategory)){
                Map<String,Object> params=new HashMap<>();
                StreamArray<Integer> pageNumbers=getPageNumberStack(interactive);
                boolean isback=interactive.getBoolean("isback","true",false);
                int previous=pageNumbers.getLast();
                if(isback){
                    pageNumbers.removeLast();
                    previous=pageNumbers.getLast();
                }else{
                    interactive.setCurrentPage(1);
                }
                String pageNumber=pageNumbers.joinString(m->{
                    return m.toString();
                },",");
                interactive.setRequestAttribute("pageNumber",pageNumber);
                params.put(WebInteractive.PAGINATION_PARAMETER_CURRENT_PAGE,previous);
                params.put("pageNumber",pageNumber);
                params.put("isback",true);
                if(questionCategory.getParent()!=null){
                    params.put("parent",questionCategory.getParent().getEntityId());
                }
                interactive.getWebPage().setBackPage(rootPath+"/questionCategory",params);
                interactive.setRequestAttribute("hasParent",true);
            }
        }else{
            queryExpress=new NullQueryExpress("parent",NullQueryExpress.NullCondition.IS_NULL);
            hiddens=new FormElementHidden[]{
                    new FormElementHidden("topParent","topParent"),
                    new FormElementHidden("deliver","true")
            };
        }
        mainTablePage(interactive,questionCategoryService,queryExpress,hiddens,new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/questionCategory";
    }

    @RequestMapping("/questionCategory/new")
    @AccessControl(name = "新增试题分类", weight = 5.31f,code = rootPath+ "/questionCategory/new", parent = rootPath+"/questionCategory")
    public String questionCategoryNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        String parent=interactive.getStringParameter("parent","");
        if(Objects.nonNull(parent)){
            interactive.setRequestAttribute("parent",questionCategoryService.findForPrimary(parent));
        }
        newOrEditPage(interactive,questionCategoryService,3);
        setDefaultPage(interactive,rootPath+"/questionCategory");
        return rootPath+"/questionCategoryForm";
    }
    @RequestMapping("/questionCategory/modify")
    @AccessControl(name = "编辑试题分类", weight = 5.32f,code = rootPath+ "/questionCategory/modify", parent = rootPath+"/questionCategory")
    public String questionCategoryModify(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,questionCategoryService,3);
        setDefaultPage(interactive,rootPath+"/questionCategory");
        String parent=interactive.getStringParameter("parent","");
        if(Objects.nonNull(parent)){
            interactive.setRequestAttribute("parent",questionCategoryService.findForPrimary(parent));
        }
        return rootPath+"/questionCategoryForm";
    }
    @PostMapping("/questionCategory/commit")
    public void questionCategoryCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=executeNewOrUpdate(interactive,questionCategoryService);
        interactive.writeUTF8Text(msg.build());
    }

    @RequestMapping("/categoryEnableQuery")
    public String categoryEnableQuery(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        StreamCollection<QuestionCategory> categories=questionCategoryService.getEnableRootCategory();
        String treeData=questionCategoryService.toJsonTree(categories,"categoryQueryTree.ftlh");
        if(StringEditor.isEmpty(treeData)){
            treeData=JsonCreator.EMPTY_JSON;
        }
        interactive.setRequestAttribute("referenceQueryName",interactive.getStringParameter("referenceQueryName",""));
        interactive.setRequestAttribute("referenceQueryId",interactive.getStringParameter("referenceQueryId",""));
        interactive.setRequestAttribute("treeData",treeData);
        return rootPath+"/questionCategoryQuery";
    }

    //categoryEnableQuery
}