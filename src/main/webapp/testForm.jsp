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
    <script type="text/javascript">
        queryDataOfOrderBySetUrl("${contextPath}/app/query/pagination${suffix}","positionService",
            [{
                field:"level",
                condition:Condition_EQUAL,
                value:0
            }],0,100,"name","asc",function(d){
                console.log(d);
            })
    </script>
</body>
</html>
