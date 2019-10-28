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
                <div class="detail-title">再答考试详情<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <%@include file="/WEB-INF/mgr/access/entityDetail.jsp"%>
                </div>
                <div class="detail-title">试题项<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>试题题目</th>
                            <th style="width:200px;">类型</th>
                            <th style="width:200px;">分值</th>
                            <th style="width:200px;">分类</th>
                            <th style="width:200px;">模拟分类</th>
                            <th style="width:200px;">考点</th>
                            <th style="width:200px;">参考答案</th>
                            <th style="width:200px;">回答答案</th>
                            <th style="width:200px;">完成时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.items}" var="item">
                            <tr>
                                <td>${item.detail}</td>
                                <td>${item.questionType.value}</td>
                                <td>${item.score}</td>
                                <td>${item.category}</td>
                                <td>${item.simulationCategory}</td>
                                <td>${item.ascriptionType}</td>
                                <td>${item.standardAnswer}</td>
                                <td>${item.resultAnswer}</td>
                                <td><fmt:formatDate value="${item.finishTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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