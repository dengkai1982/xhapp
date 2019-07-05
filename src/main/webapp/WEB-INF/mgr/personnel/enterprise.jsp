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
<div class="modal fade" id="imageModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <img src="" style="height: 100%;width:100%"/>
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
        $("#remoteDataGrid").on("click","a.lightbox",function(){
            var url=$(this).attr("url");
            $("#imageModal img").attr("src",url);
            $("#imageModal").modal("show")
        });
    }
    function customDataConvertCell(valueType,dataValue,cell, dataGrid){
        if(cell.config.name=="logoImage"){
            if(cell.config.data.logoImage!=""){
                return "<a class='lightbox' url='${managerPath}/access/accessStorageFile${suffix}?hex="+cell.config.data.logoImage+"'>点击查看</a>";
            }else{
                return "<span>未设置</span>";
            }
        }else if(cell.config.name=="licensePhoto"){
            if(cell.config.data.licensePhoto!=""){
                return "<a class='lightbox' url='${managerPath}/access/accessStorageFile${suffix}?hex="+cell.config.data.licensePhoto+"'>点击查看</a>";
            }else{
                return "<span>未设置</span>";
            }
        }
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
            recommend="推荐企业";
        }
        items.push({
            label:recommend,
            className:"privilege",
            access:"/mgr/personnel/enterprise/recommend",
            onClick:function(e){
                postJSON("${managerPath}/personnel/enterprise/recommend${suffix}",{
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
        var verifyed="";
        if(data.verifyed.ordinal=="true"){
            verifyed="取消认证";
        }else{
            verifyed="企业认证";
        }
        items.push({
            label:verifyed,
            className:"privilege",
            access:"/mgr/personnel/enterprise/verify",
            onClick:function(e){
                postJSON("${managerPath}/personnel/enterprise/verify${suffix}",{
                    entityId:dataId
                },"正在执行,请稍后...",function(result){
                    if(result.code==SUCCESS){
                        bootbox.alert({
                            title:"消息",
                            message: verifyed+"成功,点击确认返回",
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