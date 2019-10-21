package kaiyi.app.xhapp.controller.mgr.enums;

import kaiyi.puer.commons.utils.PinyinUtils;
import kaiyi.puer.h5ui.bean.H5ChosenInterface;

public enum ReportType implements H5ChosenInterface {
    COURSE{
        @Override
        public String toString() {
            return "课程统计";
        }
        @Override
        public String getHexColor() {
            return "#ea644a";
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
