package kaiyi.app.xhapp.entity.jobs;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.puer.commons.data.ICurrency;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;

@Entity(name=Certificate.TABLE_NAME)
@PageEntity(showName = "个人证书",entityName = "certificate",serviceName = "certificateService")
public class Certificate extends AbstractEntity {
    public static final String TABLE_NAME="certificate";
    private static final long serialVersionUID = -7498066066959333338L;
    @NotEmpty(hint = "证书名称必须填写")
    @PageField(label = "证书名称")
    private String name;
    @PageField(label = "证书图片",type = FieldType.DOCUMENT)
    @FieldDocument
    private String photo;
    @ICurrency
    @PageField(label = "期望价值",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int expect;
    //所有者
    private Account owner;

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
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name="owner")
    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }
}
