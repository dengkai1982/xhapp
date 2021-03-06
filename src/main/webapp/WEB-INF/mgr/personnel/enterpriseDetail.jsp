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
            <input type="hidden" name="parentInsideAccount" value="${entity.parentInsideAccount.memberName}"/>
            <div class="detail">
                <div class="detail-title">企业详情<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <%@include file="/WEB-INF/mgr/access/entityDetail.jsp"%>
                </div>
                <div class="detail-title">营业执照<span class="icon icon-caret-down"></span></div>
                <c:choose>
                    <c:when test="${empty entity.licensePhoto}">
                        未上传
                    </c:when>
                    <c:otherwise>
                        <div class="detail-content">
                            <img src="${managerPath}/access/accessStorageFile${suffix}?hex=${entity.licensePhoto}" style="height:300px;">
                        </div>
                    </c:otherwise>
                </c:choose>
                <%@include file="/WEB-INF/mgr/access/detailPageButton.jsp"%>
            </div>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    function pageReady(doc) {
        var parentInsideAccount=$("input[name='parentInsideAccount']").val();
        if(parentInsideAccount!=""){
            $("td[fieldname='parentInsideAccount']").html(parentInsideAccount);
        }
    }
</script>
</body>
</html>