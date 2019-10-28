<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/htmlHeader.jsp" %>
</head>
<body>
<%@include file="/WEB-INF/headerMenus.jsp" %>
<main id="main">
    <div class="container">
        <div id="mainMenu" class="clearfix">
        </div>
        <c:if test="${requestScope.hasData}">
            ${requestScope.searchHtml}
        </c:if>
        <div id="mainContent" class="main-row">
            <%@include file="/WEB-INF/mgr/statistics/order/saleTypeMenus.jsp" %>
            <div class="main-col main-table" style="background: #fff;padding:10px 0;">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-1">日期</label>
                        <div class="col-sm-5">
                            <div class="input-group">
                                <span class="input-group-addon">从</span>
                                <input type="text" name="startTime" class="form-control form-date" readonly
                                       placeholder="请选择开始时间">
                                <span class="input-group-addon">到</span>
                                <input type="text" name="endTime" class="form-control form-date" readonly
                                       placeholder="请选择结束时间">
                            </div>
                        </div>
                        <div class="col-sm-1">
                            <button class="btn btn-primary" id="queryNow" type="button">立即查询</button>
                        </div>
                    </div>
                </div>
                <div class="result" style="padding:10px;" id="queryResult">

                </div>
            </div>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp" %>
<script type="text/javascript">
    function pageReady(doc) {
        $("#queryNow").click(function () {
            var startTime = $("input[name='startTime']").val();
            var endTime = $("input[name='endTime']").val();
            $("#queryResult").empty();
            $.ajax({
                url:"${managerPath}/statistics/${requestScope.queryUrl}${suffix}",
                type:"GET", //GET
                async:true,    //或false,是否异步
                data:{
                    startTime: startTime,
                    endTime: endTime
                },
                timeout:5000,
                dataType:"TEXT",
                success:function(data,textStatus,xhr){
                    $("#queryResult").html(data);
                }
            })
        })
    }
</script>
</body>
</html>