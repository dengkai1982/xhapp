package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.access.enums.CapitalType;
import kaiyi.app.xhapp.entity.curriculum.CourseOrder;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

public interface CourseOrderService extends DatabaseQuery<CourseOrder> {
    /**
     * 构建订单
     */
    CourseOrder generatorOrder(String courseId, String accountId, CapitalType capitalType)throws ServiceException;
}
