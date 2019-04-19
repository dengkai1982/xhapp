package kaiyi.app.xhapp.entity.curriculum;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.FieldReference;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

import javax.persistence.*;

@Entity(name=CourseMovie.TABLE_NAME)
@PageEntity(showName = "章节视频",entityName = "courseMovie",serviceName = "courseMovieService")
public class CourseMovie extends AbstractEntity {
    private static final long serialVersionUID = 1945441403633625378L;
    public static final String TABLE_NAME="course_movie";
    @NotEmpty(hint = "视频名称必须填写")
    @PageField(label = "视频名称")
    private String name;
    @PageField(label = "播放时长")
    private String longTime;
    @PageField(label = "媒体库",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "name")
    private MediaLibrary mediaLibrary;
    @PageField(label = "显示权重")
    private int weight;
    private Chapter chapter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongTime() {
        return longTime;
    }

    public void setLongTime(String longTime) {
        this.longTime = longTime;
    }

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="mediaLibrary")
    public MediaLibrary getMediaLibrary() {
        return mediaLibrary;
    }

    public void setMediaLibrary(MediaLibrary mediaLibrary) {
        this.mediaLibrary = mediaLibrary;
    }

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name="chapter")
    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
