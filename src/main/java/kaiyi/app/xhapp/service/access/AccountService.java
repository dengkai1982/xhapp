package kaiyi.app.xhapp.service.access;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

public interface AccountService extends DatabaseQuery<Account>{
    /**
     * 账户注册
     * @param phone
     * @param password
     * @throws ServiceException
     */
    void register(String phone,String password)throws ServiceException;

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
}
