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
                <div class="detail-title">提成发放详情<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <%@include file="/WEB-INF/mgr/access/entityDetail.jsp"%>
                </div>
                <%@include file="/WEB-INF/mgr/access/detailPageButton.jsp"%>
            </div>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<style>
    .table-data tbody>tr>th{
        width:140px;
    }
    .table-data tbody>tr>td{
        width:22%;
    }
</style>
<script type="text/javascript">
    function pageReady(doc) {

    }
</script>
</body>
</html>