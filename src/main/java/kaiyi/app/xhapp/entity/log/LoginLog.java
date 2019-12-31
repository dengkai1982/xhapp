package kaiyi.app.xhapp.entity.log;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name=LoginLog.TABLE_NAME)
@Table(indexes = {
        @Index(name="login_log_index_user",columnList = "user",unique = false),
        @Index(name="course_browse_index_time",columnList = "loginTime",unique = false),
        @Index(name="course_browse_index_ipaddr",columnList = "ipaddr",unique = false),
        @Index(name="course_browse_index_error",columnList = "error",unique = false)
})
@PageEntity(showName = "账户登录日志",entityName = "loginLog",serviceName = "loginLogService")
public class LoginLog extends AbstractEntity {
    public static final String TABLE_NAME="login_log";
    @PageField(label = "登录账户",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "loginName")
    private VisitorUser user;
    @IDate
    @PageField(label = "登录时间",type = FieldType.DATETIME)
    private Date loginTime;
    @PageField(label = "登录IP")
    private String ipaddr;
    @PageField(label = "正常登录",type = FieldType.BOOLEAN,tableLength = 120)
    @FieldBoolean(values = {"正常","异常"})
    private boolean error;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="user")
    public VisitorUser getUser() {
        return user;
    }

    public void setUser(VisitorUser user) {
        this.user = user;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
