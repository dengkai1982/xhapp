<div class="table-responsive">
    <table class="table table-striped table-bordered">
        <thead>
            <tr>
                <th colspan="3">课程消费</th>
            </tr>
            <tr>
                <th>总课消费程数</th>
                <th>收费课程</th>
                <th>免费课程</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>${courseSaleStatistics.courseTotal}</td>
                <td>${courseSaleStatistics.courseNotFreeTotal}</td>
                <td>${courseSaleStatistics.courseFreeTotal}</td>
            </tr>
        </tbody>
    </table>
</div>
<br/>
<div class="table-responsive">
    <table class="table table-striped table-bordered">
        <thead>
            <tr>
                <th colspan="4">课程订单销量</th>
            </tr>
            <tr>
                <th>订单总量</th>
                <th>付款成功</th>
                <th>等待付款</th>
                <th>用户删除</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>${courseSaleStatistics.orderTotalNumber}</td>
                <td>${courseSaleStatistics.paymentNumber}</td>
                <td>${courseSaleStatistics.waitPaymentNumber}</td>
                <td>${courseSaleStatistics.deleteNumber}</td>
            </tr>
        </tbody>
    </table>
</div>
<br/>
<div class="table-responsive">
    <table class="table table-striped table-bordered">
        <thead>
            <tr>
                <th colspan="4">课程销售金额</th>
            </tr>
            <tr>
                <th>已付款(课程数/金额)</th>
                <th>使用现金(课程数/金额)</th>
                <th>使用余额(课程数/金额)</th>
                <th>使用积分(课程数/金额)</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>${courseSaleStatistics.orderTotal}/${courseSaleStatistics.orderAmount}</td>
                <td>${courseSaleStatistics.useCashTotal}/${courseSaleStatistics.useCashAmount}</td>
                <td>${courseSaleStatistics.useGoldTotal}/${courseSaleStatistics.useGoldAmount}</td>
                <td>${courseSaleStatistics.useIntegralTotal}/${courseSaleStatistics.useIntegralAmount}</td>
            </tr>
        </tbody>
    </table>
</div>