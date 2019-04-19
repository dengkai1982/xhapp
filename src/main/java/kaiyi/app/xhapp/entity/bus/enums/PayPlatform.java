package kaiyi.app.xhapp.entity.bus.enums;

import kaiyi.puer.commons.utils.PinyinUtils;
import kaiyi.puer.h5ui.bean.H5ChosenInterface;

public enum PayPlatform implements H5ChosenInterface {
    WEIXIN{
        @Override
        public String toString() {
            return "微信支付";
        }
        @Override
        public String getHexColor() {
            return "#a1a1a2";
        }
    },
    ALIPAY{
        @Override
        public String toString() {
            return "支付宝";
        }
        @Override
        public String getHexColor() {
            return "#f1a325";
        }
    },
    INSIDE{
        @Override
        public String toString() {
            return "站内支付";
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
