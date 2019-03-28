package kaiyi.app.xhapp.entity.curriculum;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity(name=MediaLibrary.TABLE_NAME)
@PageEntity(showName = "媒体库",entityName = "mediaLibrary",serviceName = "mediaLibraryService")
public class MediaLibrary extends AbstractEntity {
    private static final long serialVersionUID = 7755927604436995799L;
    public static final String TABLE_NAME="media_library";
    @PageField(label = "视频名称")
    private String name;
    @PageField(label = "视频ID")
    private String videoId;
    @IDate
    @PageField(label = "创建时间",type = FieldType.DATETIME)
    private Date createTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
