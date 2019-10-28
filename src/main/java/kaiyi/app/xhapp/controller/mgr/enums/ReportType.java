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
    },
    JOB{
        @Override
        public String toString() {
            return "人才信息一览";
        }
        @Override
        public String getHexColor() {
            return "#009944";
        }
    },
    RESUME_AND_RECRUITMENT_CATEGORY{
        @Override
        public String toString() {
            return "简历招聘";
        }
        @Override
        public String getHexColor() {
            return "#009944";
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
