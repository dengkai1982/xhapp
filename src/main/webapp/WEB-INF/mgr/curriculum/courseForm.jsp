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
        <div class="form_container">
            <div class="center-block">
                <div class="main-header">
                    <h2>${webPage.pageTitle} - 【${requestScope.category.name}】</h2>
                    <div class="pull-right btn-toolbar">
                        <a href="${webPage.backPage}&expandId=${requestScope.category.entityId}" type="button" class="btn btn-link"><i class="icon-back muted"></i> 返回</a>
                    </div>
                </div>
            </div>
            <form id="editor_form" data-form-name="${webPage.pageTitle}" class="form-horizontal" method="post"
                  action="${contextPath}${webPage.commitEntityAction}${suffix}">
                <input type="hidden" name="category" value="${requestScope.category.entityId}"/>
                <div id="mainMenu" class="clearfix" style="margin-top: 5px;">
                    <div class="pull-left btn-toolbar custom-nav-tabs">
                        <a data-tab href="#tabContent1" class="btn btn-link btn-active-text"><span class="text">课程基本信息</span></a>
                        <a data-tab href="#tabContent2" class="btn btn-link"><span class="text">会员购买费用</span></a>
                    </div>
                </div>
                <div id="tabContent1" class="tabContent active">
                    <%@include file="/WEB-INF/mgr/access/newOrEditForm.jsp"%>
                </div>
                <div id="tabContent2" class="tabContent">
                    <c:choose>
                        <c:when test="${empty requestScope.memberShips}">
                            <c:forEach items="${requestScope.entity.privilegeStream}" var="privilege">
                                <div class="form-group buyerPrivileges" data-id="${privilege.memberShip.itemNumber}">
                                    <label class="col-sm-1">${privilege.memberShip.value}</label>
                                    <div class="col-sm-3">
                                        <select name="free" data-placeholder="请选择观看类型" class="chosen-select form-control">
                                            <c:choose>
                                                <c:when test="${privilege.free}">
                                                    <option value="false">付费观看</option>
                                                    <option value="true" selected>免费观看</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="false" selected>付费观看</option>
                                                    <option value="true">免费观看</option>
                                                </c:otherwise>
                                            </c:choose>

                                        </select>
                                    </div>
                                    <label class="col-sm-1">课程金额</label>
                                    <div  class="col-sm-2">
                                        <div class="input-group">
                                            <span class="input-group-addon">￥</span>
                                            <input type="number" name="memberShipPrice" value="<currency:convert value='${privilege.price}'/>" class="form-control" placeholder="请输入金额">
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${requestScope.memberShips}" var="memberShip">
                                <div class="form-group buyerPrivileges" data-id="${memberShip.itemNumber}">
                                    <label class="col-sm-1">${memberShip.value}</label>
                                    <div class="col-sm-3">
                                        <select name="free" data-placeholder="请选择观看类型" class="chosen-select form-control">
                                            <option value="false">付费观看</option>
                                            <option value="true">免费观看</option>
                                        </select>
                                    </div>
                                    <label class="col-sm-1">课程金额</label>
                                    <div  class="col-sm-2">
                                        <div class="input-group">
                                            <span class="input-group-addon">￥</span>
                                            <input type="number" name="memberShipPrice" class="form-control" placeholder="请输入金额">
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div style="text-align: center;">
                    <button type="button" id="formSubmitAction" class="btn btn-wide btn-primary">提交保存</button>
                    <a href="${webPage.backPage}&expandId=${requestScope.category.entityId}"  class="btn btn-back btn-wide">返回</a>
                </div>
            </form>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    function pageReady(doc) {
        $("select[name='free']").on('change', function(){
            var $this=$(this);
            if($this.val()=="true"){
                $this.parents(".buyerPrivileges").find("input[name='memberShipPrice']").attr("disabled","disabled").val("0");
            }else{
                $this.parents(".buyerPrivileges").find("input[name='memberShipPrice']").removeAttr("disabled");
            }
        });
    }
    function beforeEditFormCommit($form){
        var buyerPrivileges=new Array();
        $(".buyerPrivileges").each(function(){
            var $this=$(this);
            var memberShip=$this.attr("data-id");
            var free=$this.find("select[name='free']").val();
            var price=$this.find("input[name='memberShipPrice']").val();
            buyerPrivileges.push({
                memberShip:memberShip,
                free:free,
                price:price
            })
        })
        console.log(buyerPrivileges);
        $form["buyerPrivileges"]=JSON.stringify(buyerPrivileges);
    }
</script>
</body>
</html>