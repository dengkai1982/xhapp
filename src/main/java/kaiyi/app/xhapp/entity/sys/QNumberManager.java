package kaiyi.app.xhapp.entity.sys;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.app.xhapp.entity.sys.enums.CustomerType;
import kaiyi.puer.commons.validate.NotEmpty;
import kaiyi.puer.h5ui.annotations.FieldChosen;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity(name=QNumberManager.TABLE_NAME)
@PageEntity(showName = "QQ号码",entityName = "qNumberManager",serviceName = "qNumberManagerService")
public class QNumberManager extends AbstractEntity {
    public static final String TABLE_NAME="q_number_namager";
    @NotEmpty(hint = "QQ号码必须填写")
    @PageField(label = "QQ号码")
    private String number;
    @NotEmpty(hint = "客服类型必须选择")
    @PageField(label = "客服类型",type = FieldType.CHOSEN)
    @FieldChosen
    private CustomerType customerType;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    @Enumerated(EnumType.STRING)
    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }
}
