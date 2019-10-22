package kaiyi.app.xhapp.entity.jobs;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name=WorkExperience.TABLE_NAME)
@PageEntity(showName = "工作经历",entityName = "workExperience",serviceName = "workExperienceService")
public class WorkExperience extends AbstractEntity {
    private static final long serialVersionUID = -8371057096020646292L;
    public static final String TABLE_NAME="work_experience";
    @NotEmpty(hint = "公司名称必须填写")
    @PageField(label = "公司名称")
    private String company;
    @NotEmpty(hint = "职务必须填写")
    @PageField(label = "职务")
    private String job;
    @IDate(pattern = "yyyy-MM-dd")
    @PageField(label = "开始时间",type = FieldType.DATE,tableLength = 160)
    private Date startTime;
    @IDate(pattern = "yyyy-MM-dd")
    @PageField(label = "结束时间",type = FieldType.DATE,tableLength = 160)
    private Date endTime;
    @PageField(label = "至今",type = FieldType.BOOLEAN)
    @FieldBoolean(values={"是","否"})
    private boolean current;
    //对应的简历
    @PageField(label = "引用简历",type = FieldType.REFERENCE,showForm = false,
            showTable = false,showQuery = false,showDetail = false,showSearch = false)
    @FieldReference(fieldName = "name")
    private Resume resume;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
    @Temporal(TemporalType.DATE)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    @Temporal(TemporalType.DATE)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name="resume")
    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }
}
