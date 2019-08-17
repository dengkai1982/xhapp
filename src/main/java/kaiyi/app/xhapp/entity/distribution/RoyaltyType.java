package kaiyi.app.xhapp.entity.distribution;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.data.ICurrency;
import kaiyi.puer.commons.validate.Max;
import kaiyi.puer.commons.validate.Min;
import kaiyi.puer.h5ui.annotations.FieldNumber;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;
import kaiyi.puer.web.elements.ChosenInterface;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity(name=RoyaltyType.TABLE_NAME)
@PageEntity(showName = "提成类型",entityName = "royaltyType",serviceName = "royaltyTypeService")
public class RoyaltyType  extends AbstractEntity implements ChosenInterface {
    public static final String TABLE_NAME="royalty_type";
    private static final long serialVersionUID = -2209050705054688651L;
    @PageField(label = "类型名称")
    private String name;
    @ICurrency
    @PageField(label = "结算单价",type = FieldType.NUMBER)
    private int price;
    @Min(hint = "计算比例不能小于1",val = 1)
    @Max(hint = "计算比例不能大于99",val = 99)
    @PageField(label = "直接上级提成比例",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int firstRate;
    @Min(hint = "计算比例不能小于1",val = 1)
    @Max(hint = "计算比例不能大于99",val = 99)
    @PageField(label = "上上级提成比例",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int secondRate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFirstRate() {
        return firstRate;
    }

    public void setFirstRate(int firstRate) {
        this.firstRate = firstRate;
    }

    public int getSecondRate() {
        return secondRate;
    }

    public void setSecondRate(int secondRate) {
        this.secondRate = secondRate;
    }
    @Transient
    @Override
    public String[] getSearchValues() {
        return new String[0];
    }
    @Transient
    @Override
    public int getItemNumber() {
        return 0;
    }
    @Transient
    @Override
    public String getValue() {
        return getEntityId();
    }
    @Transient
    @Override
    public String getShowName() {
        return getName();
    }
}

