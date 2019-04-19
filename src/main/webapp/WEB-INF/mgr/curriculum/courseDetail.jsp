<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/htmlHeader.jsp"%>
    <style>
        .chapter-ul{
            list-style: none;
            margin: 0;
            padding: 0;
        }
        .chapter{
            margin-bottom:10px;
        }
        .chapter::after{
            display: block;
            height: 1px;
            content: '';
            background-color: #e3e5e9;
        }
        .chapter .chapter-top{
            display: -webkit-flex;
            display:flex;
            justify-content: space-between;
            align-items:baseline;
        }
        .chapter .iconfont{
            font-size:16px;
        }
        .chapter .chapter-top .name{
            font-size:16px;
            color:#07111b;
            flex-grow:1;
            margin-left:10px;
        }
        .chapter .function a{
            margin-right: 15px;
        }
        .chapter .function a:hover{
            text-decoration: underline;
        }
        .chapter p{
            margin:8px 0 8px 26px;
        }
        .chapter .movie-item{
            list-style: none;
            margin: 0 0 0 26px;
            padding:0;
        }
        .chapter .movie-item::before{
            display: block;
            height: 1px;
            content: '';
            background-color: #e3e5e9;
        }
        .chapter .movie-item .item{
            display: -webkit-flex;
            display: flex;
            height: 30px;
            line-height: 30px;
            justify-content: space-between;
            align-items:baseline;
            border-bottom: 1px #e3e5e9 dashed;
        }
        .clear-border{
            border:none!important;
        }
        .chapter .movie-item .item .name{
            flex-grow:1;
        }
        .photo_container img{
            width: 100px;
            height:100px;
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
                    <h2>${webPage.pageTitle}</h2>
                    <div class="pull-right btn-toolbar">
                        <a href="${webPage.backPage}&expandId=${requestScope.course.category.entityId}" type="button" class="btn btn-link"><i class="icon-back muted"></i> 返回</a>
                    </div>
                </div>
            </div>
            <div class="detail">
                <div class="detail-title">课程封面<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <div class="photo_container">
                        <c:choose>
                            <c:when test="${empty requestScope.entity.cover}">
                                <img src="${contextPath}/img/default_header.png"/>
                            </c:when>
                            <c:otherwise>
                                <img src="${managerPath}/access/accessStorageFile${suffix}?hex=${requestScope.entity.cover}"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="detail-title">基本信息<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <%@include file="/WEB-INF/mgr/access/entityDetail.jsp"%>
                </div>
                <div class="detail-title">章节信息<span class="icon icon-caret-down"></span></div>
                <div class="detail-content">
                    <ul class="chapter-ul">
                        <c:forEach items="${requestScope.chapters}" var="chapter">
                            <li class="chapter">
                                <div class="chapter-top">
                                    <i class="iconfont icon-mn_yingyong_fill"></i>
                                    <div class="name">${chapter.name}</div>
                                </div>
                                <p class="desc">${chapter.detail}</p>
                                <ul class="movie-item">
                                    <c:forEach items="${chapter.courseMovieList}" var="movie" varStatus="vs">
                                        <c:choose>
                                            <c:when test="${vs.last}">
                                                <li class="item clear-border">
                                                    <div class="name">${movie.name} - (${movie.longTime})</div>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="item">
                                                    <div class="name">${movie.name} - (${movie.longTime})</div>
                                                </li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div style="text-align: center;">
                    <button type="button" id="scrollTopAction" class="btn btn-wide btn-secondary">回到顶部</button>
                    <a href="${webPage.backPage}&expandId=${requestScope.course.category.entityId}" class="btn btn-back btn-wide">返回</a>
                </div>
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