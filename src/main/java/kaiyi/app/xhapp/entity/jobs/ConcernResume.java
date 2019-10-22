package kaiyi.app.xhapp.entity.jobs;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.FieldReference;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name=ConcernResume.TABLE_NAME)
@PageEntity(showName = "关注简历",entityName = "concernResume",serviceName = "concernResumeService")
public class ConcernResume extends AbstractEntity {
    public static final String TABLE_NAME="concern_resume";
    private static final long serialVersionUID = 477782534036162556L;
    @PageField(label = "企业名称",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "enterpriseName")
    private Enterprise enterprise;
    @PageField(label = "企业联系电话")
    private String enterprisePhone;
    @PageField(label = "简历人员姓名",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "name")
    private Resume resume;
    @PageField(label = "简历联系电话")
    private String resumePhone;
    @PageField(label = "简历意向岗位")
    private String resumePosition;
    @IDate
    @PageField(label = "关注时间",type = FieldType.DATETIME)
    private Date createTime;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="enterprise")
    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="resume")
    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @Transient
    public String getEnterprisePhone() {
        if(Objects.nonNull(enterprise)){
            return enterprise.getPhone();
        }
        return "";
    }

    public void setEnterprisePhone(String enterprisePhone) {
        this.enterprisePhone = enterprisePhone;
    }
    @Transient
    public String getResumePhone() {
        if(Objects.nonNull(resume)){
            return resume.getPhone();
        }
        return "";
    }

    public void setResumePhone(String resumePhone) {
        this.resumePhone = resumePhone;
    }
    @Transient
    public String getResumePosition() {
        if(Objects.nonNull(resume)){
            return resume.getPosition().getName();
        }
        return "";
    }

    public void setResumePosition(String resumePosition) {
        this.resumePosition = resumePosition;
    }
}
