package kaiyi.app.xhapp.entity.log;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.h5ui.annotations.FieldNumber;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

import javax.persistence.Entity;
import java.beans.Transient;
@Entity(name=MenuTooltip.TABLE_NAME)
@PageEntity(showName = "菜单气泡提示",entityName = "menuTooltip",serviceName = "menuTooltipService")
public class MenuTooltip extends AbstractEntity {
    public static final String TABLE_NAME="menu_tooltip";
    @PageField(label = "菜单ID")
    private String menuId;
    @PageField(label = "试题分值",type = FieldType.NUMBER)
    @FieldNumber(type = FieldNumber.TYPE.INT)
    private int number;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
