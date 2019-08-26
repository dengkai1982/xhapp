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
    @PageField(label = "会员手机号码",tableLength = 160)
    private String phone;
    @PageField(label = "登录密码",showSearch = false,showTable = false,showDetail = false)
    private String password;
    @IDate
    @PageField(label = "注册时间",type = FieldType.DATETIME,tableLength = 160)
    private Date registerTime;
    @PageField(label = "会员等级",type = FieldType.CHOSEN,tableLength = 100)
    @FieldChosen
    private MemberShip memberShip;

    @PageField(label = "推荐人",type = FieldType.REFERENCE,showForm = false,tableLength = 140)
    @FieldReference(fieldName = "showAccountName")
    private Account recommend;
    @PageField(label = "团队人数",type = FieldType.NUMBER,showForm = false)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int teamNumber;

    @PageField(label = "用户头像",type=FieldType.DOCUMENT,showSearch = false,showDetail = false)
    @FieldDocument
    private String headerImage;
    @PageField(label = "签名",tableLength = 200)
    private String sign;
    @PageField(label = "昵称",tableLength = 200)
    private String nickName;
    @PageField(label = "性别",type = FieldType.CHOSEN)
    @FieldChosen
    private SEX sex;
    @PageField(label = "行业",tableLength = 120)
    private String industry;
    @Email(hint = "电子邮箱格式错误")
    @PageField(label = "电子邮箱",tableLength = 200)
    private String email;
    @PageField(label = "地址",tableLength = 400)
    private String address;
    @ICurrency
    @PageField(label = "账户余额",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int gold;
    @ICurrency
    @PageField(label = "积分",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int integral;
    /**********************累计销量数据(金额)*****************************/
    @ICurrency
    @PageField(label = "个人日消费",type = FieldType.NUMBER,tableLength = 140,showQuery = false)
    @FieldNumber(type = FieldNumber.TYPE.LONG)
    private long personDaySale;
    @ICurrency
    @PageField(label = "个人月消费",type = FieldType.NUMBER,tableLength = 140,showQuery = false)
    @FieldNumber(type = FieldNumber.TYPE.LONG)
    private long personMonthSale;
    @ICurrency
    @PageField(label = "个人总消费",type = FieldType.NUMBER,tableLength = 140,showQuery = false)
    @FieldNumber(type = FieldNumber.TYPE.LONG)
    private long personYearSale;
    @ICurrency
    @PageField(label = "会员总消费",type = FieldType.NUMBER,tableLength = 140,showQuery = false)
    @FieldNumber(type = FieldNumber.TYPE.LONG)
    private long memberYearSale;
    @ICurrency
    @PageField(label = "团队日消费",type = FieldType.NUMBER,tableLength = 140,showQuery = false)
    @FieldNumber(type = FieldNumber.TYPE.LONG)
    private long teamDaySale;
    @ICurrency
    @PageField(label = "团队月消费",type = FieldType.NUMBER,tableLength = 140,showQuery = false)
    @FieldNumber(type = FieldNumber.TYPE.LONG)
    private long teamMonthSale;
    @ICurrency
    @PageField(label = "团队总消费(金额)",type = FieldType.NUMBER,tableLength = 140,showQuery = false)
    @FieldNumber(type = FieldNumber.TYPE.LONG)
    private long teamYearSale;


    private String showAccountName;

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

    public void setShowAccountName(String showAccountName) {

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
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="recommend")
    public Account getRecommend() {
        return recommend;
    }

    public void setRecommend(Account recommend) {
        this.recommend = recommend;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public long getPersonDaySale() {
        return personDaySale;
    }

    public void setPersonDaySale(long personDaySale) {
        this.personDaySale = personDaySale;
    }

    public long getPersonMonthSale() {
        return personMonthSale;
    }

    public void setPersonMonthSale(long personMonthSale) {
        this.personMonthSale = personMonthSale;
    }

    public long getPersonYearSale() {
        return personYearSale;
    }

    public void setPersonYearSale(long personYearSale) {
        this.personYearSale = personYearSale;
    }

    public long getMemberYearSale() {
        return memberYearSale;
    }

    public void setMemberYearSale(long memberYearSale) {
        this.memberYearSale = memberYearSale;
    }

    public long getTeamDaySale() {
        return teamDaySale;
    }

    public void setTeamDaySale(long teamDaySale) {
        this.teamDaySale = teamDaySale;
    }

    public long getTeamMonthSale() {
        return teamMonthSale;
    }

    public void setTeamMonthSale(long teamMonthSale) {
        this.teamMonthSale = teamMonthSale;
    }

    public long getTeamYearSale() {
        return teamYearSale;
    }

    public void setTeamYearSale(long teamYearSale) {
        this.teamYearSale = teamYearSale;
    }
    /**
     * 添加个人金额销量
     * @param amount
     */
    public void addingPersonSaleAmount(long amount){
        this.personDaySale+=amount;
        this.personMonthSale+=amount;
        this.personYearSale+=amount;
    }
    /**
     * 添加团队金额销量
     * @param amount
     */
    public void addingTeamSaleAmount(long amount) {
        this.teamDaySale+=amount;
        this.teamMonthSale+=amount;
        this.teamYearSale+=amount;
    }
}
