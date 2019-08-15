package kaiyi.app.xhapp.entity.distribution.enums;

import kaiyi.puer.commons.utils.PinyinUtils;
import kaiyi.puer.h5ui.bean.H5ChosenInterface;

public enum BankType implements H5ChosenInterface {
    /**
     * 普通商品
     */
    bank{
        @Override
        public String toString() {
            return "银行卡";
        }
        @Override
        public String getHexColor() {
            return "#3280fc";
        }
    },
    weixin{
        @Override
        public String toString() {
            return "微信";
        }
        @Override
        public String getHexColor() {
            return "#ea644a";
        }
    },
    alipay{
        @Override
        public String toString() {
            return "支付宝";
        }
        @Override
        public String getHexColor() {
            return "#f1a325";
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
