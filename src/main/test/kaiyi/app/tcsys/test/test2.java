package kaiyi.app.tcsys.test;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import kaiyi.app.xhapp.service.AliyunVodHelper;
import kaiyi.app.xhapp.service.SMSSender;
import kaiyi.puer.commons.data.Currency;
import kaiyi.puer.commons.utils.CoderUtil;
import kaiyi.puer.crypt.key.KeyGeneratorUtils;
import kaiyi.puer.http.HttpException;
import kaiyi.puer.json.creator.MapJsonCreator;
import kaiyi.puer.json.creator.StringJsonCreator;
import org.junit.Test;

import java.security.KeyPair;
import java.util.List;

public class test2 {
    @Test
    public void computerPercentage(){
        System.out.println(CoderUtil.hexToString("2f7573722f6c6f63616c2f6e67696e782f68746d6c2f646f63732f323031392f31302f32352f65636664326363356231373630346262626531326365336434653836313132372e6a7067","utf-8"));
        System.out.println(CoderUtil.hexToString("2f55736572732f64656e676b61692f446f63756d656e74732f484275696c64657250726f6a656374732f496d616765732f696d6167652f323031392f31302f32372f64373236366431633463633065656239653930613438383364383935373334302e6a7067","utf-8"));
        /*
        2f55736572732f64656e676b61692f446f63756d656e74732f484275696c64657250726f6a656374732f496d616765732f696d6167652f323031392f31302f32372f64373236366431633463633065656239653930613438383364383935373334302e6a7067
        2f55736572732f64656e676b61692f446f63756d656e74732f484275696c64657250726f6a656374732f496d616765732f696d6167652f31323531363739393838333637335f656f665f33343335333433372e6a7067
         */
    }
    @Test
    public void sendsms() throws HttpException {
        SMSSender sender=new SMSSender("http://smssh1.253.com/msg/send/json",
                "N0300104","4tVpf5G7yM42e5");
        sender.send("13350672881","【鑫鸿学校】您的验证码为338911请在5分钟内输入。感谢您对鑫鸿教育的支持，祝您生活愉快");
    }

    @Test
    public void weixinJosn(){
        MapJsonCreator json=new MapJsonCreator();
        json.put("appid",new StringJsonCreator("3242143421"));
        json.put("partnerid",new StringJsonCreator("3242143421"));
        json.put("noncestr",new StringJsonCreator("3242143421"));
        json.put("package",new StringJsonCreator("3242143421"));
        json.put("prepayid",new StringJsonCreator("3242143421"));
        json.put("timestamp",new StringJsonCreator("3242143421"));
        json.put("sign",new StringJsonCreator("3242143421"));
        System.out.println(json.build());
    }
    /**
     * 我的秘钥
     * MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArJNOCEqI1hOHSa2KGgNJT9MzNJWF85TRa1ppO7Ch98MSXYCGV6CU/Lb9wPH3MZ25p8yOzLlbAaEFMXEUZqinJBBW7alUK4+MbWsoRzU9Svx0lgWnywwoDTJFX2gj2wZeAkgHFGSbeL7wRJRVE8A0yCC4tu0QAWaEhOrSi3OLb0Ay7Njw/Afw0eKFWx11S7wXtVyAPGe2K55aELkEFq/WsJVpkR05wvzzs3F5qAoQSga/i52EzaQEPRcucbhBfQYkPdgF6xU4zxAc8cp/CaSLb2FK++c40v51/7Qqnpe56iCQwKAbOUB1BRmgqBuETh2McKGvkPwL9GKGg+VroHRzRQIDAQAB
     * MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCsk04ISojWE4dJrYoaA0lP0zM0lYXzlNFrWmk7sKH3wxJdgIZXoJT8tv3A8fcxnbmnzI7MuVsBoQUxcRRmqKckEFbtqVQrj4xtayhHNT1K/HSWBafLDCgNMkVfaCPbBl4CSAcUZJt4vvBElFUTwDTIILi27RABZoSE6tKLc4tvQDLs2PD8B/DR4oVbHXVLvBe1XIA8Z7YrnloQuQQWr9awlWmRHTnC/POzcXmoChBKBr+LnYTNpAQ9Fy5xuEF9BiQ92AXrFTjPEBzxyn8JpItvYUr75zjS/nX/tCqel7nqIJDAoBs5QHUFGaCoG4ROHYxwoa+Q/Av0YoaD5WugdHNFAgMBAAECggEAGu9+7XJcuzUfF5Pbi0XaCniHdHymBKfErAHXjPZ7wUUUN73YuhKZTnxtTNE3aOoaUSNvYmrH1pBAVEA3E/Lc65cVSlD0jzv/ihCAn+SiZXLT65qEXv/NvImNJGAHwKbUw3xkhn1Ret4qeDnCsat9UYMx8XM83UsYAfr408U8DSfwSodsnmYe1yx0leS0JqNDviJuGLGRbh6avfILgiCKn3TU9stPbhQzRNEc0Ac1az1TngGPLQ5zJ8wckb1tzoKvxFKtqhOrNOXhE9WOz/wWjK/cuHgWuqm8kC0vLG8lNlAtoGQM5Jl5M4SeA7GK0WH+7WNZlGUdQ2c/buQQtxaR3QKBgQD9JlNjiTVyoCkF3bhYrV+rtoz01zlRGbA1n7eaqH6rw8muxv8urQgZbeecS5RfZZrwOLngBs+1cu/dp22c73JOQYqw+98C4DT0h88eKDRrRNheVMadrBBCyXciHCfUbzaqP8EXH4cLBsgoKFEarwoiQcE+ptqkG2CqIOuEkO2jRwKBgQCuhLuulqrRwGulOL0uJ2XY89HQ8R1saSXn3NOgVcojZFZ+QkO6ahTA0aEmIsWw1LxTLp7wt8XDCIn2Xy0yjp3DvZ+lQHc5MobRYK5awq60WyBztvL6IqOjqOr8gZQE4Jz4IhPQzdvOU5B5nhv8yAZElUegsoDd4vSGkONWk5yDEwKBgQCEwNR6HjopIlRg/yTtW/4Pzmuc+kDUhuQhOuDN+g5zhPj+7txjXdohe8E59WiZnm7iCt1u3iOOw/eGBYc2BVusLjLNHUiV+X3LvmyfI/ZZSoEs9sgxZ0ofvAb9nEZG4QuJvGVF+wKWhbfxVzMxCxoFXPY6JMYDyVen4BuiKV51gQKBgQCSMBnPa+J3hcomQtsl+Fa2X7eYYQGWuJXKHg8gNfz3nKtfZD67vvTXeEKXYXVcZTfELj9xwV9lupdmy99ehFzfhM5gbRnxzcNlTKmA2afi6JrMk6oBolaVV4BQOHM3ireym0kiFe9UbGePmMQ1AZFOjgXMNPsB44WV23S0Jz5qBQKBgFUkSIAbCTN8+1cPzdQwzQRLTKAi0u9IT2nEfBSRD2/BZ2NtWdrYt71AVRc+NoWKV1Esnz4sCNI6+F5aPlupRvjGLxpqWAlKAMXEgTFxvPGMFi9zYy8KQQKhQTcs89WCbKCglgxZPleBVv4zmsvpie58hBOEnPhliWHQaZzJtiMC
       支付宝公钥
       MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAz64aDBM7gh/1rDi7xJd1hraad334uUx6pp5sEXrY0DAFaTQP6pgtAmFBYrqYcqi5QVC7M+ftEdm+wxpRGtyV1r+qbH75pkqbTEL6A7B6X9RHwRVOCIlxUpnwsbeouQ4/T2gfOCdXMejraULt+qP0V0YcfCwcL83rTTUnNe20az2M9kw32X05nc123u+s9DbMDtuEdEkykvfpctp4vgVQALBMoM8ME7hgcS2WwxRDsHULI0//vDJmdWbKlVWqakZBSTbW/eAZ7gtevjOwId0o0ZyUSvNSe6hNIdxun6Bh+4q/nH3YjqApbdAJtCLEbGNJr6F/a9ETBTD1r5vYRhA+UQIDAQAB
       支付宝AES秘钥
       e+Fz6+Bo/5Mbbnze6fbq6g==
     */
    @Test
    public void key(){
        KeyPair keyPair=KeyGeneratorUtils.generatorRSAKey(2048);
        System.out.println(CoderUtil.base64Encode(keyPair.getPublic().getEncoded()));
        System.out.println(CoderUtil.base64Encode(keyPair.getPrivate().getEncoded()));
    }

    @Test
    public void alivod() throws Exception {
        DefaultAcsClient client=AliyunVodHelper.initVodClient("LTAIrqzREtcK5vSq","E6UdEzJxsyZ0E28iqdyIazR4JNLlTr");
        CreateUploadVideoResponse resp=AliyunVodHelper.createUploadVideo(client,null);
        System.out.println("VideoId = " + resp.getVideoId());
        System.out.println("UploadAddress = " + resp.getUploadAddress());
        System.out.println("UploadAuth = " + resp.getUploadAuth());
    }

    @Test
    public void b64Print(){
        String addr="eyJFbmRwb2ludCI6Imh0dHBzOi8vb3NzLWNuLXNoYW5naGFpLmFsaXl1bmNzLmNvbSIsIkJ1Y2tldCI6Im91dGluLTBkZGZiODg4MzY4MDExZTk4MTQ3MDAxNjNlMWE2NWI2IiwiRmlsZU5hbWUiOiJzdi8zMzQyMzFjMS0xNjk4ZTk0YjQ4MC8zMzQyMzFjMS0xNjk4ZTk0YjQ4MC5tcDQifQ==";
        String uploadAuth="eyJTZWN1cml0eVRva2VuIjoiQ0FJUzBBUjFxNkZ0NUIyeWZTaklyNG5HZnQvOGpKNFo1Ykd2VWxiY2dHSUVSOXBMaTcyY3NUejJJSGxQZTNGaEFPb2V2L2svbVc5VTdmb2NsclVxRWNBYkd4Q1pOcFlnc2MwSXFGci9KcGZadjh1ODRZQURpNUNqUVpCdTk0QUxtWjI4V2Y3d2FmK0FVQlhHQ1RtZDVNTVlvOWJUY1RHbFFDWnVXLy90b0pWN2I5TVJjeENsWkQ1ZGZybC9MUmRqcjhsbzF4R3pVUEcyS1V6U24zYjNCa2hsc1JZZTcyUms4dmFIeGRhQXpSRGNnVmJtcUpjU3ZKK2pDNEM4WXM5Z0c1MTlYdHlwdm9weGJiR1Q4Q05aNXo5QTlxcDlrTTQ5L2l6YzdQNlFIMzViNFJpTkw4L1o3dFFOWHdoaWZmb2JIYTlZcmZIZ21OaGx2dkRTajQzdDF5dFZPZVpjWDBha1E1dTdrdTdaSFArb0x0OGphWXZqUDNQRTNyTHBNWUx1NFQ0OFpYVVNPRHREWWNaRFVIaHJFazRSVWpYZEk2T2Y4VXJXU1FDN1dzcjIxN290ZzdGeXlrM3M4TWFIQWtXTFg3U0IyRHdFQjRjNGFFb2tWVzRSeG5lelc2VUJhUkJwYmxkN0JxNmNWNWxPZEJSWm9LK0t6UXJKVFg5RXoycExtdUQ2ZS9MT3M3b0RWSjM3V1p0S3l1aDRZNDlkNFU4clZFalBRcWl5a1QwcEZncGZUSzFSemJQbU5MS205YmFCMjUvelcrUGREZTBkc1Znb0lGS09waUdXRzNSTE5uK3p0Sjl4YmtlRStzS1VsS0RBL1prd1FGdDF1SUFIVkZpSUlJWTA4d00rdS9Mc3RCbksrYkcrV0M3dDVYUi91UHVncHRjZnVCbzhJNjM3MmJUSzVtQ0E1MGI5Ty9kcHhKM2xQMFIwV2dteWRuQkR4L1NmdTJrS3ZSaHBrUnZ2WWsxQXN3WElqejdoSVoxR2phRFFtaTFlZm81WG1QWEZUUW1uOGw1cEFNbXkvNjB4WHVkdmJIL3U3RVVQSytrQ0dvQUJmamtPTE5iNzFXZEloTVpWVENqODB0YlJiWTV5WjNndUYxbXk1YTRNMlAwNHlUTDBLalQ1a0xiZU9ZaEJHazVwdXliOC9aVHI0d1BMTE1UTXU3bm1PcW93Y0FsVVBQQjdOdENlUkhjaDNKNkdpUmIzNXQxN3RyTTE1M2pnRjFESmpkUjQ1bVdyTE1nSXFOMHFHa0poeWd5bHFvd1Z2bGxoQjM3UFBTWWdJMm89IiwiQWNjZXNzS2V5SWQiOiJTVFMuTkpzNWVIYUE4UnNEUHBtZGJRS1VnZHg3UyIsIkV4cGlyZVVUQ1RpbWUiOiIyMDE5LTAzLTE4VDAzOjA3OjUxWiIsIkFjY2Vzc0tleVNlY3JldCI6IkZOVkhxenR5ejNud0hnVGRRVzE2Q1hZVnRUUVBzd3ViVmR2M214Ukxrc3NyIiwiRXhwaXJhdGlvbiI6IjMxNjgiLCJSZWdpb24iOiJjbi1zaGFuZ2hhaSJ9";
        System.out.println(CoderUtil.base64Decode(addr));
        System.out.println(CoderUtil.base64Decode(uploadAuth));
    }

    @Test
    public void uploadTest(){
        String accessKeyId = "LTAIrqzREtcK5vSq";
        //账号AK信息请填写(必选)
        String accessKeySecret = "E6UdEzJxsyZ0E28iqdyIazR4JNLlTr";
        String title = "测试标题";
        String fileName = "鹿角巷招商加盟.mp3";
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为1M字节 */
        request.setPartSize(1 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        /* 是否开启断点续传, 默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
        注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启*/
        request.setEnableCheckpoint(false);
        request.setTemplateGroupId("7674fa64796f46a70c04d55eb651c375");
        //request.setCoverURL("http://cms-bucket.ws.126.net/2019/03/18/ede5ffc2657542b490daa95d8ab94c93.jpeg?imageView&thumbnail=200y125");
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }
    /*
    https://outin-0ddfb888368011e9814700163e1a65b6.oss-cn-shanghai.aliyuncs.com/sv/1c3a65ea-1698ec31f40/1c3a65ea-1698ec31f40.mp3?Expires=1552882124&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=A4hsSZYKeiVXBdqvBJgnw0HaZ40%3D
    RequestId=670916CF-A173-4ED9-BF4E-2565BE26CB35
    VideoId=9e4289e9bf164580b26ee089726f8665
     */


    @Test
    public void getPrintUrl() throws Exception {
        DefaultAcsClient client=AliyunVodHelper.initVodClient("LTAIMJDHNcxsOq7b","NYFS1UAUGqqLtDFqQjpCNflqmnnmg4");
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            response = getPlayInfo(client);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
    /*获取播放地址函数*/
    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("ff871f4570c04dacbbd6719b0d66bccd");
        return client.getAcsResponse(request);
    }
}
