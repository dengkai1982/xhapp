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
                <div class="detail-title">试题详情<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <%@include file="/WEB-INF/mgr/access/entityDetail.jsp"%>
                </div>
                <c:if test="${entity.questionType.showName!='QuestionsAndAnswers'}">
                <div class="detail-title">试题选项<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <table class="table table-striped table-bordered table-hover" style="margin-top:10px">
                        <thead>
                        <tr>
                            <th style="width: 10%">答案编号</th>
                            <th>答案描述</th>
                        </tr>
                        </thead>
                        <tbody id="answerListContainer">
                        <c:forEach items="${entity.choiceAnswerStream}" var="answer">
                            <tr data-id="${answer.entityId}">
                                <td class="optionName">${answer.optionName}</td>
                                <c:choose>
                                    <c:when test="${answer.imageType}">
                                        <td class="detailValue"><img style="height:60px" hex="${answer.detailValue}" src="${managerPath}/access/accessStorageFile${suffix}?hex=${answer.detailValue}"/></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="detailValue">${answer.detailValue}</td>
                                    </c:otherwise>
                                </c:choose>

                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                </c:if>
                <%@include file="/WEB-INF/mgr/access/detailPageButton.jsp"%>
            </div>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    function pageReady(doc) {
        $("td[fieldName='detail'],td[fieldName='analysis']").attr("colspan","6");
    }
</script>
</body>
</html>