package kaiyi.app.xhapp.service.log;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.log.AmountFlow;
import kaiyi.app.xhapp.entity.log.enums.AmountType;
import kaiyi.app.xhapp.entity.log.enums.BorrowLend;
import kaiyi.app.xhapp.entity.log.enums.TradeCourse;
import kaiyi.app.xhapp.service.FlowStatisticsCount;
import kaiyi.puer.commons.data.Currency;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseQuery;

import java.util.Date;

public interface AmountFlowService extends DatabaseQuery<AmountFlow>,DatabaseFastOper<AmountFlow>,FlowStatisticsCount {

    void saveNote(Account account, AmountType amountType, TradeCourse tradeCourse,String orderId, int beforeAmount, int amount,
                  int afterAmount, BorrowLend borrowLend);

    /**
     * 统计会员积分
     * @param accountId
     * @return
     */
    Currency totalAccountIntegral(String accountId,Date startDate,Date endDate);

}
