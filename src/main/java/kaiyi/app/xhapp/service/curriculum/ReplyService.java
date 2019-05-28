package kaiyi.app.xhapp.service.curriculum;

import kaiyi.puer.db.orm.ServiceException;

public interface ReplyService {
    /**
     * 课程回复
     * @param entityId
     * @param replierId
     * @param replyContent
     */
    void reply(String entityId,String replierId,String replyContent)throws ServiceException;
}
