package kaiyi.app.xhapp.entity.jobs;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity(name=Recruitment.TABLE_NAME)
@PageEntity(showName = "企业招聘",entityName = "recruitment",serviceName = "recruitmentService")
public class Recruitment extends AbstractEntity {
    public static final String TABLE_NAME="recruitment";
    private static final long serialVersionUID = -893953219915415823L;
    @NotEmpty(hint = "企业名称必须填写")
    @PageField(label = "企业名称")
    private String enterpriseName;
    @NotEmpty(hint = "电话必须填写")
    @PageField(label = "电话")
    private String phone;
    @NotEmpty(hint = "三合一编码必须填写")
    @PageField(label = "三合一编码",tableLength = 160)
    private String code;
    @PageField(label = "营业执照",type = FieldType.DOCUMENT)
    @FieldDocument
    private String licensePhoto;
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
    @PageField(label = "备注",tableLength = 300)
    private String remark;

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
}
