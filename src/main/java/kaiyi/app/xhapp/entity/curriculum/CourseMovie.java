package kaiyi.app.xhapp.entity.curriculum;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

import javax.persistence.*;

@Entity(name=CourseMovie.TABLE_NAME)
@PageEntity(showName = "课程视频",entityName = "courseMovie",serviceName = "courseMovieService")
public class CourseMovie extends AbstractEntity {
    private static final long serialVersionUID = 1945441403633625378L;
    public static final String TABLE_NAME="course_movie";
    @NotEmpty(hint = "视频名称必须填写")
    @PageField(label = "视频名称")
    private String name;
    @PageField(label = "播放时长")
    private String longTime;
    @PageField(label = "播放地址")
    private String playerAddress;
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

    public String getPlayerAddress() {
        return playerAddress;
    }

    public void setPlayerAddress(String playerAddress) {
        this.playerAddress = playerAddress;
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
