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
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    function pageReady(doc){
        <c:if test="${requestScope.hasData}">
        ${requestScope.tableScript}
        </c:if>
    }
    function createMenuItems(dataId,dataRow,data){
        var items = [{
            //url:"${contextPath}${webPage.modifyEntityPage}${suffix}?entityId="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage(),
            label:"编辑修改",
            className:"privilege",
            access:"${webPage.modifyEntityPage}",
            onClick:function(e){
                var url="${contextPath}${webPage.modifyEntityPage}${suffix}?entityId="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage();
                window.open(url,'_blank');
            }
        },{
            url:"${contextPath}${webPage.detailEntityPage}${suffix}?entityId="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage(),
            label:"查看详情"
        },{
            label:"删除用户",
            className:"privilege",
            privilege:"/mgr/account/visitorUser/delete",
            onClick:function(){
                confirmOper("消息","确实要删除选中的用户?",function(){
                    postJSON("${managerPath}/account/visitorUser/delete${suffix}",{
                        entityId:dataId
                    },"正在执行,请稍后...",function(result){
                        if(result.code==SUCCESS){
                            bootbox.alert({
                                title:"消息",
                                message: "删除用户成功,点击确认返回",
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
            label:"重置密码",
            onClick:function(){
                confirmOper("消息","确实要重置选中的用户的密码为[123456]?",function(){
                    postJSON("${managerPath}/account/visitorUser/resetPasswd${suffix}",{
                        entityId:dataId
                    },"正在执行,请稍后...",function(result){
                        if(result.code==SUCCESS){
                            bootbox.alert({
                                title:"消息",
                                message: "重置用户密码成功,点击确认返回",
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
        }];
        checkPrivilege(items);
        return items;
    };
</script>
</body>
</html>