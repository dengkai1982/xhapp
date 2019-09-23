<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/htmlHeader.jsp" %>
    <style>
        .table-data tbody > tr > th {
            width: 120px;
        }
    </style>
</head>
<body>
<%@include file="/WEB-INF/headerMenus.jsp" %>
<main id="main">
    <div class="container">
        <div class="form_container">
            <%@include file="/WEB-INF/mgr/access/newOrEditBaseTitle.jsp" %>
            <div class="detail">
                <div class="detail-title">申请提现详情<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <c:set value="${requestScope.entity}" var="entity"/>
                    <table class="table table-data table-condensed table-borderless">
                        <tbody>
                            <tr>
                                <th class="strong">申请人</th>
                                <td>${entity.applyer.showAccountName}</td>
                                <th class="strong">提现单号</th>
                                <td>${entity.orderId}</td>
                                <th class="strong">申请时间</th>
                                <td><fmt:formatDate value="${entity.applyTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                            </tr>
                            <tr>
                                <th class="strong">申请金额</th>
                                <td><currency:convert value="${entity.applyAmount}"/></td>
                                <th class="strong">网银类型</th>
                                <td>${entity.bankType.value}</td>
                                <th class="strong">账号/卡号</th>
                                <td>${entity.cardNumber}</td>
                            </tr>
                            <tr>
                                <th class="strong">开户行</th>
                                <td>${entity.branchName}</td>
                                <th class="strong">账户姓名</th>
                                <td>${entity.bankAccountName}</td>
                            </tr>

                        </tbody>
                    </table>
                </div>
                <div class="detail-title">处理提现审核<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <form id="withdraw_apply_form" class="form-horizontal" method="post">
                        <input type="hidden" name="entityId" value="${entity.entityId}"/>
                        <div class="form-group">
                            <label for="disposeResult" class="col-sm-1 required">处理结果</label>
                            <div data-column-type="TEXT" class="col-sm-3">
                                <select name="disposeResult" id="disposeResult"  validate="required:处理结果必须选择" data-placeholder="请选择" class='form-control chosen-select'>
                                    <option></option>
                                    <option value="false">拒绝提现</option>
                                    <option value="true">完成提现</option>
                                </select>
                            </div>
                        </div>
                        <div id="withdrawYes" style="display: none">
                            <div class="form-group">
                                <label for="transBank" class="col-sm-1 required">转账银行</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control" value="中国农业银行" name="transBank" id="transBank"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="voucher" class="col-sm-1 required">转账凭证号</label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control"  name="voucher" id="voucher"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="mark" class="col-sm-1">处理结果</label>
                            <div class="col-sm-11">
                                <textarea name="mark" rows="4" id="mark" class="form-control" placeholder="审核备注信息"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div style="text-align: center;">
                    <button type="button" id="formSubmitAction" class="btn btn-wide btn-primary">提交处理结果</button>
                    <a href="${webPage.backPage}"  class="btn btn-back btn-wide">返回</a>
                </div>
            </div>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp" %>
<script type="text/javascript">
    function pageReady(doc) {
        $("#disposeResult").change(function(){
            if($(this).val()=="true"){
                $("#withdrawYes").show();
            }else{
                $("#withdrawYes").hide();
            }
        })
        $("#formSubmitAction").click(function(){
            var $formData=$("#withdraw_apply_form").formToJson();
            console.log($formData);
            if($formData.applyResult==""){
                showMessage("处理结果必须选择");
                return;
            }
            postJSON("${managerPath}/distribution/finishWithdraw${suffix}",$formData,"正在处理请稍后",function(result){
                    if(result.code==SUCCESS){
                        bootbox.alert({
                            title:'消息',
                            message:"完成提现审核,点击确认返回",
                            size: 'small',
                            callback: function () {
                                window.location.href="${webPage.backPage}";
                            }
                        });
                    }
            })
        })
    }
</script>
</body>
</html>