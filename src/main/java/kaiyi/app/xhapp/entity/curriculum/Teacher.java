package kaiyi.app.xhapp.entity.curriculum;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity(name=Teacher.TABLE_NAME)
@PageEntity(showName = "课程讲师",entityName = "student",serviceName = "studentService")
public class Teacher extends AbstractEntity {
    public static final String TABLE_NAME="teacher";
    private static final long serialVersionUID = 3789753547078130618L;
    @NotEmpty(hint = "讲师姓名必须输入")
    @PageField(label = "讲师姓名")
    private String name;
    @NotEmpty(hint = "联系电话必须输入")
    @PageField(label = "联系电话")
    private String phone;
    @PageField(label = "职务")
    private String duty;
    @PageField(label = "讲师照片",type = FieldType.DOCUMENT,showSearch = false,showTable = false,showQuery = false,showDetail = false,
    showForm = false)
    @FieldDocument
    private String photo;
    @PageField(label = "主讲课程",type = FieldType.AREATEXT,showSearch = false,showTable = false,showQuery = false,formColumnLength = 5)
    @FieldArea
    private String primaryCourse;
    @PageField(label = "讲师简介",type = FieldType.AREATEXT,showSearch = false,showTable = false,showQuery = false,formColumnLength = 5)
    @FieldArea
    private String detail;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    @Lob
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPrimaryCourse() {
        return primaryCourse;
    }

    public void setPrimaryCourse(String primaryCourse) {
        this.primaryCourse = primaryCourse;
    }
}
