package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Enterprise;
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
}
