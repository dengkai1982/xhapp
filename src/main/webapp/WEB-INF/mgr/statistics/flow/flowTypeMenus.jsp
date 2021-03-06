<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<div class="side-col" id="sidebar">
    <div class="cell left_cell" style="overflow: visible; max-height: initial;">
        <div class="panel">
            <div class="panel-heading">
                <div class="panel-title">统计类型</div>
            </div>
            <div class="panel-body">
                <div class="list-group">
                    <c:forEach items="${requestScope.flowTypeChosen}" var="flowType">
                        <c:choose>
                            <c:when test="${flowType.selected}">
                                <a href="#" class="selected"><i class="icon icon-file-text"></i> ${flowType.html}</a>
                            </c:when>
                            <c:otherwise>
                                <a href="${managerPath}/statistics/flow${suffix}?flowType=${flowType.value}"><i class="icon icon-file-text"></i> ${flowType.html}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>