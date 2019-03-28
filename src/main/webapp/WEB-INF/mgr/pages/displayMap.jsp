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
                <visit:auth url="${webPage.newEntityPage}">
                    <a href="${contextPath}${webPage.newEntityPage}${suffix}?${paginationCurrentPage}=1" class="btn btn-primary"><i class="icon icon-plus"></i> 新增${requestScope.entityShowName}</a>
                </visit:auth>
            </div>
        </div>
        <c:if test="${requestScope.hasData}">
            <div id="queryBox" class="cell hidden">
                <form class="form-horizontal" id="query_search_from" method="post">
                    <input type="hidden" name="serviceName" value="displayMapService"/>
                    <input type="hidden" name="queryCondition">
                    <input type="hidden" name="popupSelectValueResult">
                </form>
            </div>
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
                                <a href="${contextPath}${webPage.newEntityPage}${suffix}?${paginationCurrentPage}=1" class="btn btn-info"><i class="icon icon-plus"></i> 新增${requestScope.entityShowName}</a>
                            </visit:auth>
                        </p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<div class="modal fade" id="imageModal">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <img id="imageModalContainer" style="width:100%"/>
        </div>
    </div>
</div>
<script type="text/javascript">
    function pageReady(doc){
        <c:if test="${requestScope.hasData}">
        ${requestScope.tableScript}
        </c:if>
    }
    function createMenuItems(dataId,dataRow,data){
        var items = [{
            url:"${contextPath}${webPage.modifyEntityPage}${suffix}?entityId="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage(),
            label:"编辑修改",
            className:"privilege",
            access:"${webPage.modifyEntityPage}"
        }];
        checkPrivilege(items);
        return items;
    };
    function customDataConvertCell(valueType,dataValue,cell, dataGrid){
        if(cell.config.name=="imagePath"){
            var hex=cell.config.data.imagePath;
            var imageUrl="${managerPath}/access/accessStorageFile${suffix}?hex="+hex;
            var dv="<img  style='height:80px;' src='"+imageUrl+"' class='carouselImage' alt=''/>";
            return dv;
        }
    }
    //datagrid-row-cell
    function changeGridHeight(){
        $(".datagrid-row-cell").css("height","100px");
        var height = 0;
        $(".datagrid-container .datagrid-row").each(function() {
            height += $(this).height();
        })
        $(".datagrid-container").height(height);
        $(".datagrid-row-cell").each(function(i,d){
            var top=100*i;
            $(this).css("top",(top+37)+"px")
        })
    }
</script>
</body>
</html>