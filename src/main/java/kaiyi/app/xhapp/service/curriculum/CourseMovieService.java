package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.CourseMovie;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface CourseMovieService extends DatabaseFastOper<CourseMovie>, DatabaseOperator<CourseMovie>,DatabaseQuery<CourseMovie> {
}
