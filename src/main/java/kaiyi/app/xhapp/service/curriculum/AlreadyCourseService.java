package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.AlreadyCourse;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

public interface AlreadyCourseService extends DatabaseQuery<AlreadyCourse> ,DatabaseOperator<AlreadyCourse> {
    /**
     * 构建已购清单
     * @param courseId
     */
    void generator(String accountId,String courseId)throws ServiceException;
}
