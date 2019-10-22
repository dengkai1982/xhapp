package kaiyi.app.xhapp;

import kaiyi.puer.commons.log.Logger;
import kaiyi.puer.payment.weixin.bean.WeixinUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Collections;

public class WeixinAppPayInfo {
    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String packageStr;
    private String paySign;
    private String partnerid;
    private String prepayId;
    /**
     *
     * @param appId 微信appid
     * @param prepayId 支付token
     * @param signKey 签名用到的key
     */
    public WeixinAppPayInfo(String appId, String partnerid, String prepayId, String signKey, Logger logger) {
        this.appId = appId;
        this.timeStamp = WeixinUtil.getWeixinTimestamp();
        this.nonceStr = WeixinUtil.genNonceStr();
        this.partnerid=partnerid;
        this.packageStr="Sign=WXPay";
        this.prepayId=prepayId;
        /*
        步骤3：统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给APP。
        参与签名的字段名为appid，partnerid，prepayid，noncestr，timestamp，package。
        注意：package的值格式为Sign=WXPay
         */
        //执行签名
        ArrayList<String> params = new ArrayList<String>();
        params.add("appid="+this.appId);
        params.add("partnerid="+this.partnerid);
        params.add("prepayid="+this.prepayId);
        params.add("noncestr="+nonceStr);
        params.add("timestamp="+timeStamp);
        params.add("package="+packageStr);
        Collections.sort(params);
        StringBuilder builder = new StringBuilder();
        for (String param : params) {
            builder.append(param).append("&");
        }
        logger.info(()->"src:"+builder);
        builder.append("key=").append(signKey);
        logger.info(()->"src_and_key:"+builder);
        this.paySign = DigestUtils.md5Hex(builder.toString()).toUpperCase();
        //this.paySign=WeixinUtil.signToString(signKey,params);
        /*this.paySign=doSign(this.appId,this.nonceStr,this.partnerid,this.prepayId,
                this.timeStamp,signKey);*/

    }

   /* private String doSign(String appid,String noncestr,String partnerid,String prepayid,
                         String timestamp, String signKey){
        String signStr="appid="+appid+"&noncestr="+noncestr
                +"&package=Sign=WXPay&partnerid="+partnerid+"&prepayid="+prepayid+"&timestamp="+timestamp
                +"&key="+signKey;
        return DigestUtils.md5Hex(signStr).toUpperCase();
    }
*/
    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getAppId() {
        return appId;
    }
    public String getTimeStamp() {
        return timeStamp;
    }
    public String getNonceStr() {
        return nonceStr;
    }
    public String getPackage() {
        return packageStr;
    }
    public String getPaySign() {
        return paySign;
    }

    public String getPrepayId() {
        return prepayId;
    }
}
