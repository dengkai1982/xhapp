package kaiyi.app.xhapp.entity.curriculum;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.FieldBoolean;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

import javax.persistence.Entity;
import javax.persistence.Lob;
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

    @PageField(label = "视频类型",type = FieldType.BOOLEAN)
    @FieldBoolean(values = {"在线视频","本地视频"})
    private boolean online;

    private String videoId;

    @IDate
    @PageField(label = "创建时间",type = FieldType.DATETIME)
    private Date createTime;
    @PageField(label = "访问地址",showQuery = false)
    private String url;
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
    @Lob
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
