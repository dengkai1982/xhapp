package kaiyi.app.xhapp.service.log;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.log.PerformanceCommission;
import kaiyi.app.xhapp.entity.log.enums.TradeCourse;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("performanceCommissionService")
public class PerformanceCommissionServiceImpl extends InjectDao<PerformanceCommission> implements PerformanceCommissionService {
    @Override
    public void saveNode(Account account,String orderId, boolean team, TradeCourse tradeCourse, int achievement, int rate, int amount) {
        PerformanceCommission perf=new PerformanceCommission();
        perf.setAccount(account);
        perf.setAchievement(achievement);
        perf.setAmount(amount);
        perf.setCreateTime(new Date());
        perf.setTradeCourse(tradeCourse);
        perf.setRate(rate);
        perf.setTeam(team);
        perf.setOrderId(orderId);
        saveObject(perf);
    }
}
