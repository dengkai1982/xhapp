package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.CourseComment;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

/**
 * 评论
 */
public interface CourseCommentService extends DatabaseQuery<CourseComment>,DatabaseOperator<CourseComment>,ReplyService {
    /**
     * 评论课程
     * @param courseId
     * @param commentator
     * @param content
     */
    void comment(String courseId,String commentator,String content,int score)throws ServiceException;


}
