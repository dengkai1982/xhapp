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
                <div class="detail-title">招聘详情<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <%@include file="/WEB-INF/mgr/access/entityDetail.jsp"%>
                </div>
                <div class="detail-title">招聘企业信息<span class="icon icon-caret-down"></span></div>
                <c:set var="enterprise" value="${entity.enterprise}"/>
                <div class="detail-content">
                    <table class="table table-data table-condensed table-borderless">
                        <tbody>
                        <tr>
                            <th class="strong" name="enterpriseName">企业名称</th>
                            <td fieldName="enterpriseName">${enterprise.enterpriseName}</td>
                            <th class="strong" name="recommend">推荐企业</th>
                            <td fieldName="recommend">
                                <c:choose>
                                    <c:when test="${enterprise.recommend}">
                                        是
                                    </c:when>
                                    <c:otherwise>
                                        否
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <th class="strong" name="verifyed">认证情况</th>
                            <td  fieldName="verifyed">
                                <c:choose>
                                    <c:when test="${enterprise.verifyed}">
                                        已认证
                                    </c:when>
                                    <c:otherwise>
                                        未认证
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <th class="strong" name="phone">联系电话</th>
                            <td  fieldName="phone">${enterprise.phone}</td>
                            <th class="strong" name="code">三合一编码</th>
                            <td  fieldName="code">${enterprise.code}</td>
                            <th class="strong" name="address">详细地址</th>
                            <td  fieldName="address">${enterprise.address}</td>
                        </tr>
                        <tr>
                            <th class="strong" name="owner">所属账户</th>
                            <td  fieldName="owner">${enterprise.owner.showAccountName}</td>
                            <th class="strong" name="parentInsideAccount">归属内部员工</th>
                            <td  fieldName="parentInsideAccount">${enterprise.parentInsideAccount.memberName}</td>
                        </tr>
                        <tr>
                            <th class="strong" name="content">简介</th>
                            <td  fieldName="content">${enterprise.content}</td>
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