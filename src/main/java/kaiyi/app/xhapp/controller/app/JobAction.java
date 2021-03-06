package kaiyi.app.xhapp.controller.app;

import kaiyi.app.xhapp.controller.mgr.ManagerController;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.jobs.*;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.jobs.*;
import kaiyi.app.xhapp.service.pub.ConfigureService;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.StringEditor;
import kaiyi.puer.db.Pagination;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.ContainQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.json.JsonCreator;
import kaiyi.puer.json.creator.JsonMessageCreator;
import kaiyi.puer.json.creator.ObjectJsonCreator;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping(JobAction.rootPath)
public class JobAction extends SuperAction {
    public static final String rootPath=PREFIX+"/job";
    @Resource
    private ResumeService resumeService;
    @Resource
    private ConfigureService configureService;
    @Resource
    private WorkExperienceService workExperienceService;
    @Resource
    private CertificateService certificateService;
    @Resource
    private RecruitmentService recruitmentService;
    @Resource
    private EnterpriseService enterpriseService;
    @Resource
    private ConcernEnterpriseService concernEnterpriseService;
    @Resource
    private ConcernResumeService concernResumeService;
    @Resource
    private ConcernRecruitmentService concernRecruitmentService;
    @Resource
    private AccountService accountService;
    /**
     * 新增修改个人简历
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/commitResume")
    public void commitResume(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String docSavePath=configureService.getStringValue(ConfigureItem.DOC_SAVE_PATH);
        JsonMessageCreator msg=executeNewOrUpdate(interactive,resumeService,docSavePath);
        interactive.writeUTF8Text(msg.build());
    }

    /**
     * 新增修改工作经历
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/commitWorkExperience")
    public void commitWorkExperience(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=executeNewOrUpdate(interactive,workExperienceService);
        interactive.writeUTF8Text(msg.build());
    }
    /**
     * 新增修改个人证书
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/commitCertificate")
    public void commitCertificate(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String docSavePath=configureService.getStringValue(ConfigureItem.DOC_SAVE_PATH);
        JsonMessageCreator msg=executeNewOrUpdate(interactive,certificateService,docSavePath);
        interactive.writeUTF8Text(msg.build());
    }

    /**
     * 删除个人证书
     * @param interactive
     * @param response
     */
    @PostMapping("/deleteCertificate")
    public void deleteCertificate(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        certificateService.deleteCertificate(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }


    /**
     * 新增修改招聘信息
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/commitRecruitment")
    public void commitRecruitment(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=executeNewOrUpdate(interactive,recruitmentService);
        interactive.writeUTF8Text(msg.build());
    }
    /**
     * 新增修企业信息
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/commitEnterprise")
    public void commitEnterprise(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String docSavePath=configureService.getStringValue(ConfigureItem.DOC_SAVE_PATH);
        AtomicReference<Enterprise> reference=new AtomicReference<>();
        JsonMessageCreator msg=executeNewOrUpdate(interactive,enterpriseService,interactive.getRequestParameterMap(),docSavePath,
                reference);
        if(msg.getCode().equals(JsonMessageCreator.SUCCESS)){
            Enterprise enterprise=reference.get();
            sendInsideMessage(interactive,"有新的企业加入,企业名称:"+enterprise.getEnterpriseName(),
                    ManagerController.prefix+"/personnel/enterprise",enterprise.getEntityId());
        }
        interactive.writeUTF8Text(msg.build());
    }

    /**
     * 添加关注简历
     * 参数 enterpriseId:企业ID
     * resume:简历ID
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/concernResume")
    public void concernResume(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        AtomicReference<ConcernResume> reference=new AtomicReference<>();
        JsonMessageCreator msg=executeNewOrUpdate(interactive,concernResumeService,interactive.getRequestParameterMap(),
                null,reference);
        if(msg.getCode().equals(JsonMessageCreator.SUCCESS)){
            ConcernResume resume=reference.get();
            Enterprise enterprise=enterpriseService.findForPrimary(resume.getEnterprise().getEntityId());
            sendInsideMessage(interactive,"有新的简历关注,企业名称:"+enterprise.getEnterpriseName(),
                    ManagerController.prefix+"/personnel/concernResume",resume.getEntityId());
        }
        interactive.writeUTF8Text(msg.build());
    }

    /**
     * 添加企业关注
     * 参数 account:账号ID
     * enterprise:企业ID
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/concernEnterprise")
    public void concernEnterprise(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=executeNewOrUpdate(interactive,concernEnterpriseService);
        interactive.writeUTF8Text(msg.build());
    }

    /**
     * 取消企业关注
     * @param interactive
     * @param response
     */
    @PostMapping("/cancelConcernEnterprise")
    public void cancelConcernEnterprise(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        String entityId=interactive.getStringParameter("entityId","");
        concernEnterpriseService.deleteForPrimary(entityId);
    }
    /**
     * 取消简历关注20190703
     * @param interactive
     * @param response
     */
    @PostMapping("/cancelConcernResume")
    public void cancelConcernCertificate(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        String entityId=interactive.getStringParameter("entityId","");
        concernResumeService.deleteForPrimary(entityId);
        insideNoticeService.deleteByUrl(getSendUrl(interactive,"/personnel/concernResume",entityId));
    }
    /**
     * 添加关注招聘信息
     * 参数 account:账号ID
     * recruitment:招聘岗位ID
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/concernRecruitment")
    public void concernRecruitment(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        AtomicReference<ConcernRecruitment> reference=new AtomicReference<>();
        JsonMessageCreator msg=executeNewOrUpdate(interactive,concernRecruitmentService,interactive.getRequestParameterMap(),
                null,reference);
        if(msg.getCode().equals(JsonMessageCreator.SUCCESS)){
            ConcernRecruitment concernRecruitment=reference.get();
            Account account=accountService.findForPrimary(concernRecruitment.getAccount().getEntityId());
            sendInsideMessage(interactive,"有新的招聘信息被关注,关注人:"+account.getShowAccountName(),
                    ManagerController.prefix+"/personnel/concernRecruitment",concernRecruitment.getEntityId());
        }
        interactive.writeUTF8Text(msg.build());
    }
    /**
     * 取消招聘信息关注20190704
     * @param interactive
     * @param response
     */
    @PostMapping("/cancelConcernRecruitment")
    public void cancelConcernRecruitment(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        String entityId=interactive.getStringParameter("entityId","");
        concernRecruitmentService.deleteForPrimary(entityId);
        insideNoticeService.deleteByUrl(getSendUrl(interactive,"/personnel/concernRecruitment",entityId));
    }

    /**
     * 根据简历的所有人来查询简历关注信息
     * @param interactive
     * @param response
     * @throws IOException
     */
    @RequestMapping("/queryConcernResumeByOwner")
    public void queryConcernResumeByOwner(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String accountId=interactive.getStringParameter("accountId","");
        if(StringEditor.notEmpty(accountId)){
            Account owner=new Account();
            owner.setEntityId(accountId);
            QueryExpress query=new CompareQueryExpress("owner", CompareQueryExpress.Compare.EQUAL,owner);
            StreamCollection<Resume> resumes=resumeService.getEntitys(query);
            if(resumes.assertNotEmpty()){
                query=new ContainQueryExpress<>("resume", ContainQueryExpress.CONTAINER.IN,resumes.toList());
                Pagination<ConcernResume> pagination=concernResumeService.getPagination(0,1000,query);
                ObjectJsonCreator<Pagination> creator=concernResumeService.getCustomerCreator(pagination);
                interactive.writeUTF8Text(creator.build());
            }
        }
        interactive.writeUTF8Text(JsonCreator.EMPTY_JSON);
    }

    /**
     * 根据招聘信息的发布人来查询招聘关注信息。
     * @param interactive
     * @param response
     */
    @RequestMapping("/queryConcernRecruitmentByPublisher")
    public void queryConcernRecruitmentByPublisher(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String accountId=interactive.getStringParameter("accountId","");
        if(StringEditor.notEmpty(accountId)){
            Account publisher=new Account();
            publisher.setEntityId(accountId);
            QueryExpress query=new CompareQueryExpress("publisher", CompareQueryExpress.Compare.EQUAL,publisher);
            StreamCollection<Recruitment> recruitments=recruitmentService.getEntitys(query);
            if(recruitments.assertNotEmpty()){
                query=new ContainQueryExpress<>("recruitment", ContainQueryExpress.CONTAINER.IN,recruitments.toList());
                Pagination<ConcernRecruitment> pagination=concernRecruitmentService.getPagination(0,1000,query);
                ObjectJsonCreator<Pagination> creator=concernRecruitmentService.getCustomerCreator(pagination);
                interactive.writeUTF8Text(creator.build());
            }
        }
        interactive.writeUTF8Text(JsonCreator.EMPTY_JSON);
    }

    /**
     * 企业招聘信息上下架
     * @param interactive
     * @param response
     */
    @RequestMapping("/uppperRecruitment")
    public void uppperRecruitment(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        recruitmentService.changeUpper(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }

    /**
     * 删除企业招聘信息
     * @param interactive
     * @param response
     */
    @RequestMapping("/deleteRecruitment")
    public void deleteRecruitment(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        recruitmentService.deleteRecruitment(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }

    /**
     * 个人简历上下架
     * @param interactive
     * @param response
     */
    @RequestMapping("/upperResume")
    public void upperResume(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        resumeService.changeUpper(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }

    /**
     * 删除个人简历
     * @param interactive
     * @param response
     */
    @RequestMapping("/deleteResume")
    public void deleteResume(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        resumeService.deleteResume(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }
}
