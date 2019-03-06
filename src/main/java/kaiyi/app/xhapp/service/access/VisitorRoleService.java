package kaiyi.app.xhapp.service.access;

import kaiyi.app.xhapp.entity.access.VisitorRole;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

public interface VisitorRoleService extends DatabaseQuery<VisitorRole>,DatabaseFastOper<VisitorRole>,DatabaseOperator<VisitorRole> {

    void deleteById(String entityId)throws ServiceException;
}
