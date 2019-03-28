package kaiyi.app.xhapp.controller.app;

import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.log.ShortMessageSenderNoteService;
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
@RequestMapping(AccountAction.rootPath)
public class AccountAction extends SuperAction {
    public static final String rootPath=PREFIX+"/account";

    @Resource
    private AccountService accountService;
    @Resource
    private ShortMessageSenderNoteService shortMessageSenderNoteService;
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


}
