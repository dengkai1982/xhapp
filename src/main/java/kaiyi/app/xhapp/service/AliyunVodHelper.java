package kaiyi.app.xhapp.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;
import kaiyi.app.xhapp.entity.pojo.CreateUploadVideoRequestInfo;
import kaiyi.puer.commons.data.StringEditor;

import java.util.List;

/**
 * 阿里云视频
 */
public class AliyunVodHelper {
    /**
     * 初始化服务
     * @param accessKeyId
     * @param accessKeySecret
     * @return
     * @throws ClientException
     */
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    /**
     * 刷新视频上传地址凭证
     * @param client
     * @param videoId
     * @return
     * @throws Exception
     */
    public static RefreshUploadVideoResponse refreshUploadVideo(DefaultAcsClient client,String videoId) throws Exception {
        RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }
    /**
     * 获取视频上传地址凭证
     * @param client
     * @param info
     * @return
     * @throws Exception
     */
    public static CreateUploadVideoResponse createUploadVideo(DefaultAcsClient client,CreateUploadVideoRequestInfo info) throws Exception {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setTitle(info.getTitle());
        request.setFileName(info.getFileName());
        request.setTemplateGroupId(info.getTemplateId());
        if(StringEditor.notEmpty(info.getCallbackURL())&&StringEditor.notEmpty(info.getCallbackType())){
            JSONObject userData = new JSONObject();
            JSONObject messageCallback = new JSONObject();
            messageCallback.put("CallbackURL", info.getCallbackURL());
            messageCallback.put("CallbackType",info.getCallbackType());
            userData.put("MessageCallback", messageCallback.toJSONString());
            request.setUserData(userData.toJSONString());
        }
        return client.getAcsResponse(request);
    }

    public static String getPlayUrl(DefaultAcsClient client,String videoId) throws ClientException {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(videoId);
        GetPlayInfoResponse resp=client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList=resp.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            return playInfo.getPlayURL();
        }
        return null;
    }

    /**
     * 获取视频上传地址和凭证
     * @param client 发送请求客户端
     * @return CreateUploadVideoResponse 获取视频上传地址和凭证响应数据
     * @throws Exception

    public static CreateUploadVideoResponse createUploadVideo(DefaultAcsClient client) throws Exception {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setTitle("this is a sample");
        request.setFileName("filename.mp4");
        request.setTemplateGroupId("ca8c5b2bc07006fee9d29fb738e1fcd4");
        JSONObject userData = new JSONObject();
        JSONObject messageCallback = new JSONObject();
        messageCallback.put("CallbackURL", "http://xxxxx");
        messageCallback.put("CallbackType", "http");
        userData.put("MessageCallback", messageCallback.toJSONString());
        JSONObject extend = new JSONObject();
        extend.put("MyId", "user-defined-id");
        userData.put("Extend", extend.toJSONString());
        request.setUserData(userData.toJSONString());
        return client.getAcsResponse(request);
    }*/
    /**
     * 使用STS初始化服务,获取
     * @param accessKeyId
     * @param accessKeySecret
     * @return
     * @throws ClientException

    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret,String securityToken) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret, securityToken);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }*/

}
