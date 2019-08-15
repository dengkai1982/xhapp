package kaiyi.app.xhapp.entity.distribution;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.distribution.enums.BankType;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;

@Entity(name=BankInfo.TABLE_NAME)
@PageEntity(showName = "网银信息",entityName = "bankInfo",serviceName = "bankInfoService")
public class BankInfo extends AbstractEntity {
    public static final String TABLE_NAME="bank_info";
    @PageField(label = "网银类型",type = FieldType.CHOSEN)
    @FieldChosen
    private BankType bankType;
    @PageField(label = "会员昵称",type = FieldType.REFERENCE)
    @FieldReference(fieldName="showAccountName")
    private Account account;
    @PageField(label = "账号/卡号")
    private String bankAccount;
    @PageField(label = "开户行")
    private String branchName;
    @PageField(label = "账户姓名")
    private String bankAccountName;
    @Enumerated(EnumType.STRING)
    public BankType getBankType() {
        return bankType;
    }

    public void setBankType(BankType bankType) {
        this.bankType = bankType;
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

    public BankInfo() {
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="account")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }


}
