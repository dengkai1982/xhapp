package kaiyi.app.xhapp.entity.access;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.enums.RechargeStatus;
import kaiyi.puer.commons.data.ICurrency;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name=AccountRecharge.TABLE_NAME)
@PageEntity(showName = "账户充值记录",entityName = "accountRecharge",serviceName ="accountRechargeService")
public class AccountRecharge extends AbstractEntity {
    public static final String TABLE_NAME="account_recharge";
    private static final long serialVersionUID = -5145277378665763486L;
    @PageField(label = "充值会员",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "showAccountName")
    private Account recharger;
    @IDate
    @PageField(label = "订单时间",type = FieldType.DATETIME,tableLength = 160)
    private Date orderTime;
    @PageField(label = "订单状态",type = FieldType.CHOSEN)
    @FieldChosen
    private RechargeStatus rechargeStatus;
    @ICurrency
    @PageField(label = "充值金额",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int price;
    @PageField(label = "订单号")
    private String orderId;
    @IDate
    @PageField(label = "付款时间",type = FieldType.DATETIME)
    private Date paymentDate;
    @PageField(label = "支付单号",tableLength = 160)
    private String platformOrderId;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="recharger")
    public Account getRecharger() {
        return recharger;
    }

    public void setRecharger(Account recharger) {
        this.recharger = recharger;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
    @Enumerated(EnumType.STRING)
    public RechargeStatus getRechargeStatus() {
        return rechargeStatus;
    }

    public void setRechargeStatus(RechargeStatus rechargeStatus) {
        this.rechargeStatus = rechargeStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPlatformOrderId() {
        return platformOrderId;
    }

    public void setPlatformOrderId(String platformOrderId) {
        this.platformOrderId = platformOrderId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
