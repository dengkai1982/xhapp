<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${contextPath}/app/account/changeAccountInfo${suffix}" method="post">
       id:<input type="text" name="entityId"/>
       name:<input type="text" name="nickName">
       <input type="submit" value="提交">
    </form>
</body>
</html>
