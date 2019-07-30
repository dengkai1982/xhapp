<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/htmlHeader.jsp"%>
    <style>
        .table-data tbody>tr>th{
            width:120px;
        }
        .table-data tbody>tr>td{
            width:25%;
        }
    </style>
</head>
<body>
<%@include file="/WEB-INF/headerMenus.jsp"%>
<main id="main">
    <div class="container">
        <div class="form_container">
            <%@include file="/WEB-INF/mgr/access/newOrEditBaseTitle.jsp"%>
            <c:set var="entity" value="${requestScope.entity}"/>
            <c:set var="resume" value="${entity.resume}"></c:set>
            <c:set var="enterprise" value="${entity.enterprise}"></c:set>
            <div class="detail">
                <div class="detail-title">简历信息<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <table class="table table-data table-condensed table-borderless">
                        <tbody>
                        <tr>
                            <th class="strong">姓名</th>
                            <td>${resume.name}</td>
                            <th class="strong">创建时间</th>
                            <td><fmt:formatDate value="${resume.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <th class="strong">最近更新</th>
                            <td><fmt:formatDate value="${resume.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        </tr>
                        <tr>
                            <th class="strong">专业</th>
                            <td>${resume.profess}</td>
                            <th class="strong">手机号码</th>
                            <td>${$resume.phone}</td>
                            <th class="strong">身份证号</th>
                            <td>${$resume.idcard}</td>
                        </tr>
                        <tr>
                            <th class="strong">户籍</th>
                            <td>${resume.household}</td>
                            <th class="strong">工作类型</th>
                            <td>
                                <c:choose>
                                    <c:when test="${$resume.fullTime}">
                                        全职
                                    </c:when>
                                    <c:otherwise>
                                        兼职
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <th class="strong">现住址</th>
                            <td>${$resume.houseAddress}</td>
                        </tr>
                        <tr>
                            <th class="strong">意向城市</th>
                            <td>${resume.intentCity}</td>
                            <th class="strong">意向岗位</th>
                            <td>${$resume.position.name}</td>
                            <th class="strong">期望薪资</th>
                            <td>${$resume.salary}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="detail-title">企业信息<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <table class="table table-data table-condensed table-borderless">
                        <tbody>
                        <tr>
                            <th class="strong">企业名称</th>
                            <td>${enterprise.enterpriseName}</td>
                            <th class="strong">联系电话</th>
                            <td>${enterprise.phone}</td>
                            <th class="strong">详细地址</th>
                            <td>${enterprise.address}</td>
                        </tr>
                        <tr>
                            <th class="strong">关注事件</th>
                            <td><fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <%@include file="/WEB-INF/mgr/access/detailPageButton.jsp"%>
            </div>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    function pageReady(doc) {

    }
</script>
</body>
</html>