package kaiyi.app.xhapp.entity.pages.enums;

import kaiyi.puer.commons.utils.PinyinUtils;
import kaiyi.puer.h5ui.bean.H5ChosenInterface;

public enum LinkType implements H5ChosenInterface {
    external{
        @Override
        public String toString() {
            return "外部链接";
        }

        @Override
        public String getHexColor() {
            return "#ea644a";
        }
    },
    page{
        @Override
        public String toString() {
            return "显示页面";
        }
        @Override
        public String getHexColor() {
            return "#f1a325";
        }
    },
    content {
        @Override
        public String toString() {
            return "显示内容";
        }

        @Override
        public String getHexColor() {
            return "#bd7b46";
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
