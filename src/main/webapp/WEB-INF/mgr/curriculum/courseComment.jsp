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
    function pageReady(doc){
        <c:if test="${requestScope.hasData}">
        ${requestScope.tableScript}
        </c:if>
    }
    function createMenuItems(dataId,dataRow,data){
        var items = [{
            url:"${managerPath}/curriculum/courseComment/detail${suffix}?entityId="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage(),
            label:"查看详情"
        },{
            label:"删除评论",
            privilege:"/mgr/curriculum/courseComment/delete",
            onClick:function(){
                confirmOper("消息","确实要删除选中的评论?",function(){
                    postJSON("${managerPath}/curriculum/courseComment/delete${suffix}",{
                        entityId:dataId
                    },"正在执行,请稍后...",function(result){
                        if(result.code==SUCCESS){
                            bootbox.alert({
                                title:"消息",
                                message: "删除评论成功,点击确认返回",
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
        }]
        if(data.answer.name=="false"){
            items.push({
                url:"${managerPath}/curriculum/courseComment/reply${suffix}?entityId="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage(),
                label:"课程回复",
                className:"privilege",
                access:"/mgr/curriculum/courseComment/reply"
            });
        }
        checkPrivilege(items);
        return items;
    };
</script>
</body>
</html>