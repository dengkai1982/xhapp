package kaiyi.app.xhapp.entity.pub;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.pages.ContentText;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.commons.time.DateTimeUtil;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;
import kaiyi.puer.web.html.HtmlConvertUtils;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;
import java.util.Date;
import java.util.Objects;

@Entity(name=Notice.TABLE_NAME)
@PageEntity(showName = "站内公告",entityName = "notice",serviceName = "noticeService")
public class Notice extends AbstractEntity implements ContentText {
    private static final long serialVersionUID = -3047395829998804499L;
    public static final String TABLE_NAME="notice";
    @PageField(label = "公告标题")
    private String title;
    @PageField(label="发布时间",showForm = false,showSearch = false)
    @IDate
    private Date publishDate;
    @PageField(label = "公告内容",showForm = false,showSearch = false,showTable = false)
    private String content;

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
}
