package kaiyi.app.xhapp.entity.curriculum;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.FieldReference;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

import javax.persistence.*;
import java.util.Set;

@Entity(name=Chapter.TABLE_NAME)
@PageEntity(showName = "课程章节",entityName = "chapter",serviceName = "chapterService")
public class Chapter extends AbstractEntity {
    public static final String TABLE_NAME="chapter";
    private static final long serialVersionUID = -2931004624535133163L;
    @PageField(label = "所属课程",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "name")
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
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "chapter")
    public Set<CourseMovie> getCourseMovies() {
        return courseMovies;
    }

    public void setCourseMovies(Set<CourseMovie> courseMovies) {
        this.courseMovies = courseMovies;
    }
    @Transient
    public StreamCollection<CourseMovie> getCourseMovieList(){
        if(StreamCollection.assertNotEmpty(this.courseMovies)){
            StreamCollection<CourseMovie> movies=new StreamCollection<>(this.courseMovies);
            movies.sort((o1,o2)->{
                return Integer.valueOf(o1.getWeight()).compareTo(o2.getWeight());
            });
            return movies;
        }
        return new StreamCollection<>();
    }
}
