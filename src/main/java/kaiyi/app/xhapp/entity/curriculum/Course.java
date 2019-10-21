package kaiyi.app.xhapp.entity.curriculum;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.curriculum.enums.Difficulty;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.Currency;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.*;

@Entity(name=Course.TABLE_NAME)
@Table(indexes = {
        @Index(name="course_index_name",columnList = "name",unique = false),
        @Index(name="course_index_teacher",columnList = "teacher",unique = false),
        @Index(name="course_index_free_course",columnList = "freeCourse",unique = false),
        @Index(name="course_index_free_sale",columnList = "sale",unique = false)
})
@PageEntity(showName = "课程",entityName = "course",serviceName = "courseService")
public class Course extends AbstractEntity {
    private static final long serialVersionUID = -516765397341241977L;
    public static final String TABLE_NAME="course";
    @NotEmpty(hint = "课程名称必须填写")
    @PageField(label = "课程名称",tableLength =300)
    private String name;
    @NotEmpty(hint = "课程讲师必须选择")
    @PageField(label = "课程讲师",type = FieldType.REFERENCE,tableLength =150)
    @FieldReference(fieldName = "name")
    private Teacher teacher;
    @PageField(label = "课程类别",type = FieldType.REFERENCE,showSearch = false,showForm = false,tableLength =120,showQuery = false)
    @FieldReference(fieldName = "name")
    private Category category;
    @NotEmpty(hint = "课程难度必须选择")
    @PageField(label = "课程难度",type = FieldType.CHOSEN,tableLength =120)
    @FieldChosen
    private Difficulty difficulty;
    /*@NotEmpty(hint = "课程售价必须填写")
    @ICurrency
    @PageField(label = "课程售价",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)*/
    private int price;
    @PageField(label = "是否免费",type = FieldType.BOOLEAN,tableLength =120)
    @FieldBoolean(values = {"免费","收费"})
    private boolean freeCourse;
    @PageField(label = "首页推荐",type = FieldType.BOOLEAN,tableLength =120)
    @FieldBoolean(values = {"推荐","不推荐"})
    private boolean recommendMainPage;
    @PageField(label = "外部视频",type = FieldType.BOOLEAN,tableLength =120)
    @FieldBoolean(values = {"是","否"})
    private boolean outsideMovie;
    @PageField(label = "直播视频",type = FieldType.BOOLEAN,tableLength =120)
    @FieldBoolean(values = {"是","否"})
    private boolean liveMovie;
    @PageField(label = "直播地址",tableLength =200)
    private String playUrl;
    @PageField(label = "课程上架/下架",type = FieldType.BOOLEAN,showForm = false,tableLength =160)
    @FieldBoolean(values = {"上架","下架"})
    private boolean sale;
    @NotEmpty(hint = "课程封面必须选择")
    @PageField(label = "课程封面",type = FieldType.DOCUMENT,formColumnLength = 3,showQuery = false,showSearch = false,
    showDetail = false,showTable = false)
    @FieldDocument(maxFileSize = "4mb")
    private String cover;
    @NotEmpty(hint = "课程摘要必须填写")
    @PageField(label = "课程摘要",type=FieldType.AREATEXT,showSearch = false,showTable = false,showQuery =false,formColumnLength = 3)
    @FieldArea
    private String digest;
    @NotEmpty(hint = "课程简介必须填")
    @PageField(label = "课程简介",type=FieldType.AREATEXT,showSearch = false,showTable = false,showQuery =false,formColumnLength = 3)
    @FieldArea(row=5)
    private String detail;
    @PageField(label = "购买量",type = FieldType.NUMBER,showForm = false,tableLength = 140)
    @FieldNumber(type = FieldNumber.TYPE.LONG)
    private long buyVolume;
    @PageField(label = "浏览量",type = FieldType.NUMBER,showForm = false,tableLength = 140)
    @FieldNumber(type = FieldNumber.TYPE.LONG)
    private long browseVolume;
    @PageField(label = "评价得分",type = FieldType.NUMBER,showForm = false,tableLength = 140)
    @FieldNumber(type = FieldNumber.TYPE.DOUBLE)
    private double commentAvgScore;
    @PageField(label = "购买权限",tableLength =600,showForm = false,showQuery = false,showSearch = false)
    private String buyerPrivilege;

    private Set<CourseBuyerPrivilege> privileges;
    @Override
    public StreamArray<String> filterField() {
        return new StreamArray<>(new String[]{"chapters","privileges"});
    }

    private Set<Chapter> chapters;

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
    
    public long getBuyVolume() {
        return buyVolume;
    }

    public void setBuyVolume(long buyVolume) {
        this.buyVolume = buyVolume;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="teacher")
    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public boolean isSale() {
        return sale;
    }

    public void setSale(boolean sale) {
        this.sale = sale;
    }
    @Lob
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "course")
    public Set<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(Set<Chapter> chapters) {
        this.chapters = chapters;
    }
    @Transient
    public StreamCollection<Chapter> getChapterList(){
        List<Chapter> chapterStream=new ArrayList<>();
        if(Objects.nonNull(this.chapters)){
            for(Chapter chapter:this.chapters){
                chapterStream.add(chapter);
            }
        }
        Collections.sort(chapterStream,(c1,c2)->{
            return Integer.valueOf(c1.getWeight()).compareTo(c2.getWeight());
        });
        return new StreamCollection<>(chapterStream);
    }
    @Transient
    public String getBuyerPrivilege() {
        StreamCollection<CourseBuyerPrivilege> stream=getPrivilegeStream();
        return stream.joinString(m->{
            return "【"+m.getMemberShip().getValue()+" "+Currency.noDecimalBuild(m.getPrice(),2).toString()+"】";
        },",");
    }

    public void setBuyerPrivilege(String buyerPrivilege) {
        this.buyerPrivilege = buyerPrivilege;
    }
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "course")
    public Set<CourseBuyerPrivilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<CourseBuyerPrivilege> privileges) {
        this.privileges = privileges;
    }
    @Transient
    public StreamCollection<CourseBuyerPrivilege> getPrivilegeStream(){
        if(Objects.nonNull(privileges)){
            StreamCollection<CourseBuyerPrivilege> stream=new StreamCollection<>(privileges);
            stream=new StreamCollection<>(stream.toList());
            stream.sort((p1,p2)->{
                return Integer.valueOf(p1.getMemberShip().getItemNumber()).compareTo(p2.getMemberShip().getItemNumber());
            });
            return stream;
        }
        return new StreamCollection<>();
    }

    public boolean isRecommendMainPage() {
        return recommendMainPage;
    }

    public void setRecommendMainPage(boolean recommendMainPage) {
        this.recommendMainPage = recommendMainPage;
    }

    /*
        计算课程售价
    */
    @Transient
    public int computerCoursePrice(Account account){
        int price=getPrice();
        StreamCollection<CourseBuyerPrivilege> privileges=getPrivilegeStream();
        if(account.getMemberShip()!=null){
            for(CourseBuyerPrivilege privilege:privileges){
                if(privilege.getMemberShip().getItemNumber()==account.getMemberShip().getItemNumber()){
                    return privilege.getPrice();
                }
            }
        }
        return price;
    }

    public double getCommentAvgScore() {
        return commentAvgScore;
    }

    public void setCommentAvgScore(double commentAvgScore) {
        this.commentAvgScore = commentAvgScore;
    }

    @Override
    public <T> String convertToJson(T entity, Field field, Object data) {
        if(field.getName().equals("cover")&&Objects.nonNull(data)){
            return data.toString();
        }
        return super.convertToJson(entity, field, data);
    }

    public long getBrowseVolume() {
        return browseVolume;
    }

    public void setBrowseVolume(long browseVolume) {
        this.browseVolume = browseVolume;
    }

    public boolean isFreeCourse() {
        return freeCourse;
    }

    public void setFreeCourse(boolean freeCourse) {
        this.freeCourse = freeCourse;
    }

    public boolean isLiveMovie() {
        return liveMovie;
    }

    public void setLiveMovie(boolean liveMovie) {
        this.liveMovie = liveMovie;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public boolean isOutsideMovie() {
        return outsideMovie;
    }

    public void setOutsideMovie(boolean outsideMovie) {
        this.outsideMovie = outsideMovie;
    }
}
