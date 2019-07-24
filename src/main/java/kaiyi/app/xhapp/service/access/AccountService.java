package kaiyi.app.xhapp.service.access;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.enums.MemberShip;
import kaiyi.app.xhapp.entity.curriculum.CourseOrder;
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
    void register(String phone,String password,String validateCode)throws ServiceException;

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
     * 使用余额支付
     * @param courseOrder
     */
    void usageGoldPayment(CourseOrder courseOrder);
}
