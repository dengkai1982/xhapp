package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.Teacher;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface TeacherService extends DatabaseQuery<Teacher>,DatabaseFastOper<Teacher> {
}
