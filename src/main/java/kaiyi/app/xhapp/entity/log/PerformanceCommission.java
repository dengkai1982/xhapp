package kaiyi.app.xhapp.entity.log;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.log.enums.TradeCourse;
import kaiyi.puer.commons.data.ICurrency;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name=PerformanceCommission.TABLE_NAME)
@Table(indexes = {
        @Index(name="performance_commission_index_account",columnList = "account",unique = false),
        @Index(name="performance_commission_index_createTime",columnList = "createTime",unique = false),
        @Index(name="performance_commission_index_tradeCourse",columnList = "tradeCourse",unique = false)
})
@PageEntity(showName = "销量提成",entityName = "performanceCommission",serviceName = "performanceCommissionService")
public class PerformanceCommission extends AbstractEntity {
    public static final String TABLE_NAME="performance_commission";
    @PageField(label = "归属人",type = FieldType.REFERENCE,tableLength = 160)
    @FieldReference(fieldName = "showAccountName")
    private Account account;
    @PageField(label = "团队销量",type = FieldType.BOOLEAN,tableLength = 120)
    @FieldBoolean(values = {"是","否"})
    private boolean team;
    @PageField(label = "订单号",tableLength = 160)
    private String orderId;
    @IDate
    @PageField(label = "发生时间",type = FieldType.DATETIME,tableLength = 180)
    private Date createTime;
    @PageField(label = "科目",type = FieldType.CHOSEN,tableLength = 160)
    @FieldChosen
    private TradeCourse tradeCourse;
    @ICurrency
    @PageField(label = "发生额",type = FieldType.NUMBER,tableLength = 140)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int achievement;
    @PageField(label = "提成比例",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int rate;
    @ICurrency
    @PageField(label = "发生额",type = FieldType.NUMBER,tableLength = 140)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int amount;
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
    @Enumerated(EnumType.STRING)
    public TradeCourse getTradeCourse() {
        return tradeCourse;
    }

    public void setTradeCourse(TradeCourse tradeCourse) {
        this.tradeCourse = tradeCourse;
    }

    public int getAchievement() {
        return achievement;
    }

    public void setAchievement(int achievement) {
        this.achievement = achievement;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isTeam() {
        return team;
    }

    public void setTeam(boolean team) {
        this.team = team;
    }

    public PerformanceCommission() {
    }

    public PerformanceCommission(Account account,String orderId, boolean team, Date createTime,
                                 TradeCourse tradeCourse, int achievement, int rate, int amount) {
        this.account = account;
        this.team = team;
        this.createTime = createTime;
        this.tradeCourse = tradeCourse;
        this.achievement = achievement;
        this.rate = rate;
        this.amount = amount;
        this.orderId=orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
