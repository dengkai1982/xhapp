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
                <div class="detail-title">试卷详情<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <%@include file="/WEB-INF/mgr/access/entityDetail.jsp"%>
                </div>
                <div class="detail-title">包含试题<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <table class="table table-striped table-bordered table-hover" style="margin-top:10px">
                        <thead>
                        <tr>
                            <th>试题名称</th>
                            <th style="width:8%">试题类型</th>
                            <th style="width:8%">试题分值</th>
                            <th style="width:8%">显示权重</th>
                        </tr>
                        </thead>
                        <tbody id="answerListContainer">
                        <c:forEach items="${requestScope.testPagerQuestions}" var="question">
                            <tr data-id="${question.entityId}">
                                <td>${question.question.detail}</td>
                                <td>${question.question.questionType.value}</td>
                                <td>${question.score}</td>
                                <td>${question.weight}</td>
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