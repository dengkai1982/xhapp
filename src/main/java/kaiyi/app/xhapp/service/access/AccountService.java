package kaiyi.app.xhapp.service.access;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.AccountRecharge;
import kaiyi.app.xhapp.entity.access.enums.MemberShip;
import kaiyi.app.xhapp.entity.curriculum.CourseOrder;
import kaiyi.app.xhapp.entity.log.enums.TradeCourse;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

public interface AccountService extends DatabaseQuery<Account>,DatabaseFastOper<Account>,DatabaseOperator<Account> {
    /**
     * 账户注册
     * @param phone
     * @param password
     * @param validateCode
     * @throws ServiceException
     */
    Account register(String phone,String password,String validateCode,String recommendId)throws ServiceException;

    /**
     * 重新绑定电话号码
     * @return
     * @throws ServiceException
     */
    void resetBindPhone(String entityId,String oldPhone,String newPhone,String validateCode)throws ServiceException;
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
     * 金币充值
     * @param recharge
     */
    void rechargeGold(AccountRecharge recharge);
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
    void unWithdraw(String memberId,int amount,String orderId)throws ServiceException;

    /**
     *
     * @param entityId
     * @return
     */
    StreamCollection<Account> getTeams(String entityId);
    /**
     * 执行日清理
     */
    void dayClear();
    /**
     * 执行月清理
     */
    void monthClear();
    /**
     * 设置是否为内部会员
     * @param entityId
     */
    void changeInsideMember(String entityId);
    /**
     * 获取上级内部会员
     * @param entityId
     * @return
     */
    Account findParentInsideMember(String entityId);

    /**
     * 修改访问激活状态
     * @param entityId
     */
    void changeActive(String entityId);

    /**
     * 设置员工姓名
     * @param entityId
     * @param name
     */
    void setMemberName(String entityId,String name);


}
