package kaiyi.app.xhapp.entity.jobs;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.collection.Cascadeable;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.*;

@Entity(name=Position.TABLE_NAME)
@PageEntity(showName = "职位",entityName = "position",serviceName = "positionService")
public class Position extends AbstractEntity implements Cascadeable,Comparable<Position> {
    public static final String TABLE_NAME="position";
    private static final long serialVersionUID = -3868636085788078314L;

    @NotEmpty(hint = "职位名称必须填写")
    @PageField(label = "职位名称")
    private String name;
    @PageField(label = "上级类别",type = FieldType.REFERENCE)
    @FieldReference(fieldName = "name")
    private Position parent;
    private Set<Position> children;
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
}