<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<!-- 分页控件 -->
<c:if test="${pagination.total>=0}">
    <div class="tfoot clearfix">
        <!--扩展操作面板-->
        <div class="extar_oper_container">

        </div>
        <div style="float: right; clear: none;" class="pager form-inline">
            共 <strong class="text-primary" id="pagination_total">${pagination.total}</strong> 条记录，
            <div class="dropdown dropup">
                <a href="javascript:;" data-toggle="dropdown">每页
                    <strong class="text-primary" id="pagination_page_number">${pagination.maxResult}</strong> 条
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu max_result">
                    <c:forEach begin="5" end="50" step="5" var="item">
                        <c:choose>
                            <c:when test="${item==pagination.maxResult}">
                                <li class="active"><a href='#'>${item}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href='${webPage.paginationQueryUrl}&${paginationCurrentPage}=${pagination.currentPage}&${paginationMaxResult}=${item}'>${item}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:forEach begin="100" end="200" step="100" var="item">
                        <c:choose>
                            <c:when test="${item==pagination.maxResult}">
                                <li class="active"><a href='#'>${item}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href='${webPage.paginationQueryUrl}&${paginationCurrentPage}=${pagination.currentPage}&${paginationMaxResult}=${item}'>${item}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </ul>
            </div>
            <strong class="text-primary" id="pagination_split">${pagination.currentPage}/${pagination.totalPage}</strong>
            &nbsp;
            <a href="javascript:void(0)" title="首页" pageNumber="1" class="toPage" id="pagination_first_page">
                <i class="icon-first-page" title="首页"></i>
            </a>
            <a href="javascript:void(0)" title="上一页" class="toPage" id="pagination_prev_page" pageNumber="${pagination.currentPage-1}">
                <i class="icon icon-angle-left" title="上一页"></i>
            </a>
            <a href="javascript:void(0)" title="下一页" class="toPage" id="pagination_next_page" pageNumber="${pagination.currentPage+1}">
                <i class="icon-angle-right" title="下一页"></i>
            </a>
            <a href="javascript:void(0)" title="末页" class="toPage" pageNumber="${pagination.totalPage}" id="pagination_last_page">
                <i class="icon-last-page" title="末页"></i>
            </a>
            <a href="javascript:void(0)" id="refreshData" title="刷新">
                <i class="icon icon-refresh" style="color:#444;"></i>
                <!--
                <i class="icon icon-spin icon-refresh" style="color:#444;"></i>-->
            </a>
            <input type="text" id="toPageInput"  style="text-align: center; width: 30px; height: 26px; margin-top: -4px" class="form-control">
            <button class="btn btn-sm btn-primary" id="toPageButton" style="height: 26px; margin-top: -4px" type="button">转到</button>
            <input type="hidden" name="pagination_pager_current" value="${pagination.currentPage}"/>
            <script type="text/javascript">
                $("#exportTableToExcel").click(function(){
                    exportExcelTable($(this).attr("title"));
                });
                function setPaginationCurrentPage(page){
                    $("input[name='pagination_pager_current']").val(page);
                }
                function getPaginationCurrentPage(){
                    return parseInt($("input[name='pagination_pager_current']").val());
                }
                //分页跳转
                $("#toPageButton").click(function(){
                    page=$("#toPageInput").val();
                    page=parseInt(page);
                    if(isNaN(page)||page>parseInt('${pagination.totalPage}')||page<1){
                        bootbox.alert("页面格式错误或超出返回");
                        return;
                    }
                    var myDataGrid = $('#remoteDataGrid').data('zui.datagrid');
                    setPaginationCurrentPage(page);
                    myDataGrid.goToPage(page);
                })
                //调整页面
                $(".toPage").click(function(){
                    var page=parseInt($(this).attr("pageNumber"));
                    var myDataGrid = $('#remoteDataGrid').data('zui.datagrid');
                    setPaginationCurrentPage(page);
                    myDataGrid.goToPage(page);
                });
                //设置分页条
                function setPagination(result){
                    var pager=result.pager;
                    $("#pagination_total").html(pager.recTotal);
                    $("#pagination_page_number").html(pager.recPrePage);
                    $("#pagination_split").html(pager.page+"/"+pager.totalPage);
                    $("#pagination_last_page").attr("pageNumber",pager.totalPage);
                    $("#pagination_prev_page").attr("pageNumber",pager.page==1?1:pager.page-1);
                    $("#pagination_next_page").attr("pageNumber",pager.page==pager.totalPage?pager.totalPage:pager.page+1);
                    $("#pagination_first_page").css("color","#444").css("cursor","pointer");
                    $("#pagination_prev_page").css("color","#444").css("cursor","pointer");
                    $("#pagination_next_page").css("color","#444").css("cursor","pointer");
                    $("#pagination_last_page").css("color","#444").css("cursor","pointer");
                    if(pager.page==1){
                        $("#pagination_first_page").css("color","#929292").css("cursor","default");
                        $("#pagination_prev_page").css("color","#929292").css("cursor","default");
                    }
                    if(pager.page==1&&pager.totalPage==1){
                        $("#pagination_first_page").css("color","#929292").css("cursor","default");
                        $("#pagination_prev_page").css("color","#929292").css("cursor","default");
                        $("#pagination_next_page").css("color","#929292").css("cursor","default");
                        $("#pagination_last_page").css("color","#929292").css("cursor","default");
                    }
                    if(pager.page==pager.totalPage){
                        $("#pagination_next_page").css("color","#929292").css("cursor","default");
                        $("#pagination_last_page").css("color","#929292").css("cursor","default");
                    }
                }
                //绑定查询动作
                $("#doSearchAction").click(function(){
                    reflashPageData();
                });
                $("#refreshData").click(function(){
                    $(".icon-refresh").addClass("icon-spin").css("color","#929292");
                    reflashPageData();
                });
                function exportExcelTable(fileName){
                    $("#query_search_from input[name='downloadFileName']").val(fileName);
                    var action=$("#query_search_from").attr("action");
                    $("#query_search_from").attr("action",manager+"/access/exportTableToExcel"+suffix).submit();
                    $("#query_search_from").attr("action",action);
                }
                function reflashPageData(){
                    var myDataGrid =$('#remoteDataGrid').data('zui.datagrid');
                    myDataGrid.renderLoading('正在努力获取数据...');
                    myDataGrid.$cells.find('.datagrid-row').each(function() {
                        $(this).remove();
                    });
                    myDataGrid.search(new Date().getTime());
                    $(".icon-refresh").removeClass("icon-spin").css("color","#444");
                }

                $("#doResetFormAction").click(function(){
                    $("#searchQueryForm").clearFormAndHidden();
                });
                //点击搜索按钮
                $("#query_search_submit").click(function(){
                    var searchCondition={
                        groupLink:$("#query_search_from select[name='groupLink']").val(),
                        leftGroupItem:new Array(),
                        rightGroupItem:new Array()
                    };
                    $("#query_search_from .left_group,#query_search_from .right_group").each(function(i,d){
                        var $this=$(d);
                        var itemLink=$this.find(".query_item_link").val();
                        var field=$this.find(".query_field").val();
                        var condition=$this.find(".query_condition").val();
                        var value=$this.find(".query_value").val();
                        var jsonIitem={
                            field:field,
                            condition:condition,
                            value:value,
                            itemLink:itemLink
                        }
                        if(value!=null&&value!=undefined&&value!=""){
                            if(functionExist("customerFormQuery")){
                                if($this.hasClass("left_group")){
                                    customerFormQuery(searchCondition.leftGroupItem,jsonIitem);
                                }else{
                                    customerFormQuery(searchCondition.rightGroupItem,jsonIitem);
                                }
                            }else{
                                if($this.hasClass("left_group")){
                                    searchCondition.leftGroupItem.push(jsonIitem);
                                }else{
                                    searchCondition.rightGroupItem.push(jsonIitem);
                                }
                            }
                        }
                    });
                    $("#query_search_from input[name='queryCondition']").val(JSON.stringify(searchCondition));
                    localStorage.removeItem("queryBox");
                    localStorage.setItem("queryBox",JSON.stringify(searchCondition));
                    reflashPageData();
                });
                function createPagination(result){
                    return {
                        "result":"success",
                        "message": "",
                        "pager": {
                            "page":result.pagination.currentPage,
                            "recTotal":result.pagination.total,
                            "recPerPage":result.pagination.maxResult,
                            "totalPage":result.pagination.totalPage
                        },
                        "data":result.pagination.dataList
                    }
                }
            </script>
        </div>
    </div>
</c:if>