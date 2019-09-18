package kaiyi.app.xhapp.controller.mgr;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.jobs.*;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.jobs.*;
import kaiyi.puer.commons.access.AccessControl;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.StringEditor;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.CompareQueryExpress.Compare;
import kaiyi.puer.db.query.NullQueryExpress;
import kaiyi.puer.db.query.NullQueryExpress.NullCondition;
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
    @Resource
    private ConcernRecruitmentService concernRecruitmentService;
    @Resource
    private ConcernResumeService concernResumeService;
    @Resource
    private AccountService accountService;
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

    @AccessControl(name = "显示/隐藏", weight = 4.13f, detail = "显示或隐藏职务信息",
            code = rootPath+ "/position/showable", parent = rootPath+"/position")
    @RequestMapping("/position/showable")
    public void positionShowable(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        positionService.changeShowable(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }


    @RequestMapping("/enterprise")
    @AccessControl(name = "企业信息", weight = 4.2f, detail = "企业信息", code = rootPath+ "/enterprise", parent = rootPath)
    public String enterprise(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/enterprise");
        /*mainTablePage(interactive,enterpriseService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));*/
        FormElementHidden[] formElementHiddens=null;
        String onlyEntityId=interactive.getStringParameter("onlyEntityId","");
        if(StringEditor.notEmpty(onlyEntityId)){
            formElementHiddens=new FormElementHidden[]{
                    new FormElementHidden("onlyEntityId",onlyEntityId)
            };
        }
        PageElementManager pm=getH5UIService().getPageElementManager();
        StreamCollection<PageFieldData> tableFieldDatas=pm.getPageFieldDatas(enterpriseService.getEntityClassName());
        tablePage(interactive,enterpriseService,null,formElementHiddens,pm.getSearchableFieldData(enterpriseService.getEntityClassName()),
                tableFieldDatas,new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/enterprise";
    }
    @RequestMapping("/enterprise/detail")
    @AccessControl(name = "企业详情", weight = 4.21f, detail = "企业详情",
            code = rootPath+ "/enterprise/detail", parent = rootPath+"/enterprise")
    public String enterpriseDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        Enterprise enterprise=detailPage(interactive,enterpriseService,3);
        Account owner=enterprise.getOwner();
        Account insideMember=accountService.findParentInsideMember(owner.getEntityId());
        enterprise.setParentInsideAccount(insideMember);
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

    @AccessControl(name = "企业认证", weight = 4.23f, detail = "设置企业的认证状态", code = rootPath
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
        Recruitment recruitment=detailPage(interactive,recruitmentService,3);
        Account owner=recruitment.getEnterprise().getOwner();
        Account insideMember=accountService.findParentInsideMember(owner.getEntityId());
        recruitment.getEnterprise().setParentInsideAccount(insideMember);
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
    @AccessControl(name = "发布/取消发布", weight = 4.33f, detail = "发布或取消发布招聘信息", code = rootPath
            + "/recruitment/changeUpper", parent = rootPath+"/recruitment")
    @RequestMapping("/recruitment/changeUpper")
    public void recruitmentChangeUpper(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        recruitmentService.changeUpper(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
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
        Account owner=resume.getOwner();
        StreamCollection<Certificate> certificates=certificateService.getEntitys(new CompareQueryExpress("owner",Compare.EQUAL,owner));
        interactive.setRequestAttribute("certificates",certificates);
        return rootPath+"/resumeDetail";
    }
    @AccessControl(name = "发布/取消发布", weight = 4.42f, detail = "发布或取消发布企业信息",
            code = rootPath+ "/resume/changeUpper", parent = rootPath+"/resume")
    @RequestMapping("/resume/changeUpper")
    public void resumeChangeUpper(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        resumeService.changeUpper(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
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
        Certificate cert=detailPage(interactive,certificateService,3);
        Account owner=cert.getOwner();
        Account insideMember=accountService.findParentInsideMember(owner.getEntityId());
        owner.setParentInsideAccount(insideMember);
        setDefaultPage(interactive,rootPath+"/certificate");
        return rootPath+"/certificateDetail";
    }

    /*@RequestMapping("/concernEnterprise")
    @AccessControl(name = "企业关注", weight = 4.6f, detail = "企业关注", code = rootPath+ "/concernEnterprise", parent = rootPath)
    public String concernEnterprise(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/concernEnterprise");
        mainTablePage(interactive,concernEnterpriseService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/concernEnterprise";
    }
    @RequestMapping("/concernEnterprise/detail")
    @AccessControl(name = "企业关注详情", weight = 4.61f, detail = "招聘详情",
            code = rootPath+ "/concernEnterprise/detail", parent = rootPath+"/concernEnterprise")
    public String concernEnterpriseDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,concernEnterpriseService,3);
        setDefaultPage(interactive,rootPath+"/concernEnterprise");
        return rootPath+"/concernEnterpriseDetail";
    }*/
    @RequestMapping("/concernRecruitment")
    @AccessControl(name = "招聘信息关注", weight = 4.7f, detail = "招聘信息关注", code = rootPath+ "/concernRecruitment", parent = rootPath)
    public String concernRecruitment(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/concernRecruitment");
        FormElementHidden[] formElementHiddens=null;
        String onlyEntityId=interactive.getStringParameter("onlyEntityId","");
        if(StringEditor.notEmpty(onlyEntityId)){
            formElementHiddens=new FormElementHidden[]{
                    new FormElementHidden("onlyEntityId",onlyEntityId)
            };
        }
        mainTablePage(interactive,concernRecruitmentService,null,formElementHiddens,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/concernRecruitment";
    }
    @RequestMapping("/concernRecruitment/detail")
    @AccessControl(name = "招聘信息关注详情", weight = 4.71f, detail = "招聘详情",
            code = rootPath+ "/concernRecruitment/detail", parent = rootPath+"/concernRecruitment")
    public String concernRecruitmentDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        ConcernRecruitment concernRecruitment=detailPage(interactive,concernRecruitmentService,3);
        interactive.setRequestAttribute("entity",concernRecruitment);
        setDefaultPage(interactive,rootPath+"/concernRecruitment");
        return rootPath+"/concernRecruitmentDetail";
    }
    @RequestMapping("/concernResume")
    @AccessControl(name = "简历关注", weight = 4.8f, detail = "简历关注", code = rootPath+ "/concernResume", parent = rootPath)
    public String concernResume(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/concernResume");
        FormElementHidden[] formElementHiddens=null;
        String onlyEntityId=interactive.getStringParameter("onlyEntityId","");
        if(StringEditor.notEmpty(onlyEntityId)){
            formElementHiddens=new FormElementHidden[]{
                    new FormElementHidden("onlyEntityId",onlyEntityId)
            };
        }
        mainTablePage(interactive,concernResumeService,null,formElementHiddens,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/concernResume";
    }
    @RequestMapping("/concernResume/detail")
    @AccessControl(name = "简历关注详情", weight = 4.81f, detail = "招聘详情",
            code = rootPath+ "/concernResume/detail", parent = rootPath+"/concernResume")
    public String concernResumeDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        ConcernResume concernResume=detailPage(interactive,concernResumeService,3);
        interactive.setRequestAttribute("entity",concernResume);
        setDefaultPage(interactive,rootPath+"/concernResume");
        return rootPath+"/concernResumeDetail";
    }

}
