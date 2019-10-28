package kaiyi.app.xhapp.service.log;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.log.PerformanceCommission;
import kaiyi.app.xhapp.entity.log.enums.TradeCourse;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

import java.util.Date;

public interface PerformanceCommissionService extends DatabaseQuery<PerformanceCommission>, DatabaseOperator<PerformanceCommission> {

    void saveNode(Account account,String orderId, boolean team,TradeCourse tradeCourse, int achievement, int rate, int amount);
}
