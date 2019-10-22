package kaiyi.app.xhapp.executor;

import kaiyi.app.xhapp.entity.curriculum.PaymentNotify;
import kaiyi.app.xhapp.service.curriculum.CourseOrderService;
import kaiyi.puer.commons.exec.CodeExecuter;
import kaiyi.puer.h5ui.service.ApplicationService;

public class NotifyCourseOrder implements CodeExecuter {
    private ApplicationService applicationService;
    private PaymentNotify paymentNotify;

    public NotifyCourseOrder(ApplicationService applicationService, PaymentNotify paymentNotify) {
        this.applicationService = applicationService;
        this.paymentNotify = paymentNotify;
    }

    @Override
    public void execute() {
        CourseOrderService courseOrderService=applicationService.getSpringContextHolder().getBean(CourseOrderService.class);
        courseOrderService.paymentSaleOrder(paymentNotify);
    }
}
