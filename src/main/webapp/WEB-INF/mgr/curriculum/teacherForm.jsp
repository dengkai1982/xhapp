<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/htmlHeader.jsp"%>
    <style>
        .photo_container{
            position: absolute;
            top:58px;
            left:50px;
        }
        img{
            width:128px;
            height:180px;
        }
    </style>
</head>
<body>
<%@include file="/WEB-INF/headerMenus.jsp"%>
<main id="main">
    <div class="container">
        <div class="form_container">
            <div class="photo_container">
                <c:choose>
                    <c:when test="${empty requestScope.entity.photo}">
                        <img src="${contextPath}/img/default_header.png"/>
                    </c:when>
                    <c:otherwise>
                        <img src="${managerPath}/access/accessStorageFile${suffix}?hex=${requestScope.entity.photo}"/>
                    </c:otherwise>
                </c:choose>
                <div style="color:#ff3434;font-size: 10px;">照片大小128*180</div>
                <div style="color:#ff3434;font-size: 10px;">超出部分将自动剪裁</div>
                <div id='teacherPhotoUploader' class="teacherPhotoUploader">
                    <button type="button" class="btn btn-primary uploader-btn-browse">选择文件</button>
                    <button type="button" id="deleteImage" class="btn btn-danger">删除文件</button>
                </div>
            </div>
            <%@include file="/WEB-INF/mgr/access/newOrEditBaseTitle.jsp"%>
            <form style="margin-left:140px;" id="editor_form" data-form-name="${webPage.pageTitle}" class="form-horizontal" method="post"
                  action="${contextPath}${webPage.commitEntityAction}${suffix}">
                <input type="hidden" name="photo" value="${requestScope.entity.photo}"/>
                <%@include file="/WEB-INF/mgr/access/newOrEditForm.jsp"%>
                <%@include file="/WEB-INF/mgr/access/formCommitButton.jsp"%>
            </form>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    function pageReady(doc) {
        $("#teacherPhotoUploader").uploader({
            autoUpload: true,            // 当选择文件后立即自动进行上传操作
            url: '${managerPath}/access/tempUpload${suffix}',  // 文件上传提交地址
            filters:{
                // 只允许上传图片或图标（.ico）
                mime_types: [
                    {title: '图片', extensions: 'jpg,jpeg,png,bmp'}
                ],
                max_file_size: '4mb',
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
            resize:{
                width: 128,
                height: 180,
                crop: true,
                quality: 90,
                preserve_headers: false
            },
            responseHandler:function(resp, file){
                var result=$.parseJSON(resp.response);
                if(result.msg=="success"){
                    var hex=result.body;
                    var imageUrl="${managerPath}/access/accessTempFile${suffix}?hex="+hex;
                    $(".photo_container img").attr("src",imageUrl);
                    $("#editor_form input[name='photo']").val(hex);
                }
            }
        });
        $(".uploader-files").css("display","none");
        $("#deleteImage").click(function(){
            confirmOper("警告","确实要删除选中的照片?",function(){
                $(".photo_container img").attr("src","${contextPath}/img/default_header.png");
                //$("input[name='imagePath']").val("");

            })
        });
        //
    }
</script>
</body>
</html>