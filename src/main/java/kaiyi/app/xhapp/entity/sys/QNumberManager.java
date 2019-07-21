package kaiyi.app.xhapp.entity.sys;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

import javax.persistence.Entity;

@Entity(name=QNumberManager.TABLE_NAME)
@PageEntity(showName = "QQ号码",entityName = "qNumberManager",serviceName = "qNumberManagerService")
public class QNumberManager extends AbstractEntity {
    public static final String TABLE_NAME="q_number_namager";
    @PageField(label = "QQ号码")
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
