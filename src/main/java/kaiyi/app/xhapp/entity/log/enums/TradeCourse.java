package kaiyi.app.xhapp.entity.log.enums;

import kaiyi.puer.commons.utils.PinyinUtils;
import kaiyi.puer.h5ui.bean.H5ChosenInterface;
/**
 * 资金科目
 */
public enum TradeCourse implements H5ChosenInterface {
    INTEGRAL_WITH_DRAW{
        @Override
        public String toString() {
            return "积分提现";
        }

        @Override
        public String getHexColor() {
            return "#ea644a";
        }
    },
    ALE_PRODUCT{
        @Override
        public String toString() {
            return "购买商品";
        }
        @Override
        public String getHexColor() {
            return "#f1a325";
        }
    },
    SETTLEMENT_ROYALTY {
        @Override
        public String toString() {
            return "消费提成";
        }

        @Override
        public String getHexColor() {
            return "#bd7b46";
        }
    },
    MANUAL_CACULATION{
        @Override
        public String toString() {
            return "人工结算";
        }
        @Override
        public String getHexColor() {
            return "#38b03f";
        }
    },
    REJECT_WITH_DRAW {
        @Override
        public String toString() {
            return "驳回提现";
        }
        @Override
        public String getHexColor() {
            return "#03b8cf";
        }
    },
    GOLD_RECHARGE{
        @Override
        public String toString() {
            return "金币充值";
        }
        @Override
        public String getHexColor() {
            return "#03b8cf";
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
