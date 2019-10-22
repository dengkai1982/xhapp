package kaiyi.app.xhapp.service.pages;

import kaiyi.app.xhapp.entity.pages.ExamInfo;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface ExamInfoService extends DatabaseQuery<ExamInfo>,DatabaseFastOper<ExamInfo> {

    void deleteById(String entityId);

    void readExamInfo(String entityId);
}
