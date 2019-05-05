package kaiyi.app.xhapp.service.log;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.log.AmountFlow;
import kaiyi.app.xhapp.entity.log.enums.AmountType;
import kaiyi.app.xhapp.entity.log.enums.BorrowLend;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface AmountFlowService extends DatabaseQuery<AmountFlow>,DatabaseFastOper<AmountFlow> {

    void saveNote(Account account, AmountType amountType, String orderId, int amount,
                  BorrowLend borrowLend);
}
