package kaiyi.app.xhapp.entity.examination;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.collection.Cascadeable;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.*;

import javax.persistence.*;
import java.util.*;
@Entity(name=QuestionCategory.TABLE_NAME)
@PageEntity(showName = "习题类别",entityName = "questionCategory",serviceName = "questionCategoryService")
public class QuestionCategory extends AbstractEntity implements Cascadeable,Comparable<QuestionCategory>  {
    public static final String TABLE_NAME="question_category";
    private static final long serialVersionUID = -3868636085788078314L;

    @NotEmpty(hint = "分类名称必须填写")
    @PageField(label = "分类名称")
    private String name;
    @PageField(label = "启用/停用",type = FieldType.BOOLEAN)
    @FieldBoolean(values = {"启用","停用"})
    private boolean enable;
    @NotEmpty(hint = "显示权重必须填写")
    @PageField(label = "显示权重",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.FLOAT)
    private Float weight;
    @PageField(label = "上级类别",type = FieldType.REFERENCE,showForm = false)
    @FieldReference(fieldName = "name")
    private QuestionCategory parent;
    private Set<QuestionCategory> children;
    @PageField(label = "层级",type = FieldType.NUMBER,showForm = false,
            showTable = false,showQuery = false,showDetail = false,showSearch = false)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int level;
    @NotEmpty(hint = "单选题数量必须填写")
    @PageField(label = "单选题数量",type = FieldType.NUMBER,tableLength = 110)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int singleNumber;
    @NotEmpty(hint = "多选题数量必须填写")
    @PageField(label = "多选选题数量",type = FieldType.NUMBER,tableLength = 110)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int multipleNumber;
    @NotEmpty(hint = "问答题数量必须填写")
    @PageField(label = "问答题数量",type = FieldType.NUMBER,tableLength = 110)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int answerNumber;

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
    public QuestionCategory getParent() {
        return parent;
    }

    @Override
    public <T extends Cascadeable> void addParent(T parent) {
        if(parent instanceof QuestionCategory){
            setParent((QuestionCategory) parent);
        }
    }

    @Override
    public <T extends Cascadeable> void addChildren(T child) {
        if(child instanceof QuestionCategory&&this.children!=null){
            this.children.add((QuestionCategory)child);
        }
    }

    @Transient
    @Override
    public List<? extends Cascadeable> getChildrenList() {
        if(this.children!=null){
            List<QuestionCategory> list=StreamCollection.setToList(this.children);
            Collections.sort(list);
            return list;
        }
        return new ArrayList<QuestionCategory>();
    }

    @Override
    public <T extends Cascadeable> T mirror() {
        QuestionCategory questionCategory=new QuestionCategory();
        questionCategory.setName(this.name);
        questionCategory.setEntityId(this.getEntityId());
        questionCategory.setChildren(new HashSet<>());
        questionCategory.setLevel(this.level);
        questionCategory.setEnable(this.enable);
        questionCategory.setWeight(this.weight);
        return (T)questionCategory;
    }

    public void setParent(QuestionCategory parent) {
        this.parent = parent;
    }

    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="parent")
    public Set<QuestionCategory> getChildren() {
        return children;
    }

    public void setChildren(Set<QuestionCategory> children) {
        this.children = children;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int compareTo(QuestionCategory o) {
        return this.getName().compareTo(o.getName());
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public int getSingleNumber() {
        return singleNumber;
    }

    public void setSingleNumber(int singleNumber) {
        this.singleNumber = singleNumber;
    }

    public int getMultipleNumber() {
        return multipleNumber;
    }

    public void setMultipleNumber(int multipleNumber) {
        this.multipleNumber = multipleNumber;
    }

    public int getAnswerNumber() {
        return answerNumber;
    }

    public void setAnswerNumber(int answerNumber) {
        this.answerNumber = answerNumber;
    }
}
