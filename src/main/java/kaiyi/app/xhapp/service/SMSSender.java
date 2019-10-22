package kaiyi.app.xhapp.service;

import com.alibaba.fastjson.JSON;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.http.HttpException;
import kaiyi.puer.http.HttpHeaderNames;
import kaiyi.puer.http.HttpResponse;
import kaiyi.puer.http.connect.HttpConnector;
import kaiyi.puer.http.connect.OKHttpConnection;
import kaiyi.puer.http.parse.JsonParser;
import kaiyi.puer.http.request.HttpPostRequest;

import java.util.Map;

public class SMSSender {
    //String smsSingleRequestServerUrl = "https://XXX/msg/send/json";
    private String account;
    private String password;
    private String url;
    public SMSSender(String url,String account, String password) {
        this.url=url;
        this.account = account;
        this.password = password;
    }

    public boolean send(String phone,String content) throws HttpException {
        HttpPostRequest postRequest=new HttpPostRequest(url);
        postRequest.getHttpHeader().clear();
        postRequest.addHttpHeader(HttpHeaderNames.CONTENT_TYPE,"application/json");
        SMSendRequest reuqest=new SMSendRequest(account,password,content,phone);
        String requestJson = JSON.toJSONString(reuqest);
        postRequest.setContent("application/json","utf-8",requestJson);
        HttpConnector connector=new OKHttpConnection();
        HttpResponse<Map<String, JavaDataTyper>> resp=connector.doRequest(postRequest,new JsonParser());
        Map<String, JavaDataTyper> data=resp.getResponseData().getData();
        return data.get("code").stringValue().equals("0");
    }
}
