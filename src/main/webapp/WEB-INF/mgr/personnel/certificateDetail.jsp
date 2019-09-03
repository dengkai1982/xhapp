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
                <div class="detail-title">会员信息<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <table class="table table-data table-condensed table-borderless">
                        <c:set value="${entity.owner}" var="account"/>
                        <tbody>
                        <tr>
                            <th class="strong" name="phone">会员手机号码</th>
                            <td  fieldName="phone">${account.phone}</td>
                            <th class="strong" name="insideMember">内部会员</th>
                            <td  fieldName="insideMember">
                                <c:choose>
                                    <c:when test="${account.insideMember}">
                                        内部会员
                                    </c:when>
                                    <c:otherwise>
                                        外部会员
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <th class="strong" name="memberName">员工姓名</th>
                            <td  fieldName="memberName">${account.memberName}</td>
                        </tr>
                        <tr>
                            <th class="strong" name="registerTime">注册时间</th>
                            <td  fieldName="registerTime">
                                <fmt:formatDate value="${account.registerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                            <th class="strong" name="memberShip">会员等级</th>
                            <td  fieldName="memberShip">${account.memberShip.value}</td>
                            <th class="strong" name="recommend">推荐人</th>
                            <td  fieldName="recommend">${account.recommend.showAccountName}</td>
                        </tr>
                        <tr>
                            <th class="strong" name="teamNumber">团队人数</th>
                            <td  fieldName="teamNumber">${account.teamNumber}</td>
                            <th class="strong" name="sign">签名</th>
                            <td  fieldName="sign">${account.sign}</td>
                            <th class="strong" name="nickName">昵称</th>
                            <td  fieldName="nickName">${account.nickName}</td>
                        </tr>
                        <tr>
                            <th class="strong" name="sex">性别</th>
                            <td  fieldName="sex">${account.sex.value}</td>
                            <th class="strong" name="industry">行业</th>
                            <td  fieldName="industry">${account.industry}</td>
                            <th class="strong" name="email">电子邮箱</th>
                            <td  fieldName="email">${account.email}</td>
                        </tr>
                        <tr>
                            <th class="strong" name="address">地址</th>
                            <td  fieldName="address">${account.address}</td>
                            <th class="strong" name="active">账户状态</th>
                            <td  fieldName="active">
                                <c:choose>
                                    <c:when test="${account.active}">
                                        账户可用
                                    </c:when>
                                    <c:otherwise>
                                        账户冻结
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <th class="strong" name="parentInsideAccount">归属内部员工</th>
                            <td  fieldName="parentInsideAccount">${account.parentInsideAccount.memberName} </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="detail-title">简历详情<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <%@include file="/WEB-INF/mgr/access/entityDetail.jsp"%>
                </div>
                <div class="detail-title">证书图片<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <img src="${managerPath}/access/accessStorageFile${suffix}?hex=${entity.photo}" style="height:300px;margin-right:20px;">
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