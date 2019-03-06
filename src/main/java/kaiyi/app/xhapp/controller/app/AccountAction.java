package kaiyi.app.xhapp.controller.app;

import kaiyi.app.xhapp.service.access.AccountService;
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
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            accountService.register(phone,password);
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
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
}
