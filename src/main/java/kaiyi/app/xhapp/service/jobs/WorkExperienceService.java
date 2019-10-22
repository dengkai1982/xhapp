package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.WorkExperience;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface WorkExperienceService extends DatabaseQuery<WorkExperience>,
        DatabaseOperator<WorkExperience>,DatabaseFastOper<WorkExperience> {
}
