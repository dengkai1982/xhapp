package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.curriculum.Course;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface CourseService extends DatabaseQuery<Course>,DatabaseFastOper<Course>,DatabaseOperator<Course> {
    void changeSale(String entityId);

    /**
     * 增加课程浏览量
     * @param entityId
     */
    void addBrowseVolume(String entityId);

    /**
     * 增加课程购买量
     * @param entityId
     */
    void addBuyVolume(String entityId);

}
