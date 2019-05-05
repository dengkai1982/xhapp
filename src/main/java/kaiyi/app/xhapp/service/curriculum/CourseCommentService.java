package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.CourseComment;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

/**
 * 评论
 */
public interface CourseCommentService extends DatabaseQuery<CourseComment> {
    /**
     * 评论课程
     * @param courseId
     * @param commentator
     * @param content
     */
    void comment(String courseId,String commentator,String content,int score)throws ServiceException;
    /**
     * 课程回复
     * @param entityId
     * @param replierId
     * @param replyContent
     */
    void reply(String entityId,String replierId,String replyContent)throws ServiceException;

}
