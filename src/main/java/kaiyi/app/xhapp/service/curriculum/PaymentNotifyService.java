package kaiyi.app.xhapp.service.curriculum;


import kaiyi.app.xhapp.entity.curriculum.PaymentNotify;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface PaymentNotifyService extends DatabaseQuery<PaymentNotify>,DatabaseOperator<PaymentNotify> {
}
