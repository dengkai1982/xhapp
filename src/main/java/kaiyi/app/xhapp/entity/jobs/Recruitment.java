package kaiyi.app.xhapp.entity.jobs;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;

@Entity(name=Recruitment.TABLE_NAME)
@PageEntity(showName = "企业招聘",entityName = "recruitment",serviceName = "recruitmentService")
public class Recruitment extends AbstractEntity {
    public static final String TABLE_NAME="recruitment";
    private static final long serialVersionUID = -893953219915415823L;
    @PageField(label = "企业信息",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "name")
    private Enterprise enterprise;
    @NotEmpty(hint = "招聘岗位必须填写")
    @PageField(label = "招聘岗位")
    private String job;
    @PageField(label = "工作类型",type = FieldType.BOOLEAN)
    @FieldBoolean(values = {"全职","兼职"})
    private boolean fullTime;
    @NotEmpty(hint = "工作城市必须填写")
    @PageField(label = "工作城市")
    private String workCity;
    @NotEmpty(hint = "招聘人数必须填写")
    @PageField(label = "招聘人数",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int personNumber;
    @NotEmpty(hint = "薪资待遇必须填写")
    @PageField(label = "薪资待遇")
    private String salary;
    @PageField(label = "工作年限要求",tableLength = 160)
    private String workYear;
    @PageField(label = "其他要求",tableLength = 160)
    private String otherRequire;
    @PageField(label = "发布人",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "phone")
    private Account publisher;
    @PageField(label = "备注",tableLength = 300)
    private String remark;


    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

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
}
