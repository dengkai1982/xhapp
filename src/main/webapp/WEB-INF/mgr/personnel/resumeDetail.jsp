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
        <div class="form_container">
            <%@include file="/WEB-INF/mgr/access/newOrEditBaseTitle.jsp"%>
            <div class="detail">
                <div class="detail-title">简历详情<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <%@include file="/WEB-INF/mgr/access/entityDetail.jsp"%>
                </div>
                <div class="detail-title">身份证<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <img src="${managerPath}/access/accessStorageFile${suffix}?hex=${entity.idcardFront}" style="height:220px;margin-right:20px;">
                    <img src="${managerPath}/access/accessStorageFile${suffix}?hex=${entity.idcardBack}" style="height:220px;">
                </div>
                <div class="detail-title">工作经历<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>公司名称</th>
                                <th>职务</th>
                                <th>开始时间</th>
                                <th>结束时间</th>
                                <th>公司名称</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.workExperiences}" var="work">
                                <tr>
                                    <td>${work.company}</td>
                                    <td>${work.job}</td>
                                    <td><fmt:formatDate value="${work.startTime}" pattern="yyyy-MM-dd"/></td>
                                    <c:choose>
                                        <c:when test="${work.current}">
                                            <td>至今</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td><fmt:formatDate value="${work.endTime}" pattern="yyyy-MM-dd"/></td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <%@include file="/WEB-INF/mgr/access/detailPageButton.jsp"%>
            </div>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    function pageReady(doc) {

    }
</script>
</body>
</html>