<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page isELIgnored="false" %>
<%@ page import="kaiyi.puer.web.servlet.WebInteractive" %>
<%@ page import="kaiyi.puer.web.servlet.WebPage" %>
<%@ page import="kaiyi.app.xhapp.controller.mgr.ManagerController" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://kaiyi.puer.web/jsp/jstl/enums" prefix="enums"%>
<%@ taglib uri="http://kaiyi.puer.web/jsp/jstl/currency" prefix="currency"%>
<%@ taglib uri="http://kaiyi.puer.web/jsp/jstl/webPage" prefix="webpage"%>
<%@ taglib uri="http://kaiyi.web.authority/visit" prefix="visit"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="prefix" value="<%=ManagerController.prefix%>"/>
<c:set var="managerMenus" value="<%=session.getAttribute(ManagerController.NAME_SESSION_MENU)%>"/>
<c:set var="managerPath" value="<%=request.getServletContext().getContextPath()+ManagerController.prefix%>"/>
<c:set var="childAutors" value="<%=session.getAttribute(ManagerController.NAME_SESSION_AUTHORS)%>"/>
<c:set var="pagination" value="<%=request.getAttribute(ManagerController.PAGINATION)%>"/>
<c:set var="paginationCurrentPage" value="<%=WebInteractive.PAGINATION_PARAMETER_CURRENT_PAGE%>"/>
<c:set var="paginationFirst" value="<%=WebInteractive.PAGINATION_PARAMETER_FIRST%>"/>
<c:set var="paginationMaxResult" value="<%=WebInteractive.PAGINATION_PARAMETER_MAX_RESULT%>"/>
<c:set var="paginationSortBy" value="<%=WebInteractive.ORDER_SORT_BY%>"/>
<c:set var="paginationOrderType" value="<%=WebInteractive.ORDER_TYPE%>"/>
<c:set var="webPage" value="<%=request.getAttribute(WebPage.WEB_PAGE)%>"/>
<c:set var="suffix" value=".xhtml"/>
<c:set var="managerUser" value="<%=ManagerController.getLoginedUser(request)%>"/>
<c:set var="system_name" value="鑫鸿电商平台后台管理系统"/>
<c:set var="ecoInfo" value="四川金软科技有限公司"/>