package kaiyi.app.xhapp.entity.access;

import kaiyi.puer.commons.access.AccessControl;
import kaiyi.puer.commons.bean.Identify;
import kaiyi.puer.commons.collection.Cascadeable;
import kaiyi.puer.commons.collection.StreamCollection;

import javax.persistence.*;
import java.util.*;
/**
 * 访问菜单
 */
@Entity(name=VisitorMenu.TABLE_NAME)
public class VisitorMenu implements Comparable<VisitorMenu>,Cascadeable,Identify {
    public static final String TABLE_NAME= "visitor_menu";
    private static final long serialVersionUID = 4833090598064043619L;
    private String entityId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单详情
     */
    private String detail;
    /**
     * 菜单动作
     */
    private AccessControl.Action menuAction;
    /**
     * 动作标识，可以是URL,类名等,与menuUsage公用
     */
    private String actionFlag;
    /**
     * 显示序列
     */
    private Float weight;
    /**
     * 父级别菜单
     */
    private VisitorMenu parent;
    /**
     * 子菜单
     */
    private Set<VisitorMenu> child;
    /**
     * 菜单层级
     */
    private int level;
    /**
     * 是否为当前激活菜单
     */
    private boolean active;
    /**
     * 是否显示
     */
    private boolean showable;

    private String parentId;
    /**
     * 默认具备该权限
     */
    private boolean defaultAuthor;
    @Override
    public int compareTo(VisitorMenu o) {
        return this.weight.compareTo(o.weight);
    }

    @Id
    @Override
    public String getEntityId() {
        return entityId;
    }
    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getActionFlag() {
        return actionFlag;
    }

    public void setActionFlag(String actionFlag) {
        this.actionFlag = actionFlag;
    }

    public Float getWeight() {
        return this.weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    @ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
    @JoinColumn(name="parent")
    public VisitorMenu getParent() {
        return parent;
    }

    @Override
    public <T extends Cascadeable> void addParent(T p) {
        if(p instanceof VisitorMenu){
            setParent((VisitorMenu)p);
        }
    }

    @Override
    public <T extends Cascadeable> void addChildren(T c) {
        if(c instanceof VisitorMenu&&this.child!=null){
            child.add((VisitorMenu)c);
        }
    }

    @Transient
    @Override
    public List<VisitorMenu> getChildrenList() {
        if(this.child!=null){
            List<VisitorMenu> list=StreamCollection.setToList(child);
            Collections.sort(list);
            return list;
        }
        return new ArrayList<VisitorMenu>();
    }

    public VisitorMenu() {

    }

    public VisitorMenu(String name, String detail,AccessControl.Action action, String actionFlag, Float weight, int level) {
        this.name = name;
        this.detail = detail;
        if(actionFlag!=null){
            this.actionFlag = actionFlag;
        }
        this.weight = weight;
        this.level = level;
        this.menuAction=action;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Cascadeable> T mirror() {
        VisitorMenu menu=new VisitorMenu(this.name, this.detail,this.menuAction, this.actionFlag,
                this.weight, this.level);
        menu.child=new HashSet<VisitorMenu>();
        menu.entityId=this.entityId;
        return (T) menu;
    }

    public void setParent(VisitorMenu parent) {
        this.parent = parent;
    }
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="parent")
    public Set<VisitorMenu> getChild() {
        return child;
    }

    public void setChild(Set<VisitorMenu> child) {
        this.child = child;
    }
    @Column(name="o_level")
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    @Transient
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    @Enumerated(EnumType.STRING)
    public AccessControl.Action getMenuAction() {
        return menuAction;
    }

    public void setMenuAction(AccessControl.Action menuAction) {
        this.menuAction = menuAction;
    }

    public boolean isShowable() {
        return showable;
    }

    public void setShowable(boolean showable) {
        this.showable = showable;
    }
    @Transient
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isDefaultAuthor() {
        return defaultAuthor;
    }

    public void setDefaultAuthor(boolean defaultAuthor) {
        this.defaultAuthor = defaultAuthor;
    }
}