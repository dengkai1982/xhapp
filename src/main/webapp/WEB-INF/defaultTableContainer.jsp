<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<c:choose>
    <c:when test="${requestScope.hasData}">
        <div id="remoteDataGrid" class="datagrid-borderless datagrid-striped" style="background:#efefef;width:100%;table-layout:fixed;display:table;"></div>
        <%@ include file="/WEB-INF/paginationPage.jsp"%>
    </c:when>
    <c:otherwise>
        <div class="table-empty-tip">
            <p>
                <span class="text-muted">抱歉,展示没有任何数据。</span>
                <span class="text-muted">您现在可以 </span>
                <a href="${contextPath}${webPage.newEntityPage}${suffix}?${paginationCurrentPage}=1" class="btn btn-info"><i class="icon icon-plus"></i> 新增员工</a>
            </p>
        </div>
    </c:otherwise>
</c:choose>