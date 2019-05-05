package kaiyi.app.xhapp.entity.log;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.log.enums.AmountType;
import kaiyi.app.xhapp.entity.log.enums.BorrowLend;
import kaiyi.puer.commons.data.ICurrency;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name=AmountFlow.TABLE_NAME)
@PageEntity(showName = "账户资金流水",entityName = "amountFlow",serviceName = "amountFlowService")
public class AmountFlow extends AbstractEntity {
    public static final String TABLE_NAME="amount_flow";
    private static final long serialVersionUID = -3119766248121324039L;
    @PageField(label = "归属人",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "phone")
    private Account account;
    @PageField(label = "账户类型",type = FieldType.CHOSEN)
    @FieldChosen
    private AmountType amountType;
    @PageField(label = "流水单号",tableLength = 160)
    private String orderId;
    @ICurrency
    @PageField(label = "发生额",type = FieldType.NUMBER,tableLength = 120)
    @FieldNumber(type = FieldNumber.TYPE.LONG)
    private int amount;
    @IDate
    @PageField(label = "发生时间",type = FieldType.DATETIME,tableLength = 160)
    private Date createTime;
    @PageField(label = "收入/支出",type = FieldType.CHOSEN)
    @FieldChosen
    private BorrowLend borrowLend;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="account")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    @Enumerated(EnumType.STRING)
    public AmountType getAmountType() {
        return amountType;
    }

    public void setAmountType(AmountType amountType) {
        this.amountType = amountType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    @Enumerated(EnumType.STRING)
    public BorrowLend getBorrowLend() {
        return borrowLend;
    }

    public void setBorrowLend(BorrowLend borrowLend) {
        this.borrowLend = borrowLend;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public AmountFlow() {
    }

    public AmountFlow(Account account, AmountType amountType, String orderId, int amount,
                      BorrowLend borrowLend) {
        this.account = account;
        this.amountType = amountType;
        this.orderId = orderId;
        this.amount = amount;
        this.borrowLend = borrowLend;
        this.createTime=new Date();
    }
}
