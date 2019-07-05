package kaiyi.app.xhapp.entity.jobs;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name=Recruitment.TABLE_NAME)
@PageEntity(showName = "企业招聘",entityName = "recruitment",serviceName = "recruitmentService")
public class Recruitment extends AbstractEntity {
    public static final String TABLE_NAME="recruitment";
    private static final long serialVersionUID = -893953219915415823L;
    @NotEmpty(hint = "企业名称必须指定")
    @PageField(label = "企业名称",type = FieldType.REFERENCE,tableLength = 180)
    @FieldReference(fieldName = "enterpriseName")
    private Enterprise enterprise;
    @NotEmpty(hint = "招聘岗位必须填写")
    @PageField(label = "招聘岗位",type = FieldType.REFERENCE,tableLength = 220)
    @FieldReference(fieldName = "name")
    private Position position;
    @PageField(label = "推荐岗位",type = FieldType.BOOLEAN)
    @FieldBoolean(values = {"是","否"})
    private boolean recommend;
    @NotEmpty(hint = "工作类型必须填写")
    @PageField(label = "工作类型",type = FieldType.BOOLEAN)
    @FieldBoolean(values = {"全职","兼职"})
    private boolean fullTime;
    @NotEmpty(hint = "工作城市必须填写")
    @PageField(label = "工作城市",tableLength = 280)
    private String workCity;
    @NotEmpty(hint = "招聘人数必须填写")
    @PageField(label = "招聘人数",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int personNumber;
    @NotEmpty(hint = "薪资待遇必须填写")
    @PageField(label = "薪资待遇")
    private String salary;
    @NotEmpty(hint = "工作年限要求必须填写")
    @PageField(label = "工作年限要求",tableLength = 160)
    private String workYear;
    @PageField(label = "其他要求",tableLength = 360)
    private String otherRequire;
    @PageField(label = "发布人",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "phone")
    private Account publisher;
    @IDate
    @PageField(label = "发布时间",type = FieldType.DATETIME,tableLength = 160)
    public Date publishTime;
    @NotEmpty(hint = "有效时间必须填写")
    @IDate(pattern = "yyyy-MM-dd")
    @PageField(label = "有效时间",type = FieldType.DATETIME,tableLength = 160)
    public Date invalidTime;
    @PageField(label = "备注",tableLength = 300)
    private String remark;


    public boolean isFullTime() {
        return fullTime;
    }

    public void setFullTime(boolean fullTime) {
        this.fullTime = fullTime;
    }

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    public int getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(int personNumber) {
        this.personNumber = personNumber;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getWorkYear() {
        return workYear;
    }

    public void setWorkYear(String workYear) {
        this.workYear = workYear;
    }

    public String getOtherRequire() {
        return otherRequire;
    }

    public void setOtherRequire(String otherRequire) {
        this.otherRequire = otherRequire;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="enterprise")
    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="publisher")
    public Account getPublisher() {
        return publisher;
    }

    public void setPublisher(Account publisher) {
        this.publisher = publisher;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="position")
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }
}
