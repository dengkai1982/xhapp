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
            <form id="editor_form" data-form-name="${webPage.pageTitle}" class="form-horizontal" method="post"
                  action="${contextPath}${webPage.commitEntityAction}${suffix}">
                <%@include file="/WEB-INF/mgr/access/newOrEditForm.jsp"%>
                <div class="form-group">
                    <label for="answer" class="col-sm-1 required">正确答案</label>
                    <div class="col-sm-8">
                        <input type="text" validate="required:正确答案未选择" value="${entity.answer}" name="answer" class="form-control" id="answer" readonly>
                    </div>
                </div>
                <div id="answerContainer" style="display: none;padding: 0 0 0 142px;">
                    <button type="button" id="newAnswerButton" class="btn btn-wide btn-danger">新增答案</button>
                    <table class="table table-striped table-bordered table-hover" style="margin-top:10px">
                        <thead>
                        <tr>
                            <th style="width: 10%">答案编号</th>
                            <th>答案描述</th>
                            <th style="width: 10%">正确答案</th>
                            <th style="width: 10%">操作</th>
                        </tr>
                        </thead>
                        <tbody id="answerListContainer">
                            <c:forEach items="${entity.choiceAnswerStream}" var="answer">
                                <tr data-id="${answer.entityId}">
                                    <td class="optionName">${answer.optionName}</td>
                                    <td class="detailValue">${answer.detailValue}</td>
                                    <c:choose>
                                        <c:when test="${entity.questionType.itemNumber==0}">
                                            <c:choose>
                                                <c:when test="${answer.checked}">
                                                    <td><input type="radio" checked="checked" class="singleRadio" name="answerRadio" optionName="${answer.optionName}"></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td><input type="radio" class="singleRadio" name="answerRadio" optionName="${answer.optionName}"></td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${answer.checked}">
                                                    <td><input type="checkbox" checked="checked" optionName="${answer.optionName}" class="multipleCheckBox"/></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td><input type="checkbox" optionName="${answer.optionName}" class="multipleCheckBox"/></td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                    <td><a href="###" class="text-danger editAnswer"><i class="icon icon-edit"></i></a>&nbsp;<a href="###" class="text-danger deleteAnswer"><i class="icon icon-trash"></i></a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <%@include file="/WEB-INF/mgr/access/formCommitButton.jsp"%>
            </form>
        </div>
    </div>
</main>
<div class="modal fade" id="newOrEditAnswerModal">
    <div class="modal-dialog" style="width:520px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title" >新增修改答案</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="newOrEditAnswerForm">
                    <input type="hidden" name="modal_answerId">
                    <input type="hidden" name="modal_is_editor">
                    <div class="form-group">
                        <label for="optionName_name" class="col-sm-2 required">答案编号</label>
                        <div class="col-sm-9 ref_optionName_name">
                            <input type="text"  class="form-control" validate="required:答案编号必须输入" name="optionName_name" id="optionName_name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="detailValue_name" class="col-sm-2 required">答案描述</label>
                        <div class="col-sm-9 ref_detailValue_name">
                            <input type="text"  class="form-control" validate="required:答案描述必须输入" name="detailValue_name" id="detailValue_name">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">取消返回</button>
                <button type="button" class="btn btn-danger" id="newOrEditAnswerFormAction">确定</button>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript" src="${contextPath}/js/category.js"></script>
<script type="text/javascript">
    function pageReady(doc) {
        var single=false;
        $("#categoryReference").removeClass("popupSingleChoose").click(function(){
            queryCategory("选择试题所属类别","category","categoryReference",function(){
                console.log("close");
            })
        })
        $("#answerListContainer").on("click",".singleRadio",function(){
            $("#answer").val($(this).attr("optionName"));
        });
        $("#answerListContainer").on("click",".multipleCheckBox",function(){
            var checkArray=new Array();
            $("#answerListContainer .multipleCheckBox").each(function(){
                var $this=$(this);
                if($this.is(':checked')){
                    checkArray.push($this.attr("optionName"))
                }
            })
            $("#answer").val(checkArray.join(","));
        });
        $("#answerListContainer").on("click",".deleteAnswer",function(){
            var $tr=$(this).parents("tr");
            var answerId=$tr.attr("data-id");
            confirmOper("消息","确实要删除选中的答案?",function(){
                if(answerId!=""){
                    postJSON("${managerPath}/examination/question/deleteAnswer${suffix}",{
                        entityId:answerId
                    },"正在执行,请稍后...",function(result){
                        if(result.code==SUCCESS){
                            $("#answer").val("");
                            $tr.remove();
                        }else{
                            showMessage(result.msg,1500);
                        }
                    });
                }else{
                    $("#answer").val("");
                    $tr.remove();
                }
            })
        });
        $("#answerListContainer").on("click",".editAnswer",function(){
            var $tr=$(this).parents("tr");
            var answerId=$tr.attr("data-id");
            var optionName=$tr.find(".optionName").html();
            var detailValue=$tr.find(".detailValue").html();
            $("#newOrEditAnswerModal input[name='modal_answerId']").val(answerId);
            $("#newOrEditAnswerModal input[name='optionName_name']").val(optionName);
            $("#newOrEditAnswerModal input[name='detailValue_name']").val(detailValue);
            $("#newOrEditAnswerModal input[name='modal_is_editor']").val("true");
            $("#newOrEditAnswerModal").modal("show");
        });
        var $questionType=$("#questionType").val();
        if($questionType=="0"){
            $("#answerContainer").show();
            single=true;
        }else if($questionType=="1"){
            $("#answerContainer").show();
            single=false;
        }else{
            $("#answerContainer").hide();

        }

        $("#questionType").change(function(){
            var questionType=$(this).val();
            if(questionType=="0"){
                $("#answerContainer").show();
                single=true;
                $("#answer").val("");
            }else if(questionType=="1"){
                $("#answerContainer").show();
                $("#answer").val("");
                single=false;
            }else{
                $("#answerContainer").hide();
                $("#answer").val("-");
            }
            $("#answerListContainer").empty();

        })
        $("#newAnswerButton").click(function(){
            $("#newOrEditAnswerModal input[name='modal_answerId']").val("");
            $("#newOrEditAnswerModal input[name='optionName_name']").val("");
            $("#newOrEditAnswerModal input[name='detailValue_name']").val("");
            $("#newOrEditAnswerModal input[name='modal_is_editor']").val("false");
            $("#newOrEditAnswerModal").modal("show");
        })
        $("#newOrEditAnswerFormAction").click(function(){
            var flag=$("#newOrEditAnswerForm").formValidate(function(el,hint){
                $(".ref_"+el.attr("name")).addClass("has-error");
                toast(hint);
                el.focus().keydown(function(){
                    $(".ref_"+el.attr("name")).removeClass("has-error");
                })
            });
            if(flag){
                $("#newOrEditAnswerModal").modal("hide");
                var $form=$("#newOrEditAnswerForm").formToJson();
                if($form.modal_is_editor=="true"){
                    var dataId=$form.modal_answerId;
                    var $tr=$("#answerListContainer").find("tr[data-id='"+dataId+"']");
                    $tr.find(".optionName").html($form.optionName_name);
                    $tr.find(".detailValue").html($form.detailValue_name);
                }else{
                    var context={
                        answer:{
                            entityId:new Date().getTime(),
                            optionName:$form.optionName_name,
                            detailValue:$form.detailValue_name,
                            single:single
                        }
                    }
                    var html=getTemplateHtml("answerList",context,$);
                    $("#answerListContainer").append(html);
                }
            }
        })
    }
    function customFormValidate($this,$formData){
        var choiceAnswer=new Array();
        $("#answerListContainer tr").each(function(){
            var $tr=$(this);
            choiceAnswer.push({
                entityId:$tr.attr("data-id"),
                optionName:$tr.find(".optionName").html(),
                detailValue:$tr.find(".detailValue").html()
            })
        });
        $formData["choiceAnswer"]=JSON.stringify(choiceAnswer);
        return true;
    }
</script>
</body>
</html>