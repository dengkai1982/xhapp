package kaiyi.app.xhapp.entity.distribution;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.puer.commons.data.ICurrency;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name=RoyaltySettlement.TABLE_NAME)
@PageEntity(showName = "提成结算",entityName = "royaltySettlement",serviceName = "royaltySettlementService")
public class RoyaltySettlement extends AbstractEntity {
    public static final String TABLE_NAME="royalty_settlement";
    @NotEmpty(hint = "结算账户必须选择")
    @PageField(label = "结算账户",type = FieldType.REFERENCE,tableLength = 160)
    @FieldReference(fieldName = "showAccountName")
    private Account account;
    @IDate
    @PageField(label = "创建时间",type = FieldType.DATETIME,tableLength = 160,showForm = false)
    private Date createTime;
    @PageField(label = "创建人",type = FieldType.REFERENCE,showForm = false)
    @FieldReference(fieldName = "showVisitorUserName")
    private VisitorUser creater;
    @IDate(pattern = "yyyy-MM-dd")
    @PageField(label = "结算时间",type = FieldType.DATETIME,tableLength = 160,showForm = false)
    private Date settlementTime;
    @PageField(label = "是否发放",type = FieldType.BOOLEAN,showForm = false)
    @FieldBoolean(values = {"已发放","未发放"})
    private boolean grant;
    @PageField(label = "结算单号",showForm = false,tableLength = 160)
    private String orderId;
    @NotEmpty(hint = "结算类型必须选择")
    @PageField(label = "结算类型",type = FieldType.REFERENCE,tableLength = 120)
    @FieldReference(fieldName = "name",pupupSelect=false)
    private RoyaltyType royaltyType;
    @ICurrency
    @PageField(label = "结算金额",type = FieldType.NUMBER,tableLength = 200)
    private int price;
    @PageField(label = "直接上级",type = FieldType.REFERENCE,tableLength = 140)
    @FieldReference(fieldName = "showAccountName")
    private Account recommend1;
    @ICurrency
    @PageField(label = "直接上级提成金额",type = FieldType.NUMBER,tableLength = 200)
    private int level1Amount;
    @PageField(label = "上上级",type = FieldType.REFERENCE,tableLength = 140)
    @FieldReference(fieldName = "showAccountName")
    private Account recommend2;
    @ICurrency
    @PageField(label = "上上级提成金额",type = FieldType.NUMBER,tableLength = 200)
    private int level2Amount;
    @PageField(label = "内部员工",type = FieldType.REFERENCE,tableLength = 140)
    @FieldReference(fieldName = "showAccountName")
    private Account insideMember;
    @ICurrency
    @PageField(label = "内部员工提成金额",type = FieldType.NUMBER,tableLength = 200)
    private int insideAmount;
    @PageField(label = "发放人",type = FieldType.REFERENCE,showForm = false,tableLength = 120)
    @FieldReference(fieldName = "showVisitorUserName")
    private VisitorUser granter;
    @IDate
    @PageField(label = "发放时间",type = FieldType.DATETIME,tableLength = 160,showForm = false)
    private Date grantTime;

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
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="royaltyType")
    public RoyaltyType getRoyaltyType() {
        return royaltyType;
    }

    public void setRoyaltyType(RoyaltyType royaltyType) {
        this.royaltyType = royaltyType;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="recommend1")
    public Account getRecommend1() {
        return recommend1;
    }

    public void setRecommend1(Account recommend1) {
        this.recommend1 = recommend1;
    }

    public int getLevel1Amount() {
        return level1Amount;
    }

    public void setLevel1Amount(int level1Amount) {
        this.level1Amount = level1Amount;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="recommend2")
    public Account getRecommend2() {
        return recommend2;
    }

    public void setRecommend2(Account recommend2) {
        this.recommend2 = recommend2;
    }

    public int getLevel2Amount() {
        return level2Amount;
    }

    public void setLevel2Amount(int level2Amount) {
        this.level2Amount = level2Amount;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="creater")
    public VisitorUser getCreater() {
        return creater;
    }

    public void setCreater(VisitorUser creater) {
        this.creater = creater;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="granter")
    public VisitorUser getGranter() {
        return granter;
    }

    public void setGranter(VisitorUser granter) {
        this.granter = granter;
    }
    @Column(name="isgrant")
    public boolean isGrant() {
        return grant;
    }

    public void setGrant(boolean grant) {
        this.grant = grant;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getGrantTime() {
        return grantTime;
    }

    public void setGrantTime(Date grantTime) {
        this.grantTime = grantTime;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="insideMember")
    public Account getInsideMember() {
        return insideMember;
    }

    public void setInsideMember(Account insideMember) {
        this.insideMember = insideMember;
    }

    public int getInsideAmount() {
        return insideAmount;
    }

    public void setInsideAmount(int insideAmount) {
        this.insideAmount = insideAmount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
