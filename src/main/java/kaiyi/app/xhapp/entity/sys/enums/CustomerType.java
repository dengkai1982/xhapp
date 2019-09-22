package kaiyi.app.xhapp.entity.sys.enums;

import kaiyi.puer.commons.utils.PinyinUtils;
import kaiyi.puer.h5ui.bean.H5ChosenInterface;

public enum CustomerType implements H5ChosenInterface {
    rc{
        @Override
        public String toString() {
            return "人才客服";
        }

        @Override
        public String getHexColor() {
            return "#ea644a";
        }
    },
    qy{
        @Override
        public String toString() {
            return "企业客服";
        }
        @Override
        public String getHexColor() {
            return "#f1a325";
        }
    },
    zxsp{
        @Override
        public String toString() {
            return "在线视频客服";
        }

        @Override
        public String getHexColor() {
            return "#bd7b46";
        }
    },
    sy{
        @Override
        public String toString() {
            return "在线视频客服";
        }

        @Override
        public String getHexColor() {
            return "#0099ff";
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
