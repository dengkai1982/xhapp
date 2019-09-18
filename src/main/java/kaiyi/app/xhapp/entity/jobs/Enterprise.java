package kaiyi.app.xhapp.entity.jobs;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.Objects;

@Entity(name=Enterprise.TABLE_NAME)
@PageEntity(showName = "企业信息",entityName = "enterprise",serviceName = "enterpriseService")
public class Enterprise extends AbstractEntity {
    public static final String TABLE_NAME="enterprise";
    @NotEmpty(hint = "企业名称必须填写")
    @PageField(label = "企业名称",tableLength = 260)
    private String enterpriseName;
    @PageField(label = "企业Logo",type = FieldType.DOCUMENT,showSearch = false,showForm = false,showDetail = false)
    @FieldDocument
    private String logoImage;
    @PageField(label = "推荐企业",type = FieldType.BOOLEAN)
    @FieldBoolean(values = {"是","否"})
    private boolean recommend;
    @PageField(label = "认证情况",type = FieldType.BOOLEAN)
    @FieldBoolean(values = {"已认证","未认证"})
    private boolean verifyed;
    @NotEmpty(hint = "电话必须填写")
    @PageField(label = "联系电话",tableLength = 160)
    private String phone;
    @PageField(label = "三合一编码",tableLength = 160)
    private String code;
    @PageField(label = "营业执照",type = FieldType.DOCUMENT,showSearch = false,showForm = false,showDetail = false)
    @FieldDocument
    private String licensePhoto;
    @NotEmpty(hint = "企业详细地址必须填写")
    @PageField(label = "详细地址")
    private String address;
    @PageField(label = "所属账户",type = FieldType.REFERENCE,tableLength = 160)
    @FieldReference(fieldName = "showAccountName")
    private Account owner;
    @PageField(label = "归属内部员工",type = FieldType.REFERENCE,showForm = false,showTable = false,
            showQuery = false,showSearch = false,tableLength = 140)
    @FieldReference(fieldName = "showAccountName")
    private Account parentInsideAccount;
    @PageField(label = "简介",type = FieldType.AREATEXT,tableLength = 600,formColumnLength = 3)
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
    @Lob
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

    public boolean isVerifyed() {
        return verifyed;
    }

    public void setVerifyed(boolean verifyed) {
        this.verifyed = verifyed;
    }

    @Override
    public <Enterprise> String convertToJson(Enterprise entity, Field field, Object data) {
        if(Objects.nonNull(data)){
            return data.toString();
        }else{
            return "";
        }
    }
    @Transient
    public Account getParentInsideAccount() {
        return parentInsideAccount;
    }

    public void setParentInsideAccount(Account parentInsideAccount) {
        this.parentInsideAccount = parentInsideAccount;
    }
}
