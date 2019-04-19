package kaiyi.app.xhapp.service.bus;


import kaiyi.app.xhapp.entity.pub.Notice;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface NoticeService extends DatabaseQuery<Notice>,DatabaseFastOper<Notice> {
    void deleteById(String entityId);
}
