package kaiyi.app.xhapp.controller.mgr;


import kaiyi.app.xhapp.entity.examination.*;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.examination.*;
import kaiyi.app.xhapp.service.pub.ConfigureService;
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
import kaiyi.puer.h5ui.service.DocumentService;
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
    @Resource
    private ConfigureService configureService;
    @Resource
    private SimulationCategoryService simulationCategoryService;
    @Resource
    private ExamQuestionService examQuestionService;
    @Resource
    private ExamQuestionItemService examQuestionItemService;

    @RequestMapping("/question")
    @AccessControl(name = "试题库", weight = 5.1f, detail = "管理试题库内容", code = rootPath+ "/question", parent = rootPath)
    public String question(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/question");
        mainTablePage(interactive,questionService,null,null,
                new DynamicGridInfo(true,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/question";
    }
    @RequestMapping("/question/new")
    @AccessControl(name = "新增试题", weight = 5.11f, detail = "添加新的试题",
            code = rootPath+ "/question/new", parent = rootPath+"/question")
    public String questionNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,questionService,3);
        setDefaultPage(interactive,rootPath+"/question");
        interactive.setRequestAttribute("defaultItems",new String[]{"A","B","C","D"});
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
            code = rootPath+ "/question/detail", parent = rootPath+"/question")
    public String questionDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
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

    @RequestMapping("/question/batchDelete")
    public void questionBatchDelete(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        StreamArray<String> entityIdArray=interactive.getStringStreamArray("entityIdArray",",");
        entityIdArray.forEachByOrder((i,d)->{
            questionService.deleteById(d,false);
        });
        testPagerService.updateNumber();
        interactive.writeUTF8Text(getSuccessMessage().build());
    }

    @RequestMapping("/question/delete")
    @AccessControl(name = "删除试题", weight = 5.15f, detail = "删除试题",
            code = rootPath+ "/question/delete", parent = rootPath+"/question")
    public void questionDelete(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        questionService.deleteById(entityId,true);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }

    @PostMapping("/question/batchEnable")
    public void questionBatchEnable(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        StreamArray<String> entityIdArray=interactive.getStringStreamArray("entityIdArray",",");
        boolean enable=interactive.getBoolean("enable","true",false);
        questionService.batchEnable(entityIdArray,enable);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }

    /**
     * 一键全部启用或停用
     * @param interactive
     * @param response
     */
    @PostMapping("/question/batchEnableOrDisableAll")
    public void questionBatchEnableAll(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        boolean enable=interactive.getBoolean("enable","true",true);
        questionService.batchEnableAll(enable);
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
                            questionReference.set(questionService.parseQuestion(line));
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
        String serverPathParams=configureService.getStringValue(ConfigureItem.DOC_SERVER_PREFIX);
        String storagePathParams=configureService.getStringValue(ConfigureItem.DOC_SAVE_PATH);
        String detail=params.get("detail").stringValue();
        detail=DocumentService.replaceImageSrc(detail,AccessController.getAccessTempFilePathPrefix(interactive),
                storagePathParams,serverPathParams);
        params.put("detail",new JavaDataTyper(detail));
        String analysis=params.get("analysis").stringValue();
        analysis=DocumentService.replaceImageSrc(analysis,AccessController.getAccessTempFilePathPrefix(interactive),
                storagePathParams,serverPathParams);
        params.put("analysis",new JavaDataTyper(analysis));
        JsonMessageCreator msg=executeNewOrUpdate(interactive,questionService,params,storagePathParams);
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

    @RequestMapping("/simulationCategory")
    @AccessControl(name = "模拟试题分类", weight = 5.4f, detail = "模拟试题分类", code = rootPath+ "/simulationCategory", parent = rootPath)
    public String simulationCategory(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/simulationCategory");
        String parent=interactive.getStringParameter("parent","");
        interactive.setRequestAttribute("parent",parent);
        QueryExpress queryExpress=null;
        FormElementHidden[] hiddens=null;
        if(StringEditor.notEmpty(parent)){
            SimulationCategory questionCategory=simulationCategoryService.findForPrimary(parent);
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
                interactive.getWebPage().setBackPage(rootPath+"/simulationCategory",params);
                interactive.setRequestAttribute("hasParent",true);
            }
        }else{
            queryExpress=new NullQueryExpress("parent",NullQueryExpress.NullCondition.IS_NULL);
            hiddens=new FormElementHidden[]{
                    new FormElementHidden("topParent","topParent"),
                    new FormElementHidden("deliver","true")
            };
        }
        mainTablePage(interactive,simulationCategoryService,queryExpress,hiddens,new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/simulationCategory";
    }

    @RequestMapping("/simulationCategory/new")
    @AccessControl(name = "新增模拟试题分类", weight = 5.41f,code = rootPath+ "/simulationCategory/new", parent = rootPath+"/simulationCategory")
    public String simulationCategoryNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        String parent=interactive.getStringParameter("parent","");
        if(Objects.nonNull(parent)){
            interactive.setRequestAttribute("parent",simulationCategoryService.findForPrimary(parent));
        }
        newOrEditPage(interactive,simulationCategoryService,3);
        setDefaultPage(interactive,rootPath+"/simulationCategory");
        return rootPath+"/simulationCategoryForm";
    }
    @RequestMapping("/simulationCategory/modify")
    @AccessControl(name = "编辑模拟试题分类", weight = 5.42f,code = rootPath+ "/simulationCategory/modify", parent = rootPath+"/simulationCategory")
    public String simulationCategoryModify(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,simulationCategoryService,3);
        setDefaultPage(interactive,rootPath+"/simulationCategory");
        String parent=interactive.getStringParameter("parent","");
        if(Objects.nonNull(parent)){
            interactive.setRequestAttribute("parent",simulationCategoryService.findForPrimary(parent));
        }
        return rootPath+"/simulationCategoryForm";
    }
    @PostMapping("/simulationCategory/commit")
    public void simulationCategoryCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=executeNewOrUpdate(interactive,simulationCategoryService);
        interactive.writeUTF8Text(msg.build());
    }

    @RequestMapping("/simulationCategoryEnableQuery")
    public String simulationCategoryEnableQuery(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        StreamCollection<SimulationCategory> categories=simulationCategoryService.getEnableRootCategory();
        String treeData=simulationCategoryService.toJsonTree(categories,"categoryQueryTree.ftlh");
        if(StringEditor.isEmpty(treeData)){
            treeData=JsonCreator.EMPTY_JSON;
        }
        interactive.setRequestAttribute("referenceQueryName",interactive.getStringParameter("referenceQueryName",""));
        interactive.setRequestAttribute("referenceQueryId",interactive.getStringParameter("referenceQueryId",""));
        interactive.setRequestAttribute("treeData",treeData);
        return rootPath+"/simulationCategoryQuery";
    }

    @RequestMapping("/examQuestion")
    @AccessControl(name = "再答考试", weight = 5.5f, detail = "进行中的考试内容", code = rootPath+ "/examQuestion", parent = rootPath)
    public String examQuestion(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/examQuestion");
        mainTablePage(interactive,examQuestionService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/examQuestion";
    }
    @RequestMapping("/examQuestion/detail")
    @AccessControl(name = "试题详情", weight = 5.51f, detail = "试题详情",
            code = rootPath+ "/examQuestion/detail", parent = rootPath+"/examQuestion",defaultAuthor = true)
    public String examQuestionDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        ExamQuestion question=detailPage(interactive,examQuestionService,3);
        QueryExpress queryExpress=new CompareQueryExpress("examQuestion", CompareQueryExpress.Compare.EQUAL,question);
        StreamCollection<ExamQuestionItem> items=examQuestionItemService.getEntitys(queryExpress);
        interactive.setRequestAttribute("items",items);
        setDefaultPage(interactive,rootPath+"/examQuestion");
        return rootPath+"/examQuestionDetail";
    }

    @RequestMapping("/examQuestionItem")
    @AccessControl(name = "考试试题", weight = 5.6f, detail = "正在进行考试的试题", code = rootPath+ "/examQuestionItem", parent = rootPath)
    public String examQuestionItem(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/examQuestionItem");
        mainTablePage(interactive,examQuestionItemService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.none));
        return rootPath+"/examQuestionItem";
    }

    @RequestMapping("/examQuestionItem/detail")
    @AccessControl(name = "考试试题详情", weight = 5.61f, detail = "试题详情",
            code = rootPath+ "/examQuestionItem/detail", parent = rootPath+"/examQuestionItem",defaultAuthor = true)
    public String examQuestionItemDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,examQuestionItemService,3);
        setDefaultPage(interactive,rootPath+"/examQuestionItem");
        return rootPath+"/examQuestionItemDetail";
    }
}
