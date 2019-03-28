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
                <input type="hidden" name="popupSelectValueResult">
                <div class="form-group">
                    <label for="name" class="col-sm-1">视频名称</label>
                    <div data-column-type="TEXT" class="col-sm-3">
                        <input type="text" value="${entity.name}" name="name" class="form-control" id="name" placeholder="请输入视频名称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-1">视频文件</label>
                    <div class="col-sm-7">
                        <div id='mediaUploader' class="mediaUploader">
                            <button type="button" class="btn btn-primary uploader-btn-browse">选择文件</button>
                            <button type="button" id="deleteImage" class="btn btn-danger">删除文件</button>
                        </div>
                    </div>
                </div>
                <input type="file" id="fileUpload" style="display: none;">
                <%@include file="/WEB-INF/mgr/access/formCommitButton.jsp"%>
            </form>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    var uploader;
    function pageReady(doc) {
        $(".mediaUploader .uploader-btn-browse").click(function(){
            $("#fileUpload").click();
        })
        $("#fileUpload").change(function(e){
            var file = e.target.files[0]
            if (!file) {
                toast("请先选择需要上传的文件!")
                return
            }
            var title = file.name
            var userData = '{"Vod":{}}'
            if (uploader) {
                uploader.stopUpload()
                $('#auth-progress').text('0')
                $('#status').text("")
            }
            uploader = createUploader()
            // 首先调用 uploader.addFile(event.target.files[i], null, null, null, userData)
            console.log(uploader)
            uploader.addFile(file, null, null, null, userData);
            uploader.startUpload();
        })
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
                }
                else{
                    // 如果 uploadInfo.videoId 不存在,调用 获取视频上传地址和凭证接口(https://help.aliyun.com/document_detail/55407.html)
                    getJson("${managerPath}/curriculum/uploadVideoRequest${suffix}",{

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
                uploadInfo.videoId;
            },
            // 文件上传失败
            'onUploadFailed': function (uploadInfo, code, message) {
                console.log("onUploadFailed: file:" + uploadInfo.file.name + ",code:" + code + ", message:" + message);
            },
            // 文件上传进度，单位：字节
            'onUploadProgress': function (uploadInfo, totalSize, loadedPercent) {
                console.log("onUploadProgress:file:" + uploadInfo.file.name + ", fileSize:" + totalSize + ", percent:" + Math.ceil(loadedPercent * 100) + "%");
            },
            // 上传凭证超时
            'onUploadTokenExpired': function (uploadInfo) {
                console.log("onUploadTokenExpired");
                //实现时，根据uploadInfo.videoId调用刷新视频上传凭证接口重新获取UploadAuth
                //https://help.aliyun.com/document_detail/55408.html
                //从点播服务刷新的uploadAuth,设置到SDK里
                uploader.resumeUploadWithAuth(uploadAuth);
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