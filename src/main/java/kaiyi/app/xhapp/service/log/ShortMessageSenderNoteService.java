package kaiyi.app.xhapp.service.log;

import kaiyi.app.xhapp.entity.log.ShortMessageSenderNote;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

import javax.sound.midi.ShortMessage;

/**
 * 短信验证码服务
 *
 */
public interface ShortMessageSenderNoteService extends DatabaseQuery<ShortMessageSenderNote>, DatabaseOperator<ShortMessageSenderNote> {
    /**
     * 为指定的手机号发送验证码
     * @param phone
     * @param usage 用途
     * @return
     * @throws ServiceException
     */
    String sendValidateCode(String phone, String usage)throws ServiceException;
    /**
     * 短信验证码校验
     * @param phone
     * @param code
     * @return
     */
    boolean validateCode(String phone, String code);

}
