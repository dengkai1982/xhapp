package kaiyi.app.xhapp.entity.pages;
import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.commons.time.DateTimeUtil;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;
import kaiyi.puer.web.html.HtmlConvertUtils;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Objects;

@Entity(name=ExamInfo.TABLE_NAME)
@PageEntity(showName = "考试资讯",entityName = "examInfo",serviceName = "examInfoService")
public class ExamInfo extends AbstractEntity implements ContentText {
    private static final long serialVersionUID = -3047395829998804499L;
    public static final String TABLE_NAME="exam_info";
    @NotEmpty(hint = "资讯标题必须填写")
    @PageField(label = "资讯标题",formColumnLength = 3)
    private String title;
    @PageField(label="发布时间",showForm = false,showSearch = false)
    @IDate
    private Date publishDate;
    @PageField(label = "资讯内容",showForm = false,showSearch = false,showTable = false)
    private String content;
    @PageField(label = "阅读量",type = FieldType.NUMBER,showForm = false)
    @FieldNumber(type = FieldNumber.TYPE.LONG)
    private long readVolume;
    @PageField(label = "封面图片",type = FieldType.DOCUMENT,formColumnLength = 3,
            showQuery = false,showSearch = false,showDetail = false,showTable = false)
    @FieldDocument(maxFileSize = "4mb")
    private String cover;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
    @Lob
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @Transient
    @Override
    public boolean getShowPublishTime() {
        return true;
    }
    @Transient
    @Override
    public String getPublishTime() {
        if(Objects.nonNull(publishDate)){
            return DateTimeUtil.yyyyMMddHHmmss.format(publishDate);
        }
        return "";
    }
    @Transient
    @Override
    public String getHtmlContent() {
        if(Objects.nonNull(content)){
            return HtmlConvertUtils.htmlUnescape(content);
        }
        return "";
    }

    public long getReadVolume() {
        return readVolume;
    }

    public void setReadVolume(long readVolume) {
        this.readVolume = readVolume;
    }
    @Lob
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public <T> String convertToJson(T entity, Field field, Object data) {
        if(Objects.nonNull(data)){
            return data.toString();
        }
        return super.convertToJson(entity, field, data);
    }
}
