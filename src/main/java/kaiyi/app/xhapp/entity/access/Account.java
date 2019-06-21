package kaiyi.app.xhapp.entity.access;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.enums.MemberShip;
import kaiyi.app.xhapp.entity.access.enums.SEX;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.data.ICurrency;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.commons.validate.Email;
import kaiyi.puer.commons.validate.Mobile;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Objects;

@Entity(name=Account.TABLE_NAME)
@PageEntity(showName = "用户账户",entityName = "account",serviceName = "accountService")
public class Account extends AbstractEntity {
    private static final long serialVersionUID = -555171879389398184L;
    public static final String TABLE_NAME="account";
    @Mobile(hint = "手机号码格式错误")
    @PageField(label = "手机号码")
    private String phone;
    @PageField(label = "登录密码",showSearch = false,showTable = false,showDetail = false)
    private String password;
    @IDate
    @PageField(label = "注册时间",type = FieldType.DATETIME)
    private Date registerTime;
    @PageField(label = "用户头像",type=FieldType.DOCUMENT,showSearch = false)
    @FieldDocument
    private String headerImage;
    @PageField(label = "签名")
    private String sign;
    @PageField(label = "昵称")
    private String nickName;
    @PageField(label = "性别",type = FieldType.CHOSEN)
    @FieldChosen
    private SEX sex;
    @PageField(label = "行业")
    private String industry;
    @Email(hint = "电子邮箱格式错误")
    @PageField(label = "电子邮箱")
    private String email;
    @PageField(label = "地址")
    private String address;
    @ICurrency
    @PageField(label = "金币",type = FieldType.NUMBER)
    private int gold;
    @PageField(label = "会员等级",type = FieldType.CHOSEN)
    @FieldChosen
    private MemberShip memberShip;

    @Override
    public StreamArray<String> filterField() {
        return new StreamArray<>(new String[]{"password"});
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Lob
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    @Enumerated(EnumType.STRING)
    public SEX getSex() {
        return sex;
    }

    public void setSex(SEX sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
    @Enumerated(EnumType.STRING)
    public MemberShip getMemberShip() {
        return memberShip;
    }

    public void setMemberShip(MemberShip memberShip) {
        this.memberShip = memberShip;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }
    @Transient
    public String getShowAccountName(){
        if(Objects.isNull(nickName)){
            return phone;
        }
        return nickName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public <T> String convertToJson(T entity, Field field, Object data) {
        if(field.getName().equals("headerImage")){
            return data.toString();
        }
        return "";
    }
}
