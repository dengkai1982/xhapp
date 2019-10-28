package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Resume;
import kaiyi.app.xhapp.entity.pojo.ResumeAndRecruitment;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

import java.util.Date;

public interface ResumeService extends DatabaseOperator<Resume>, DatabaseFastOper<Resume>, DatabaseQuery<Resume> {

    /**
     * 修改发布状态
     * @param entityId
     */
    void changeUpper(String entityId);

    void deleteResume(String entityId);

    /**
     * 批量发布
     * @param entityIdArray
     * @param infoUpper
     */
    void batchInfoUpper(StreamArray<String> entityIdArray, boolean infoUpper);
    /**
     * 批量冻结
     * @param entityIdArray
     * @param frozen
     */
    void batchFrozen(StreamArray<String> entityIdArray, boolean frozen);

    ResumeAndRecruitment resumeAndRecruitment(Date startTime, Date endTime);
}
