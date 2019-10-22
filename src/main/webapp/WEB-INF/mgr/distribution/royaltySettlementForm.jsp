<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/htmlHeader.jsp"%>
    <style>
        .col-sm-3{
            width:22%;
        }
        .form_container #editor_form label{
            width: 130px;
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
                <input type="hidden" name="entityId" value="${entity.entityId}">
                <input type="hidden" name="popupSelectValueResult">
                <input type="hidden" name="maybeCommit"/>
                <div class="form-group">
                    <label for="account" class="col-sm-1 required">结算账户</label>
                    <div data-column-type="REFERENCE" class="col-sm-3">
                        <div class="input-group">
                            <input type="hidden" id="account" name="account" value="${entity.account.entityId}" class="query_value" data-ref-value="account"/>
                            <input id="accountReference" name="accountReference" type="text" class="form-control popupSingleChoose" data-ref-value="account" readonly placeholder="点击选择"
                                   value="${entity.account.showAccountName}"
                                   data-service-name="accountService"
                                   data-search-title-name="查找搜索"
                                   data-action-button-name="立即选择"
                                   data-field-name="showAccountName" validate="required:结算账户必须选择"/>
                            <span class="input-group-addon border_right clear_query_search_input"><i class="icon icon-close"></i></span>
                        </div>
                    </div>
                    <label for="royaltyType" class="col-sm-1 required">结算类型</label>
                    <div  class="col-sm-3">
                        <select name="royaltyType" id="royaltyType"  validate="required:结算类型必须选择" data-placeholder="" class='form-control chosen-select'>
                            <option></option>
                            <c:forEach items="${requestScope.typeChosen}" var="type">
                                <c:choose>
                                    <c:when test="${type.selected}">
                                        <option value='${type.value}' selected>${type.html}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value='${type.value}'>${type.html}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                    <label for="settlementTime" class="col-sm-1 required">结算时间</label>
                    <div  class="col-sm-3">
                        <div class="input-group">
                            <input type="text" name="settlementTime" id="settlementTime" validate="required:结算时间必须选择" value="<fmt:formatDate value='${entity.settlementTime}' pattern="yyyy-MM-dd"/>" class="form-control form-date" readonly placeholder="点击选择">
                            <span class="input-group-addon border_right clear_date_input"><i class="icon icon-close"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="recommend1" class="col-sm-1">直接上级</label>
                    <div class="col-sm-3">
                        <input type="text" id="recommend1" readonly class="form-control" value="${entity.recommend1.showAccountName}"/>
                    </div>
                    <label for="level1Amount" class="col-sm-1">直接上级提成金额</label>
                    <div data-column-type="NUMBER" class="col-sm-3">
                        <input type="number" id="level1Amount" readonly class="form-control" value="<currency:convert value='${entity.level1Amount}'/>">
                    </div>
                    <label for="recommend2" class="col-sm-1">上上级</label>
                    <div class="col-sm-3">
                        <input type="text" id="recommend2" readonly class="form-control" value="${entity.recommend2.showAccountName}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="level2Amount" class="col-sm-1">上上级提成金额</label>
                    <div data-column-type="NUMBER" class="col-sm-3">
                        <input type="number" id="level2Amount" readonly class="form-control" value="<currency:convert value='${entity.level2Amount}'/>">
                    </div>
                    <label for="insideMember" class="col-sm-1">内部员工</label>
                    <div class="col-sm-3">
                        <input type="text" id="insideMember" readonly class="form-control" value="${entity.insideMember.showAccountName}"/>
                    </div>
                    <label for="insideAmount" class="col-sm-1">内部员工提成金额</label>
                    <div class="col-sm-3">
                        <input type="number" id="insideAmount" readonly class="form-control" value="<currency:convert value='${entity.insideAmount}'/>">
                    </div>
                </div>
                <%@include file="/WEB-INF/mgr/access/formCommitButton.jsp"%>
            </form>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    function pageReady(doc) {
        $("#royaltyType").change(function(e){
            generatorRoyalty();
        })
    }
    function generatorRoyalty(){
        var royaltyTypeId=$("#royaltyType").val();
        var accountId=$("#account").val();
        if(royaltyTypeId!=""&&accountId!=""){
            postJSON("${managerPath}/distribution/royaltySettlement/generator${suffix}",{
                royaltyTypeId:royaltyTypeId,
                accountId:accountId
            },"正在执行,请稍后...",function(result){
                $("input[name='maybeCommit']").val("false");
                if(result.royaltySettlement.recommend1){
                    $("input[name='maybeCommit']").val("true");
                    $("#recommend1").val(result.royaltySettlement.recommend1);
                    $("#level1Amount").val(result.royaltySettlement.level1Amount);
                    if(result.royaltySettlement.recommend2){
                        $("#recommend2").val(result.royaltySettlement.recommend2);
                        $("#level2Amount").val(result.royaltySettlement.level2Amount);
                    }
                    if(result.royaltySettlement.insideMember){
                        $("#insideMember").val(result.royaltySettlement.insideMember);
                        $("#insideAmount").val(result.royaltySettlement.insideAmount)
                    }
                }
                console.log(result);
            });
        }

    }
    function popupSingleChooseTriggerClose(){
        generatorRoyalty();
    }
    function customFormValidate($this,$formData){
        if($("input[name='maybeCommit']").val()=="false"){
            toast("没有可结算的上级用户");
            return false;
        }else{
            return true;
        }
    }
</script>
</body>
</html>