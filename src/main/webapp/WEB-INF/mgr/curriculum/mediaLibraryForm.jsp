<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/htmlHeader.jsp"%>
    <!--[if IE]>
    <script type="text/javascript" charset="utf-8" src="${contextPath}/js/es6-promise.min.js"></script>
    <![endif]-->
    <script type="text/javascript" charset="utf-8" src="${contextPath}/js/aliyun-oss-sdk-5.3.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${contextPath}/js/aliyun-upload-sdk-1.5.0.min.js"></script>
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
                <input type="hidden" name="entityId" value="${entity.entityId}">
                <div class="form-group">
                    <label for="name" class="col-sm-1 required">视频名称</label>
                    <div data-column-type="TEXT" class="col-sm-3">
                        <input type="text" value="${entity.name}" name="name" class="form-control" id="name" placeholder="请输入视频名称">
                    </div>
                    <label for="online" class="col-sm-1 required">视频类型</label>
                    <div data-column-type="TEXT" class="col-sm-3">
                        <select name="online" id="online" data-placeholder="请选择视频类型" class="chosen-select form-control">
                            <c:choose>
                                <c:when test="${entity.online}">
                                    <option value="true" selected>在线视频</option>
                                    <option value="false">本地视频</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="true">在线视频</option>
                                    <option value="false" selected>本地视频</option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </div>
                </div>
                <div class="form-group" id="mediaUploaderPanel">
                    <label for="mediaUploader" class="col-sm-1">视频文件</label>
                    <div class="col-sm-7">
                        <input type="text" id="fileName" class="form-control" readonly style="margin-bottom: 10px"/>
                        <label id="upload_progress" style="display: none;">视频上传中,当前进度<span id="progress_value">0</span>%</label>
                        <div id='mediaUploader' class="mediaUploader">
                            <button type="button" class="btn btn-primary uploader-btn-browse">选择文件</button>
                        </div>
                    </div>
                </div>
                <div class="form-group" id="urlPanel">
                    <label for="url" class="col-sm-1 required">播放地址</label>
                    <div class="col-sm-7">
                        <input type="text" value="${entity.url}" name="url" class="form-control" id="url" placeholder="请输入视频名称">
                    </div>
                </div>
                <input type="file" id="fileUpload" style="display: none;" accept=".flv,.mp4,.mp3">
                <div style="text-align: center;">
                    <button type="button" id="commitToMediaLibrary" class="btn btn-wide btn-primary">确认提交</button>
                    <a href="${webPage.backPage}"  class="btn btn-back btn-wide">返回</a>
                </div>
            </form>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    var uploader;
    var chooseFile;
    var fileTitle;
    var fileName;
    var online=true;
    function changeOnline(){
        if($("#online").val()=="true"){
            online=true;
            $("#mediaUploaderPanel").show();
            $("#urlPanel").hide();
        }else{
            online=false;
            $("#mediaUploaderPanel").hide();
            $("#urlPanel").show();
        }
    }
    function pageReady(doc) {
        changeOnline();
        $("#online").change(function(){
            changeOnline();
        })
        $(".mediaUploader .uploader-btn-browse").click(function(){
            $("#fileUpload").click();
        });
        $("#fileUpload").change(function(e){
            chooseFile = e.target.files[0];
            if(chooseFile){
                $("#fileName").val(chooseFile.name)
            }
        })
        $("#commitToMediaLibrary").click(function(){
            var $form=$("#editor_form").formToJson();
            if($form.title==""){
                toast("视频名称必须填写");
                return;
            }
            if(online){
                fileTitle=$form.name;
                if($form.entityId==""&&!chooseFile){
                    toast("请先选择需要上传的文件!")
                    return
                }
                fileName = chooseFile.name
                var userData = '{"Vod":{}}'
                if (uploader) {
                    uploader.stopUpload()
                    $('#auth-progress').text('0')
                    $('#status').text("")
                }
                uploader = createUploader();
                uploader.addFile(chooseFile, null, null, null, userData);
                $("#upload_progress").show();
                uploader.startUpload();
                $(this).addClass("disabled");
            }else{
                if($form.url==""){
                    toast("播放地址未指定!")
                    return;
                }
                $(this).addClass("disabled");
                postJson("${managerPath}/curriculum/commitMediaLibrary${suffix}",{
                    title:$form.name,
                    url:$form.url,
                    online:false
                },function(data){
                    if(data.code==SUCCESS){
                        bootbox.alert({
                            title:'消息',
                            message: "操作成功,点击确认返回",
                            size: 'small',
                            callback: function () {
                                window.location.href="${webPage.backPage}";
                            }
                        });
                    }
                });
            }
        })
    }
    function resetUpload(){
        uploader=undefined;
        chooseFile=undefined;
        fileTitle=undefined;
        fileName=undefined;
        $("#upload_progress").hide();
        $("#progress_value").html("");
        $("#commitToMediaLibrary").removeClass("disabled");
    }
    function createUploader () {
        var uploader = new AliyunUpload.Vod({
            userId:"${requestScope.aliyunUserId}",
            //上传到点播的地域， 默认值为'cn-shanghai',//eu-central-1,ap-southeast-1
            region:"cn-shanghai",
            //分片大小默认1M，不能小于100K
            partSize: 1048576,
            //并行上传分片个数，默认5
            parallel: 5,
            //网络原因失败时，重新上传次数，默认为3
            retryCount: 3,
            //网络原因失败时，重新上传间隔时间，默认为2秒
            retryDuration: 2,
            addFileSuccess: function (uploadInfo) {
                console.log('addFileSuccess')
                console.log(uploadInfo)
            },
            onUploadstarted: function (uploadInfo) {
                if (uploadInfo.videoId) {
                    // 如果 uploadInfo.videoId 存在, 调用 刷新视频上传凭证接口(https://help.aliyun.com/document_detail/55408.html)
                    postJson("${managerPath}/curriculum/refreshUploadVideoRequest${suffix}",{
                        videoId:uploadInfo.videoId
                    },function(data){
                        console.log(data);
                        var uploadAuth = data.UploadAuth
                        var uploadAddress = data.UploadAddress
                        var videoId = data.VideoId
                        uploader.setUploadAuthAndAddress(uploadInfo, uploadAuth, uploadAddress,videoId)
                    });
                }
                else{
                    // 如果 uploadInfo.videoId 不存在,调用 获取视频上传地址和凭证接口(https://help.aliyun.com/document_detail/55407.html)
                    postJson("${managerPath}/curriculum/uploadVideoRequest${suffix}",{
                        fileName:fileName,
                        title:fileTitle
                    },function(data){
                        console.log(data);
                        var uploadAuth = data.UploadAuth
                        var uploadAddress = data.UploadAddress
                        var videoId = data.VideoId
                        uploader.setUploadAuthAndAddress(uploadInfo, uploadAuth, uploadAddress,videoId)
                    });
                }
            },
            // 文件上传成功
            'onUploadSucceed': function (uploadInfo) {
                console.log("upload success");
                console.log(uploadInfo)
                //获取访问ID
                var $form=$("#editor_form").formToJson();
                postJson("${managerPath}/curriculum/commitMediaLibrary${suffix}",{
                    title:$form.name,
                    videoId:uploadInfo.videoId,
                    online:true
                },function(data){
                    if(data.code==SUCCESS){
                        bootbox.alert({
                            title:'消息',
                            message: "操作成功,点击确认返回",
                            size: 'small',
                            callback: function () {
                                window.location.href="${webPage.backPage}";
                            }
                        });
                    }
                });
            },
            // 文件上传失败
            'onUploadFailed': function (uploadInfo, code, message) {
                toast("文件上传失败: file:" + uploadInfo.file.name + ",code:" + code + ", message:" + message);
                resetUpload();
            },
            // 文件上传进度，单位：字节
            'onUploadProgress': function (uploadInfo, totalSize, loadedPercent) {
                $("#progress_value").html(Math.ceil(loadedPercent * 100));
                //console.log("onUploadProgress:file:" + uploadInfo.file.name + ", fileSize:" + totalSize + ", percent:" + Math.ceil(loadedPercent * 100) + "%");
            },
            // 上传凭证超时
            'onUploadTokenExpired': function (uploadInfo) {
                toast("上传凭证超时");
                resetUpload();
                //实现时，根据uploadInfo.videoId调用刷新视频上传凭证接口重新获取UploadAuth
                //https://help.aliyun.com/document_detail/55408.html
                //从点播服务刷新的uploadAuth,设置到SDK里
                //uploader.resumeUploadWithAuth(uploadAuth);
            },
            //全部文件上传结束
            'onUploadEnd':function(uploadInfo){
                console.log("onUploadEnd: uploaded all the files");
                console.log(uploadInfo)
            }
        });
        return uploader;
    }
</script>
</body>
</html>