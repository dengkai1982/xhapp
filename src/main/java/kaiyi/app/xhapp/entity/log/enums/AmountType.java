package kaiyi.app.xhapp.entity.log.enums;

import kaiyi.puer.commons.utils.PinyinUtils;
import kaiyi.puer.h5ui.bean.H5ChosenInterface;

public enum AmountType implements H5ChosenInterface {
    GOLD{
        @Override
        public String toString() {
            return "金币";
        }
        @Override
        public String getHexColor() {
            return "#a1a1a2";
        }
    };
    @Override
    public String[] getSearchValues() {
        return new String[]{
                toString(),PinyinUtils.getStringPinYin(toString())
        };
    }

    @Override
    public int getItemNumber() {
        return ordinal();
    }

    @Override
    public String getValue() {
        return toString();
    }

    @Override
    public String getShowName() {
        return name();
    }


    @Override
    public String getDataKeys() {
        return getSearchValues().toString();
    }
}
