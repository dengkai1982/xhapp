package kaiyi.app.xhapp.service.distribution;

import kaiyi.app.xhapp.entity.distribution.RoyaltySettlement;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface RoyaltySettlementService extends DatabaseOperator<RoyaltySettlement>,
        DatabaseFastOper<RoyaltySettlement>,DatabaseQuery<RoyaltySettlement> {

}
