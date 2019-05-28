package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Position;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface PositionService extends DatabaseQuery<Position>,DatabaseFastOper<Position>,
        DatabaseOperator<Position> {
}
