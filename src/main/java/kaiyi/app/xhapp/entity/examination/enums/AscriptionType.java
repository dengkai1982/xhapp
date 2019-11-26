package kaiyi.app.xhapp.entity.examination.enums;

import kaiyi.puer.commons.utils.PinyinUtils;
import kaiyi.puer.h5ui.bean.H5ChosenInterface;

/**
 * 归属类型
 */
public enum AscriptionType implements H5ChosenInterface {
    dgkd{
        @Override
        public String toString() {
            return "大纲考点";
        }

        @Override
        public String getHexColor() {
            return "#ea644a";
        }
    },
    lnzt{
        @Override
        public String toString() {
            return "历年真题";
        }
        @Override
        public String getHexColor() {
            return "#f1a325";
        }
    },
    kqyt{
        @Override
        public String toString() {
            return "考前押题";
        }
        @Override
        public String getHexColor() {
            return "#38b03f";
        }
    },
    qt{
        @Override
        public String toString() {
            return "其他";
        }
        @Override
        public String getHexColor() {
            return "#009944";
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

    public static final  AscriptionType getByName(String name){
        AscriptionType[] types=AscriptionType.values();
        for(AscriptionType type:types){
            if(type.getValue().equals(name)){
                return type;
            }
        }
        return null;
    }
}
