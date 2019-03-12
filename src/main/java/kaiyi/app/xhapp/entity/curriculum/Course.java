package kaiyi.app.xhapp.entity.curriculum;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.enums.MemberShip;
import kaiyi.app.xhapp.entity.curriculum.enums.Difficulty;
import kaiyi.puer.commons.data.ICurrency;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;

@Entity(name=Course.TABLE_NAME)
@PageEntity(showName = "课程",entityName = "course",serviceName = "courseService")
public class Course extends AbstractEntity {
    private static final long serialVersionUID = -516765397341241977L;
    public static final String TABLE_NAME="course";
    @NotEmpty(hint = "课程名称必须填写")
    @PageField(label = "课程名称")
    private String name;
    @PageField(label = "课程讲师",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "name")
    private Student student;
    @PageField(label = "课程类别",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "name")
    private Category category;
    @PageField(label = "课程难度",type = FieldType.CHOSEN)
    @FieldChosen
    private Difficulty difficulty;
    @NotEmpty(hint = "课程摘要必须填写")
    @PageField(label = "课程摘要",type=FieldType.AREATEXT,showSearch = false,showTable = false,showQuery =false,formColumnLength = 3)
    @FieldArea
    private String digest;
    @NotEmpty(hint = "课程简介必须填")
    @PageField(label = "课程简介",type=FieldType.AREATEXT,showSearch = false,showTable = false,showQuery =false,formColumnLength = 3)
    @FieldArea(row=5)
    private String detail;
    @ICurrency
    @PageField(label = "课程售价",type = FieldType.NUMBER)
    private int price;
    @PageField(label = "可购买等级",type = FieldType.CHOSEN)
    @FieldChosen
    private MemberShip purchase;
    @PageField(label = "免费观看",type = FieldType.CHOSEN)
    @FieldChosen
    private MemberShip freeMember;
    @PageField(label = "浏览量",type = FieldType.NUMBER)
    private long browseVolume;
    @PageField(label = "购买量",type = FieldType.NUMBER)
    private long buyVolume;
    //课程上下架
    private boolean sale;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Lob
    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }
    @Lob
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="category")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    @Enumerated(EnumType.STRING)
    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    @Enumerated(EnumType.STRING)
    public MemberShip getPurchase() {
        return purchase;
    }

    public void setPurchase(MemberShip purchase) {
        this.purchase = purchase;
    }
    @Enumerated(EnumType.STRING)
    public MemberShip getFreeMember() {
        return freeMember;
    }

    public void setFreeMember(MemberShip freeMember) {
        this.freeMember = freeMember;
    }

    public long getBrowseVolume() {
        return browseVolume;
    }

    public void setBrowseVolume(long browseVolume) {
        this.browseVolume = browseVolume;
    }

    public long getBuyVolume() {
        return buyVolume;
    }

    public void setBuyVolume(long buyVolume) {
        this.buyVolume = buyVolume;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }
}
