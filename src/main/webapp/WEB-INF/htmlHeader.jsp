<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<meta charset='utf-8'>
<meta http-equiv='X-UA-Compatible' content='IE=edge'>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="renderer" content="webkit">
<title>${system_name}</title>
<link media='screen' rel="stylesheet" href="${contextPath}/css/zui-theme-default.css" />
<link media='screen' id="linkChangeTheme" rel="stylesheet" href="${contextPath}/css/zui-theme-classic.css" />
<link rel="stylesheet" href="${contextPath}/zui/lib/datagrid/zui.datagrid.min.css" />
<link rel="stylesheet" href="${contextPath}/zui/lib/datetimepicker/datetimepicker.min.css">
<link rel="stylesheet" href="${contextPath}/zui/lib/chosen/chosen.min.css">
<link rel="stylesheet" href="${contextPath}/zui/lib/chosenicons/zui.chosenicons.min.css">
<link rel="stylesheet" href="${contextPath}/zui/lib/uploader/zui.uploader.min.css">
<link rel="stylesheet" href="${contextPath}/zui/lib/bootbox/bootbox.min.css">
<link rel="stylesheet" href="${contextPath}/zui/lib/dashboard/zui.dashboard.min.css">
<link rel="stylesheet" href="${contextPath}/iconfont/iconfont.css" />
<link rel="stylesheet" href="${contextPath}/css/adapter.css" />
<script type="text/javascript" charset="utf-8" src="${contextPath}/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/zui/js/zui.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/js/jquery.blockUI.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/zui/lib/datagrid/zui.datagrid.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/zui/lib/chosen/chosen.min.js" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/zui/lib/chosenicons/zui.chosenicons.min.js" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/zui/lib/uploader/zui.uploader.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/zui/lib/datetimepicker/datetimepicker.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/zui/lib/bootbox/bootbox.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/zui/lib/dashboard/zui.dashboard.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/zui/lib/sortable/zui.sortable.min.js"></script>
<!--[if lt IE 9]>
<div class="alert alert-danger">您正在使用 <strong>过时的</strong> 浏览器. 是时候 <a
href="https://www.google.cn/chrome/">请下载最新浏览器</a> 来提升用户体验.
</div>
<![endif]-->
<!--针对IE8浏览器，我们引入html5shiv来使得HTML5标签在IE8中也能使用-->
<!--[if lt IE 9]>
<script src="${contextPath}/zui/lib/ieonly/html5shiv.js"></script>
<script src="${contextPath}/zui/lib/ieonly/respond.js"></script>
<script src="${contextPath}/zui/lib/ieonly/excanvas.js"></script>
<![endif]-->
<script type="text/javascript" charset="utf-8" src="${contextPath}/template7/template7.min.js" ></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/js/js-ext.js"></script>
<script type="text/javascript" charset="utf-8" src="${contextPath}/js/manager.js"></script>
<link rel="icon" href="${contextPath}/img/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${contextPath}/img/favicon.ico" type="image/x-icon" />
<%@ include file="/WEB-INF/template.jsp"%>
<script>
    var contextPath="${contextPath}";
    var manager="${managerPath}";
    var suffix="${suffix}";
    var tokenName="${tokenName}";

</script>