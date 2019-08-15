package kaiyi.app.xhapp.service.curriculum;

import kaiyi.puer.db.orm.ServiceException;

public interface ReplyService {
    void reply(String entityId, String replierId, String replyContent) throws ServiceException;
}
