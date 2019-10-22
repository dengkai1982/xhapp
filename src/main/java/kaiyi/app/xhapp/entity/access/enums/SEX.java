package kaiyi.app.xhapp.entity.access.enums;

import kaiyi.puer.commons.utils.PinyinUtils;
import kaiyi.puer.h5ui.bean.H5ChosenInterface;

public enum SEX implements H5ChosenInterface {
    MAN{
        @Override
        public String toString() {
            return "男";
        }

        @Override
        public String getHexColor() {
            return "#ea644a";
        }
    },WOMAN{
        @Override
        public String toString() {
            return "女";
        }

        @Override
        public String getHexColor() {
            return "#f1a325";
        }
    }
    ;
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
