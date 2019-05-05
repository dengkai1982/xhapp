package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.curriculum.Course;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface CourseService extends DatabaseQuery<Course>,DatabaseFastOper<Course>,DatabaseOperator<Course> {
    void changeSale(String entityId);

}
