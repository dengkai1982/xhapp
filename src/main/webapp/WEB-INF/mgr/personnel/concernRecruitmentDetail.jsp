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
            <c:set var="account" value="${entity.account}"></c:set>
            <c:set var="recruitment" value="${entity.recruitment}"></c:set>
            <c:set var="enterprise" value="${recruitment.enterprise}"></c:set>
            <div class="detail">
                <div class="detail-title">关注人详情<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <table class="table table-data table-condensed table-borderless">
                        <tbody>
                        <tr>
                            <th class="strong">会员手机号</th>
                            <td>${account.phone}</td>
                            <th class="strong">注册时间</th>
                            <td><fmt:formatDate value="${account.registerTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <th class="strong">会员手机号</th>
                            <td>${account.phone}</td>
                        </tr>
                        <tr>
                            <th class="strong">会员等级</th>
                            <td>${account.memberShip.value}</td>
                            <th class="strong">昵称</th>
                            <td>${account.nickName}</td>
                            <th class="strong">性别</th>
                            <td>${account.sex.value}</td>

                        </tr>
                        <tr>
                            <th class="strong">签名</th>
                            <td>${account.sign}</td>
                            <th class="strong">行业</th>
                            <td>${account.industry}</td>
                            <th class="strong">电子邮箱</th>
                            <td>${account.email}</td>
                        </tr>
                        <tr>
                            <th class="strong">地址</th>
                            <td>${account.address}</td>
                            <th class="strong">关注时间</th>
                            <td><fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="detail-title">招聘信息<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <table class="table table-data table-condensed table-borderless">
                        <tbody>
                        <tr>
                            <th class="strong">招聘岗位</th>
                            <td>${recruitment.position.name}</td>
                            <th class="strong">是否推荐岗位</th>
                            <td><c:choose>
                                <c:when test="${recruitment.recommend}">
                                    推荐岗位
                                </c:when>
                                <c:otherwise>
                                    未推荐
                                </c:otherwise>
                            </c:choose></td>
                            <th class="strong">工作类型</th>
                            <td><c:choose>
                                <c:when test="${recruitment.fullTime}">
                                    全职
                                </c:when>
                                <c:otherwise>
                                    兼职
                                </c:otherwise>
                            </c:choose></td>
                        </tr>
                        <tr>
                            <th class="strong">招聘人数</th>
                            <td>${recruitment.personNumber}</td>
                            <th class="strong">薪资待遇</th>
                            <td>${recruitment.salary}</td>
                            <th class="strong">工作年限要求</th>
                            <td>${recruitment.workYear}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="detail-title">发布企业<span class="icon icon-caret-down"></span></div>
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