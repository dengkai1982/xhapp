package kaiyi.app.xhapp.entity.examination.enums;

import kaiyi.puer.commons.utils.PinyinUtils;
import kaiyi.puer.h5ui.bean.H5ChosenInterface;
public enum QuestionType implements H5ChosenInterface {
    SingleChoice{
        @Override
        public String toString() {
            return "单选题";
        }

        @Override
        public String getHexColor() {
            return "#ea644a";
        }
    },
    MultipleChoice{
        @Override
        public String toString() {
            return "多选题";
        }
        @Override
        public String getHexColor() {
            return "#f1a325";
        }
    },
    QuestionsAndAnswers{
        @Override
        public String toString() {
            return "问答题";
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

    public static final  QuestionType getByName(String name){
        QuestionType[] types=QuestionType.values();
        for(QuestionType type:types){
            if(type.getValue().equals(name)){
                return type;
            }
        }
        return null;
    }
}
