<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/htmlHeader.jsp"%>
</head>
<body>
<%@include file="/WEB-INF/headerMenus.jsp"%>
<main id="main">
    <div class="container">
        <div id="mainMenu" class="clearfix">
            <div class="btn-toolbar pull-right">
                <c:if test="${requestScope.hasData}">
                    <a href="#" class="btn btn-link querybox-toggle" id="show_or_hide_search"><i class="icon-search icon"></i> 搜索</a>
                </c:if>
                <visit:auth url="${webPage.newEntityPage}">
                    <a href="${contextPath}${webPage.newEntityPage}${suffix}?${paginationCurrentPage}=1" class="btn btn-primary"><i class="icon icon-plus"></i> 新增${requestScope.entityShowName}</a>
                </visit:auth>
            </div>
        </div>
        <c:if test="${requestScope.hasData}">
            ${requestScope.searchHtml}
        </c:if>
        <div id="mainContent">
            <c:choose>
                <c:when test="${requestScope.hasData}">
                    <div id="remoteDataGrid" class="datagrid-borderless datagrid-striped" style="background:#efefef;width:100%;table-layout:fixed;display:table;"></div>
                    <%@ include file="/WEB-INF/paginationPage.jsp"%>
                </c:when>
                <c:otherwise>
                    <div class="table-empty-tip">
                        <p>
                            <span class="text-muted">抱歉,展示没有任何数据。</span>
                            <span class="text-muted">您现在可以 </span>
                            <a href="${contextPath}${webPage.newEntityPage}${suffix}?${paginationCurrentPage}=1" class="btn btn-info"><i class="icon icon-plus"></i> 新增${requestScope.entityShowName}</a>
                        </p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</main>
<div class="modal fade" id="changeMemberShipModal">
    <div class="modal-dialog" style="width:520px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title" >修改会员等级</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="changeMemberShipForm">
                    <input type="hidden" name="entityId" id="accountId"/>
                    <div class="form-group">
                        <label for="memberShip" class="col-sm-2 required">会员等级</label>
                        <div class="col-sm-9 ref_memberShip">
                            <select name="memberShip" id="memberShip"  class='form-control chosen-select'>
                                <c:forEach items="${requestScope.memberShips}" var="sl">
                                <option value='${sl.value}' key="${sl.dataKeys}">${sl.html}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">取消返回</button>
                <button type="button" class="btn btn-danger" id="changeMemberShipAction">确定</button>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    function pageReady(doc){
        <c:if test="${requestScope.hasData}">
        ${requestScope.tableScript}
        </c:if>
        $("#changeMemberShipAction").click(function(){
            var $form=$("#changeMemberShipForm").formToJson();
            postJSON("${managerPath}/account/account/changeMemberShip${suffix}",{
                entityId:$form.entityId,
                memberShip:$form.memberShip
            },"正在执行,请稍后...",function(result){
                if(result.code==SUCCESS){
                    $("#changeMemberShipModal").modal("hide");
                    bootbox.alert({
                        title:"消息",
                        message: "修改会员类型成功,点击确认返回",
                        callback: function () {
                            reflashPageData();
                        }
                    })
                }else{
                    showMessage(result.msg,1500);
                }
            });
        })
    }
    function createMenuItems(dataId,dataRow,data){
        var items = [{
            url:"${contextPath}${webPage.detailEntityPage}${suffix}?entityId="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage(),
            label:"查看详情"
        },{
            label:"重置密码",
            url:"#"+dataId,
            className:"privilege",
            access:"/mgr/account/account/resetPasswd",
            onClick:function(e){
                confirmOper("消息","将重置账号密码为【123456】，是否继续?",function(){
                    postJSON("${managerPath}/account/account/resetPasswd${suffix}",{
                        entityId:dataId
                    },"正在执行,请稍后...",function(result){
                        if(result.code==SUCCESS){
                            bootbox.alert({
                                title:"消息",
                                message: "重置账户密码成功,点击确认返回",
                                callback: function () {
                                    reflashPageData();
                                }
                            })
                        }else{
                            showMessage(result.msg,1500);
                        }
                    });
                })
            }
        },{
            label:"修改会员等级",
            url:"#"+dataId,
            className:"privilege",
            access:"/mgr/account/account/changeMemberShip",
            onClick:function(e){
                var memberShip=data.memberShip.ordinal;
                $("#changeMemberShipModal #accountId").val(data.entityId);
                $("#changeMemberShipModal #memberShip").val(memberShip);
                $("#changeMemberShipModal").modal("show");
                $("#changeMemberShipModal #memberShip").trigger('chosen:updated');
            }
        }];
        var insideMember="";
        if(data.insideMember.ordinal=="true"){
            insideMember="取消内部会员";
        }else{
            insideMember="标记内部会员";
        }
        var active="";
        if(data.active.ordinal=="true"){
            active="解锁会员";
        }else{
            active="冻结会员";
        }
        items.push({
            label:insideMember,
            className:"privilege",
            access:"/mgr/account/account/changeInsideMember",
            onClick:function(e){
                postJSON("${managerPath}/account/account/changeInsideMember${suffix}",{
                    entityId:dataId
                },"正在执行,请稍后...",function(result){
                    if(result.code==SUCCESS){
                        bootbox.alert({
                            title:"消息",
                            message: insideMember+"成功,点击确认返回",
                            callback: function () {
                                reflashPageData();
                            }
                        })
                    }else{
                        showMessage(result.msg,1500);
                    }
                });
            }
        });
        items.push({
            label:"设置员工姓名",
            className:"privilege",
            access:"/mgr/account/account/setMemberName",
            onClick:function(){
                bootbox.prompt("请输入员工姓名", function(result){
                    if(result!=""){
                        postJSON("${managerPath}/account/account/setMemberName${suffix}",{
                            entityId:dataId,
                            memberName:result
                        },"正在执行,请稍后...",function(result){
                            if(result.code==SUCCESS){
                                bootbox.alert({
                                    title:"消息",
                                    message: "设置员工姓名成功,点击确认返回",
                                    callback: function () {
                                        reflashPageData();
                                    }
                                })
                            }else{
                                showMessage(result.msg,1500);
                            }
                        });
                    }
                });
            }
        });
        items.push({
            label:active,
            className:"privilege",
            access:"/mgr/account/account/changeActive",
            onClick:function(e){
                postJSON("${managerPath}/account/account/changeActive${suffix}",{
                    entityId:dataId
                },"正在执行,请稍后...",function(result){
                    if(result.code==SUCCESS){
                        bootbox.alert({
                            title:"消息",
                            message: active+"成功,点击确认返回",
                            callback: function () {
                                reflashPageData();
                            }
                        })
                    }else{
                        showMessage(result.msg,1500);
                    }
                });
            }
        });
        checkPrivilege(items);
        return items;
    };
</script>
</body>
</html>