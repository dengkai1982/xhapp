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
            <form id="editor_form" data-form-name="${webPage.pageTitle}" class="form-horizontal" method="post"
                  action="${contextPath}${webPage.commitEntityAction}${suffix}">
                <%@include file="/WEB-INF/mgr/access/newOrEditForm.jsp"%>
                <%@include file="/WEB-INF/mgr/access/formCommitButton.jsp"%>
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