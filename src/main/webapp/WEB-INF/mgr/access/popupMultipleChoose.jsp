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
                    <a href="javascript:void(0)" id="finishChoose" type="button" class="btn btn-primary"><i class="icon-check"></i> 确认选择</a>
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
<script type="text/javascript" src="${contextPath}/js/category.js"></script>
<script type="text/javascript">
    function pageReady(doc){
        <c:if test="${requestScope.hasData}">
        ${requestScope.tableScript}
        </c:if>
        $("#finishChoose").click(function(){
            if(window.parent.popupMultipleChooseTrigger){
                var datagrid=$('#remoteDataGrid').data('zui.datagrid');
                var checkItems=datagrid.getCheckItems();
                for(i=0;i<checkItems.length;i++){
                    var data=checkItems[i];
                    if(data!=null){
                        window.parent.${requestScope.contextName}.push(data);
                    }
                }
                window.parent.popupMultipleChooseTrigger.close();
            }
        })
    }
    function customDataConvertCell(valueType,dataValue,cell, dataGrid){
        if(cell.colIndex==1){
            var dv=HTMLDecode(dataValue);
            return "<a title='"+dv+"'>"+dv+"</dv>";
        }
        console.log(cell.colIndex+","+dataValue);
        return null;
    }
    function interceptorSelectModal(showName,showValue,serviceName,fieldName,searchTitleName,actionButtonName){
        if(serviceName=="questionCategoryService"){
            $("#category").attr("name","category")
            queryQuestionCategory("选择试题所属类别","category","categoryReference",function(){
                console.log("close");
            })
            return true;
        }else if(serviceName=="simulationCategoryService"){
            $("#simulationCategory").attr("name","simulationCategory");
            simulationQuestionCategory("选择试题模拟考试分类","simulationCategory","simulationCategoryReference",function(){
                console.log("close");
            })
            return true;
        }
        return false;
    }
</script>
</body>
</html>