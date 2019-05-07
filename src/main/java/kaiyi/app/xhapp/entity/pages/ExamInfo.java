package kaiyi.app.xhapp.entity.pages;
import kaiyi.app.xhapp.entity.AbstractEntity;
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

@Entity(name=ExamInfo.TABLE_NAME)
@PageEntity(showName = "考试咨询",entityName = "examInfo",serviceName = "examInfoService")
public class ExamInfo extends AbstractEntity implements ContentText {
    private static final long serialVersionUID = -3047395829998804499L;
    public static final String TABLE_NAME="exam_info";
    @PageField(label = "资讯标题")
    private String title;
    @PageField(label="发布时间",showForm = false,showSearch = false)
    @IDate
    private Date publishDate;
    @PageField(label = "资讯内容",showForm = false,showSearch = false,showTable = false)
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
