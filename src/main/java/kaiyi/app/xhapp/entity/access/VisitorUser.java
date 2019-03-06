package kaiyi.app.xhapp.entity.access;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.FieldReference;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;
import kaiyi.puer.h5ui.controller.Accesser;

import javax.persistence.*;
import java.util.Date;

@Entity(name=VisitorUser.TABLE_NAME)
@PageEntity(showName = "用户",entityName = "visitorUser",serviceName = "visitorUserService")
public class VisitorUser extends AbstractEntity implements Accesser {
    private static final long serialVersionUID = 1986062763273225706L;
    public static final String TABLE_NAME="visitor_user";
    @PageField(label = "登录名",orderBy = false)
    private String loginName;
    @PageField(label = "姓名",orderBy = false)
    private String realName;
    @PageField(label = "密码",showSearch = false,showTable = false,showForm = false,showQuery = false,showDetail = false)
    private String password;
    @PageField(label = "所属角色",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "name")
    private VisitorRole visitorRole;
    @PageField(label = "最后登录时间",tableLength = 160,showQuery = false,showTable = false, showForm = false)
    @IDate
    private Date lastLoginTime;
    @PageField(label = "登录次数",showQuery = false,showTable = false,showForm = false)
    private long accessNumber;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    @Lob
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public long getAccessNumber() {
        return accessNumber;
    }

    public void setAccessNumber(long accessNumber) {
        this.accessNumber = accessNumber;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="visitorRole")
    public VisitorRole getVisitorRole() {
        return visitorRole;
    }

    public void setVisitorRole(VisitorRole visitorRole) {
        this.visitorRole = visitorRole;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
