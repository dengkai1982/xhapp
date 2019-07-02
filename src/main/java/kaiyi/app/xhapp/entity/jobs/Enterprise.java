package kaiyi.app.xhapp.entity.jobs;
import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;
import kaiyi.puer.web.elements.UIColumn;
import kaiyi.puer.web.elements.UIColumnType;
import kaiyi.puer.web.elements.UIEntity;

import javax.persistence.*;

@Entity(name=Enterprise.TABLE_NAME)
@PageEntity(showName = "企业信息",entityName = "enterprise",serviceName = "enterpriseService")
public class Enterprise extends AbstractEntity {
    public static final String TABLE_NAME="enterprise";
    @NotEmpty(hint = "企业名称必须填写")
    @PageField(label = "企业名称")
    private String enterpriseName;
    @PageField(label = "企业logo",type = FieldType.DOCUMENT)
    @FieldDocument
    private String logoImage;
    @PageField(label = "推荐企业",type = FieldType.BOOLEAN)
    @FieldBoolean(values = {"是","否"})
    private boolean recommend;
    @NotEmpty(hint = "电话必须填写")
    @PageField(label = "联系电话")
    private String phone;
    @PageField(label = "三合一编码",tableLength = 160)
    private String code;
    @PageField(label = "营业执照",type = FieldType.DOCUMENT)
    @FieldDocument
    private String licensePhoto;
    @NotEmpty(hint = "联系电话必须填写")
    @PageField(label = "联系电话")
    private String address;
    @PageField(label = "所有人",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "phone")
    private Account owner;
    @PageField(label = "简介",type = FieldType.AREATEXT)
    @FieldArea(row = 4)
    private String content;

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLicensePhoto() {
        return licensePhoto;
    }

    public void setLicensePhoto(String licensePhoto) {
        this.licensePhoto = licensePhoto;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="owner")
    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }
    @Lob
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @Lob
    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }
}
