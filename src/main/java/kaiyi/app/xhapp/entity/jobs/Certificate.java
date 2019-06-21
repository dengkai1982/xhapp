package kaiyi.app.xhapp.entity.jobs;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.puer.commons.data.ICurrency;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name=Certificate.TABLE_NAME)
@PageEntity(showName = "个人证书",entityName = "certificate",serviceName = "certificateService")
public class Certificate extends AbstractEntity {
    public static final String TABLE_NAME="certificate";
    private static final long serialVersionUID = -7498066066959333338L;
    @NotEmpty(hint = "证书名称必须填写")
    @PageField(label = "证书名称")
    private String name;
    @ICurrency
    @PageField(label = "期望价值",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int expect;
    @IDate(pattern = "yyyy-MM-dd")
    @PageField(label = "到期时间",type = FieldType.DATE)
    private Date expire;
    @PageField(label = "证书图片",type = FieldType.DOCUMENT,tableLength = 3,showTable = false,showQuery = false,
    showSearch = false)
    @FieldDocument
    private String photo;
    //所有者
    @PageField(label = "所有人",type = FieldType.REFERENCE,showForm = false,tableLength = 3,
            showTable = false,showQuery = false,showDetail = false,showSearch = false)
    @FieldReference(fieldName = "phone")
    private Account owner;
    @PageField(label = "期望价值",type = FieldType.AREATEXT,showSearch = false,showQuery = false,
    showTable = false)
    @FieldArea(row = 4)
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Lob
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getExpect() {
        return expect;
    }

    public void setExpect(int expect) {
        this.expect = expect;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="owner")
    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }
    @Lob
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
