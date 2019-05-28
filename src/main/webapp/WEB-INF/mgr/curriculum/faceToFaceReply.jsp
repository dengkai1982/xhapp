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
        <div class="form_container">
            <%@include file="/WEB-INF/mgr/access/newOrEditBaseTitle.jsp"%>
            <div class="detail">
                <div class="detail-title">预约面授<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <%@include file="/WEB-INF/mgr/access/entityDetail.jsp"%>
                </div>
                <div class="detail-title">预约面授详情<span class="icon icon-caret-down"></span></div>
                <form id="editor_form" action="${managerPath}/curriculum/faceToFace/doReply${suffix}" method="post">
                    <div class="detail-content form-horizontal">
                        <c:set value="${requestScope.entity}" var="entity"/>
                        <input type="hidden" name="entityId" value="${entity.entityId}"/>
                        <div class="form-group">
                            <label class="col-sm-1 required">回复内容</label>
                            <div class="col-sm-11">
                                <textarea class="form-control" rows="6" name="replyContent"></textarea>
                            </div>
                        </div>
                    </div>
                    <div style="text-align: center;">
                        <button type="button" id="doReplyAction" class="btn btn-wide btn-primary">提交回复</button>
                        <a href="${webPage.backPage}" id="backAction"  class="btn btn-back btn-wide">返回</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    function pageReady(doc) {
        clearDetailItem("replier");
        clearDetailItem("replyTime");
        clearDetailItem("reply");
        $("#doReplyAction").click(function(){
            var $form=$("#editor_form");
            var $formData=$form.formToJson();
            var $this=$(this);
            $this.addClass("disabled");
            if(!checkValue("required",$formData.replyContent)){
                toast("回复内容必须填写");
                $this.removeClass("disabled");
                return;
            }
            postJSON($form.attr("action"),$formData,"正在处理请稍后",function(result){
                $this.removeClass("disabled");
                if(result.code==SUCCESS){
                    bootbox.alert({
                        title:'消息',
                        message: "预约面授回复成功,点击确认返回",
                        size: 'small',
                        callback: function () {
                            window.location.href=$("#backAction").attr("href");
                        }
                    });
                }else{
                    bootbox.alert({
                        title:"错误",
                        message:result.msg,
                        callback:function(){
                            $("#editor_form input[name='"+tokenName+"']").val(result.body);
                        }
                    });
                }
            });
        })
    }
</script>
</body>
</html>