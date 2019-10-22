package kaiyi.app.xhapp.service.access;

import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

public interface VisitorUserService extends DatabaseQuery<VisitorUser>,DatabaseFastOper<VisitorUser>,
        DatabaseOperator<VisitorUser> {
    /**
     * 用户登录
     * @param loginName
     * @param password
     * @throws ServiceException
     */
    VisitorUser doLogin(String loginName, String password,String ipaddr)throws ServiceException;
    /**
     * 修改密码
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @throws ServiceException
     */
    void changePassword(String userId, String oldPassword, String newPassword)throws ServiceException;
    /**
     * 删除员工
     * @param id
     */
    void deleteById(String id)throws ServiceException;

    /**
     * 重置密码
     * @param id
     */
    void resetPassword(String id);

}
