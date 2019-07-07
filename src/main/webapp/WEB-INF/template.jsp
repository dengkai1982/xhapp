<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<script id="operAction" type="text/template7">
    <a href="{{href}}" class="{{classStyle}}" target="{{#if isBlank}}_blank{{else}}_self{{/if}}" access="{{access}}" entityId="{{entityId}}" title="{{title}}">{{showName}}</a>
</script>
<script id="answerList" type="text/template7">
    <tr data-id="{{answer.entityId}}">
        <td class="optionName">{{answer.optionName}}</td>
        <td class="detailValue">{{answer.detailValue}}</td>
        {{#if answer.single}}
        <td><input type="radio" class="singleRadio" name="answerRadio" optionName="{{answer.optionName}}"></td>
        {{else}}
        <td><input type="checkbox" class="multipleCheckBox" optionName="{{answer.optionName}}"/></td>
        {{/if}}
        <td><a href="###" class="text-danger editAnswer"><i class="icon icon-edit"></i></a>&nbsp;<a href="###" class="text-danger deleteAnswer"><i class="icon icon-trash"></i></a></td>
    </tr>
</script>
<script id="questionList" type="text/template7">
    <tr data-id="{{testPagerQuestionId}}">
        <td class="detail" data-id="{{questionId}}">{{detail}}</td>
        <td class="score"><input class="form-control" type="number" value="{{score}}"/></td>
        <td class="weight"><input class="form-control" type="number" value="{{weight}}"/></td>
        <td><a href="###" class="text-danger deleteQuestion"><i class="icon icon-trash"></i></a></td>
    </tr>
</script>


