package kaiyi.app.xhapp.entity.access;
import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.web.elements.ChosenInterface;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class ChosenInterfaceEntity extends AbstractEntity implements ChosenInterface {
    private static final long serialVersionUID = 8907336103622201329L;
    @Transient
    @Override
    public String[] getSearchValues() {
        return new String[]{
                toString()
        };
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
        return chosenName();
    }

    public abstract String chosenName();
}
