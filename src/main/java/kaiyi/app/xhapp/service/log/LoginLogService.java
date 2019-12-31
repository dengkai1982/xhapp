package kaiyi.app.xhapp.service.log;

import kaiyi.app.xhapp.entity.log.LoginLog;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface LoginLogService extends DatabaseOperator<LoginLog> , DatabaseQuery<LoginLog> {
}
