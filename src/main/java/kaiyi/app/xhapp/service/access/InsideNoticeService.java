package kaiyi.app.xhapp.service.access;

import kaiyi.app.xhapp.entity.access.InsideNotice;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface InsideNoticeService extends DatabaseQuery<InsideNotice> {
    /**发送站内消息*/
    void sendMessage(String content, String actionUrl);
    /**读取消息*/
    InsideNotice readMessage(String id);
    /**删除通知*/
    void deleteById(String id);

    void deleteByUrl(String url);

}
