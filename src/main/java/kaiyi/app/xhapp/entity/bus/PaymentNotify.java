package kaiyi.app.xhapp.entity.bus;


import kaiyi.app.xhapp.entity.bus.enums.PayPlatform;
import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.h5ui.annotations.PageEntity;

import javax.persistence.*;
import java.util.Date;
@Entity(name=PaymentNotify.TABLE_NAME)
@PageEntity(showName = "支付通知",entityName = "paymentNotify",serviceName = "paymentNotifyService")
public class PaymentNotify extends AbstractEntity {
    public static final String TABLE_NAME="payment_notify";
    private static final long serialVersionUID = -1854267947425664221L;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 支付平台
     */
    private PayPlatform platform;
    /**
     * 是否支付成功
     */
    private boolean success;
    /**
     * 支付结果
     */
    private String payResult;
    /**
     * 交易类型
     */
    private String tradeType;
    /**
     * 付款银行信息
     */
    private String bankType;
    /**
     * 支付金额
     */
    private String totalAmount;
    /**
     * 货币种类
     */
    private String feeType;
    /**
     * 第三方支付单号
     */
    private String thirdPartOrderId;
    /**
     * 支付完成时间
     */
    private Date finishTime;

    public PaymentNotify() {
        super();
    }

    /**
     *
     * @param orderId 商户订单号
     * @param platform 支付平台名称
     * @param payResult 支付结果
     * @param tradeType 交易类型
     * @param bankType 付款银行信息
     * @param totalAmount 支付金额
     * @param feeType 货币种类
     * @param thirdPartOrderId 第三方支付单号
     * @param finishTime 完成是否
     * @param success 是否支付成功
     */
    public PaymentNotify(String orderId, PayPlatform platform, String payResult, String tradeType, String bankType,
                             String totalAmount, String feeType, String thirdPartOrderId, Date finishTime,boolean success) {
        this.orderId = orderId;
        this.platform = platform;
        this.payResult = payResult;
        this.tradeType = tradeType;
        this.bankType = bankType;
        this.totalAmount = totalAmount;
        this.feeType = feeType;
        this.thirdPartOrderId = thirdPartOrderId;
        this.finishTime = finishTime;
        this.success=success;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    @Enumerated(EnumType.STRING)
    public PayPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(PayPlatform platform) {
        this.platform = platform;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getPayResult() {
        return payResult;
    }

    public void setPayResult(String payResult) {
        this.payResult = payResult;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getThirdPartOrderId() {
        return thirdPartOrderId;
    }

    public void setThirdPartOrderId(String thirdPartOrderId) {
        this.thirdPartOrderId = thirdPartOrderId;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}
