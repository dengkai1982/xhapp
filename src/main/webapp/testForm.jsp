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
        queryDataOfOrderBySetUrl("${contextPath}/app/query/pagination${suffix}","questionFavoritesService",
            [{
                field:"question",
                condition:"0",
                value:"15689496813820040"
            },{
                field:"account",
                condition:"0",
                value:"15688503905390085",
                link:LINK_AND
            }],0,100,"createTime","ase",function(d){
                console.log(d);
            })
    </script>
</body>
</html>
















