<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/htmlHeader.jsp"%>
    <style>
        .detail-content{
            height:200px;
            margin-left:100px;
        }
        .photo_container{
            position: absolute;
            left:60px;
            top:84px;
        }
    </style>
</head>
<body>
<%@include file="/WEB-INF/headerMenus.jsp"%>
<main id="main">
    <div class="container">
        <div class="form_container">
            <%@include file="/WEB-INF/mgr/access/newOrEditBaseTitle.jsp"%>
            <div class="detail">
                <div class="detail-title">会员详情<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <%@include file="/WEB-INF/mgr/access/entityDetail.jsp"%>
                </div>
                <div class="photo_container">
                    <c:choose>
                        <c:when test="${empty requestScope.entity.headerImage}">
                            <img src="${contextPath}/img/default_header.png"/>
                        </c:when>
                        <c:otherwise>
                            <img src="${managerPath}/access/accessStorageFile${suffix}?hex=${requestScope.entity.headerImage}"/>
                        </c:otherwise>
                    </c:choose>
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