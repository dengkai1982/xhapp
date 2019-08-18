<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html class="ui-page-login">

<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title></title>
    <link href="${contextPath}/css/mui.min.css" rel="stylesheet"/>
    <style>
        .area {
            margin: 20px auto 0px auto;
        }

        .mui-input-group:first-child {
            margin-top: 20px;
        }

        .mui-input-group label {
            width: 30%;
        }

        .mui-input-row label ~ input,
        .mui-input-row label ~ select,
        .mui-input-row label ~ textarea {
            width: 70%;
        }

        .mui-checkbox input[type=checkbox],
        .mui-radio input[type=radio] {
            top: 6px;
        }

        .mui-content-padded {
            margin-top: 25px;
        }

        .mui-btn {
            padding: 10px;
        }

    </style>
</head>

<body>
<header class="mui-bar mui-bar-nav">

    <h1 class="mui-title">会员注册成功</h1>
</header>
<div class="mui-content">
    <div style="margin: 30px auto; text-align: center;"><img src="${contextPath}/images/success.png" width="174"
                                                             height="192"/></div>
    <div class="mui-content-padded"></div>
    <div class="mui-content-padded">
        <div style="padding: 10px 0; margin: 0 auto; text-align: center; font-size:22px">您已经成功注册为职猿会员！</div>
    </div>
    <div class="mui-content-padded">
        <div style="padding: 10px 0; margin: 0 auto; text-align: center;">点击下方图标下载安装</div>
    </div>
    <div class="mui-content-padded">
        <div style="padding: 10px 0; margin: 0 auto; text-align: center;"><a href="${contextPath}/az_down.jsp"><img
                src="${contextPath}/images/az_down.png" width="200" height="48"/></a></div>
        <div style="padding: 10px 0; margin: 0 auto; text-align: center;"><a href="${contextPath}/down.jsp"><img
                src="${contextPath}/images/pg_down.png" width="200" height="48"/></a></div>
    </div>
</div>
<script src="${contextPath}/js/mui.min.js"></script>
<script src="${contextPath}/js/app.js"></script>
</body>

</html>