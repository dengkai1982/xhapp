package kaiyi.app.xhapp.entity.curriculum.enums;

import kaiyi.puer.commons.utils.PinyinUtils;
import kaiyi.puer.h5ui.bean.H5ChosenInterface;

public enum CommentType implements H5ChosenInterface {
    comment{
        @Override
        public String toString() {
            return "评论";
        }

        @Override
        public String getHexColor() {
            return "#ea644a";
        }
    },
    problem{
        @Override
        public String toString() {
            return "提问";
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
