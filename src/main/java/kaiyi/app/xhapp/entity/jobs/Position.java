package kaiyi.app.xhapp.entity.jobs;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.collection.Cascadeable;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.*;

@Entity(name=Position.TABLE_NAME)
@PageEntity(showName = "职位",entityName = "position",serviceName = "positionService")
public class Position extends AbstractEntity implements Cascadeable,Comparable<Position> {
    public static final String TABLE_NAME="position";
    private static final long serialVersionUID = -3868636085788078314L;

    @NotEmpty(hint = "职位名称必须填写")
    @PageField(label = "职位名称")
    private String name;
    @PageField(label = "首页显示",type=FieldType.BOOLEAN)
    @FieldBoolean(values = {"是","否"})
    private boolean showMainPage;
    @PageField(label = "显示权重",type=FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.FLOAT)
    private float weight;
    @PageField(label = "Logo图标",type = FieldType.DOCUMENT,formColumnLength = 3,showQuery = false,showSearch = false,
            showDetail = false,showTable = false)
    @FieldDocument(maxFileSize = "4mb")
    private String logo;
    @PageField(label = "显示/隐藏",type = FieldType.BOOLEAN,tableLength = 120,showForm = false)
    @FieldBoolean(values = {"显示","隐藏"})
    private boolean showable;
    @PageField(label = "上级类别",type = FieldType.REFERENCE,showForm = false)
    @FieldReference(fieldName = "name")
    private Position parent;
    private Set<Position> children;
    @PageField(label = "层级",type = FieldType.NUMBER,showForm = false,
            showTable = false,showQuery = false,showDetail = false,showSearch = false)
    @FieldNumber(type = FieldNumber.TYPE.INT)
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



    @Column(name="struct_level")
    @Override
    public int getLevel() {
        return this.level;
    }

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name="parent")
    public Position getParent() {
        return parent;
    }

    @Override
    public <T extends Cascadeable> void addParent(T parent) {
        if(parent instanceof Position){
            setParent((Position) parent);
        }
    }

    @Override
    public <T extends Cascadeable> void addChildren(T child) {
        if(child instanceof Position&&this.children!=null){
            this.children.add((Position)child);
        }
    }

    @Transient
    @Override
    public List<? extends Cascadeable> getChildrenList() {
        if(this.children!=null){
            List<Position> list=StreamCollection.setToList(this.children);
            Collections.sort(list);
            return list;
        }
        return new ArrayList<Position>();
    }

    @Override
    public <T extends Cascadeable> T mirror() {
        Position position=new Position();
        position.setName(this.name);
        position.setEntityId(this.getEntityId());
        position.setChildren(new HashSet<>());
        position.setLevel(this.level);
        return (T)position;
    }

    public void setParent(Position parent) {
        this.parent = parent;
    }

    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="parent")
    public Set<Position> getChildren() {
        return children;
    }

    public void setChildren(Set<Position> children) {
        this.children = children;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int compareTo(Position o) {
        return this.getName().compareTo(o.getName());
    }

    public boolean isShowable() {
        return showable;
    }

    public void setShowable(boolean showable) {
        this.showable = showable;
    }

    public boolean isShowMainPage() {
        return showMainPage;
    }

    public void setShowMainPage(boolean showMainPage) {
        this.showMainPage = showMainPage;
    }
    @Lob
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public <T> String convertToJson(T entity, Field field, Object data) {
        if(Objects.nonNull(data)){
            return data.toString();
        }
        return super.convertToJson(entity, field, data);
    }
}