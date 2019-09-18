package kaiyi.app.xhapp.executor;

import kaiyi.app.xhapp.entity.curriculum.PaymentNotify;
import kaiyi.app.xhapp.service.access.AccountRechargeService;
import kaiyi.puer.commons.exec.CodeExecuter;
import kaiyi.puer.h5ui.service.ApplicationService;

public class NotifyRechargeOrder implements CodeExecuter {
    private ApplicationService applicationService;
    private PaymentNotify paymentNotify;

    public NotifyRechargeOrder(ApplicationService applicationService, PaymentNotify paymentNotify) {
        this.applicationService = applicationService;
        this.paymentNotify = paymentNotify;
    }

    @Override
    public void execute() {
        AccountRechargeService accountRechargeService=applicationService.getSpringContextHolder().getBean(AccountRechargeService.class);
        accountRechargeService.paymentSaleOrder(paymentNotify);
    }
}
