<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/htmlHeader.jsp"%>
        <style>
            body{
                margin-top:12%;
                text-align: center;
            }
            .loginbox{
                width:32%;
                margin-left: auto;
                margin-right: auto;
            }
            .loginbox .panel-body{
                text-align: left;
                color:#353535;
            }
            .loginbox h2,.loginbox h3{
                margin:10px;
            }
            .loginbox .form_panel{
                padding:0 10px;
            }
            .loginbox label{
                margin-bottom:0px;
                margin-top:5px;
                font-weight:normal;
            }
            #login_action{
                width:100%;
            }
            .input-group .form-control{
                height:40px;
            }
            .input-group-addon .icon{
                color:#1a1a1a;
                font-weight: normal;
            }
        </style>
    </head>
    <body class="bg-primary">
    <div class="panel loginbox box-shadow">
        <div class="panel-body">
            <h3 class="text-center">后台管理登录</h3>
            <form class="form_panel" id="login_form">
                <div id="ref_loginName" class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="icon icon-person"></i></span>
                        <input type="text" class="form-control" validate="required:账户必须填写" name="username" placeholder="请输入登录账号">
                    </div>
                </div>
                <div id="ref_password" class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="icon icon-lock"></i></span>
                        <input type="password" validate="required:密码必须填写" name="password"  class="form-control" placeholder="请输入登录密码">
                    </div>
                </div>
                <div class="form-group">
                    <span class="text-red" id="error_hint"></span>
                </div>
                <div class="form-group">
                    <button id="login_action" type="button" class="btn btn-primary btn-lg">立即登录</button>
                </div>
            </form>
        </div>
    </div>
    <!--
    <div>${ecoInfo}</div>
    <div>技术支持</div>-->
    <script type="text/javascript">
        function pageReady(doc){
            $("#login_action").click(function(){
                $("#error_hint").html("");
                var flag=$("#login_form").formValidate(function(el,hint){
                    toast(hint);
                    $("#ref_"+el.attr("name")).addClass("check-error");
                    el.focus(function(){
                        $("#ref_"+el.attr("name")).removeClass("check-error");
                    })
                });
                if(flag){
                    var data=$("#login_form").formToJson();
                    postJSON("${managerPath}/access/login${suffix}",data,"正在登录请稍后...",function(result){
                        if(result.code==SUCCESS){
                            //console.log(result.body);
                            showBlock("正在进入请稍后");
                            window.location.href=result.body;
                        }else{
                            $("#error_hint").html(result.msg);
                        }
                    });
                }
            });
        }
    </script>
    </body>
</html>