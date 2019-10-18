package kaiyi.app.xhapp.entity.jobs;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.FieldReference;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name=ConcernRecruitment.TABLE_NAME)
@PageEntity(showName = "关注招聘信息",entityName = "concernRecruitment",serviceName = "concernRecruitmentService")
public class ConcernRecruitment extends AbstractEntity {
    private static final long serialVersionUID = 7452489698253445345L;

    public static final String TABLE_NAME="concern_recruitment";

    @PageField(label = "关注人",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "showAccountName")
    private Account account;

    @PageField(label = "联系电话")
    private String accountPhone;
    @PageField(label = "招聘岗位",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "positionName")
    private Recruitment recruitment;
    @PageField(label = "企业名称")
    private String enterpriseName;
    @PageField(label = "企业电话")
    private String enterprisePhone;
    @IDate
    @PageField(label = "关注时间",type = FieldType.DATETIME)
    private Date createTime;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="account")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="recruitment")
    public Recruitment getRecruitment() {
        return recruitment;
    }

    public void setRecruitment(Recruitment recruitment) {
        this.recruitment = recruitment;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @Transient
    public String getEnterpriseName() {
        if(Objects.nonNull(recruitment)){
            return recruitment.getEnterprise().getEnterpriseName();
        }
        return "";
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
    @Transient
    public String getEnterprisePhone() {
        if(Objects.nonNull(recruitment)){
            return recruitment.getEnterprise().getPhone();
        }
        return "";
    }

    public void setEnterprisePhone(String enterprisePhone) {
        this.enterprisePhone = enterprisePhone;
    }
    @Transient
    public String getAccountPhone() {
        if(Objects.nonNull(account)){
            return account.getPhone();
        }
        return "";
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }
}
