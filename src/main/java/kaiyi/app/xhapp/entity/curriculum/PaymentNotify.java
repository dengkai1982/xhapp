package kaiyi.app.xhapp.entity.curriculum;


import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.curriculum.enums.PayPlatform;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Date;
@Entity(name=PaymentNotify.TABLE_NAME)
@Table(indexes = {
        @Index(name="payment_notify_index_order_id",columnList = "orderId",unique = false),
        @Index(name="payment_notify_index_platform",columnList = "platform",unique = false),
        @Index(name="payment_notify_index_third_part_order_id",columnList = "thirdPartOrderId",unique = false),
        @Index(name="payment_notify_index_finish_time",columnList = "finishTime",unique = false)
})
@PageEntity(showName = "支付通知",entityName = "paymentNotify",serviceName = "paymentNotifyService")
public class PaymentNotify extends AbstractEntity {
    public static final String TABLE_NAME="payment_notify";
    private static final long serialVersionUID = -1854267947425664221L;
    /**
     * 订单号
     */
    @PageField(label = "订单号",tableLength =160)
    private String orderId;
    /**
     * 支付平台
     */
    @PageField(label = "支付方式",type = FieldType.CHOSEN,tableLength = 120)
    @FieldChosen
    private PayPlatform platform;
    /**
     * 是否支付成功
     */
    @PageField(label = "支付结果",type = FieldType.BOOLEAN,tableLength = 100)
    @FieldBoolean(values = {"成功","失败"})
    private boolean success;
    /**
     * 支付结果
     */
    @PageField(label = "三方结果",tableLength = 100)
    private String payResult;
    /**
     * 交易类型
     */
    @PageField(label = "交易类型",tableLength = 100)
    private String tradeType;
    /**
     * 付款银行信息
     */
    @PageField(label = "付款银行",tableLength = 100)
    private String bankType;
    /**
     * 支付金额
     */
    @PageField(label = "付款金额",tableLength = 120)
    private String totalAmount;
    /**
     * 货币种类
     */
    @PageField(label = "货币种类",tableLength = 100)
    private String feeType;
    /**
     * 第三方支付单号
     */
    @PageField(label = "三方单号",tableLength =300)
    private String thirdPartOrderId;
    /**
     * 支付完成时间
     */
    @IDate
    @PageField(label = "完成时间",type = FieldType.DATETIME,tableLength = 160)
    private Date finishTime;
    /**
     * 是否为充值
     */
    @PageField(label = "是否充值",type = FieldType.BOOLEAN,tableLength = 100)
    @FieldBoolean(values = {"是","否"})
    private boolean recharge;

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

    public boolean isRecharge() {
        return recharge;
    }

    public void setRecharge(boolean recharge) {
        this.recharge = recharge;
    }
}
