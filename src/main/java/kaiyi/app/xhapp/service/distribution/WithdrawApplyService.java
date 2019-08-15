package kaiyi.app.xhapp.service.distribution;

import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.app.xhapp.entity.distribution.WithdrawApply;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

public interface WithdrawApplyService extends DatabaseQuery<WithdrawApply> ,DatabaseFastOper<WithdrawApply>,
        DatabaseOperator<WithdrawApply> {
    /**
     * 申请提现
     * @param bankInfoId 网银信息
     * @param accountId 申请人
     * @param amount 申请金额
     * @throws ServiceException
     */
    void apply(String bankInfoId,String accountId,int amount,String pohone)throws ServiceException;

    /**
     * 处理提现申请
     * @param entityId
     * @param disposeResult
     * @param transBank
     * @param voucher
     * @param mark
     * @param disposeUser
     * @throws ServiceException
     */
    void dispose(String entityId, boolean disposeResult, String transBank, String voucher,
                 String mark, VisitorUser disposeUser) throws ServiceException;
    /**
     * 当日是否有申请过提现
     * @param memberId
     * @return
     */
    boolean existDay(String memberId);
}
