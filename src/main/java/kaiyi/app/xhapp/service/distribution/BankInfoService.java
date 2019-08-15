package kaiyi.app.xhapp.service.distribution;

import kaiyi.app.xhapp.entity.distribution.BankInfo;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface BankInfoService extends DatabaseQuery<BankInfo> ,DatabaseFastOper<BankInfo>,
        DatabaseOperator<BankInfo> {
}
