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
                </c:if>
                <c:if test="${requestScope.hasParent}">
                    <a href="${webPage.backPage}" type="button" class="btn btn-link"><i class="icon-back muted"></i> 返回</a>
                </c:if>
                <visit:auth url="${webPage.newEntityPage}">
                    <a href="${contextPath}${webPage.newEntityPage}${suffix}?${paginationCurrentPage}=1&parent=${requestScope.parent}" class="btn btn-primary"><i class="icon icon-plus"></i> 新增${requestScope.entityShowName}</a>
                </visit:auth>
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
                            <visit:auth url="${webPage.newEntityPage}">
                                <span class="text-muted">您现在可以 </span>
                                <a href="${contextPath}${webPage.newEntityPage}${suffix}?${paginationCurrentPage}=1&parent=${requestScope.parent}" class="btn btn-info"><i class="icon icon-plus"></i> 新增${requestScope.entityShowName}</a>
                            </visit:auth>
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
            url:"${managerPath}/examination/simulationCategory${suffix}?pageNumber=${requestScope.pageNumber}&parent="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage(),
            label:"查看下级"
        },{
            url:"${contextPath}${webPage.modifyEntityPage}${suffix}?pageNumber=${requestScope.pageNumber}&entityId="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage(),
            label:"编辑分类",
            className:"privilege",
            access:"${webPage.modifyEntityPage}"
        }];
        checkPrivilege(items);
        return items;
    };
</script>
</body>
</html>