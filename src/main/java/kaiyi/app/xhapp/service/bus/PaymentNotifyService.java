package kaiyi.app.xhapp.service.bus;


import kaiyi.app.xhapp.entity.bus.PaymentNotify;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface PaymentNotifyService extends DatabaseQuery<PaymentNotify>,DatabaseOperator<PaymentNotify> {
}
