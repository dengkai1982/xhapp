package kaiyi.app.xhapp.controller.app;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.log.AmountFlowService;
import kaiyi.app.xhapp.service.log.ShortMessageSenderNoteService;
import kaiyi.app.xhapp.service.log.TeamJoinNoteService;
import kaiyi.app.xhapp.service.pub.ConfigureService;
import kaiyi.puer.commons.data.StringEditor;
import kaiyi.puer.commons.image.QrCodeImage;
import kaiyi.puer.commons.image.SimpleImage;
import kaiyi.puer.commons.utils.CoderUtil;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.json.creator.JsonMessageCreator;
import kaiyi.puer.json.creator.MutilJsonCreator;
import kaiyi.puer.web.servlet.ServletUtils;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
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
    @Resource
    private TeamJoinNoteService teamJoinNoteService;
    /**
     * 获取个人分享二维码
     * recommendId: 个人账户ID
     * @param interactive
     * @param response
     */
    @RequestMapping("/generatorShareQRCode")
    public void generatorShareQRCode(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        /*String recommendId=interactive.getStringParameter("recommendId","");
        Map<String, Object> params=new HashMap<>();
        params.put("recommendId",recommendId);
        String url=ServletUtils.getRequestHostContainerProtolAndPort(interactive.getHttpServletRequest());
        url+=interactive.generatorRequestUrl(rootPath+"/shareRegistPage",params);
        QrCodeImage qrCodeImage=new QrCodeImage(url);
        qrCodeImage.encode(400,400);
        BufferedImage image=qrCodeImage.getCodeImage();
        String realPath=interactive.getHttpServletRequest().getServletContext().getRealPath("/");
        File logoFile=new File(realPath+"/image/logo.png");
        image=QrCodeImage.encodeImgLogo(image,logoFile.toURI().toURL());
        File bgFile=new File(realPath+"/images/bg.jpg");
        SimpleImage sim=new SimpleImage(bgFile);
        sim.water(image,180,685);
        //SimpleImage sim=new SimpleImage(image,"png");
        sim.write(response.getOutputStream());
        qrCodeImage.writeTo(response.getOutputStream(),"png");*/
        shareTo(interactive,response,400,400,180,685,"bg.jpg");
    }
    //获取课程分享二维码
    @RequestMapping("/generatorShareCourse")
    public void generatorShareCourse(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        shareTo(interactive,response,160,160,585,1173,"share_course.png");
    }
    //获取试题分享二维码
    @RequestMapping("/generatorShareQuestion")
    public void generatorShareQuestion(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        shareTo(interactive,response,180,180,453,1125,"share_question.png");
    }
    private void shareTo(WebInteractive interactive, HttpServletResponse response,int width,int height,int waterLeft,int waterRight,
                         String imageName) throws IOException {
        String recommendId=interactive.getStringParameter("recommendId","");
        Map<String, Object> params=new HashMap<>();
        params.put("recommendId",recommendId);
        String url=ServletUtils.getRequestHostContainerProtolAndPort(interactive.getHttpServletRequest());
        url+=interactive.generatorRequestUrl(rootPath+"/shareRegistPage",params);
        //QrCodeImage qrCodeImage=new QrCodeImage(url);
        //qrCodeImage.encode(width,height);
        BufferedImage image=createQRCode(url,width,height);//qrCodeImage.getCodeImage();
        String realPath=interactive.getHttpServletRequest().getServletContext().getRealPath("/");
        File logoFile=new File(realPath+"/image/logo.png");
        image=QrCodeImage.encodeImgLogo(image,logoFile.toURI().toURL());
        File bgFile=new File(realPath+"/images/"+imageName);
        SimpleImage sim=new SimpleImage(bgFile);
        sim.water(image,waterLeft,waterRight);
        sim.write(response.getOutputStream());
        //ImageIO.write(,"png",response.getOutputStream());
        //qrCodeImage.writeTo(response.getOutputStream(),"png");
    }

    private BufferedImage createQRCode(String content,int width,int height){
        BufferedImage barCodeImage=null;
        try {

            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, CoderUtil.charset.toString());
            // 设置QR二维码的纠错级别——这里选择最高H级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.MARGIN, 0);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
            barCodeImage = new BufferedImage(bitMatrix.getWidth(),  bitMatrix.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < height; y++)
                {
                    barCodeImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xff000000 : 0xFFFFFFFF);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return barCodeImage;
    }

    /**
     * 分享注册页面
     * @param interactive
     * @param response
     * @return
     */
    @RequestMapping("/shareRegistPage")
    public String  shareRegistPage(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        String recommendId=interactive.getStringParameter("recommendId","");
        Account recommend=accountService.findForPrimary(recommendId);
        if(Objects.isNull(recommend)){
            return null;
        }
        interactive.setRequestAttribute("recommend",recommend);
        return rootPath+"/shareRegistPage";
    }

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
     * 重新绑定电话号码
     * entityId 账户ID
     * oldPhone 旧手机号码
     * newPhone 新手机号码
     * validateCode 验证码
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/resetBindPhont")
    public void resetBindPhont(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        String oldPhone=interactive.getStringParameter("oldPhone","");
        String newPhone=interactive.getStringParameter("newPhone","");
        String validateCode=interactive.getStringParameter("validateCode","");
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            accountService.resetBindPhone(entityId,oldPhone,newPhone,validateCode);
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Text(jmc.build());
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
        String recommendId=interactive.getStringParameter("recommendId","");
        String device=interactive.getStringParameter("device","");
        //短信验证码
        String validateCode=interactive.getStringParameter("validateCode","");
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            //TODO 要求鑫鸿提供一个默认上级
            if(StringEditor.isEmpty(device)){
                device="ios";
            }
            Account account=accountService.register(phone,password,validateCode,recommendId,device);
            Account recommend=account.getRecommend();
            if(Objects.nonNull(recommend)){
                teamJoinNoteService.saveNote(recommend,account);
            }
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
        //2019-12-25
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
