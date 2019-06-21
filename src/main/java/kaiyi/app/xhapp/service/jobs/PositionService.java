package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Position;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface PositionService extends DatabaseQuery<Position> , DatabaseFastOper<Position>, DatabaseOperator<Position> {
    /**
     *
     * @return
     */
    StreamCollection<Position> getChild(String districtId);
    /**
     *
     * @param districtId
     * @return
     */
    StreamCollection<Position> getSameLevel(String districtId);

    void newJobs(String name,String parentName);
}
