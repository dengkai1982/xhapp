package kaiyi.app.xhapp.controller.mgr.enums;

import kaiyi.puer.commons.utils.PinyinUtils;
import kaiyi.puer.h5ui.bean.H5ChosenInterface;

/**
 * 统计流水类型
 */
public enum FlowType implements H5ChosenInterface {
    MEMBER_AMOUNT_FLOW{
        @Override
        public String toString() {
            return "账户资金流水";
        }
        @Override
        public String getHexColor() {
            return "#ea644a";
        }
    },
    MEMBER_JOIN_FLOW{
        @Override
        public String toString() {
            return "会员加入记录";
        }
        @Override
        public String getHexColor() {
            return "#a1a1a2";
        }
    },
    SHORT_MESSAGE_SENDER_NOTE{
        @Override
        public String toString() {
            return "短信发送记录";
        }
        @Override
        public String getHexColor() {
            return "#a1a1a2";
        }
    },
    COURSE_BROWSE{
        @Override
        public String toString() {
            return "课程浏览记录";
        }
        @Override
        public String getHexColor() {
            return "#a1a1a2";
        }
    },
    PAYMENT_NOTE{
        @Override
        public String toString() {
            return "支付记录";
        }
        @Override
        public String getHexColor() {
            return "#f1a325";
        }
    },
    ACCOUNT_RECHARGE{
        @Override
        public String toString() {
            return "账户充值记录";
        }
        @Override
        public String getHexColor() {
            return "#f1a325";
        }
    },
    PERFORMANCE_COMMISSION{
        @Override
        public String toString() {
            return "销量提成记录";
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
