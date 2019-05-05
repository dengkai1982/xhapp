package kaiyi.app.xhapp.service.bus;

import kaiyi.app.xhapp.entity.access.enums.CapitalType;
import kaiyi.app.xhapp.entity.bus.CourseOrder;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

public interface CourseOrderService extends DatabaseQuery<CourseOrder> {
    /**
     * 构建订单
     */
    CourseOrder generatorOrder(String courseId, String accountId, CapitalType capitalType)throws ServiceException;
}
