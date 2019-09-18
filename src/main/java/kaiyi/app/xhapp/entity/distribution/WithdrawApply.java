package kaiyi.app.xhapp.entity.distribution;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.app.xhapp.entity.distribution.enums.BankType;
import kaiyi.app.xhapp.entity.distribution.enums.WithdrawStatus;
import kaiyi.puer.commons.data.ICurrency;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name=WithdrawApply.TABLE_NAME)
@Table(indexes = {
        @Index(name="withdraw_apply_index_applyer",columnList = "applyer",unique = false),
        @Index(name="withdraw_apply_index_order_id",columnList = "orderId",unique = false),
        @Index(name="withdraw_apply_index_apply_time",columnList = "applyTime",unique = false),
        @Index(name="withdraw_apply_index_apply_status",columnList = "status",unique = false),
})
@PageEntity(showName = "提现申请记录",entityName = "withdrawApply",serviceName = "withdrawApplyService")
public class WithdrawApply extends AbstractEntity {
    public static final String TABLE_NAME="withdraw_apply";
    @PageField(label = "申请人",type = FieldType.REFERENCE,showForm = false)
    @FieldReference(fieldName = "showAccountName")
    private Account applyer;
    @PageField(label = "提现单号",tableLength = 220)
    private String orderId;
    @IDate
    @PageField(label = "申请时间",type = FieldType.DATETIME,tableLength = 160)
    private Date applyTime;
    @ICurrency
    @PageField(label = "申请金额",type = FieldType.NUMBER)
    @FieldNumber(type=FieldNumber.TYPE.INT)
    private int applyAmount;
    @PageField(label = "网银类型",type = FieldType.CHOSEN)
    @FieldChosen
    private BankType bankType;
    @PageField(label = "申请人电话",tableLength = 140)
    private String contractPhone;
    @PageField(label = "账号/卡号",tableLength = 300)
    private String cardNumber;
    @PageField(label = "开户行")
    private String branchName;
    @PageField(label = "账户姓名")
    private String bankAccountName;
    @PageField(label = "申请状态",type = FieldType.CHOSEN)
    @FieldChosen
    private WithdrawStatus status;
    @PageField(label = "处理人",type = FieldType.REFERENCE,showForm = false)
    @FieldReference(fieldName = "loginName")
    private VisitorUser disposeUser;
    @IDate
    @PageField(label = "处理时间",type = FieldType.DATETIME,tableLength = 160)
    private Date disposeTime;
    @PageField(label = "转账银行",tableLength = 140)
    private String transBank;
    @PageField(label = "转账凭证",tableLength = 200)
    private String voucher;
    @PageField(label = "原因/备注",type=FieldType.AREATEXT,tableLength = 300)
    private String mark;

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="applyer")
    public Account getApplyer() {
        return applyer;
    }

    public void setApplyer(Account applyer) {
        this.applyer = applyer;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(int applyAmount) {
        this.applyAmount = applyAmount;
    }
    @Enumerated(EnumType.STRING)
    public BankType getBankType() {
        return bankType;
    }

    public void setBankType(BankType bankType) {
        this.bankType = bankType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }
    @Enumerated(EnumType.STRING)
    public WithdrawStatus getStatus() {
        return status;
    }

    public void setStatus(WithdrawStatus status) {
        this.status = status;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="disposeUser")
    public VisitorUser getDisposeUser() {
        return disposeUser;
    }

    public void setDisposeUser(VisitorUser disposeUser) {
        this.disposeUser = disposeUser;
    }
    @Lob
    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDisposeTime() {
        return disposeTime;
    }

    public void setDisposeTime(Date disposeTime) {
        this.disposeTime = disposeTime;
    }

    public String getTransBank() {
        return transBank;
    }

    public void setTransBank(String transBank) {
        this.transBank = transBank;
    }

    public String getContractPhone() {
        return contractPhone;
    }

    public void setContractPhone(String contractPhone) {
        this.contractPhone = contractPhone;
    }
}
