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
                    <a href='javascript:void(0)' id="exportTableToExcel" title="招聘信息表" class="btn btn-link"><i class="icon-import muted"> </i> 导出数据表</a>
                </c:if>
                <div class="btn-group">
                    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                        批量操作 <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu pull-right" role="menu">
                        <li><a href="#" id="batchInfoUpper">批量发布</a></li>
                        <li><a href="#" id="batchUnInfoUpper">批量取消发布</a></li>
                        <li><a href="#" id="batchRecommend">批量推荐</a></li>
                        <li><a href="#" id="batchUnRecommend">批量取消推荐</a></li>
                    </ul>
                </div>
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
                        </p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    function batchInfoUpper(enable){
        var datagrid=$('#remoteDataGrid').data('zui.datagrid');
        var checkItems=datagrid.getCheckItems();
        if(checkItems.length==0){
            toast("没有招聘信息被选中");
            return;
        }
        var entityIdArray=new Array();
        for(i=0;i<checkItems.length;i++){
            var data=checkItems[i];
            if(data!=null){
                entityIdArray.push(data.entityId);
            }
        }
        postJSON("${managerPath}/personnel/recruitment/batchInfoUpper${suffix}",{
            entityIdArray:entityIdArray.join(","),
            enable:enable
        },"正在执行,请稍后...",function(result){
            if(result.code==SUCCESS){
                bootbox.alert({
                    title:"消息",
                    message: "完成批量操作,点击确认返回",
                    callback: function () {
                        reflashPageData();
                    }
                })
            }else{
                showMessage(result.msg,1500);
            }
        });
    }
    function batchRecommend(enable){
        var datagrid=$('#remoteDataGrid').data('zui.datagrid');
        var checkItems=datagrid.getCheckItems();
        if(checkItems.length==0){
            toast("没有招聘信息被选中");
            return;
        }
        var entityIdArray=new Array();
        for(i=0;i<checkItems.length;i++){
            var data=checkItems[i];
            if(data!=null){
                entityIdArray.push(data.entityId);
            }
        }
        postJSON("${managerPath}/personnel/recruitment/batchRecommend${suffix}",{
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
        $("#batchInfoUpper").click(function(){
            batchInfoUpper(true);
        });
        $("#batchUnInfoUpper").click(function(){
            batchInfoUpper(false);
        });
        $("#batchRecommend").click(function(){
            batchRecommend(true);
        });
        $("#batchUnRecommend").click(function(){
            batchRecommend(false);
        });
    }
    function createMenuItems(dataId,dataRow,data){
        var items = [{
            url:"${contextPath}${webPage.detailEntityPage}${suffix}?entityId="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage(),
            label:"查看详情"
        }];
        var recommend="";
        if(data.recommend.ordinal=="true"){
            recommend="取消推荐";
        }else{
            recommend="推荐岗位";
        }
        var infoUpper="";
        if(data.infoUpper.ordinal=="true"){
            infoUpper="取消发布";
        }else{
            infoUpper="发布招聘";
        }
        items.push({
            label:infoUpper,
            className:"privilege",
            access:"/mgr/personnel/recruitment/changeUpper",
            onClick:function(e){
                postJSON("${managerPath}/personnel/recruitment/changeUpper${suffix}",{
                    entityId:dataId
                },"正在执行,请稍后...",function(result){
                    if(result.code==SUCCESS){
                        bootbox.alert({
                            title:"消息",
                            message: infoUpper+"成功,点击确认返回",
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
            label:recommend,
            className:"privilege",
            access:"/mgr/personnel/recruitment/recommend",
            onClick:function(e){
                postJSON("${managerPath}/personnel/recruitment/recommend${suffix}",{
                    entityId:dataId
                },"正在执行,请稍后...",function(result){
                    if(result.code==SUCCESS){
                        bootbox.alert({
                            title:"消息",
                            message: recommend+"成功,点击确认返回",
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