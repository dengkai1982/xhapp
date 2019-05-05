package kaiyi.app.xhapp.entity.log.enums;

import kaiyi.puer.commons.utils.PinyinUtils;
import kaiyi.puer.h5ui.bean.H5ChosenInterface;

public enum BorrowLend implements H5ChosenInterface {
    /**
     * 普通商品
     */
    income{
        @Override
        public String toString() {
            return "收入";
        }
        @Override
        public String getHexColor() {
            return "#3280fc";
        }
    },
    expenditure{
        @Override
        public String toString() {
            return "支出";
        }
        @Override
        public String getHexColor() {
            return "#ea644a";
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
