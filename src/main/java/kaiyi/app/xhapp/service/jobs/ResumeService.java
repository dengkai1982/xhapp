package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Resume;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface ResumeService extends DatabaseOperator<Resume>, DatabaseFastOper<Resume>, DatabaseQuery<Resume> {

    /**
     * 修改发布状态
     * @param entityId
     */
    void changeUpper(String entityId);
}
