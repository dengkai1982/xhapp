package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.CourseProblem;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

public interface CourseProblemService extends DatabaseQuery<CourseProblem>,ReplyService {
    /**
     * 课程提问
     * @param courseId
     * @param commentator
     * @param content
     */
    void problem(String courseId,String commentator,String content)throws ServiceException;
}
