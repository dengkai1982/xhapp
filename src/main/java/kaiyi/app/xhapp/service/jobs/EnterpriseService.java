package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Enterprise;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface EnterpriseService extends DatabaseFastOper<Enterprise>,DatabaseOperator<Enterprise>,
        DatabaseQuery<Enterprise> {
    /**
     * 设置企业为推荐企业
     * @param entityId
     */
    void changeRecommend(String entityId);
    /**
     * 企业认证
     */
    void changeVerifyed(String entityId);

    /**
     * 批量认证
     * @param entityIdArray
     * @param verify
     */
    void batchVerifyed(StreamArray<String> entityIdArray, boolean verify);

    /**
     * 批量推荐
     * @param entityIdArray
     * @param recommend
     */
    void batchRecommend(StreamArray<String> entityIdArray, boolean recommend);

    /**
     * 批量冻结
     * @param entityIdArray
     * @param frozen
     */
    void batchFrozen(StreamArray<String> entityIdArray, boolean frozen);
}
