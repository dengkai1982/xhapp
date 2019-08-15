package kaiyi.app.xhapp.entity.distribution;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.FieldReference;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;
import javax.persistence.*;
import java.util.Date;

@Entity(name=WithdrawApply.TABLE_NAME)
@PageEntity(showName = "提成结算",entityName = "royaltySettlement",serviceName = "royaltySettlementService")
public class RoyaltySettlement extends AbstractEntity {

    @PageField(label = "结算账户",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "showAccountName")
    private Account account;
    @IDate
    @PageField(label = "记录时间",type = FieldType.DATETIME,tableLength = 160)
    private Date createTime;
    @IDate(pattern = "yyyy-MM-dd")
    @PageField(label = "结算时间",type = FieldType.DATETIME,tableLength = 160)
    private Date settlementTime;

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="account")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @Temporal(TemporalType.DATE)
    public Date getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(Date settlementTime) {
        this.settlementTime = settlementTime;
    }
}
