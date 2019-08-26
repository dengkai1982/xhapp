<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<style>
    .table-data tbody>tr>th{
        width:120px;
    }
    .table-data tbody>tr>td{
        width:25%;
    }
</style>
<table class="table table-data table-condensed table-borderless">
    <tbody>
    <c:forEach items="${requestScope.pageFieldDataLines}" var="dataLine">
        <tr>
            <c:forEach items="${dataLine.fieldDatas}" var="fieldData">
                <th class="strong" name="${fieldData.field.name}">${fieldData.label}</th>
                <td  fieldName="${fieldData.field.name}">
                    <c:choose>
                        <c:when test="${fieldData.type.fieldType.showName=='CHOSEN'||fieldData.type.fieldType.showName=='BOOLEAN'}">
                            <c:forEach items="${fieldData.value}" var="sl">
                                <c:choose>
                                    <c:when test="${sl.selected}">
                                        ${sl.html}
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </c:when>
                        <c:when test="${fieldData.type.fieldType.showName=='REFERENCE'}">
                            <c:choose>
                                <c:when test="${fieldData.type.pupupSelect}">
                                    ${fieldData.value.html}
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${fieldData.value}" var="fv">
                                        <c:if test="${fv.selected}">
                                            ${fv.html}
                                        </c:if>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:when test="${fieldData.type.fieldType.showName=='AREATEXT'}">
                            <htmlc:src value="${fieldData.value}"/>
                        </c:when>
                        <c:otherwise>
                            ${fieldData.value}
                        </c:otherwise>
                    </c:choose>
                </td>
            </c:forEach>
        </tr>
    </c:forEach>
    </tbody>
</table>