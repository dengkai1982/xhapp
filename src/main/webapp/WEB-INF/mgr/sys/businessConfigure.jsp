<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/htmlHeader.jsp"%>
    <style>
        legend{
            font-size:14px;
            padding:10px;
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
                    <h2>业务配置</h2>
                </div>
            </div>
            <form id="editor_form" data-form-name="${webPage.pageTitle}" class="form-horizontal" method="post"
                  action="${contextPath}${webPage.commitEntityAction}${suffix}">
                <c:set var="configure" value="${requestScope.configureMap}" />
                <%--<fieldset>
                    <legend>随机生成试题参数配置(<span style="color:#f60">注:三种试题数量相加必须等于100</span>)</legend>
                    <div class="form-group">
                        <label for="EXAM_QUESTION_SINGLE_NUMBER" class="col-sm-1">单选题数量</label>
                        <div class="col-sm-2">
                            <input type="number" class="form-control" id="EXAM_QUESTION_SINGLE_NUMBER" value="${configure['EXAM_QUESTION_SINGLE_NUMBER']}" name="EXAM_QUESTION_SINGLE_NUMBER" placeholder="请填写单选题生成数量">
                        </div>
                        <label for="EXAM_QUESTION_MULTIPLE_NUMBER" class="col-sm-1">多选题数量</label>
                        <div class="col-sm-2">
                            <input type="number" class="form-control" id="EXAM_QUESTION_MULTIPLE_NUMBER" value="${configure['EXAM_QUESTION_MULTIPLE_NUMBER']}" name="EXAM_QUESTION_MULTIPLE_NUMBER" placeholder="请填写多选题生成数量">
                        </div>
                        <label for="EXAM_QUESTION_ANSWER_NUMBER" class="col-sm-1">问答题数量</label>
                        <div class="col-sm-2">
                            <input type="number" class="form-control" id="EXAM_QUESTION_ANSWER_NUMBER" value="${configure['EXAM_QUESTION_ANSWER_NUMBER']}" name="EXAM_QUESTION_ANSWER_NUMBER" placeholder="请填写单选题生成数量">
                        </div>
                    </div>
                </fieldset>--%>
                <fieldset>
                    <legend>课程提现配置</legend>
                    <div class="form-group">
                        <label for="SALE_LEVEL_COMMISSION_1" class="col-sm-1" style="width:130px;">直接上级提成比例</label>
                        <div class="col-sm-2">
                            <div class="input-group">
                                <input type="number" class="form-control" id="SALE_LEVEL_COMMISSION_1" value="${configure['SALE_LEVEL_COMMISSION_1']}" name="SALE_LEVEL_COMMISSION_1" placeholder="请填写直接上级提成比例">
                                <span class="input-group-addon" style="border-right:1px solid #dcdcdc">%</span>
                            </div>
                        </div>
                        <label for="SALE_LEVEL_COMMISSION_2" class="col-sm-1" style="width:130px;">上上级提成比例</label>
                        <div class="col-sm-2">
                            <div class="input-group">
                                <input type="number" class="form-control" id="SALE_LEVEL_COMMISSION_2" value="${configure['SALE_LEVEL_COMMISSION_2']}" name="SALE_LEVEL_COMMISSION_2" placeholder="请填写上上级提成比例">
                                <span class="input-group-addon" style="border-right:1px solid #dcdcdc">%</span>
                            </div>
                        </div>
                        <label for="INSIDE_MEMBER_COMMISSION" class="col-sm-1" style="width:130px;">内部员工提成比例</label>
                        <div class="col-sm-2">
                            <div class="input-group">
                                <input type="number" class="form-control" id="INSIDE_MEMBER_COMMISSION" value="${configure['INSIDE_MEMBER_COMMISSION']}" name="INSIDE_MEMBER_COMMISSION" placeholder="请填写内部员工提成比例">
                                <span class="input-group-addon" style="border-right:1px solid #dcdcdc">%</span>
                            </div>
                        </div>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>课程提现配置</legend>
                    <label for="LIMIT_WITHDRAW_AMOUNT" class="col-sm-1">提成金额上限</label>
                    <div class="col-sm-2">
                        <input type="number" class="form-control" id="LIMIT_WITHDRAW_AMOUNT" value="${configure['LIMIT_WITHDRAW_AMOUNT']}" name="LIMIT_WITHDRAW_AMOUNT" placeholder="请填写提成金额上限">
                    </div>
                </fieldset>
                <div style="text-align: center;">
                    <button type="button" id="configureCommit" class="btn btn-wide btn-primary">提交保存</button>
                </div>
            </form>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    function pageReady(doc) {
        $("#configureCommit").click(function(){
            var $this=$(this);
            var $formData=$("#editor_form").formToJson();
            postJSON("${managerPath}/sys/commitConfigure${suffix}",$formData,"正在处理请稍后",function(result){
                $this.removeClass("disabled");
                if(result.code==SUCCESS){
                    bootbox.alert({
                        title:'消息',
                        message: "修改配置成功,点击确认关闭",
                        size: 'small',
                        callback: function () {

                        }
                    });
                }else{
                    bootbox.alert({
                        title:"错误",
                        message:result.msg,
                        callback:function(){
                        }
                    });
                }
            });
        })
    }
</script>
</body>
</html>