package kaiyi.app.xhapp.controller.mgr;

import kaiyi.app.xhapp.entity.jobs.Position;
import kaiyi.app.xhapp.entity.jobs.Recruitment;
import kaiyi.app.xhapp.entity.jobs.Resume;
import kaiyi.app.xhapp.entity.jobs.WorkExperience;
import kaiyi.app.xhapp.service.jobs.*;
import kaiyi.puer.commons.access.AccessControl;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.StringEditor;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.CompareQueryExpress.*;
import kaiyi.puer.db.query.NullQueryExpress;
import kaiyi.puer.db.query.NullQueryExpress.*;
import kaiyi.puer.db.query.OrderBy;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.h5ui.bean.DynamicGridInfo;
import kaiyi.puer.h5ui.bean.PageFieldData;
import kaiyi.puer.h5ui.service.PageElementManager;
import kaiyi.puer.json.creator.JsonMessageCreator;
import kaiyi.puer.web.elements.FormElementHidden;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping(PersonnelController.rootPath)
@AccessControl(name = "人才库", weight = 4f, detail = "人才信息库管理", code = PersonnelController.rootPath)
public class PersonnelController extends ManagerController{
    public static final String rootPath=prefix+"/personnel";
    @Resource
    private PositionService positionService;
    @Resource
    private EnterpriseService enterpriseService;
    @Resource
    private RecruitmentService recruitmentService;
    @Resource
    private ResumeService resumeService;
    @Resource
    private WorkExperienceService workExperienceService;
    @Resource
    private CertificateService certificateService;
    @RequestMapping("/position")
    @AccessControl(name = "招聘职位", weight = 4.1f, detail = "管理招聘职位", code = rootPath+ "/position", parent = rootPath)
    public String position(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/position");
        String parent=interactive.getStringParameter("parent","");
        interactive.setRequestAttribute("parent",parent);
        QueryExpress queryExpress=null;
        FormElementHidden[] hiddens=null;
        if(StringEditor.notEmpty(parent)){
            Position parentposition=positionService.findForPrimary(parent);
            hiddens=new FormElementHidden[]{
                    new FormElementHidden("parent",parent)
            };
            queryExpress=new CompareQueryExpress("parent",Compare.EQUAL,parentposition);
            if(Objects.nonNull(parentposition)){
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
                if(parentposition.getParent()!=null){
                    params.put("parent",parentposition.getParent().getEntityId());
                }
                interactive.getWebPage().setBackPage(rootPath+"/position",params);
                interactive.setRequestAttribute("hasParent",true);
            }
        }else{
            queryExpress=new NullQueryExpress("parent",NullCondition.IS_NULL);
            hiddens=new FormElementHidden[]{
                    new FormElementHidden("topParent","topParent"),
                    new FormElementHidden("deliver","true")
            };
        }
        mainTablePage(interactive,positionService,queryExpress,hiddens,new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/position";
    }
    @RequestMapping("/position/new")
    @AccessControl(name = "新增招聘职位", weight = 4.11f,code = rootPath+ "/position/new", parent = rootPath+"/position")
    public String positionNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        String parent=interactive.getStringParameter("parent","");
        if(Objects.nonNull(parent)){
            interactive.setRequestAttribute("parent",positionService.findForPrimary(parent));
        }
        newOrEditPage(interactive,positionService,3);
        setDefaultPage(interactive,rootPath+"/position");
        return rootPath+"/positionForm";
    }
    @RequestMapping("/position/modify")
    @AccessControl(name = "编辑招聘职位", weight = 4.12f,code = rootPath+ "/position/modify", parent = rootPath+"/position")
    public String positionModify(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,positionService,3);
        setDefaultPage(interactive,rootPath+"/position");
        String parent=interactive.getStringParameter("parent","");
        if(Objects.nonNull(parent)){
            interactive.setRequestAttribute("parent",positionService.findForPrimary(parent));
        }
        return rootPath+"/positionForm";
    }
    @PostMapping("/position/commit")
    public void positionCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=executeNewOrUpdate(interactive,positionService);
        interactive.writeUTF8Text(msg.build());
    }

    @RequestMapping("/enterprise")
    @AccessControl(name = "企业信息", weight = 4.2f, detail = "企业信息", code = rootPath+ "/enterprise", parent = rootPath)
    public String enterprise(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/enterprise");
        /*mainTablePage(interactive,enterpriseService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));*/
        PageElementManager pm=getH5UIService().getPageElementManager();
        StreamCollection<PageFieldData> tableFieldDatas=pm.getPageFieldDatas(enterpriseService.getEntityClassName());
        tablePage(interactive,enterpriseService,null,null,pm.getSearchableFieldData(enterpriseService.getEntityClassName()),
                tableFieldDatas,new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/enterprise";
    }
    @RequestMapping("/enterprise/detail")
    @AccessControl(name = "企业详情", weight = 4.21f, detail = "企业详情",
            code = rootPath+ "/enterprise/detail", parent = rootPath+"/enterprise")
    public String enterpriseDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,enterpriseService,3);
        setDefaultPage(interactive,rootPath+"/enterprise");
        return rootPath+"/enterpriseDetail";
    }
    @AccessControl(name = "设置推荐", weight = 4.22f, detail = "设置企业为推荐企业", code = rootPath
            + "/enterprise/recommend", parent = rootPath+"/enterprise")
    @RequestMapping("/enterprise/recommend")
    public void enterpriseRecommend(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        JsonMessageCreator jmc=getSuccessMessage();
        enterpriseService.changeRecommend(entityId);
        interactive.writeUTF8Text(jmc.build());
    }

    @AccessControl(name = "企业认证", weight = 4.22f, detail = "设置企业的认证状态", code = rootPath
            + "/enterprise/verify", parent = rootPath+"/enterprise")
    @RequestMapping("/enterprise/verify")
    public void enterpriseVerify(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        JsonMessageCreator jmc=getSuccessMessage();
        enterpriseService.changeVerifyed(entityId);
        interactive.writeUTF8Text(jmc.build());
    }
    @RequestMapping("/recruitment")
    @AccessControl(name = "招聘信息", weight = 4.3f, detail = "企业招聘信息", code = rootPath+ "/recruitment", parent = rootPath)
    public String recruitment(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/recruitment");
        mainTablePage(interactive,recruitmentService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/recruitment";
    }
    @RequestMapping("/recruitment/detail")
    @AccessControl(name = "招聘详情", weight = 4.31f, detail = "招聘详情",
            code = rootPath+ "/recruitment/detail", parent = rootPath+"/recruitment")
    public String recruitmentDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,recruitmentService,3);
        setDefaultPage(interactive,rootPath+"/recruitment");
        return rootPath+"/recruitmentDetail";
    }
    @AccessControl(name = "设置推荐", weight = 4.32f, detail = "设置岗位为推荐岗位", code = rootPath
            + "/recruitment/recommend", parent = rootPath+"/recruitment")
    @RequestMapping("/recruitment/recommend")
    public void recruitmentRecommend(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        JsonMessageCreator jmc=getSuccessMessage();
        recruitmentService.changeRecommend(entityId);
        interactive.writeUTF8Text(jmc.build());
    }
    @RequestMapping("/resume")
    @AccessControl(name = "个人简历", weight = 4.4f, detail = "个人简历", code = rootPath+ "/resume", parent = rootPath)
    public String resume(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/resume");
        mainTablePage(interactive,resumeService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/resume";
    }
    @RequestMapping("/resume/detail")
    @AccessControl(name = "简历详情", weight = 4.41f, detail = "招聘详情",
            code = rootPath+ "/resume/detail", parent = rootPath+"/resume")
    public String resumeDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        Resume resume=detailPage(interactive,resumeService,3);
        setDefaultPage(interactive,rootPath+"/resume");
        QueryExpress query=new CompareQueryExpress("resume",Compare.EQUAL,resume);
        StreamCollection<WorkExperience> workExperiences=workExperienceService.getEntitys(query,new OrderBy(query.getPrefix(),"startTime", OrderBy.TYPE.ASC));
        interactive.setRequestAttribute("workExperiences",workExperiences);
        return rootPath+"/resumeDetail";
    }
    @RequestMapping("/certificate")
    @AccessControl(name = "个人证书", weight = 4.5f, detail = "个人证书", code = rootPath+ "/certificate", parent = rootPath)
    public String certificate(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/certificate");
        mainTablePage(interactive,certificateService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/certificate";
    }

    @RequestMapping("/certificate/detail")
    @AccessControl(name = "证书详情", weight = 4.51f, detail = "招聘详情",
            code = rootPath+ "/certificate/detail", parent = rootPath+"/certificate")
    public String certificateDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,certificateService,3);
        setDefaultPage(interactive,rootPath+"/certificate");
        return rootPath+"/certificateDetail";
    }
}
