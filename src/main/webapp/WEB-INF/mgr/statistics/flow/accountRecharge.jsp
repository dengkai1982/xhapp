<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/htmlHeader.jsp"%>
</head>
<body>
<%@include file="/WEB-INF/headerMenus.jsp"%>
<main id="main">
    <div class="container">
        <div id="mainMenu" class="clearfix">
            <div class="btn-toolbar pull-right">
                <c:if test="${requestScope.hasData}">
                    <a href="#" class="btn btn-link querybox-toggle" id="show_or_hide_search"><i class="icon-search icon"></i> 搜索</a>
                    <a href='javascript:void(0)' id="exportTableToExcel" title="账户充值表" class="btn btn-link"><i class="icon-import muted"> </i> 导出数据表</a>
                </c:if>
            </div>
        </div>
        <c:if test="${requestScope.hasData}">
            ${requestScope.searchHtml}
        </c:if>
        <div id="mainContent" class="main-row">
            <%@include file="/WEB-INF/mgr/statistics/flow/flowTypeMenus.jsp"%>
            <div class="main-col main-table">
                <c:choose>
                    <c:when test="${requestScope.hasData}">
                        <div id="remoteDataGrid" class="datagrid-borderless datagrid-striped" style="background:#efefef;width:100%;table-layout:fixed;display:table;"></div>
                        <%@ include file="/WEB-INF/paginationPage.jsp"%>
                    </c:when>
                    <c:otherwise>
                        <div class="table-empty-tip">
                            <p>
                                <span class="text-muted">抱歉,暂时展示没有任何数据。</span>
                            </p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    var categoryId;
    function pageReady(doc){
        <c:if test="${requestScope.hasData}">
        ${requestScope.tableScript}
        </c:if>
    }
    function dataLoadSuccess(query){
        var searchForm=$("#query_search_from").formToJson();
        postJSON("${managerPath}/statistics/accountRechargeCount${suffix}", searchForm, "正在处理请稍后", function (result) {
            var flowStatistics=result.flowStatistics;
            $(".extar_oper_container").html("累计充值金额:<span class='text-red'>"+flowStatistics.totalAmount+"</span>");
        })
    }
</script>
</body>
</html>