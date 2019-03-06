package kaiyi.app.xhapp.entity.curriculum.enums;

import kaiyi.puer.commons.utils.PinyinUtils;
import kaiyi.puer.h5ui.bean.H5ChosenInterface;

/**
 * 难度
 */
public enum Difficulty implements H5ChosenInterface {
    level1{
        @Override
        public String toString() {
            return "入门";
        }

        @Override
        public String getHexColor() {
            return "#ea644a";
        }
    },
    level2{
        @Override
        public String toString() {
            return "初级";
        }
        @Override
        public String getHexColor() {
            return "#f1a325";
        }
    },
    level3{
        @Override
        public String toString() {
            return "中级";
        }
        @Override
        public String getHexColor() {
            return "#3280fc";
        }
    },
    level4{
        @Override
        public String toString() {
            return "高级";
        }
        @Override
        public String getHexColor() {
            return "#38b03f";
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
