package kaiyi.app.xhapp.entity.examination.enums;

import kaiyi.puer.commons.utils.PinyinUtils;
import kaiyi.puer.h5ui.bean.H5ChosenInterface;

/**
 * 考试题来源
 */
public enum ResourceType implements H5ChosenInterface {
    COURSE{
        @Override
        public String toString() {
            return "课程类别";
        }

        @Override
        public String getHexColor() {
            return "#ea644a";
        }
    },
    QUESTION{
        @Override
        public String toString() {
            return "试题类别";
        }
        @Override
        public String getHexColor() {
            return "#f1a325";
        }
    },
    SIMULATION{
        @Override
        public String toString() {
            return "模拟题类别";
        }
        @Override
        public String getHexColor() {
            return "#38b03f";
        }
    },
    TEST_PAGER{
        @Override
        public String toString() {
            return "试卷";
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
                toString(), PinyinUtils.getStringPinYin(toString())
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

    public static final ResourceType getByName(String name){
        ResourceType[] types=ResourceType.values();
        for(ResourceType type:types){
            if(type.getValue().equals(name)){
                return type;
            }
        }
        return null;
    }
}
