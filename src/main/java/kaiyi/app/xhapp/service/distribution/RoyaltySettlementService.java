package kaiyi.app.xhapp.service.distribution;

import kaiyi.app.xhapp.entity.distribution.RoyaltySettlement;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface RoyaltySettlementService extends DatabaseOperator<RoyaltySettlement>,
        DatabaseFastOper<RoyaltySettlement>,DatabaseQuery<RoyaltySettlement> {
    /**
     * 完成提成发放
     * @param entityId
     * @param granterId 发放人
     */
    void grant(String entityId, String granterId);

    /**
     * 根据结算类型获取结算额度
     * @return
     */
    RoyaltySettlement generatorRoyaltySettlement(String accountId,String royaltyTypeId);

    /**
     * 根据ID删除提成记录
     * @param entityId
     */
    void deleteById(String entityId);
}
