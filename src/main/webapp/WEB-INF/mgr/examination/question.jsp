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
                <div class="btn-group">
                    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                        批量操作 <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu pull-right" role="menu">
                        <li><a href="#" id="batchEnable">批量启用</a></li>
                        <li><a href="#" id="batchDisable">批量停用</a></li>
                        <li><a href="#" id="batchDelete">批量删除</a></li>
                        <li><a href="#" id="batchEnableAll">全部启用</a></li>
                        <li><a href="#" id="batchDisableAll">全部停用</a></li>
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
<script type="text/javascript" src="${contextPath}/js/category.js"></script>
<script type="text/javascript">
    function batchEnableOrDisableAll(enable){
        var showName=enable?"启用":"停用";
        confirmOper("警告","确实用"+showName+"所有的试题?",function(){
            postJSON("${managerPath}/examination/question/batchEnableOrDisableAll${suffix}",{
                enable:enable
            },"正在执行,请稍后...",function(result){
                if(result.code==SUCCESS){
                    bootbox.alert({
                        title:"消息",
                        message: "操作成功,点击确认返回",
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
    function batchEnableOrDisable(enable){
        var datagrid=$('#remoteDataGrid').data('zui.datagrid');
        var checkItems=datagrid.getCheckItems();
        if(checkItems.length==0){
            toast("没有试题被选中");
            return;
        }
        var entityIdArray=new Array();
        for(i=0;i<checkItems.length;i++){
            var data=checkItems[i];
            if(data!=null){
                entityIdArray.push(data.entityId);
            }
        }
        postJSON("${managerPath}/examination/question/batchEnable${suffix}",{
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
        $("#batchEnableAll").click(function(){
            batchEnableOrDisableAll(true);
        });
        $("#batchDisableAll").click(function(){
            batchEnableOrDisableAll(false);
        });

        $("#batchDelete").click(function(){
            var datagrid=$('#remoteDataGrid').data('zui.datagrid');
            var checkItems=datagrid.getCheckItems();
            if(checkItems.length==0){
                toast("没有试题被选中");
                return;
            }
            var entityIdArray=new Array();
            for(i=0;i<checkItems.length;i++){
                var data=checkItems[i];
                if(data!=null){
                    entityIdArray.push(data.entityId);
                }
            }
            postJSON("${managerPath}/examination/question/batchDelete${suffix}",{
                entityIdArray:entityIdArray.join(",")
            },"正在执行,请稍后...",function(result){
                if(result.code==SUCCESS){
                    bootbox.alert({
                        title:"消息",
                        message: "批量删除完毕,点击确认返回",
                        callback: function () {
                            reflashPageData();
                        }
                    })
                }else{
                    showMessage(result.msg,1500);
                }
            });
        })
        /*$("input[data-service-name='questionCategoryService']").removeClass("popupSingleChoose").click(function(){
            console.log("clock")
        })*/
    }
    /*function filterQueryConditionValue(chosenField,valueHtml){
        console.log(chosenField)
        console.log(valueHtml)
        return valueHtml;
    }*/
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
    function customDataConvertCell(valueType,dataValue,cell, dataGrid){
        if(cell.colIndex==1||cell.colIndex==8){
            var dv=HTMLDecode(dataValue);
            return "<a title='"+dv+"'>"+dv+"</dv>";
        }
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
            label:"删除试题",
            className:"privilege",
            access:"${webPage.deleteEntityPage}",
            onClick:function(){
                confirmOper("警告","确实要删除选中的试题?",function(){
                    postJSON("${managerPath}/examination/question/delete${suffix}",{
                        entityId:dataId
                    },"正在执行,请稍后...",function(result){
                        if(result.code==SUCCESS){
                            bootbox.alert({
                                title:"消息",
                                message: "删除试题成功,点击确认返回",
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
        var enableName="";
        if(data.enable.name){
            enableName="停用试题";
        }else{
            enableName="启用试题";
        }
        items.push({
            label:enableName,
            className:"privilege",
            access:"/mgr/examination/question/enable",
            onClick:function(e){
                postJSON("${managerPath}/examination/question/enable${suffix}",{
                    entityId:dataId
                },"正在执行,请稍后...",function(result){
                    if(result.code==SUCCESS){
                        bootbox.alert({
                            title:"消息",
                            message: enableName+"成功,点击确认返回",
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
    /*function customerFormQuery(groupItem,joinItem){
        console.log(joinItem);
        if(joinItem.field!="category"&&joinItem.field!="simulationCategory"){
            groupItem.push(joinItem);
        }
    }*/
</script>
</body>
</html>