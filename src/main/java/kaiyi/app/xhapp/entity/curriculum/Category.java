package kaiyi.app.xhapp.entity.curriculum;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;

@Entity(name=Category.TABLE_NAME)
@PageEntity(showName = "课程分类",entityName = "category",serviceName = "categoryService")
public class Category extends AbstractEntity {
    public static final String TABLE_NAME="category";
    private static final long serialVersionUID = -8344965236426535810L;
    @NotEmpty(hint = "类别名称必须填写")
    @PageField(label = "类别名称")
    private String name;
    @NotEmpty(hint = "显示权重必须填写")
    @PageField(label = "显示权重",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.FLOAT)
    private Float weight;
    @PageField(label = "上级类别",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "name")
    private Category parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name="parent")
    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }
}
