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
                    <a href='javascript:void(0)' id="exportTableToExcel" title="课程问题表" class="btn btn-link"><i class="icon-import muted"> </i> 导出数据表</a>
                </c:if>
            </div>
        </div>
        <c:if test="${requestScope.hasData}">
            ${requestScope.searchHtml}
        </c:if>
        <div id="mainContent">
            <c:choose>
                <c:when test="${requestScope.hasData}">
                    <div id="remoteDataGrid" class="datagrid-borderless datagrid-striped" style="background:#efefef;width:100%;table-layout:fixed;display:table;"></div>
                    <%@ include file="/WEB-INF/paginationPage.jsp"%>
                </c:when>
                <c:otherwise>
                    <div class="table-empty-tip">
                        <p>
                            <span class="text-muted">抱歉,展示没有任何数据。</span>
                        </p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    function pageReady(doc){
        <c:if test="${requestScope.hasData}">
        ${requestScope.tableScript}
        </c:if>
    }
    function createMenuItems(dataId,dataRow,data){
        var items = [{
            url:"${managerPath}/curriculum/courseProblem/detail${suffix}?entityId="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage(),
            label:"查看详情"
        }]
        if(data.answer.name=="false"){
            items.push({
                url:"${managerPath}/curriculum/courseProblem/reply${suffix}?entityId="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage(),
                label:"问题回复",
                className:"privilege",
                access:"/mgr/curriculum/courseProblem/reply"
            });
        }
        checkPrivilege(items);
        return items;
    };
    function customDataConvertCell(valueType,dataValue,cell, dataGrid){
        if(cell.colIndex==1){
            return cell.config.data.submitter.showAccountName
        }else if(cell.colIndex==2){
            return cell.config.data.course.name
        }
        return null
    }
</script>
</body>
</html>