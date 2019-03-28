package kaiyi.app.xhapp.entity.curriculum;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

import javax.persistence.*;
import java.util.Set;

@Entity(name=Chapter.TABLE_NAME)
@PageEntity(showName = "课程章节",entityName = "chapter",serviceName = "chapterService")
public class Chapter extends AbstractEntity {
    public static final String TABLE_NAME="chapter";
    private static final long serialVersionUID = -2931004624535133163L;
    //所属课程
    private Course course;
    @NotEmpty(hint = "章节名称必须填写")
    @PageField(label = "章节名称")
    private String name;
    @PageField(label = "章节介绍")
    private String detail;
    @PageField(label = "显示权重")
    private int weight;

    private Set<CourseMovie> courseMovies;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name="course")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "chapter")
    public Set<CourseMovie> getCourseMovies() {
        return courseMovies;
    }

    public void setCourseMovies(Set<CourseMovie> courseMovies) {
        this.courseMovies = courseMovies;
    }
}
