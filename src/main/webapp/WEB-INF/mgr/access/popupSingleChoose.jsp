<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/htmlHeader.jsp"%>
</head>
<body>
<main id="main" style="padding-top:15px">
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
<script type="text/javascript">
    function pageReady(doc){
        <c:if test="${requestScope.hasData}">
        ${requestScope.tableScript}
        </c:if>
        $("#remoteDataGrid").on('click','.choseIt',function(){
            var id=$(this).attr("data-id");
            var value=$(this).attr("data-value")
            //popupSelectValueResult
            if(window.parent.popupSingleChooseTrigger){
                var popupSelectValueResult={
                    id:id,
                    value:value
                };
                $("input[name='popupSelectValueResult']", window.parent.document).val(JSON.stringify(popupSelectValueResult));
                window.parent.popupSingleChooseTrigger.close();
            }
        });
    }
    function createMenus(dataValue, cell, dataGrid){
        var data=cell.config.data;
        return "<a data-id='" + data.entityId + "' data-value='"+data.${requestScope.fieldName}+"' class='text-primary choseIt'>${requestScope.actionButtonName}</a>";
    }
</script>
</body>
</html>