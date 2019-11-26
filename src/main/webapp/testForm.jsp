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
        queryDataOfOrderBySetUrl("${contextPath}/app/query/pagination${suffix}","examQuestionService",
            [{
                field:"account",
                condition:"0",
                value:"15728382341210094"
            },{
                field:"resourceType",
                condition:"0",
                value:"2",
                link:"0"
            },{
                field:"referenceId",
                condition:"0",
                value:"15676710999355077",
                link:"0"
            }],0,100,"createTime","ase",function(d){
                console.log(d);
            })
    </script>
</body>
</html>
















