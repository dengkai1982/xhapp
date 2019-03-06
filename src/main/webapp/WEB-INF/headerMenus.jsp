<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<div class="modal fade" id="change_my_password_modal">
    <div class="modal-dialog" style="width: 420px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">修改密码</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="change_password_form">
                    <div class="form-group">
                        <label for="oldPassword" style="float:left;width:70px;">旧密码</label>
                        <input type="password" style="float:left;width:270px;" class="form-control" name="oldPassword" id="oldPassword" placeholder="请输入旧密码">
                    </div>
                    <div class="form-group">
                        <label for="oldPassword" style="float:left;width:70px;">新密码</label>
                        <input type="password" style="float:left;width:270px;" class="form-control" name="newPassword" id="newPassword1" placeholder="请输入新密码">
                    </div>
                    <div class="form-group">
                        <label for="oldPassword" style="float:left;width:70px;">确认密码</label>
                        <input type="password" style="float:left;width:270px;" class="form-control" name="donePassword" id="newPassword2" placeholder="请再次确认新密码">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">取消返回</button>
                <button type="button" class="btn btn-danger" id="save_change_password">立即修改</button>
            </div>
        </div>
    </div>
</div>
<header id="header" class="box_shadow">
    <div id="mainHeader">
        <div class="container">
            <div id="heading">
                <h1 id="companyname" title="${system_name}">${system_name}</h1>
            </div>
            <nav id="navbar">
                <ul class="nav nav-default">
                    <c:forEach items="${managerMenus}" var="mainMenu">
                        <c:if test="${mainMenu.showable}">
                            <c:choose>
                                <c:when test="${mainMenu.active}">
                                    <li class="active"><a  href="#">${mainMenu.name}</a></li>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${empty mainMenu.actionFlag}">
                                            <li><a  href="${managerPath}/access/chooseFirstMenu${suffix}?entityId=${mainMenu.entityId}">${mainMenu.name}</a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a href="${contextPath}${mainMenu.actionFlag}${suffix}">${mainMenu.name}</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:forEach>
                </ul>
            </nav>
            <div id="toolbar">
                <div id="userMenu">
                    <ul id="userNav" class="nav nav-default">
                        <li>
                            <a class="dropdown-toggle" data-toggle="dropdown"><span class="user-name">${managerUser.loginName}</span><span class="caret"></span></a>
                            <ul class="dropdown-menu pull-right">
                                <li class="dropdown-submenu">
                                    <a href="javascript:;">主题</a>
                                    <ul class="dropdown-menu pull-left">
                                        <li class="selected">
                                            <a class="changeThemeAction" href='javascript:void(0)' data-value="classic">经典</a>
                                        </li>
                                        <li>
                                            <a class="changeThemeAction" href='javascript:void(0)' data-value="default">蔚蓝</a>
                                        </li>
                                        <li>
                                            <a class="changeThemeAction" href='javascript:void(0)' data-value="green">叶兰绿</a>
                                        </li>
                                        <li>
                                            <a class="changeThemeAction" href='javascript:void(0)' data-value="red">赤诚红</a>
                                        </li>
                                        <li>
                                            <a class="changeThemeAction" href='javascript:void(0)' data-value="purple">玉烟紫</a>
                                        </li>
                                        <li>
                                            <a class="changeThemeAction" href='javascript:void(0)' data-value="pink">芙蕖粉</a>
                                        </li>
                                        <li>
                                            <a class="changeThemeAction" href='javascript:void(0)' data-value="black">黑莓</a>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <a href="#" data-toggle="modal" data-target="#change_my_password_modal">修改密码</a>
                                </li>
                                <li>
                                    <a href="javascript:void(0)" id="exitUserLogin">退出登录</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div id="subHeader">
        <div class="container">
            <nav id="subNavbar">
                <ul class="nav nav-default" style="position:static;">
                    <c:forEach items="${managerMenus}" var="mainMenu" varStatus="vs">
                        <c:if test="${mainMenu.active}">
                            <c:forEach items="${mainMenu.childrenList}" var="subMenu" varStatus="varStatus">
                                <c:if test="${subMenu.showable}">
                                    <c:choose>
                                        <c:when test="${varStatus.index==10}">
                                            <li class="dropdown dropdown-hover " data-id="list">
                                                <a href="#">更多<span class="caret"></span></a>
                                                <ul class="dropdown-menu">
                                            <c:choose>
                                                <c:when test="${subMenu.active}">
                                                    <li class="active"><a href="${contextPath}${subMenu.actionFlag}${suffix}" title="${subMenu.detail}">${subMenu.name}</a></li>
                                                </c:when>
                                                <c:when test="${empty subMenu.actionFlag}">
                                                    <li><a  href="${managerPath}/access/chooseFirstMenu${suffix}?entityId=${subMenu.entityId}">${subMenu.name}</a></li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li><a href="${contextPath}${subMenu.actionFlag}${suffix}" title="${subMenu.detail}">${subMenu.name}</a></li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:when test="${varStatus.index>=10&&varStatus.last}">
                                            <c:choose>
                                                <c:when test="${subMenu.active}">
                                                    <li class="active"><a href="${contextPath}${subMenu.actionFlag}${suffix}" title="${subMenu.detail}">${subMenu.name}</a></li>
                                                </c:when>
                                                <c:when test="${empty subMenu.actionFlag}">
                                                    <li><a  href="${managerPath}/access/chooseFirstMenu${suffix}?entityId=${subMenu.entityId}">${subMenu.name}</a></li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li><a href="${contextPath}${subMenu.actionFlag}${suffix}" title="${subMenu.detail}">${subMenu.name}</a></li>
                                                </c:otherwise>
                                            </c:choose>
                                                </ul>
                                                </li>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${subMenu.active}">
                                                    <li class="active"><a href="${contextPath}${subMenu.actionFlag}${suffix}" title="${subMenu.detail}">${subMenu.name}</a></li>
                                                </c:when>
                                                <c:when test="${empty subMenu.actionFlag}">
                                                    <li><a  href="${managerPath}/access/chooseFirstMenu${suffix}?entityId=${subMenu.entityId}">${subMenu.name}</a></li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li><a href="${contextPath}${subMenu.actionFlag}${suffix}" title="${subMenu.detail}">${subMenu.name}</a></li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </c:forEach>
                </ul>
            </nav>
        </div>
    </div>
</header>
<c:forEach items="${childAutors}" var="author">
    <div class="hidden subAuthors" data-value="${author.menu.entityId}" visit="${author.visit}"></div>
</c:forEach>
<script type="text/javascript">
    $("#save_change_password").click(function(){
        var $form=$("#change_password_form").formToJson();
        if($form.oldPassword==""){
            bootbox.alert("旧密码必须填写");
            return;
        }
        if($form.newPassword==""){
            bootbox.alert("新密码必须填写");
            return;
        }
        if($form.newPassword!=$form.donePassword){
            bootbox.alert("两次密码不一致");
            return;
        }
        postJSON("${contextPath}/mgr/access/changePassword${suffix}",{
            oldPassword:$form.oldPassword,
            newPassword:$form.newPassword
        },"正在修改密码请稍后",function(result){
            if(result.code==SUCCESS){
                bootbox.alert({
                    title:"消息",
                    message: "修改密码成功,点击确认关闭",
                    callback: function () {
                        $("#change_my_password_modal").modal('hide');
                    }
                })
            }else{
                bootbox.alert({
                    title:"错误",
                    message:result.msg,
                    callback:function(){

                    }
                });
            }
        });
    });
    $('#change_my_password_modal').on('show.zui.modal', function() {
        document.getElementById("change_password_form").reset();
    });
    $("#exitUserLogin").click(function(){
        bootbox.confirm({
            title:'提示',
            message: "确实要退出本次登录",
            buttons: {
                confirm: {
                    label: '确定',
                    className: 'btn-success'
                },
                cancel: {
                    label: '取消',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                if(result){
                    postJSON("${contextPath}/mgr/access/exit${suffix}",{},"正在退出系统请稍后",function(data){
                        window.location.href="${contextPath}/login.jsp";
                    });
                }
            }
        });
    });
</script>