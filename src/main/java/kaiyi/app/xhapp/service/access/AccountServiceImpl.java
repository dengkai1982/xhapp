package kaiyi.app.xhapp.service.access;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.log.ShortMessageSenderNoteService;
import kaiyi.puer.commons.data.StringEditor;
import kaiyi.puer.commons.validate.VariableVerifyUtils;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.h5ui.service.ApplicationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Service("accountService")
public class AccountServiceImpl extends InjectDao<Account> implements AccountService {
    @Resource
    private ApplicationService applicationService;
    @Resource
    private ShortMessageSenderNoteService shortMessageSenderNoteService;
    @Override
    public void register(String phone, String password,String validateCode) throws ServiceException {
        if(!shortMessageSenderNoteService.validateCode(phone,validateCode)){
            throw ServiceExceptionDefine.validateCodeError;
        }
        if(!VariableVerifyUtils.mobileValidate(phone)){
            throw ServiceExceptionDefine.phoneFormatError;
        }
        Account account=signleQuery("phone",phone);
        if(Objects.nonNull(account)){
            throw ServiceExceptionDefine.phoneNumberExist;
        }
        if(StringEditor.isEmpty(password)){
            throw ServiceExceptionDefine.passwordNotEmpty;
        }
        account=new Account();
        account.setPhone(phone);
        account.setRegisterTime(new Date());
        account.setPassword(applicationService.cipherToString(password));
        saveObject(account);
    }

    @Override
    public Account login(String phone, String password) throws ServiceException {
        Account account=signleQuery("phone",phone);
        if(Objects.isNull(account)){
            throw ServiceExceptionDefine.userNotExist;
        }
        if(!applicationService.checkChiper(password,account.getPassword())){
            throw ServiceExceptionDefine.passwordError;
        }
        return account;
    }

    @Override
    public void changePassword(String phone, String oldPassword, String newPassword) throws ServiceException {
        Account account=signleQuery("phone",phone);
        if(Objects.isNull(account)){
            throw ServiceExceptionDefine.userNotExist;
        }
        if(!applicationService.checkChiper(oldPassword,account.getPassword())){
            throw ServiceExceptionDefine.passwordError;
        }
        account.setPassword(applicationService.cipherToString(newPassword));
        updateObject(account);
    }
}
