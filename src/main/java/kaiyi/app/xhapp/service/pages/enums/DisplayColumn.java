package kaiyi.app.xhapp.service.pages.enums;

import kaiyi.puer.commons.utils.PinyinUtils;
import kaiyi.puer.h5ui.bean.H5ChosenInterface;

public enum DisplayColumn implements H5ChosenInterface {
    MAIN_CAROUSEL{
        @Override
        public String toString() {
            return "首页轮播";
        }
        @Override
        public String getHexColor() {
            return "#f1a325";
        }
    },
    LOGIN_PAGE{
        @Override
        public String toString() {
            return "登录页";
        }

        @Override
        public String getHexColor() {
            return "#bd7b46";
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
