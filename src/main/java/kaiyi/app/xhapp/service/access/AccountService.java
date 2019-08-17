package kaiyi.app.xhapp.service.access;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.enums.MemberShip;
import kaiyi.app.xhapp.entity.curriculum.CourseOrder;
import kaiyi.app.xhapp.entity.log.enums.TradeCourse;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

public interface AccountService extends DatabaseQuery<Account>,DatabaseFastOper<Account> {
    /**
     * 账户注册
     * @param phone
     * @param password
     * @param validateCode
     * @throws ServiceException
     */
    void register(String phone,String password,String validateCode,String recommendId)throws ServiceException;

    /**
     * 账户登录
     * @param phone
     * @param password
     * @return
     * @throws ServiceException
     */
    Account login(String phone,String password)throws ServiceException;
    /**
     * 修改密码
     * @param phone
     * @param oldPassword
     * @param newPassword
     * @throws ServiceException
     */
    void changePassword(String phone,String oldPassword,String newPassword)throws ServiceException;

    /**
     * 重置密码
     * @param phone
     * @param newPassword
     */
    void resetPassword(String phone,String newPassword)throws ServiceException;

    /**
     * 更改会员类型
     * @param entityId
     * @param memberShip
     */
    void changeMemberShip(String entityId, MemberShip memberShip);

    /**
     * 使用金币支付
     * @param courseOrder
     */
    void usageGoldPayment(CourseOrder courseOrder);
    /**
     * 使用余额支付
     * @param courseOrder
     */
    void usageIntegralPayment(CourseOrder courseOrder);
    /**
     * 发放提成
     * @param orderId 单号
     * @param tradeCourse 科目
     * @param amount 金额
     */
    void grantRoyalty(String accountId,String orderId, TradeCourse tradeCourse,int amount);
    /**
     * 申请提现
     * @param accountId
     * @param amount
     */
    Account applyWithdraw(String accountId,int amount,String orderId)throws ServiceException;
    /**
     * 提现失败退回余额
     * @param memberId
     * @param amount
     * @param orderId
     * @throws ServiceException
     */
    void unWithdraw(String memberId,int amount,String orderId)throws  ServiceException;


}
