package kaiyi.app.xhapp.entity.curriculum;
import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.collection.Cascadeable;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;
import kaiyi.puer.web.entity.LogicDeleteEntity;

import javax.persistence.*;
import java.util.*;

@Entity(name=Category.TABLE_NAME)
@PageEntity(showName = "课程分类",entityName = "category",serviceName = "categoryService")
public class Category extends AbstractEntity implements Cascadeable,Comparable<Category> {
    public static final String TABLE_NAME="category";
    private static final long serialVersionUID = -8344965236426535810L;
    @NotEmpty(hint = "类别名称必须填写")
    @PageField(label = "类别名称")
    private String name;
    @PageField(label = "启用/停用",type = FieldType.BOOLEAN)
    @FieldBoolean(values = {"启用","停用"})
    private boolean enable;
    @NotEmpty(hint = "显示权重必须填写")
    @PageField(label = "显示权重",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.FLOAT)
    private Float weight;
    @PageField(label = "上级类别",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "name")
    private Category parent;
    private Set<Category> children;
    private int level;
    @Override
    public StreamArray<String> filterField() {
        return new StreamArray<>(new String[]{"children"});
    }
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

    @Column(name="struct_level")
    @Override
    public int getLevel() {
        return this.level;
    }

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="parent")
    public Category getParent() {
        return parent;
    }

    @Override
    public <T extends Cascadeable> void addParent(T parent) {
        if(parent instanceof Category){
            setParent((Category) parent);
        }
    }

    @Override
    public <T extends Cascadeable> void addChildren(T child) {
        if(child instanceof Category&&this.children!=null){
            this.children.add((Category)child);
        }
    }

    @Transient
    @Override
    public List<? extends Cascadeable> getChildrenList() {
        if(this.children!=null){
            List<Category> list=StreamCollection.setToList(this.children);
            Collections.sort(list);
            return list;
        }
        return new ArrayList<Category>();
    }

    @Override
    public <T extends Cascadeable> T mirror() {
        Category category=new Category();
        category.setName(this.name);
        category.setEntityId(this.getEntityId());
        category.setChildren(new HashSet<>());
        category.setLevel(this.level);
        category.setWeight(this.weight);
        category.setEnable(this.isEnable());
        return (T)category;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    @Override
    public int compareTo(Category o) {
        return 0;
    }
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="parent")
    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
