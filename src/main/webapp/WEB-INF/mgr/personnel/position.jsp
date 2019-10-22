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
                <c:if test="${requestScope.hasParent}">
                    <a href="${webPage.backPage}" type="button" class="btn btn-link"><i class="icon-back muted"></i> 返回</a>
                </c:if>
                <visit:auth url="${webPage.newEntityPage}">
                    <a href="${contextPath}${webPage.newEntityPage}${suffix}?${paginationCurrentPage}=1&parent=${requestScope.parent}" class="btn btn-primary"><i class="icon icon-plus"></i> 新增${requestScope.entityShowName}</a>
                </visit:auth>
                <a href="#" id="batchEnable" class="btn btn-secondary">批量显示</a>
                <a href="#" id="batchDisable" class="btn btn-secondary">批量隐藏</a>
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
                            <visit:auth url="${webPage.newEntityPage}">
                                <span class="text-muted">您现在可以 </span>
                                <a href="${contextPath}${webPage.newEntityPage}${suffix}?${paginationCurrentPage}=1&parent=${requestScope.parent}" class="btn btn-info"><i class="icon icon-plus"></i> 新增${requestScope.entityShowName}</a>
                            </visit:auth>
                        </p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    function batchEnableOrDisable(enable){
        var datagrid=$('#remoteDataGrid').data('zui.datagrid');
        var checkItems=datagrid.getCheckItems();
        if(checkItems.length==0){
            toast("没有职位被选中");
            return;
        }
        var entityIdArray=new Array();
        for(i=0;i<checkItems.length;i++){
            var data=checkItems[i];
            if(data!=null){
                entityIdArray.push(data.entityId);
            }
        }
        postJSON("${managerPath}/personnel/position/batchShowOrHide${suffix}",{
            entityIdArray:entityIdArray.join(","),
            enable:enable
        },"正在执行,请稍后...",function(result){
            if(result.code==SUCCESS){
                bootbox.alert({
                    title:"消息",
                    message: "完成批量更新,点击确认返回",
                    callback: function () {
                        reflashPageData();
                    }
                })
            }else{
                showMessage(result.msg,1500);
            }
        });
    }
    function pageReady(doc){
        <c:if test="${requestScope.hasData}">
        ${requestScope.tableScript}
        </c:if>
        $("#batchEnable").click(function(){
            batchEnableOrDisable(true);
        })
        $("#batchDisable").click(function(){
            batchEnableOrDisable(false);
        })
    }
    function createMenuItems(dataId,dataRow,data){
        var items = [{
            url:"${managerPath}/personnel/position${suffix}?pageNumber=${requestScope.pageNumber}&parent="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage(),
            label:"查看下级"
        },{
            //url:"${contextPath}${webPage.modifyEntityPage}${suffix}?pageNumber=${requestScope.pageNumber}&entityId="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage(),
            label:"编辑职务",
            className:"privilege",
            access:"${webPage.modifyEntityPage}",
            onClick:function(e){
                var url="${contextPath}${webPage.modifyEntityPage}${suffix}?entityId="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage();
                window.open(url,'_blank');
            }
        }];
        var showable="";
        if(data.showable.ordinal=="true"){
            showable="隐藏职位";
        }else{
            showable="显示职位";
        }
        items.push({
            label:showable,
            className:"privilege",
            access:"/mgr/personnel/position/showable",
            onClick:function(e){
                postJSON("${managerPath}/personnel/position/showable${suffix}",{
                    entityId:dataId
                },"正在执行,请稍后...",function(result){
                    if(result.code==SUCCESS){
                        bootbox.alert({
                            title:"消息",
                            message: showable+"成功,点击确认返回",
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