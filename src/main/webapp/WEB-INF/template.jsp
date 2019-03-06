<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<script id="operAction" type="text/template7">
    <a href="{{href}}" class="{{classStyle}}" target="{{#if isBlank}}_blank{{else}}_self{{/if}}" access="{{access}}" entityId="{{entityId}}" title="{{title}}">{{showName}}</a>
</script>