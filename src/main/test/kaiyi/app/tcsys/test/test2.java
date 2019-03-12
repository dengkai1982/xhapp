package kaiyi.app.tcsys.test;

import kaiyi.app.xhapp.service.SMSSender;
import kaiyi.app.xhapp.service.SMSendRequest;
import kaiyi.puer.http.HttpException;
import org.junit.Test;

public class test2 {
    @Test
    public void sendsms() throws HttpException {
        SMSSender sender=new SMSSender("http://smssh1.253.com/msg/send/json",
                "N3254716","OZzKkrxYuQ1616");
        sender.send("13350672881","【253云通讯】您的验证码为338911请在5分钟内输入。感谢您对鑫鸿教育的支持，祝您生活愉快");
    }
}
