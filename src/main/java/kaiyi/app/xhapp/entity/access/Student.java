package kaiyi.app.xhapp.entity.access;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.h5ui.annotations.FieldDocument;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

import javax.persistence.Entity;

@Entity(name=Student.TABLE_NAME)
@PageEntity(showName = "课程讲师",entityName = "student",serviceName = "studentService")
public class Student extends AbstractEntity {
    public static final String TABLE_NAME="student";
    private static final long serialVersionUID = 3789753547078130618L;
    @PageField(label = "讲师姓名")
    private String name;
    @PageField(label = "讲师照片",type = FieldType.DOCUMENT)
    @FieldDocument
    private String photo;
    @PageField(label = "联系电话")
    private String phone;
    @PageField(label = "职务")
    private String duty;
    @PageField(label = "讲师简介")
    private String detail;
    @PageField(label = "主讲课程")
    private String primaryCourse;

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
