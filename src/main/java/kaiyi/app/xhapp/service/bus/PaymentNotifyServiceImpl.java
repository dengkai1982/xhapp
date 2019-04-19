package kaiyi.app.xhapp.service.bus;

import kaiyi.app.xhapp.entity.bus.PaymentNotify;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("paymentNotifyService")
public class PaymentNotifyServiceImpl extends InjectDao<PaymentNotify> implements PaymentNotifyService {
    private static final long serialVersionUID = 1315522015963396416L;
}
