package kaiyi.app.xhapp.entity.access.enums;

import kaiyi.puer.commons.utils.PinyinUtils;
import kaiyi.puer.h5ui.bean.H5ChosenInterface;

public enum MemberShip implements H5ChosenInterface {
    normal{
        @Override
        public String toString() {
            return "普通会员";
        }

        @Override
        public String getHexColor() {
            return "#ea644a";
        }

    },
    gold{
        @Override
        public String toString() {
            return "黄金会员";
        }
        @Override
        public String getHexColor() {
            return "#3280fc";
        }
    },
    vip{
        @Override
        public String toString() {
            return "VIP会员";
        }
        @Override
        public String getHexColor() {
            return "#38b03f";
        }
    },
    supreme{
        @Override
        public String toString() {
            return "至尊会员";
        }
        @Override
        public String getHexColor() {
            return "#8666b8";
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

    @Override
    public int getItemNumber() {
        return ordinal();
    }

}
