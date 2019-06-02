package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Recruitment;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface RecruitmentService extends DatabaseFastOper<Recruitment>,DatabaseQuery<Recruitment>,
        DatabaseOperator<Recruitment> {
}
