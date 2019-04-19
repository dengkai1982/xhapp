package kaiyi.app.xhapp.entity.bus.enums;
import kaiyi.puer.commons.utils.PinyinUtils;
import kaiyi.puer.h5ui.bean.H5ChosenInterface;

/**
 * 课程订单
 */
public enum CourseOrderStatus implements H5ChosenInterface {
    WAIT_PAYMENT{
        @Override
        public String toString() {
            return "等待付款";
        }
        @Override
        public String getHexColor() {
            return "#a1a1a2";
        }
    },
    PAYMENTED{
        @Override
        public String toString() {
            return "完成付款";
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
