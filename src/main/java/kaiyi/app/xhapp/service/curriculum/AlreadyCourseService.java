package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.AlreadyCourse;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface AlreadyCourseService extends DatabaseQuery<AlreadyCourse> ,DatabaseOperator<AlreadyCourse> {
}
