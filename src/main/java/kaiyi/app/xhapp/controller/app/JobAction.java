package kaiyi.app.xhapp.controller.app;

import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.jobs.*;
import kaiyi.app.xhapp.service.pub.ConfigureService;
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
    private ConcernCertificateService concernCertificateService;
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
        JsonMessageCreator msg=executeNewOrUpdate(interactive,enterpriseService,docSavePath);
        interactive.writeUTF8Text(msg.build());
    }

    /**
     * 添加证书关注
     * 参数 account:账号ID
     * certificate:证号ID
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/concernCertificate")
    public void concernCertificate(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=executeNewOrUpdate(interactive,concernCertificateService);
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
     * 取消企业关注
     * @param interactive
     * @param response
     */
    @PostMapping("/cancelConcernCertificate")
    public void cancelConcernCertificate(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        String entityId=interactive.getStringParameter("entityId","");
        concernEnterpriseService.deleteForPrimary(entityId);
    }
}
