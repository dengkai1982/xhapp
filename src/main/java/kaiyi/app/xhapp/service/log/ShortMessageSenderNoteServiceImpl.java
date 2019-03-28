package kaiyi.app.xhapp.service.log;
import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.log.ShortMessageSenderNote;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.SMSSender;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.pub.ConfigureService;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.time.DateTimeUtil;
import kaiyi.puer.commons.utils.RandomUtils;
import kaiyi.puer.commons.validate.VariableVerifyUtils;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.CompareQueryExpress.Compare;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress.LINK;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.http.HttpException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service("shortMessageSenderNoteService")
public class ShortMessageSenderNoteServiceImpl extends InjectDao<ShortMessageSenderNote> implements ShortMessageSenderNoteService {
    private static final long serialVersionUID = 2979335295793277041L;
    @Resource
    private ConfigureService configureService;
    @Resource
    private AccountService accountService;
    @Override
    public String sendValidateCode(String phone, String usage) throws ServiceException {
        clearCodeStatus();
        if(!VariableVerifyUtils.mobileValidate(phone)){
            throw ServiceExceptionDefine.phoneFormatError;
        }
        /*if(!accountService.exist(new CompareQueryExpress("phone",Compare.EQUAL,phone))){
            throw ServiceExceptionDefine.userNotExist;
        }*/
        QueryExpress query=new CompareQueryExpress("phone",Compare.EQUAL,phone);
        query=new LinkQueryExpress(query,LINK.AND,new CompareQueryExpress("invalide",Compare.EQUAL,Boolean.FALSE));
        StreamCollection<ShortMessageSenderNote> noteList=getEntitys(query);
        String code=null;
        String userName=configureService.getStringValue(ConfigureItem.SMS_USER_NAME);
        String url=configureService.getStringValue(ConfigureItem.SMS_SEND_URL);
        String passwd=configureService.getStringValue(ConfigureItem.SMS_PASSWORD);
        String sender=configureService.getStringValue(ConfigureItem.BUSINESS_NAME);
        if(noteList.assertEmpty()){
            code=RandomUtils.getNumber(6);
        }else{
            code = noteList.get(0).getCode();
        }
        SMSSender smsSender=new SMSSender(url,userName,passwd);
        try {
            boolean result=smsSender.send(phone,"【"+sender+"】您的验证码为"+code+"请在5分钟内输入。感谢您对鑫鸿教育的支持，祝您生活愉快");
            if(result){
                ShortMessageSenderNote note=new ShortMessageSenderNote(phone, false, false,code);
                note.setCodeUsage(usage);
                saveObject(note);
                return code;
            }
        } catch (HttpException e) {
            throw ServiceExceptionDefine.messageSenderError;
        }
        return code;
    }

    @Override
    public boolean validateCode(String phone, String code) {
        QueryExpress query=new CompareQueryExpress("phone", Compare.EQUAL,phone);
        query=new LinkQueryExpress(query, LINK.AND,new CompareQueryExpress("invalide", Compare.EQUAL, Boolean.FALSE));
        query=new LinkQueryExpress(query, LINK.AND,new CompareQueryExpress("validate", Compare.EQUAL, Boolean.FALSE));
        query=new LinkQueryExpress(query, LINK.AND,new CompareQueryExpress("code", Compare.EQUAL,code));
        StreamCollection<ShortMessageSenderNote> noteList=getEntitys(query);
        if(noteList.isEmpty()){
            return false;
        }else{
            String noteId=noteList.get(0).getEntityId();
            em.createQuery("update "+getEntityName(entityClass)+" o set o.validate=:validate, "
                    +" o.updateTime=:updateTime,o.invalide=:invalide"
                    + " where o.entityId=:entityId").setParameter("validate", Boolean.TRUE)
                    .setParameter("updateTime", new Date())
                    .setParameter("invalide", Boolean.TRUE).setParameter("entityId",noteId).executeUpdate();
            return true;
        }
    }

    //清除短信验证码状态
    private void clearCodeStatus(){
        long millis=System.currentTimeMillis()-(DateTimeUtil.getMinutesMillisecond()*5);
        em.createQuery("update "+getEntityName(entityClass)+" o set o.invalide=:invalide,o.updateTime=:updateTime "
                + "where o.createTime<=:createTime")
                .setParameter("invalide", Boolean.TRUE)
                .setParameter("updateTime", new Date()).setParameter("createTime",new Date(millis))
                .executeUpdate();
    }
}
