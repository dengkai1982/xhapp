package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.Chapter;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface ChapterService extends DatabaseQuery<Chapter> ,DatabaseFastOper<Chapter> {
    void deleteByEntityId(String entityId);
}
