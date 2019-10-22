package kaiyi.app.xhapp.entity.pojo;

/**
 * 课程消费统计
 */
public class CourseSaleStatistics {
    //课程消费金额
    //private String courseAmount;
    //总课程数
    private int courseTotal;
    //收费课程
    private int courseNotFreeTotal;
    //免费课程
    private int courseFreeTotal;///////
    //订单总量
    private int orderTotalNumber;
    //等待支付数
    private int waitPaymentNumber;
    //用户删除数
    private int deleteNumber;
    //已付款数
    private int paymentNumber;

    //已付款订单金额
    private String orderAmount;
    //已付款订单数量
    private int orderTotal;
    //使用现金金额
    private String useCashAmount;

    private int useCashTotal;
    //使用余额金额
    private String useGoldAmount;

    private int useGoldTotal;
    //使用积分金额
    private String useIntegralAmount;

    private int useIntegralTotal;
    public CourseSaleStatistics(){
        //courseAmount="0.00";
        courseTotal=0;
        courseNotFreeTotal=0;
        courseFreeTotal=0;
        orderTotalNumber=0;
        waitPaymentNumber=0;
        deleteNumber=0;
        paymentNumber=0;
        orderAmount="0.00";
        orderTotal=0;
        useCashAmount="0.00";
        useCashTotal=0;
        useGoldAmount="0.00";
        useGoldTotal=0;
        useIntegralAmount="0.00";
        useIntegralTotal=0;
    }

    public int getCourseTotal() {
        return courseTotal;
    }

    public void setCourseTotal(int courseTotal) {
        this.courseTotal = courseTotal;
    }

    public int getCourseNotFreeTotal() {
        return courseNotFreeTotal;
    }

    public void setCourseNotFreeTotal(int courseNotFreeTotal) {
        this.courseNotFreeTotal = courseNotFreeTotal;
    }

    public int getCourseFreeTotal() {
        return courseFreeTotal;
    }

    public void setCourseFreeTotal(int courseFreeTotal) {
        this.courseFreeTotal = courseFreeTotal;
    }

    public int getOrderTotalNumber() {
        return orderTotalNumber;
    }

    public void setOrderTotalNumber(int orderTotalNumber) {
        this.orderTotalNumber = orderTotalNumber;
    }

    public int getWaitPaymentNumber() {
        return waitPaymentNumber;
    }

    public void setWaitPaymentNumber(int waitPaymentNumber) {
        this.waitPaymentNumber = waitPaymentNumber;
    }

    public int getDeleteNumber() {
        return deleteNumber;
    }

    public void setDeleteNumber(int deleteNumber) {
        this.deleteNumber = deleteNumber;
    }

    public int getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(int paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(int orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getUseCashAmount() {
        return useCashAmount;
    }

    public void setUseCashAmount(String useCashAmount) {
        this.useCashAmount = useCashAmount;
    }

    public int getUseCashTotal() {
        return useCashTotal;
    }

    public void setUseCashTotal(int useCashTotal) {
        this.useCashTotal = useCashTotal;
    }

    public String getUseGoldAmount() {
        return useGoldAmount;
    }

    public void setUseGoldAmount(String useGoldAmount) {
        this.useGoldAmount = useGoldAmount;
    }

    public int getUseGoldTotal() {
        return useGoldTotal;
    }

    public void setUseGoldTotal(int useGoldTotal) {
        this.useGoldTotal = useGoldTotal;
    }

    public String getUseIntegralAmount() {
        return useIntegralAmount;
    }

    public void setUseIntegralAmount(String useIntegralAmount) {
        this.useIntegralAmount = useIntegralAmount;
    }

    public int getUseIntegralTotal() {
        return useIntegralTotal;
    }

    public void setUseIntegralTotal(int useIntegralTotal) {
        this.useIntegralTotal = useIntegralTotal;
    }
}
