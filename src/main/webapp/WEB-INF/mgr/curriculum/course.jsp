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
            <div class="pull-left btn-toolbar">
                <a href="#" class="btn btn-primary">新增类别</a>
            </div>
            <div class="btn-toolbar pull-right">
                <c:if test="${requestScope.hasData}">
                    <a href="#" class="btn btn-link querybox-toggle" id="show_or_hide_search"><i class="icon-search icon"></i> 搜索</a>
                </c:if>
                <visit:auth url="${webPage.newEntityPage}">
                    <a href="#" id="newCourse" url="${contextPath}${webPage.newEntityPage}${suffix}?${paginationCurrentPage}=1" class="btn btn-primary"><i class="icon icon-plus"></i> 新增${requestScope.entityShowName}</a>
                </visit:auth>
            </div>
        </div>
        <c:if test="${requestScope.hasData}">
            ${requestScope.searchHtml}
        </c:if>
        <div id="mainContent" class="main-row">
            <div class="side-col" id="sidebar" style="width: 260px;">
                <div class="cell left_cell" style="width: 230px;overflow: visible; max-height: initial;">
                    <div class="panel">
                        <div class="panel-heading">
                            <div class="panel-title">课程类别</div>
                        </div>
                        <div class="panel-body">
                            <ul id="categoryTree" class="tree tree-menu tree-lines">

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="main-col main-table">
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
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    var categoryId;
    function pageReady(doc){
        <c:if test="${requestScope.hasData}">
        ${requestScope.tableScript}
        </c:if>
        var data=${requestScope.treeData};
        if(data.category){
            data=data.category;
        }
        $("#categoryTree").tree({
            animate:true,
            data:data,
            itemCreator:function($li, item){
                $li.append(item.title);
            }
        });
        $("#categoryTree").on("click",".menu_title",function(){
            $(".menu_title").removeClass("text-red");
            var parentInfo=$(this).parent("span");
            $(this).addClass("text-red");
            categoryId=parentInfo.attr("data-id");
        })
        $("#categoryTree").on("click",".oper",function(e){
            var parentInfo=$(this).parent("span");
            $(this).addClass("text-red");
            categoryId=parentInfo.attr("data-id");
            var level=parentInfo.attr("data-level");
            var items =[];
            if(level=="0"){
                items.push({
                    label:"新增子类",
                    onClick:function(){
                        console.log(categoryId)
                    }
                })
            }
            items.push({
                label:"编辑修改",
                onClick:function(){

                }
            });
            items.push({
                label:"编辑修改",
                onClick:function(){

                }
            })
            $.zui.ContextMenu.show([{

            },{
                label:"编辑修改",
                onClick:function(){

                }
            }]);
            $(".contextmenu-menu").css("top", e.clientY + "px").css("left", e.clientX + "px")
        })
    }
    function createMenuItems(dataId,dataRow,data){
        var items = [{
            url:"${contextPath}${webPage.modifyEntityPage}${suffix}?entityId="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage(),
            label:"编辑修改",
            className:"privilege",
            access:"${webPage.modifyEntityPage}"
        },{
            url:"${contextPath}${webPage.detailEntityPage}${suffix}?entityId="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage(),
            label:"查看详情"
        }];
        checkPrivilege(items);
        return items;
    };
</script>
</body>
</html>