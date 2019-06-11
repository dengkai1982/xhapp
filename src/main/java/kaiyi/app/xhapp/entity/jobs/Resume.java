package kaiyi.app.xhapp.entity.jobs;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.data.ICurrency;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.commons.validate.IDCard;
import kaiyi.puer.commons.validate.Mobile;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name=Resume.TABLE_NAME)
@PageEntity(showName = "个人简历",entityName = "resume",serviceName = "resumeService")
public class Resume extends AbstractEntity {
    public static final String TABLE_NAME="resume";
    private static final long serialVersionUID = 8315666509602535972L;
    @NotEmpty(hint = "姓名必须填写")
    @PageField(label = "姓名")
    private String name;
    @IDate
    @PageField(label = "创建时间",type = FieldType.DATETIME,tableLength = 160)
    private Date createTime;
    @IDate
    @PageField(label = "更新时间",type = FieldType.DATETIME,tableLength = 160)
    private Date updateTime;
    @PageField(label = "专业",tableLength = 120)
    private String profess;
    @NotEmpty(hint = "手机号码必须填写")
    @Mobile(hint = "手机号码格式错误")
    @PageField(label = "手机号码")
    private String phone;
    @NotEmpty(hint = "身份证号必须填写")
    @IDCard(hint = "身份证号码格式错误")
    @PageField(label = "身份证号")
    private String idcard;
    @PageField(label = "身份证正面",type = FieldType.DOCUMENT)
    @FieldDocument
    private String idcardFront;
    @PageField(label = "身份证背面",type = FieldType.DOCUMENT)
    @FieldDocument
    private String idcardBack;
    @PageField(label = "户籍")
    private String household;
    @PageField(label = "工作类型",type = FieldType.BOOLEAN)
    @FieldBoolean(values = {"全职","兼职"})
    private boolean fullTime;
    @PageField(label = "现住址")
    private String houseAddress;
    @PageField(label = "意向城市")
    private String intentCity;
    @PageField(label = "意向岗位",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "name")
    private Position position;
    @ICurrency
    @PageField(label = "薪资",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int salary;

    private Account owner;

    @Override
    public StreamArray<String> filterField() {
        return new StreamArray<>(new String[]{"owner"});
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
    @Lob
    public String getIdcardFront() {
        return idcardFront;
    }

    public void setIdcardFront(String idcardFront) {
        this.idcardFront = idcardFront;
    }
    @Lob
    public String getIdcardBack() {
        return idcardBack;
    }

    public void setIdcardBack(String idcardBack) {
        this.idcardBack = idcardBack;
    }

    public String getHousehold() {
        return household;
    }

    public void setHousehold(String household) {
        this.household = household;
    }

    public boolean isFullTime() {
        return fullTime;
    }

    public void setFullTime(boolean fullTime) {
        this.fullTime = fullTime;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getIntentCity() {
        return intentCity;
    }

    public void setIntentCity(String intentCity) {
        this.intentCity = intentCity;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="position")
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name="owner")
    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getProfess() {
        return profess;
    }

    public void setProfess(String profess) {
        this.profess = profess;
    }
}
