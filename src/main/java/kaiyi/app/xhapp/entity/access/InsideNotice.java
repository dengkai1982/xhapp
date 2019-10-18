package kaiyi.app.xhapp.entity.access;

import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.h5ui.annotations.FieldBoolean;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;
import kaiyi.puer.web.entity.TimeStampEntity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity(name=InsideNotice.TABLE_NAME)
@PageEntity(showName = "系统消息",entityName = "insideNotice",serviceName = "insideNoticeService")
public class InsideNotice extends TimeStampEntity {
    public static final String TABLE_NAME="inside_notice";
    private static final long serialVersionUID = 2800439706926475159L;
    @PageField(label = "消息内容")
    private String content;
    @PageField(label = "是否已读",type = FieldType.BOOLEAN)
    @FieldBoolean(values = {"已读","未读"})
    private boolean readed;
    @PageField(label = "访问地址",showForm = false,showTable = false,showSearch = false,showQuery = false,
    showDetail = false)
    private String actionUrl;
    @IDate
    @PageField(label = "消息时间",type = FieldType.DATETIME,tableLength = 160)
    private Date publishDate;

    @Override
    public StreamArray<String> filterField() {
        return new StreamArray<>(new String[]{"receiver"});
    }

    @Lob
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public InsideNotice() {
    }

    public InsideNotice(String content, String actionUrl) {
        this.content = content;
        this.actionUrl = actionUrl;
        this.readed=false;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
