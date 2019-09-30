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
                <a href="#" id="newTopCategory" class="btn btn-primary">新增类别</a>
            </div>
            <div class="btn-toolbar pull-right">
                <a href="#" class="btn btn-link querybox-toggle" id="show_or_hide_search"><i class="icon-search icon"></i> 搜索</a>
                <visit:auth url="${webPage.newEntityPage}">
                    <a href="#" id="newCourse" url="${contextPath}${webPage.newEntityPage}${suffix}?${paginationCurrentPage}=1" class="btn btn-primary"><i class="icon icon-plus"></i> 新增${requestScope.entityShowName}</a>
                </visit:auth>
            </div>
        </div>
        ${requestScope.searchHtml}
        <div id="mainContent" class="main-row">
            <div class="side-col" id="sidebar" style="width: 280px;">
                <div class="cell left_cell" style="width: 260px;overflow: visible; max-height: initial;">
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
                <div id="remoteDataGrid" class="datagrid-borderless datagrid-striped" style="background:#efefef;width:100%;table-layout:fixed;display:table;"></div>
                <%@ include file="/WEB-INF/paginationPage.jsp"%>
            </div>
        </div>
    </div>
</main>
<%--modal--%>
<div class="modal fade" id="categoryModal">
    <div class="modal-dialog" style="width:520px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title" >新增修改类别</h4>
            </div>
            <div class="modal-body" style="padding-bottom: 0px;">
                <form class="form-horizontal" id="categoryModalForm">
                    <input type="hidden" name="parent">
                    <input type="hidden" name="entityId" value="">
                    <div class="form-group">
                        <label class="col-sm-2">上级类别</label>
                        <div class="col-sm-9">
                            <div class="input-group">
                                <input type="text" readonly class="form-control chosenParentCategory" placeholder="无上级类别" name="parentCategoryName">
                                <span class="input-group-addon border_right clear_parent_category" style="border-right-width:1px;cursor: pointer"><i class="icon icon-close"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="name" class="col-sm-2 required">类别名称</label>
                        <div class="col-sm-9 ref_categoryName">
                            <input type="text" class="form-control" validate="required:类别名称必须输入" name="name" id="name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="weight" class="col-sm-2 required">排序权重</label>
                        <div class="col-sm-9">
                            <input type="number" class="form-control" validate="required:排序权重必须输入" name="weight" id="weight">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">取消返回</button>
                <button type="button" class="btn btn-danger" id="categoryModalAction">确定</button>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    var chooseCategoryId="";
    var expandId="${requestScope.expandId}";
    function pageReady(doc){
        ${requestScope.tableScript}
        /***** 类别管理 ******/
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
            var category=$(this).parent("span");
            $(this).addClass("text-red");
            var level=category.attr("data-level");
            if(level=="0"){
                chooseCategoryId="";
            }else{
                chooseCategoryId=category.attr("data-id");
            }
            loadCourseData(chooseCategoryId);
        });
        $("#categoryTree").on("click",".oper",function(e){
            var $this=$(this);
            var category=$(this).parent("span");
            var categoryId=category.attr("data-id");
            var level=category.attr("data-level");
            var name=category.attr("data-name");
            var weight=category.attr("data-weight");
            var enable=category.attr("data-enable");
            var items =[];
            if(level=="0"){
                items.push({
                    label:"新增子类",
                    onClick:function(){
                        $("#categoryModalForm input[name='parent']").val(categoryId);
                        $("#categoryModalForm input[name='parentCategoryName']").val(name);
                        $("#categoryModalForm input[name='weight']").val("0");
                        $('#categoryModal').modal("show");
                    }
                })
            }
            items.push({
                label:"编辑修改",
                onClick:function(){
                    $("#categoryModalForm input[name='entityId']").val(categoryId);
                    $("#categoryModalForm input[name='name']").val(name);
                    var parentCategory=$this.parents("ul").prev("span");
                    var parentName=parentCategory.attr("data-name");
                    var parentId=parentCategory.attr("data-id");
                    $("#categoryModalForm input[name='parent']").val(parentId);
                    $("#categoryModalForm input[name='parentCategoryName']").val(parentName);
                    $("#categoryModalForm input[name='weight']").val(weight);
                    $('#categoryModal').modal("show");
                }
            });
            items.push({
                label:"启用/停用",
                onClick:function(){
                    var enableStatus=(enable=="true"?"禁用":"启用");
                    confirmOper("消息","确实要改变类别状态为"+enableStatus+"?",function(){
                        postJSON("${managerPath}/curriculum/course/category/enableOrDisable${suffix}",{
                            entityId:categoryId
                        },"正在执行,请稍后...",function(result){
                            if(result.code==SUCCESS){
                                bootbox.alert({
                                    title:"消息",
                                    message: "修改类别状态成功,点击确认返回",
                                    callback: function () {
                                        window.location.reload();
                                    }
                                })
                            }else{
                                showMessage(result.msg,1500);
                            }
                        });
                    })
                }
            });
            $.zui.ContextMenu.show(items);
            $(".contextmenu-menu").css("top", e.clientY + "px").css("left", e.clientX + "px")
        });
        $("#newTopCategory").click(function(){
            $("#categoryModalForm input[name='parent']").val("");
            $("#categoryModalForm input[name='parentCategoryName']").val("");
            $("#categoryModalForm input[name='weight']").val("0");
            $('#categoryModal').modal("show");
        });
        $('#categoryModal').on('hidden.zui.modal', function() {
            $("#categoryModalForm").clearFormAndHidden();
            $("input[name='imagePath']").val("");
            $("#categoryImage").hide();
        });
        $("#categoryModalAction").click(function(){
            var $this=$(this);
            var $form=$("#categoryModalForm");
            $this.addClass("disabled");
            var flag=$form.formValidate(function(el,hint){
                toast(hint);
                $("#ref_"+el.attr("name")).addClass("check-error");
                el.focus(function(){
                    $("#ref_"+el.attr("name")).removeClass("check-error");
                })
            });
            if(flag){
                var $formData=$form.formToJson();
                var actionUrl="${managerPath}/curriculum/course/category/newOrEdit${suffix}";
                postJSON(actionUrl,$formData,"正在处理请稍后",function(result){
                    $this.removeClass("disabled");
                    if(result.code==SUCCESS){
                        bootbox.alert({
                            title:"消息",
                            message: "新增修改类别成功,点击确认返回",
                            callback: function () {
                                window.location.reload();
                            }
                        })
                    }else{
                        bootbox.alert({
                            title:"错误",
                            message:result.msg
                        });
                    }
                })
            }else{
                $this.removeClass("disabled");
            }
        });
        //新增课程判断
        $("#newCourse").click(function(){
            if(chooseCategoryId==""){
                toast("请选择需要新增课程的二级类别");
            }else{
                window.location.href=$(this).attr("url")+"&categoryId="+chooseCategoryId;
            }
        });
        if(expandId!=""){
            expandTreeNode(expandId);
            chooseCategoryId=expandId;
            loadCourseData(chooseCategoryId);
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
            url:"${managerPath}/curriculum/course/chapter/editor${suffix}?entityId="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage(),
            label:"章节管理",
            className:"privilege",
            access:"${webPage.modifyEntityPage}"
        },{
            url:"${contextPath}${webPage.detailEntityPage}${suffix}?entityId="+dataId+"&${paginationCurrentPage}="+getPaginationCurrentPage(),
            label:"查看详情"
        },{
            label:"课程上/下架",
            onClick:function(){
                var sale=data.sale.name=="true"?"下架":"上架";
                confirmOper("消息","确实要对该课程进行"+sale+"处理?",function(){
                    postJSON("${managerPath}/curriculum/course/chapter/changeSale${suffix}",{
                        entityId:dataId
                    },"正在执行,请稍后...",function(result){
                        if(result.code==SUCCESS){
                            bootbox.alert({
                                title:"消息",
                                message: "处理课程"+sale+"成功,点击确认返回",
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
    function expandTreeNode(entityId){
        var category=$("span[data-id='"+entityId+"']");
        var $li=category.parents("li");
        var tree=$("#categoryTree").data('zui.tree');
        tree.expand($li);
        category.find(".menu_title").addClass("text-red");
        loadCourseData(chooseCategoryId);
    }
    function loadCourseData(entityId){
        $("#query_search_from input[name='currentCategory']").val(entityId);
        reflashPageData();
    }
</script>
</body>
</html>