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
        <div style="padding: 10px 0; margin: 0 auto; text-align: center;"><a href="http://www.xinhongapp.cn/xhapp.apk"><img
                src="${contextPath}/images/az_down.png" width="200" height="48"/></a></div>
        <div style="padding: 10px 0; margin: 0 auto; text-align: center;"><a href="@"><img
                src="${contextPath}/images/pg_down.png" width="200" height="48"/></a></div>
    </div>
</div>
<script src="${contextPath}/js/mui.min.js"></script>
<script src="${contextPath}/js/app.js"></script>
<script type="text/javascript">
    function is_weixin() {
        var ua = navigator.userAgent.toLowerCase();
        if (ua.match(/MicroMessenger/i) == "micromessenger") {
            return true;
        } else {
            return false;
        }
    }
    var isWeixin = is_weixin();
    var winHeight = typeof window.innerHeight != 'undefined' ? window.innerHeight : document.documentElement.clientHeight;
    console.log(winHeight);
    function loadHtml(){
        var div = document.createElement('div');
        div.id = 'weixin-tip';
        div.innerHTML = '<p><img src="${contextPath}/images/live_weixin.png" alt="微信打开"/></p>';
        document.body.appendChild(div);
    }

    function loadStyleText(cssText) {
        var style = document.createElement('style');
        style.rel = 'stylesheet';
        style.type = 'text/css';
        try {
            style.appendChild(document.createTextNode(cssText));
        } catch (e) {
            style.styleSheet.cssText = cssText; //ie9以下
        }
        var head=document.getElementsByTagName("head")[0]; //head标签之间加上style样式
        head.appendChild(style);
    }
    var cssText = "#weixin-tip{position: fixed; left:0; top:0; background: rgba(0,0,0,0.3); filter:alpha(opacity=30); width: 100%; height:100%; z-index: 100;} #weixin-tip p{text-align: center;}";
    if(isWeixin){
        loadHtml();
        loadStyleText(cssText);
    }
</script>
</body>

</html>