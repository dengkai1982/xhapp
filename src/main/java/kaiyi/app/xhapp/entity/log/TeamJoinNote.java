package kaiyi.app.xhapp.entity.log;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.FieldReference;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

import javax.persistence.*;
import java.util.Date;

@Entity(name=TeamJoinNote.TABLE_NAME)
@PageEntity(showName = "团队人员加入记录",entityName = "teamJoinNote",serviceName = "teamJoinNoteService")
public class TeamJoinNote extends AbstractEntity {
    public static final String TABLE_NAME="team_join_note";
    @PageField(label = "上级会员",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "showAccountName")
    private Account parentAccount;
    @PageField(label = "加入会员",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "showAccountName")
    private Account joinAccount;
    @IDate
    @PageField(label = "加入时间",type = FieldType.DATETIME)
    private Date joinTime;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="parentAccount")
    public Account getParentAccount() {
        return parentAccount;
    }

    public void setParentAccount(Account parentAccount) {
        this.parentAccount = parentAccount;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="joinAccount")
    public Account getJoinAccount() {
        return joinAccount;
    }

    public void setJoinAccount(Account joinAccount) {
        this.joinAccount = joinAccount;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }
}
