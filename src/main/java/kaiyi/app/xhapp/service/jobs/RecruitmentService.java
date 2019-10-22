package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Recruitment;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface RecruitmentService extends DatabaseFastOper<Recruitment>,DatabaseQuery<Recruitment>,
        DatabaseOperator<Recruitment> {

    void changeRecommend(String entityId);

    /**
     * 修改发布状态
     * @param entityId
     */
    void changeUpper(String entityId);

    /**
     * 删除招聘信息
     * @param entityId
     */
    void deleteRecruitment(String entityId);

    /**
     * 批量发布
     * @param entityIdArray
     * @param infoUpper
     */
    void batchInfoUpper(StreamArray<String> entityIdArray, boolean infoUpper);

    /**
     * 批量推荐
     * @param entityIdArray
     * @param recommend
     */
    void batchRecommend(StreamArray<String> entityIdArray, boolean recommend);
}
