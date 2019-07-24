package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.access.enums.CapitalType;
import kaiyi.app.xhapp.entity.curriculum.CourseOrder;
import kaiyi.app.xhapp.entity.curriculum.PaymentNotify;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

public interface CourseOrderService extends DatabaseQuery<CourseOrder> {
    /**
     * 构建订单
     */
    CourseOrder generatorOrder(StreamCollection<String> courseIdStream,
                               String accountId, CapitalType capitalType)throws ServiceException;
    /**
     * 完成付款通知
     * @param paymentNotify
     * @return
     */
    CourseOrder paymentSaleOrder(PaymentNotify paymentNotify);
}
