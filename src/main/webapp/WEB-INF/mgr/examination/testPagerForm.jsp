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
                <input type="hidden" name="selectQuestionId"/>
                <input type="hidden" name="selectQuestionDetail"/>
                <%@include file="/WEB-INF/mgr/access/newOrEditForm.jsp"%>
                <div id="testPagerQuestionContainer" style="padding: 0 0 0 142px;">
                    <button type="button" id="newAnswerButton" class="btn btn-wide btn-danger">添加试题</button>
                    <table class="table table-striped table-bordered table-hover" style="margin-top:10px">
                        <thead>
                            <tr>
                                <th>试题描述</th>
                                <th style="width:8%">分值</th>
                                <th style="width:8%">权重</th>
                                <th style="width:8%">操作</th>
                            </tr>
                        </thead>
                        <tbody id="questionContainer">
                            <c:forEach items="${requestScope.testPagerQuestions}" var="question">
                                <tr data-id="${question.entityId}">
                                    <td class="detail" data-id="${question.question.entityId}">${question.question.detail}</td>
                                    <td class="score"><input type="number" value="${question.score}"/></td>
                                    <td class="weight"><input type="number" value="${question.weight}"/></td>
                                    <td><a href="###" class="text-danger deleteQuestion"><i class="icon icon-trash"></i></a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th colspan="4" style="text-align: right">分值合计：<span class="totalScore">0</span></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                <%@include file="/WEB-INF/mgr/access/formCommitButton.jsp"%>
            </form>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    var questionArray;
    function pageReady(doc) {
        $("#newAnswerButton").click(function(){
            var showName=$("input[name='selectQuestionDetail']");
            var showValue=$("input[name='selectQuestionId']");
            questionArray=new Array();
            openMultipleChooseTrigger(showName,showValue,"questionService","detail","试题选择","立即选择","questionArray");
        });
        computerTotalScore();
        $("#questionContainer").on("keyup",".score input",function(){
            computerTotalScore();
        })
        $("#questionContainer").on("click",".deleteQuestion",function(){
            var $tr=$(this).parents("tr");
            var testPagerQuestionId=$tr.attr("data-id");
            confirmOper("消息","确实要删除选中的试题?",function(){
                postJSON("${managerPath}/examination/testPager/removeTestPagerQuestion${suffix}",{
                    entityId:testPagerQuestionId
                },"正在执行,请稍后...",function(result){
                    if(result.code==SUCCESS){
                        $tr.remove();
                        computerTotalScore();
                    }else{
                        showMessage(result.msg,1500);
                    }
                });
            })
        })
    }
    function popupMultipleChooseTriggerClose(){
        if(questionArray.length==0)return;
        var context={
            question:new Array()
        }
        var time=parseInt(new Date().getTime());
        for(i=0;i<questionArray.length;i++){
            var qa=questionArray[i];
            context.question.push({
                testPagerQuestionId:time++,
                questionId:qa.entityId,
                score:qa.score,
                weight:1,
                detail:qa.detail
            })
        }
        var html=getTemplateHtml("questionList",context,$);
        $("#questionContainer").append(html);
        computerTotalScore();
    }
    function computerTotalScore(){
        var total=0;
        $("#questionContainer tr").each(function(i,d){
            var $tr=$(d);
            var score=$tr.find(".score").find("input").val();
            total+=parseInt(score);
        })
        $(".totalScore").html(total);
    }
    function getExistQuestion(){
        var entityIdArray=new Array();
        $("#questionContainer tr").each(function(i,d){
            var $tr=$(d);
            var entityId=$tr.find(".detail").attr("data-id");
            entityIdArray.push(entityId);
        })
        return entityIdArray;
    }

    function queryBoxExtendParams(serviceName,fieldName){
        var entityIdArray=getExistQuestion();
        return {
            existEntityIdArray:entityIdArray.join("_")
        }
    }
    function customFormValidate($this,$formData){
        var testPagerQuestion=new Array();
        $("#questionContainer tr").each(function(i,d){
            var $tr=$(this);
            testPagerQuestion.push({
                questionId:$tr.find(".detail").attr("data-id"),
                score:$tr.find(".score").find("input").val(),
                weight:$tr.find(".weight").find("input").val()
            });
        })
        $formData["testPagerQuestion"]=JSON.stringify(testPagerQuestion);
        return true;
    }
</script>
</body>
</html>