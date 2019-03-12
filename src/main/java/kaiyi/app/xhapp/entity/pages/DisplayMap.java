package kaiyi.app.xhapp.entity.pages;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.pages.enums.LinkType;
import kaiyi.app.xhapp.service.pages.enums.DisplayColumn;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;
import kaiyi.puer.web.html.HtmlConvertUtils;
import javax.persistence.*;
import java.util.Objects;
@Entity(name=DisplayMap.TABLE_NAME)
@PageEntity(showName = "展示图片",entityName = "displayMap",serviceName = "displayMapService")
public class DisplayMap extends AbstractEntity implements ContentText {
    private static final long serialVersionUID = 2252891380977738148L;
    public static final String TABLE_NAME="display_map";
    @PageField(label = "展示图片",showForm = false)
    private String imagePath;
    @PageField(label = "展示位置",type = FieldType.CHOSEN,placeholder="请选择图片展示位置")
    @FieldChosen
    private DisplayColumn displayColumn;
    @NotEmpty(hint = "跳转方式必须选择")
    @PageField(label = "跳转方式",type = FieldType.CHOSEN,placeholder="请选择跳转方式")
    @FieldChosen
    private LinkType linkType;
    /*
    @PageField(label = "显示顺序",type = FieldType.NUMBER,placeholder = "请输入轮播图片的显示顺序")
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int seq;*/
    @PageField(label = "内容标题",placeholder = "请填写内容标题",showForm = false,showTable = false,
            showQuery = false,showSearch = false,showDetail = false)
    private String title;
    @PageField(label = "显示内容",placeholder = "请填写显示内容",showForm = false,showTable = false,
            showQuery = false,showSearch = false,showDetail = false)
    private String content;
    @PageField(label = "内部页面",showForm = false,showTable = false,showQuery = false,
            showSearch = false,showDetail = false)
    private String page;
    @PageField(label = "外部链接",showForm = false,showTable = false,showQuery = false,
            showSearch = false,showDetail = false)
    private String external;

    @Lob
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    /*public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }*/
    @Enumerated(EnumType.STRING)
    public LinkType getLinkType() {
        return linkType;
    }

    public void setLinkType(LinkType linkType) {
        this.linkType = linkType;
    }
    @Lob
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getExternal() {
        return external;
    }

    public void setExternal(String external) {
        this.external = external;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public DisplayColumn getDisplayColumn() {
        return displayColumn;
    }

    public void setDisplayColumn(DisplayColumn displayColumn) {
        this.displayColumn = displayColumn;
    }

    @Transient
    @Override
    public boolean getShowPublishTime() {
        return false;
    }
    @Transient
    @Override
    public String getPublishTime() {
        return null;
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
