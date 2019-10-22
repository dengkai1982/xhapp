<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/htmlHeader.jsp"%>
    <link rel="stylesheet" href="${contextPath}/wangEditor/wangEditor.min.css" />
    <script type="text/javascript" charset="utf-8" src="${contextPath}/wangEditor/wangEditor.min.js"></script>
    <style>
        .w-e-emoticon-container .w-e-item img{
            width:20px;
            height:20px;
        }
        .preview_phone{
            background: url("${contextPath}/img/phone.png") no-repeat;
            background-size: 100%;
            width: 235px;
            height: 470px;
            position: fixed;
            right: 100px;
            top: 120px;
        }
        .preview_phone #preview_content{
            position: absolute;
            left:7px;
            height:407px;
            width:236px;
            top:46px;
            padding:10px;
            overflow-y:scroll;
        }
        #preview_title{
            font-size: 18px;
            font-weight: bold;
        }
        #preview_time{
            padding:10px 0;
        }
    </style>
</head>
<body>
<%@include file="/WEB-INF/headerMenus.jsp"%>
<main id="main">
    <div class="container">
        <div class="form_container">
            <%@include file="/WEB-INF/mgr/access/newOrEditBaseTitle.jsp"%>
            <form id="editor_form" data-form-name="${webPage.pageTitle}" class="form-horizontal" method="post"
                  action="${contextPath}${webPage.commitEntityAction}${suffix}">
                <c:set var="entity" value="${requestScope.entity}"/>
                <input type="hidden" name="imagePath" value="${entity.imagePath}"/>
                <%@include file="/WEB-INF/mgr/access/newOrEditForm.jsp"%>
                <div class="form-group">
                    <label class="col-sm-1">展示图片</label>
                    <div class="col-sm-7">
                        <c:choose>
                            <c:when test="${empty entity.imagePath}">
                                <div id="carouselImage" style="display:none;margin:10px 0;width:430px;height:242px;background-repeat:no-repeat;background-size:100% 100%;-moz-background-size:100% 100%;"></div>
                            </c:when>
                            <c:otherwise>
                                <div id="carouselImage" style="background-image:url('${managerPath}/access/accessStorageFile${suffix}?hex=${entity.imagePath}');margin:10px 0;width:430px;height:242px;background-repeat:no-repeat;background-size:100% 100%;-moz-background-size:100% 100%;"></div>
                            </c:otherwise>
                        </c:choose>
                        <div id='carouselUploader' class="carouselUploader">
                            <button type="button" class="btn btn-primary uploader-btn-browse">选择文件</button>
                            <button type="button" id="deleteImage" class="btn btn-danger">删除文件</button>
                        </div>
                    </div>
                </div>
                <div class="form-group" id="insidePageContainer" style="display: none;">
                    <label class="col-sm-1">跳转页面</label>
                    <div class="col-sm-5">
                        <input type="text" value="${entity.page}" name="page" class="form-control" id="page" placeholder="请输入跳转页面">
                    </div>
                </div>
                <div class="form-group" id="externalContainer" style="display: none;">
                    <label for="external" class="col-sm-1">外部链接</label>
                    <div data-column-type="TEXT" class="col-sm-5">
                        <input type="text" value="${entity.external}" name="external" class="form-control" id="external" placeholder="请输入外部链接">
                    </div>
                </div>
                <div class="form-group" id="titleContainer" style="display: none;">
                    <label for="title" class="col-sm-1">内容标题</label>
                    <div data-column-type="TEXT" class="col-sm-5">
                        <input type="text" value="${entity.title}" name="title" class="form-control" id="title" placeholder="请输入内容标题">
                    </div>
                </div>
                <div class="form-group" id="contentContainer" style="display: none;">
                    <label for="external" class="col-sm-1">显示内容</label>
                    <div class="col-sm-8">
                        <div id="contentEditor">

                        </div>
                    </div>
                </div>
                <div style="text-align: center;">
                    <button type="button" id="formSubmitAction" class="btn btn-wide btn-primary">提交保存</button>
                    <a href="${webPage.backPage}"  class="btn btn-back btn-wide">返回</a>
                </div>
            </form>
            <div class="preview_phone" style="display: none;">
                <div id="preview_content">
                    <c:choose>
                        <c:when test="${empty entity}">
                            <div id="preview_title">文档标题</div>
                        </c:when>
                        <c:otherwise>
                            <div id="preview_title">${entity.title}</div>
                        </c:otherwise>
                    </c:choose>
                    <%--
                    <div id="preview_time"><%=new Date().toLocaleString()%></div>--%>
                    <div id="update_content"></div>
                </div>
            </div>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript" src="${contextPath}/js/wangEditorCeator.js"></script>
<script type="text/javascript">
    var editor;
    function changeLinkType(){
        var linkType=$("#linkType").val();
        $("#externalContainer").hide();
        $("#insidePageContainer").hide();
        $("#contentContainer").hide();
        $("#titleContainer").hide();
        $(".preview_phone").hide();
        if(linkType=="0"){
            $("#externalContainer").show();
        }else if(linkType=="1"){
            $("#insidePageContainer").show();
        }else if(linkType=="2"){
            $("#titleContainer").show();
            $("#contentContainer").show();
            $(".preview_phone").show();
        }
    }
    function pageReady(doc) {
        $("#linkType").on("change",function(){
            changeLinkType();
        });
        changeLinkType();
        //上传轮播图
        $("#carouselUploader").uploader({
            autoUpload: true,            // 当选择文件后立即自动进行上传操作
            url: '${managerPath}/access/tempUpload${suffix}',  // 文件上传提交地址
            filters:{
                // 只允许上传图片或图标（.ico）
                mime_types: [
                    {title: '图片', extensions: 'jpg,jpeg,png,bmp'}
                ],
                max_file_size: '1mb',
                prevent_duplicates: true
            },
            rename:false,
            renameExtension:false,
            renameByClick:false,
            unique_names:true,
            multi_selection:false,
            chunk_size:0,//不执行分片
            limitFilesCount:false,
            flash_swf_url:"${contextPath}/zui/lib/uploader/Moxie.swf",
            silverlight_xap_url:"${contextPath}/zui/lib/uploader/Moxie.xap",
            responseHandler:function(resp, file){
                var result=$.parseJSON(resp.response);
                if(result.msg=="success"){
                    var hex=result.body;
                    var imageUrl="${managerPath}/access/accessTempFile${suffix}?hex="+hex;
                    $("#carouselImage").css("background-image","url("+imageUrl+")").show();
                    $("input[name='imagePath']").val(hex);
                }
            }
        });
        $(".uploader-files").css("display","none");
        $("#deleteImage").click(function(){
            confirmOper("警告","确实要删除选中的图片?",function(){
                $("input[name='imagePath']").val("");
                $("#carouselImage").hide();
            })
        });
        //设置内容
        editor=createEditor("${contextPath}","${suffix}","contentEditor",function change(html){
            $("#update_content").html(html);
        });
        var content=HTMLDecode("${entity.content}");
        editor.txt.html(content);
        $("#update_content").html(content);
        $("#titleContainer input[name='title']").keyup(function(){
            $("#preview_title").html($(this).val());
        })
    }
    function customFormValidate($this,$formData){
        if($formData.seq==""){
            $formData.seq="1";
        }
        if($formData.imagePath==""){
            toast("轮播图片没有上传");
            $this.removeClass("disabled");
            return false;
        }
        var contentHtml=editor.txt.html();
        $formData["content"]=contentHtml;
        return true;
    }
</script>
</body>
</html>