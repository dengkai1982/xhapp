package kaiyi.app.xhapp.controller.app;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.log.ShortMessageSenderNoteService;
import kaiyi.app.xhapp.service.pub.ConfigureService;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.json.JsonCreator;
import kaiyi.puer.json.creator.JsonMessageCreator;
import kaiyi.puer.json.creator.MutilJsonCreator;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping(AccountAction.rootPath)
public class AccountAction extends SuperAction {
    public static final String rootPath=PREFIX+"/account";

    @Resource
    private AccountService accountService;
    @Resource
    private ShortMessageSenderNoteService shortMessageSenderNoteService;
    @Resource
    private ConfigureService configureService;
    /**
     * 修改用户信息
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/changeAccountInfo")
    public void visitorRoleCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String docSavePath=configureService.getStringValue(ConfigureItem.DOC_SAVE_PATH);
        JsonMessageCreator msg=executeNewOrUpdate(interactive,accountService,docSavePath);
        interactive.writeUTF8Text(msg.build());
    }
    /**
     * 用户注册
     * phone 手机号码
     * password 密码
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/register")
    public void register(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String phone=interactive.getStringParameter("phone","");
        String password=interactive.getStringParameter("password","");
        //短信验证码
        String validateCode=interactive.getStringParameter("validateCode","");
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            accountService.register(phone,password,validateCode);
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Text(jmc.build());
    }

    /**
     * 发送验证码短信
     * @param interactive
     * @param response
     */
    @PostMapping("/sendShorMessage")
    public void sendShorMessage(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        //手机号码
        String phone=interactive.getStringParameter("phone","");
        //用途,比如用户注册，找回密码
        String usage=interactive.getStringParameter("usage","");
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            shortMessageSenderNoteService.sendValidateCode(phone,usage);
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Json(jmc);
    }

    /**
     * 密码找回
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/resetAccountPassword")
    public void resetAccountPassword(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String phone=interactive.getStringParameter("phone","");
        String validateCode=interactive.getStringParameter("validateCode","");
        String password=interactive.getStringParameter("password","");
        JsonMessageCreator jmc=getSuccessMessage();
        if(!shortMessageSenderNoteService.validateCode(phone,validateCode)){
            jmc.setCode(JsonMessageCreator.FAIL);
            jmc.setMsg("验证码错误");
        }else{
            try {
                accountService.resetPassword(phone,password);
            } catch (ServiceException e) {
                catchServiceException(jmc,e);
            }
        }
        interactive.writeUTF8Text(jmc.build());
    }
    /**
     * 修改密码
     * phone 手机号码
     * oldPassword 旧密码
     * newPassword 新密码
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/changePassword")
    public void changePassword(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String phone=interactive.getStringParameter("phone","");
        String oldPassword=interactive.getStringParameter("oldPassword","");
        String newPassword=interactive.getStringParameter("newPassword","");
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            accountService.changePassword(phone,oldPassword,newPassword);
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Text(jmc.build());
    }

    @PostMapping("/login")
    public void login(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String phone=interactive.getStringParameter("phone","");
        String password=interactive.getStringParameter("password","");
        JsonMessageCreator jmc=getSuccessMessage();
        MutilJsonCreator mutilJsonCreator=new MutilJsonCreator(jmc);
        try {
            Account account=accountService.login(phone,password);
            mutilJsonCreator.addJsonCreator(defaultWriteObject(account));
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Text(mutilJsonCreator.build());
    }

}
