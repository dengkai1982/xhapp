package kaiyi.app.xhapp.controller.app;

import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.entity.sys.enums.CustomerType;
import kaiyi.app.xhapp.service.pub.ConfigureService;
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
    @Resource
    private ConfigureService configureService;
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
        CustomerType customerType=interactive.getEnumParameterByOrdinal(CustomerType.class,"customerType", CustomerType.sy);
        jmc.setBody(qNumberManagerService.getRandomQQNumber(customerType));
        interactive.writeUTF8Text(jmc.build());
    }

    /**
     * 查询应用版本
     * @param interactive
     * @param response
     */
    @RequestMapping("/queryVersion")
    public void queryVersion(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String type=interactive.getStringParameter("type","");
        JsonMessageCreator jmc=getSuccessMessage();
        if(type.equals("ios")){
            jmc.setBody(configureService.getStringValue(ConfigureItem.CURRENT_IOS_VERSION));
        }else{
            jmc.setBody(configureService.getStringValue(ConfigureItem.CURRENT_ANDROID_VERSION));
        }
        interactive.writeUTF8Text(jmc.build());
    }
}
