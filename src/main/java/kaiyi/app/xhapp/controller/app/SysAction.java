package kaiyi.app.xhapp.controller.app;

import kaiyi.app.xhapp.service.sys.ConsultationService;
import kaiyi.app.xhapp.service.sys.QNumberManagerService;
import kaiyi.puer.db.orm.ServiceException;
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
@RequestMapping(SysAction.rootPath)
public class SysAction extends SuperAction{
    public static final String rootPath=PREFIX+"/sys";
    @Resource
    private ConsultationService consultationService;
    @Resource
    private QNumberManagerService qNumberManagerService;
    /**
     * 系统留言
     * @param interactive
     * @param response
     */
    @PostMapping("/consultationCommit")
    public void consultationCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String accountId=interactive.getStringParameter("accountId","");
        String content=interactive.getStringParameter("content","");
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            consultationService.commitConsultation(accountId,content);
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Text(jmc.build());
    }
    /**
     * 随机获取QQ号码
     */
    @RequestMapping("/getRandomQQNumber")
    public void getRandomQQNumber(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator jmc=getSuccessMessage();
        jmc.setBody(qNumberManagerService.getRandomQQNumber());
        interactive.writeUTF8Text(jmc.build());
    }
}