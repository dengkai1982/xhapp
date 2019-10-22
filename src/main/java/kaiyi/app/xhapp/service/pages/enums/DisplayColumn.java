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
    },
    EXAM_INFO{
        @Override
        public String toString() {
            return "考试资讯";
        }

        @Override
        public String getHexColor() {
            return "#ea644a";
        }
    },
    RECRUITMENT{
        @Override
        public String toString() {
            return "人才猎聘";
        }

        @Override
        public String getHexColor() {
            return "#38b03f";
        }
    },
    ENTERPRISE_COLUMN{
        @Override
        public String toString() {
            return "企业招聘栏目";
        }

        @Override
        public String getHexColor() {
            return "#009944";
        }
    },
    QUESTION{
        @Override
        public String toString() {
            return "习题练习";
        }

        @Override
        public String getHexColor() {
            return "#f60";
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
