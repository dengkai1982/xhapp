<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<footer id="footer">
    <div class="container">
        <ul class="breadcrumb">
            <c:forEach items="${webPage.navigations}" var="nav">
                <c:choose>
                    <c:when test="${nav.active}">
                        <li><a href="#" class="active">${nav.name}</a></li>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${empty nav.path}">
                                <li><a href="${managerPath}/access/chooseFirstMenu${suffix}?entityId=${nav.accessId}">${nav.name}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="${contextPath}${nav.path}${suffix}">${nav.name}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>
        <div id="poweredBy">
            <span>${ecoInfo}</span>
        </div>
    </div>
</footer>