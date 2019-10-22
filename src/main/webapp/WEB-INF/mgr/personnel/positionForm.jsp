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
            <div class="center-block">
                <div class="main-header">
                    <h2>${webPage.pageTitle} - 上级职位【${requestScope.parent.name}】</h2>
                    <div class="pull-right btn-toolbar">
                        <a href="javascript:void(0)" onclick="javascript:history.go(-1);return false;" type="button" class="btn btn-link"><i class="icon-back muted"></i> 返回</a>
                    </div>
                </div>
            </div>
            <form id="editor_form" data-form-name="${webPage.pageTitle}" class="form-horizontal" method="post"
                  action="${contextPath}${webPage.commitEntityAction}${suffix}">
                <input type="hidden" name="parent" value="${requestScope.parent.entityId}">
                <%@include file="/WEB-INF/mgr/access/newOrEditForm.jsp"%>
                <div style="text-align: center;">
                    <button type="button" id="formSubmitAction" class="btn btn-wide btn-primary">提交保存</button>
                    <a href="javascript:void(0)" onclick="javascript:history.go(-1);return false;"  class="btn btn-back btn-wide">返回</a>
                </div>
            </form>
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