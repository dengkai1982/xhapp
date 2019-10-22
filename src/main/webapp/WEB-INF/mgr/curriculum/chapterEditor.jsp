<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/htmlHeader.jsp"%>
    <style>
        .chapter-ul{
            list-style: none;
            margin: 0;
            padding: 0;
        }
        .chapter{
            margin-bottom:10px;
        }
        .chapter::after{
            display: block;
            height: 1px;
            content: '';
            background-color: #e3e5e9;
        }
        .chapter .chapter-top{
            display: -webkit-flex;
            display:flex;
            justify-content: space-between;
            align-items:baseline;
        }
        .chapter .iconfont{
            font-size:16px;
        }
        .chapter .chapter-top .name{
            font-size:16px;
            color:#07111b;
            flex-grow:1;
            margin-left:10px;
        }
        .chapter .function a{
            margin-right: 15px;
        }
        .chapter .function a:hover{
            text-decoration: underline;
        }
        .chapter p{
            margin:8px 0 8px 26px;
        }
        .chapter .movie-item{
            list-style: none;
            margin: 0 0 0 26px;
            padding:0;
        }
        .chapter .movie-item::before{
            display: block;
            height: 1px;
            content: '';
            background-color: #e3e5e9;
        }
        .chapter .movie-item .item{
            display: -webkit-flex;
            display: flex;
            height: 30px;
            line-height: 30px;
            justify-content: space-between;
            align-items:baseline;
            border-bottom: 1px #e3e5e9 dashed;
        }
        .clear-border{
            border:none!important;
        }
        .chapter .movie-item .item .name{
            flex-grow:1;
        }
    </style>
</head>
<body>
<%@include file="/WEB-INF/headerMenus.jsp"%>
<main id="main">
    <div class="container">
        <div class="form_container">
            <div class="center-block">
                <div class="main-header">
                    <h2>${webPage.pageTitle} - 【${requestScope.course.category.name}】-【${requestScope.course.name}】</h2>
                    <div class="pull-right btn-toolbar">
                        <a href="${webPage.backPage}&expandId=${requestScope.course.category.entityId}" type="button" class="btn btn-link"><i class="icon-back muted"></i> 返回</a>
                    </div>
                </div>
            </div>
            <form id="editor_form" data-form-name="${webPage.pageTitle}" class="form-horizontal" method="post"
                  action="${contextPath}${webPage.commitEntityAction}${suffix}">
                <input type="hidden" name="course" value="${requestScope.course.entityId}"/>
                <div id="chapterContainer">
                    <ul class="chapter-ul">
                        <c:forEach items="${requestScope.chapters}" var="chapter">
                            <li class="chapter">
                                <div class="chapter-top">
                                    <i class="iconfont icon-mn_yingyong_fill"></i>
                                    <div class="name">${chapter.name}</div>
                                    <div class="function">
                                        <a href="#" data-id="${chapter.entityId}" class="text-danger newChapterMovie">新增章节视频</a>
                                        <a href="#" data-id="${chapter.entityId}" data-name="${chapter.name}" data-weight="${chapter.weight}" data-detail="${chapter.detail}" class="text-success editorChapter">编辑章节</a>
                                        <a href="#" data-id="${chapter.entityId}" class="text-warning deleteChapter">删除章节</a>
                                    </div>
                                </div>
                                <p class="desc">${chapter.detail}</p>
                                <ul class="movie-item">
                                    <c:forEach items="${chapter.courseMovieList}" var="movie" varStatus="vs">
                                        <c:choose>
                                            <c:when test="${vs.last}">
                                                <li class="item clear-border">
                                                    <div class="name">${movie.name} - (${movie.longTime})</div>
                                                    <div class="function">
                                                        <a href="#" data-id="${movie.entityId}" class="text-danger playMovie" data-mediaLibrary="${movie.mediaLibrary.entityId}">播放</a>
                                                        <a href="#" data-chapter="${chapter.entityId}" data-id="${movie.entityId}"
                                                           data-name="${movie.name}" data-weight="${movie.weight}"
                                                           class="text-danger editMovie" data-mediaLibrary="${movie.mediaLibrary.entityId}"
                                                           data-mediaLibrary-name="${movie.mediaLibrary.name}"
                                                           data-longTime="${movie.longTime}">编辑</a>
                                                        <a href="#" data-id="${movie.entityId}" class="text-warning deleteMovie">删除</a>
                                                    </div>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="item">
                                                    <div class="name">${movie.name} - (${movie.longTime})</div>
                                                    <div class="function">
                                                        <a href="#" data-id="${movie.entityId}" class="text-danger playMovie" data-mediaLibrary="${movie.mediaLibrary.entityId}">播放</a>
                                                        <a href="#" data-chapter="${chapter.entityId}" data-id="${movie.entityId}"
                                                           data-name="${movie.name}" data-weight="${movie.weight}"
                                                           class="text-danger editMovie" data-mediaLibrary="${movie.mediaLibrary.entityId}"
                                                           data-mediaLibrary-name="${movie.mediaLibrary.name}"
                                                           data-longTime="${movie.longTime}">编辑</a>
                                                        <a href="#" data-id="${movie.entityId}" class="text-warning deleteMovie">删除</a>
                                                    </div>
                                                </li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div style="text-align: center;">
                    <button type="button" id="newChapter" class="btn btn-wide btn-danger">添加章节</button>
                    <a href="${webPage.backPage}&expandId=${requestScope.course.category.entityId}"  class="btn btn-back btn-wide">返回</a>
                </div>
            </form>
        </div>
    </div>
</main>
<%--modal--%>
<div class="modal fade" id="chapterModal">
    <div class="modal-dialog" style="width:520px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title" >新增修改章节</h4>
            </div>
            <div class="modal-body" style="padding-bottom: 0px;">
                <form class="form-horizontal" id="chapterModalForm">
                    <input type="hidden" name="entityId" value="">
                    <div class="form-group">
                        <label for="chapterName" class="col-sm-2 required">章节名称</label>
                        <div class="col-sm-9 ref_chapterName">
                            <input type="text" class="form-control" validate="required:章节名称必须输入" name="name" id="chapterName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="chapterWeight" class="col-sm-2 required">排序权重</label>
                        <div class="col-sm-9 ref_chapterWeight">
                            <input type="number" class="form-control" validate="required:排序权重必须输入" name="weight" id="chapterWeight">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="chapterDetail" class="col-sm-2 required">章节介绍</label>
                        <div class="col-sm-9 ref_chapterDetail">
                            <textarea name="detail" id="chapterDetail" class="form-control" rows="4"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">取消返回</button>
                <button type="button" class="btn btn-danger" id="chapterModalAction">确定</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="courseMovieModal">
    <div class="modal-dialog" style="width:520px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title" >新增修改章节视频</h4>
            </div>
            <div class="modal-body" style="padding-bottom: 0px;">
                <form class="form-horizontal" id="courseMovieModalForm">
                    <input type="hidden" name="entityId" value="">
                    <input type="hidden" name="chapter" value=""/>
                    <input type="hidden" name="popupSelectValueResult">
                    <div class="form-group">
                        <label for="courseMovieName" class="col-sm-2 required">名称</label>
                        <div class="col-sm-9 ref_courseMovieName">
                            <input type="text" class="form-control" validate="required:章节视频名称必须输入" name="name" id="courseMovieName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="longTime" class="col-sm-2 required">播放时长</label>
                        <div class="col-sm-9 ref_courseMovieName">
                            <input type="text" class="form-control" validate="required:播放时长必须输入" name="longTime" id="longTime">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="longTime" class="col-sm-2 required">媒体文件</label>
                        <div class="col-sm-9 ref_courseMovieName">
                            <div class="input-group">
                                <input type="hidden" name="mediaLibrary" value="" class="query_value" data-ref-value="mediaLibrary">
                                <input type="text" name="mediaLibraryValue" class="form-control popupSingleChoose" data-ref-value="mediaLibrary" readonly="" placeholder="点击选择"
                                       value="" data-service-name="mediaLibraryService" data-search-title-name="查找搜索"
                                       data-action-button-name="立即选择" data-field-name="name" validate="required:媒体文件必须选择">
                                <span class="input-group-addon border_right clear_query_search_input"><i class="icon icon-close"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="courseMovieWeight" class="col-sm-2 required">排序权重</label>
                        <div class="col-sm-9 ref_courseMovie">
                            <input type="number" class="form-control" validate="required:排序权重必须输入" name="weight" id="courseMovieWeight">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">取消返回</button>
                <button type="button" class="btn btn-danger" id="courseMovieModalAction">确定</button>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    function pageReady(doc) {
        //新增修改章节
        $("#newChapter").click(function(){
            clearChapterModal();
            $('#chapterModal').modal("show");
        });
        $(".editorChapter").click(function(){
            var $this=$(this);
            $("#chapterModal input[name='entityId']").val($this.attr("data-id"));
            $("#chapterModal input[name='name']").val($this.attr("data-name"));

            $("#chapterModal input[name='weight']").val($this.attr("data-weight"));
            $("#chapterModal textarea[name='detail']").val($this.attr("data-detail"));
            $('#chapterModal').modal("show");
        });
        $("#chapterModalAction").click(function(){
            var $this=$(this);
            var $form=$("#chapterModalForm");
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
                $formData["course"]="${requestScope.course.entityId}";
                var actionUrl="${managerPath}/curriculum/course/commitChapter${suffix}";
                postJSON(actionUrl,$formData,"正在处理请稍后",function(result){
                    $this.removeClass("disabled");
                    $('#chapterModal').modal("hide");
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
        //新增修改媒体库
        //courseMovieModal
        $(".newChapterMovie").click(function(){
            clearCourseMovieModal();
            var chapterId=$(this).attr("data-id");
            $("#courseMovieModal input[name='chapter']").val(chapterId);
            $("#courseMovieModal").modal("show");
        });
        $(".editMovie").click(function(){
            clearCourseMovieModal();
            var $this=$(this);
            $("#courseMovieModal input[name='entityId']").val($this.attr("data-id"));
            $("#courseMovieModal input[name='chapter']").val($this.attr("data-chapter"));
            $("#courseMovieModal input[name='name']").val($this.attr("data-name"));
            $("#courseMovieModal input[name='weight']").val($this.attr("data-weight"));
            $("#courseMovieModal input[name='longTime']").val($this.attr("data-longTime"));
            $("#courseMovieModal input[name='mediaLibrary']").val($this.attr("data-mediaLibrary"));
            $("#courseMovieModal input[name='mediaLibraryValue']").val($this.attr("data-mediaLibrary-name"));
            $("#courseMovieModal").modal("show");
        });
        $(".popupSingleChoose").click(function(){
            var $showName=$(this);
            var data_ref_value=$showName.attr("data-ref-value");
            var $showValue=$showName.prev("input[data-ref-value='"+data_ref_value+"']");
            var serviceName=$showName.attr("data-service-name");//查询服务类
            var searchTitleName=$showName.attr("data-search-title-name");//弹出层的title
            var actionButtonName=$showName.attr("data-action-button-name");//弹出层选中的按钮值
            var fieldName=$showName.attr("data-field-name");//选中后返回的字段
            openSingleChoosenTrigger($showName,$showValue,serviceName,fieldName,searchTitleName,actionButtonName);
        });
        $("#courseMovieModalAction").click(function(){
            var $this=$(this);
            var $form=$("#courseMovieModalForm");
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
                var actionUrl="${managerPath}/curriculum/course/commitCourseMovie${suffix}";
                postJSON(actionUrl,$formData,"正在处理请稍后",function(result){
                    $this.removeClass("disabled");
                    $('#chapterModal').modal("hide");
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
        })
        //其他
        $(".playMovie").click(function(){
            var mediaLibraryId=$(this).attr("data-mediaLibrary");
            postJson("${managerPath}/curriculum/getPlayAddress${suffix}",{
                entityId:mediaLibraryId
            },function(data){
                if(data.code=SUCCESS){
                    window.open(data.body,"_blank");
                    //window.location.href=data.body;
                }else{
                    toast(data.msg);
                }
            })
        });
        $(".deleteChapter").click(function(){
            var entityId=$(this).attr("data-id");
            confirmOper("消息","确实要删除选中的章节，删除该章节将同时删除该章节的所有章节视频,是否继续?",function(){
                postJSON("${managerPath}/curriculum/course/deleteChapter${suffix}",{
                    entityId:entityId
                },"正在执行,请稍后...",function(result){
                    if(result.code==SUCCESS){
                        bootbox.alert({
                            title:"消息",
                            message: "删除章节成功,点击确认返回",
                            callback: function () {
                                window.location.reload();
                            }
                        })
                    }else{
                        showMessage(result.msg,1500);
                    }
                });
            })
        })
        $(".deleteMovie").click(function(){
            var entityId=$(this).attr("data-id");
            confirmOper("消息","确实要删除选中的章节视频?",function(){
                postJSON("${managerPath}/curriculum/course/deleteCourseMovie${suffix}",{
                    entityId:entityId
                },"正在执行,请稍后...",function(result){
                    if(result.code==SUCCESS){
                        bootbox.alert({
                            title:"消息",
                            message: "删除视频章节成功,点击确认返回",
                            callback: function () {
                                window.location.reload();
                            }
                        })
                    }else{
                        showMessage(result.msg,1500);
                    }
                });
            })
        });
    }
    function clearCourseMovieModal(){
        $("#courseMovieModal input[name='entityId']").val("");
        $("#courseMovieModal input[name='chapter']").val("");
        $("#courseMovieModal input[name='name']").val("");
        $("#courseMovieModal input[name='weight']").val("0");
        $("#courseMovieModal input[name='longTime']").val("");
        $("#courseMovieModal input[name='mediaLibrary']").val("");
        $("#courseMovieModal input[name='mediaLibraryValue']").val("");

    }
    function clearChapterModal(){
        $("#chapterModal input[name='entityId']").val("");
        $("#chapterModal input[name='name']").val("");
        $("#chapterModal input[name='weight']").val("0");
        $("#chapterModal textarea[name='detail']").val("");
    }
</script>
</body>
</html>