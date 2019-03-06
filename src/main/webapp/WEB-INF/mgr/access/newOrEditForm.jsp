<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<c:set var="entity" value="${requestScope.entity}"/>
<input type="hidden" name="entityId" value="${entity.entityId}">
<input type="hidden" name="popupSelectValueResult">
<c:forEach items="${requestScope.pageFieldDataLines}" var="dataLine">
    <div class="form-group">
    <c:forEach items="${dataLine.fieldDatas}" var="fieldData">
        <c:choose>
            <c:when test="${fieldData.require}">
                <label for="${fieldData.field.name}" class="col-sm-1 required">${fieldData.label}</label>
            </c:when>
            <c:otherwise>
                <label for="${fieldData.field.name}" class="col-sm-1">${fieldData.label}</label>
            </c:otherwise>
        </c:choose>
        <div data-column-type="${fieldData.type.fieldType.showName}" class="col-sm-${fieldData.showColumnSize}">
            <%@include file="/WEB-INF/mgr/access/formControl.jsp"%>
        </div>
    </c:forEach>
    </div>
</c:forEach>