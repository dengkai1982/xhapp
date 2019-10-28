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
                    <a href='javascript:void(0)' id="exportTableToExcel" title="再答考试" class="btn btn-link"><i class="icon-import muted"> </i> 导出数据表</a>
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
            //url:"${contextPath}${webPage.modifyEntityPage}${suffix}?entityId="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage(),
            label:"查看详情",
            onClick:function(e){
                var url="${contextPath}${webPage.detailEntityPage}${suffix}?entityId="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage();
                window.open(url,'_blank');
            }
        }];
        checkPrivilege(items);
        return items;
    };
</script>
</body>
</html>