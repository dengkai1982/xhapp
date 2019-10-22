package kaiyi.app.xhapp.service.access;

import kaiyi.app.xhapp.entity.access.AccountRecharge;
import kaiyi.app.xhapp.entity.curriculum.PaymentNotify;
import kaiyi.app.xhapp.service.FlowStatisticsCount;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

public interface AccountRechargeService extends DatabaseQuery<AccountRecharge>,FlowStatisticsCount {
    /**
     * 构建充值订单
     * @return
     */
    AccountRecharge generatorOrder(String rechargerId,int price)throws ServiceException;
    /**
     * 通知付款结果
     * @param paymentNotify
     * @return
     */
    AccountRecharge paymentSaleOrder(PaymentNotify paymentNotify);

}
