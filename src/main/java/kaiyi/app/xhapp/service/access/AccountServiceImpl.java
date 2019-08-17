package kaiyi.app.xhapp.service.access;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.enums.MemberShip;
import kaiyi.app.xhapp.entity.curriculum.CourseOrder;
import kaiyi.app.xhapp.entity.log.enums.AmountType;
import kaiyi.app.xhapp.entity.log.enums.BorrowLend;
import kaiyi.app.xhapp.entity.log.enums.TradeCourse;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.log.AmountFlowService;
import kaiyi.app.xhapp.service.log.ShortMessageSenderNoteService;
import kaiyi.puer.commons.data.StringEditor;
import kaiyi.puer.commons.validate.VariableVerifyUtils;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.h5ui.service.ApplicationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.LockModeType;
import java.util.Date;
import java.util.Objects;

@Service("accountService")
public class AccountServiceImpl extends InjectDao<Account> implements AccountService {
    @Resource
    private ApplicationService applicationService;
    @Resource
    private ShortMessageSenderNoteService shortMessageSenderNoteService;
    @Resource
    private AmountFlowService amountFlowService;
    @Override
    public void register(String phone, String password,String validateCode,String recommendId) throws ServiceException {
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
        account.setMemberShip(MemberShip.normal);
        account.setPassword(applicationService.cipherToString(password));
        Account recommend=findForPrimary(recommendId);
        if(Objects.nonNull(recommend)){
            account.setRecommend(recommend);
        }
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

    @Override
    public void resetPassword(String phone, String newPassword)throws ServiceException {
        Account account=signleQuery("phone",phone);
        if(Objects.isNull(account)){
            throw ServiceExceptionDefine.userNotExist;
        }
        account.setPassword(applicationService.cipherToString(newPassword));
        updateObject(account);
    }

    @Override
    public void changeMemberShip(String entityId, MemberShip memberShip) {
        Account account=findForPrimary(entityId);
        if(Objects.nonNull(account)){
           account.setMemberShip(memberShip);
        }
    }

    @Override
    public void usageGoldPayment(CourseOrder courseOrder) {
        Account account=findForPrimary(courseOrder.getAccount().getEntityId());
        int before=account.getGold();
        account.setGold(account.getGold()-courseOrder.getAmount());
        updateObject(account);
        amountFlowService.saveNote(account,AmountType.GOLD,TradeCourse.ALE_PRODUCT,
                courseOrder.getOrderId(),before,courseOrder.getAmount(),account.getGold(),
                BorrowLend.expenditure);
    }

    @Override
    public void usageIntegralPayment(CourseOrder courseOrder) {
        Account account=findForPrimary(courseOrder.getAccount().getEntityId());
        int before=account.getIntegral();
        account.setIntegral(account.getIntegral()-courseOrder.getAmount());
        updateObject(account);
        amountFlowService.saveNote(account,AmountType.INTEGRAL,TradeCourse.ALE_PRODUCT,
                courseOrder.getOrderId(),before,courseOrder.getAmount(),account.getIntegral(),
                BorrowLend.expenditure);
    }

    @Override
    public void grantRoyalty(String accountId,String orderId, TradeCourse tradeCourse, int amount) {
        Account account=findForPrimary(accountId);
        int before=account.getIntegral();
        account.setIntegral(account.getIntegral()+amount);
        updateObject(account);
        amountFlowService.saveNote(account,AmountType.INTEGRAL,tradeCourse,orderId,before,amount,
                account.getIntegral(),BorrowLend.income);
    }
    @Override
    public Account applyWithdraw(String accountId, int amount,String orderId)throws ServiceException {
        Account account=findForPrimary(accountId);
        if(Objects.isNull(account)){
            throw ServiceExceptionDefine.entityNotExist;
        }
        em.lock(account, LockModeType.PESSIMISTIC_WRITE);
        if(amount<=0){
            throw ServiceExceptionDefine.withdrawableAmountError;
        }
        if(account.getIntegral()<amount){
            throw ServiceExceptionDefine.withdrawableError;
        }
        int before=account.getIntegral();
        account.setIntegral(account.getIntegral()-amount);
        updateObject(account);
        amountFlowService.saveNote(account,AmountType.INTEGRAL,TradeCourse.INTEGRAL_WITH_DRAW,orderId,
                before,amount,account.getIntegral(),BorrowLend.expenditure);
        return account;
    }

    @Override
    public void unWithdraw(String accountId, int amount, String orderId) throws ServiceException {
        Account account=findForPrimary(accountId);
        if(Objects.isNull(account)){
            throw ServiceExceptionDefine.entityNotExist;
        }
        em.lock(account, LockModeType.PESSIMISTIC_WRITE);
        int before=account.getIntegral();
        account.setIntegral(account.getIntegral()+amount);
        updateObject(account);
        amountFlowService.saveNote(account,AmountType.INTEGRAL,TradeCourse.REJECT_WITH_DRAW,orderId,
                before,amount,account.getIntegral(),BorrowLend.income);
    }
}
