<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html class="ui-page-login">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title></title>
    <link href="${contextPath}/css/mui.min.css" rel="stylesheet" />
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
        .mui-input-row label~input,
        .mui-input-row label~select,
        .mui-input-row label~textarea {
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
    <h1 class="mui-title">会员注册</h1>
</header>
<div class="mui-content">
    <div style="margin: 50px auto; text-align: center;"><img src="${contextPath}/images/logo.png" width="30%" height="auto" /></div>
    <form class="mui-input-group">
        <input type="hidden" id="recommendId" name="recommendId" value="${recommend.entityId}"/>
        <div class="mui-input-row">
            <label>手机号码</label>
            <input id='phoneNumber' type="text" class="mui-input" placeholder="请输入账号">
        </div>
        <div class="mui-input-row validateCodeConotainer">
            <label>验证码</label>

            <span style="position: absolute;right:2%;padding-top: 10px; padding-right: 10px;">
                <button type="button" id="sendCode">获取验证码</button>
            </span>
            <input id='validateCode' type="number" class="mui-input" placeholder="请输入验证码" style="width: 40%; float: left;">
        </div>
        <div class="mui-input-row">
            <label>输入密码</label>
            <input id='password' type="password" class="mui-input" placeholder="请输入密码">
        </div>
        <div class="mui-input-row">
            <label>确认密码</label>
            <input id='password_confirm' type="password" class="mui-input" placeholder="请确认密码">
        </div>

    </form>
    <div class="mui-content-padded">
        <button id='reg' class="mui-btn mui-btn-block mui-btn-primary">立即注册</button>
    </div>
</div>
<script src="${contextPath}/js/mui.min.js"></script>
<script src="${contextPath}/js/app.js"></script>
<script>
    var codeTime=60;
    mui.init();
    mui(".mui-content-padded").on("tap","#reg",function(){
        var recommendId=document.getElementById("recommendId").value;
        var phoneNumber=document.getElementById("phoneNumber").value;
        var validateCode=document.getElementById("validateCode").value;
        var password=document.getElementById("password").value;
        var password_confirm=document.getElementById("password_confirm").value;
        if(recommendId==""){
            mui.toast("页面来源错误");
            return;
        }
        if(phoneNumber==""){
            mui.toast("电话号码必须填写");
            return;
        }
        if(validateCode==""){
            mui.toast("验证码必须填写");
            return;
        }
        if(password==""||password!=password_confirm){
            mui.toast("两次密码不一致");
            return;
        }
        mui.ajax("${contextPath}/app/account/register.xhtml",{
            data:{
                phone:phoneNumber,
                password:password,
                recommendId:recommendId,
                validateCode:validateCode
            },
            dataType:'json',//服务器返回json格式数据
            type:'post',//HTTP请求类型
            success:function(data){
                if(data.code=="success"){
                    window.location.href="${contextPath}/registSuccess.jsp"
                }else{
                    mui.toast(data.msg)
                }
            }
        });
    })
    mui(".validateCodeConotainer").on("tap","#sendCode",function(){
        var phoneNumber=document.getElementById("phoneNumber");
        if(phoneNumber.value!=""){
            mui.ajax("${contextPath}/app/account/sendShorMessage.xhtml",{
                data:{
                    phone:phoneNumber.value,
                    usage:"用户注册"
                },
                dataType:'json',//服务器返回json格式数据
                type:'post',//HTTP请求类型
                success:function(data){
                    if(data.code=="success"){
                        changeCodeTime(document.getElementById("sendCode"));
                    }else{
                        mui.toast(data.msg)
                    }
                }
            });
        }else{
            mui.toast("手机号码必须填写")
        }
    });
    function changeCodeTime(tag){
        console.log(tag);
        if(codeTime==0){
            tag.innerHTML="发送验证码";
            tag.disabled=false;
            codeTime = 60;
        }else{
            tag.innerHTML=codeTime + "s后重新发送";
            tag.disabled=true;
            codeTime=codeTime-1;
            setTimeout(function(){
                changeCodeTime(tag);
            },1000);
        }
    }

</script>
</body>
</html>