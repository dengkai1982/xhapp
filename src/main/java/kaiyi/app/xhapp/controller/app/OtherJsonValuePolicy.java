package kaiyi.app.xhapp.controller.app;

import kaiyi.puer.commons.bean.BeanSyntacticSugar;
import kaiyi.puer.commons.bean.Reflects;
import kaiyi.puer.commons.data.Currency;
import kaiyi.puer.commons.data.EnumDataInterface;
import kaiyi.puer.commons.data.ICurrency;
import kaiyi.puer.commons.data.IDate;
import kaiyi.puer.commons.time.DateTimeUtil;
import kaiyi.puer.json.JsonCreator;
import kaiyi.puer.json.JsonValuePolicy;
import kaiyi.puer.json.creator.JsonBuilder;
import kaiyi.puer.json.creator.MapJsonCreator;
import kaiyi.puer.json.creator.ObjectJsonCreator;
import kaiyi.puer.json.creator.StringJsonCreator;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OtherJsonValuePolicy <T> implements JsonValuePolicy<T> {
    @Override
    public JsonCreator getCreator(T entity, String field, Object value) {
        if(value instanceof Date){
            Date date=(Date)value;
            if(Reflects.fieldHasAnnotation(entity.getClass(),field, IDate.class)){
                IDate iDate=Reflects.getFieldAnnotation(entity.getClass(),field,IDate.class);
                SimpleDateFormat format=new SimpleDateFormat(iDate.pattern());
                return new StringJsonCreator(format.format(date));
            }
            return new StringJsonCreator(DateTimeUtil.yyyyMMddHHmmss.format(date));
        }else if(value instanceof JsonBuilder){
            //处理实现了jsonBuilder接口的类
            JsonBuilder jb=(JsonBuilder)value;
            String[] filterFields= BeanSyntacticSugar.getFieldStringNotStatic(jb.getClass(),jb.filterField().getArray());
            return new ObjectJsonCreator(jb,filterFields,jb.jsonFieldReplacePolicy(),jb.jsonValuePolicy());
        }else if(value instanceof BigDecimal){
            return new StringJsonCreator(value.toString());
        }else if(Reflects.fieldHasAnnotation(entity.getClass(),field, ICurrency.class)){
            //处理Currency类型
            Long currency=Long.valueOf(value.toString());
            ICurrency ic=Reflects.getFieldAnnotation(entity.getClass(),field,ICurrency.class);
            return new StringJsonCreator(Currency.noDecimalBuild(currency,ic.decimals()).toString());
        }
        return null;
    }
}
