<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/htmlHeader.jsp"%>
    <style>
        .photo_container{
            position: absolute;
            left:30px;
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
                <div class="detail-title">授课讲师详情<span class="icon icon-caret-down"></span></div>
                <div class="detail-content" style="margin-left:140px;">
                    <c:set var="teacher" value="${requestScope.entity}"/>
                    <table class="table table-data table-condensed table-borderless">
                        <tbody>
                        <tr>
                            <th class="strong">讲师姓名</th>
                            <td>${teacher.name}</td>
                        </tr>
                        <tr>
                            <th class="strong">联系电话</th>
                            <td>${teacher.phone}</td>
                        </tr>
                        <tr>
                            <th class="strong">职务</th>
                            <td>${teacher.duty}</td>
                        </tr>
                        <tr>
                            <th class="strong">主讲课程</th>
                            <td>${teacher.primaryCourse}</td>
                        </tr>
                        <tr>
                            <th class="strong">讲师简介</th>
                            <td>${teacher.detail}</td>
                        </tr>
                        </tbody>
                    </table>
                    <div class="photo_container">
                        <c:choose>
                            <c:when test="${empty requestScope.entity.photo}">
                                <img src="${contextPath}/img/default_header.png"/>
                            </c:when>
                            <c:otherwise>
                                <img src="${managerPath}/access/accessStorageFile${suffix}?hex=${requestScope.entity.photo}"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
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