<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<html>
<head>
    <title>Title</title>
    <%@include file="/WEB-INF/htmlHeader.jsp"%>
</head>
<body>
<h1>测试</h1>
<script src="${contextPath}/js/app.js" type="text/javascript"></script>
<%--
<form action="${contextPath}/app/curriculum/generatorOrder${suffix}" method="post">
    &nbsp;课程ID：<input type="text" name="courseIdArray"/><br>
    &nbsp;账户ID：<input type="text"name="accountId"/><br>
    &nbsp;付款类型：<input type="text"name="capitalType"/><br>
    &nbsp;课：<input type="submit" name="subimt"><br>
</form>
--%>
<form action="${contextPath}/app/account/resetAccountPassword${suffix}" method="post">
    <input type="hidden" name="phone" value="13350106164"/>
    <input type="hidden" name="validateCode" value="902120"/>
    <input type="hidden" name="password" value="abcd1234"/>
    <input type="hidden" name="confirm_password" value="abcd1234"/>
    <input type="submit" name="subimt"><br>
</form>
</body>
</html>