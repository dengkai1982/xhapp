package kaiyi.app.xhapp.service.distribution;

import kaiyi.app.xhapp.entity.distribution.RoyaltyType;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface RoyaltyTypeService extends DatabaseQuery<RoyaltyType>,DatabaseFastOper<RoyaltyType>,
        DatabaseOperator<RoyaltyType> {

}
