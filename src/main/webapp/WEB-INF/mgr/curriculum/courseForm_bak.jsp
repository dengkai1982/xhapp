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
    </style>
</head>
<body>
<%@include file="/WEB-INF/headerMenus.jsp"%>
<main id="main">
    <div class="container">
        <div class="form_container">
            <div class="center-block">
                <div class="main-header">
                    <h2>${webPage.pageTitle} -【${requestScope.category.name}】</h2>
                    <div class="pull-right btn-toolbar">
                        <a href="${webPage.backPage}" type="button" class="btn btn-link"><i class="icon-back muted"></i> 返回</a>
                    </div>
                </div>
            </div>
            <form id="editor_form" data-form-name="${webPage.pageTitle}" class="form-horizontal" method="post"
                  action="${contextPath}${webPage.commitEntityAction}${suffix}">
                <c:set var="entity" value="${requestScope.entity}"/>
                <div id="mainMenu" class="clearfix" style="margin-top: 5px;">
                    <div class="pull-left btn-toolbar custom-nav-tabs">
                        <a data-tab href="#tabContent1" class="btn btn-link"><span class="text">课程信息</span></a>
                        <a data-tab href="#tabContent2" class="btn btn-link btn-active-text"><span class="text">课程章节</span></a>
                    </div>
                </div>
                <div id="tabContent1" class="tabContent">
                    <%@include file="/WEB-INF/mgr/access/newOrEditForm.jsp"%>
                </div>
                <div id="tabContent2" class="tabContent active">
                    <div id="chapterContainer">
                        <ul class="chapter-ul">
                            <li class="chapter">
                                <div class="chapter-top">
                                    <i class="iconfont icon-mn_yingyong_fill"></i>
                                    <div class="name">第1章 课程导学</div>
                                    <div class="function">
                                        <a href="#" class="text-danger newChapterMovie">新增章节视频</a>
                                        <a href="#" class="text-success editorChapterMovie">编辑章节</a>
                                        <a href="#" class="text-warning deleteChapter">删除章节</a>
                                    </div>
                                </div>
                                <p class="desc">包括课程概述、课程安排、学习前提等方面的介绍，让同学们对计算机视觉有所理解</p>
                                <ul class="movie-item">
                                    <li class="item">
                                        <div class="name">视频名称</div>
                                        <div class="function">
                                            <a href="#" class="text-danger playMovie">播放</a>
                                            <a href="#" class="text-danger editMovie">编辑</a>
                                            <a href="#" class="text-warning deleteMovie">删除</a>
                                        </div>
                                    </li>
                                    <li class="item clear-border">
                                        <div class="name">视频名称</div>
                                        <div class="function">
                                            <a href="#" class="text-danger playMovie">播放</a>
                                            <a href="#" class="text-danger editMovie">编辑</a>
                                            <a href="#" class="text-warning deleteMovie">删除</a>
                                        </div>
                                    </li>
                                </ul>
                            </li>
                            <li class="chapter">
                                <div class="chapter-top">
                                    <i class="iconfont icon-mn_yingyong_fill"></i>
                                    <div class="name">第1章 课程导学</div>
                                    <div class="function">
                                        <a href="#" class="text-danger newChapterMovie">新增章节视频</a>
                                        <a href="#" class="text-success editorChapterMovie">编辑章节</a>
                                        <a href="#" class="text-warning deleteChapter">删除章节</a>
                                    </div>
                                </div>
                                <p class="desc">包括课程概述、课程安排、学习前提等方面的介绍，让同学们对计算机视觉有所理解</p>
                                <ul class="movie-item">
                                    <li class="item">
                                        <div class="name">视频名称</div>
                                        <div class="function">
                                            <a href="#" class="text-danger playMovie">播放</a>
                                            <a href="#" class="text-danger editMovie">编辑</a>
                                            <a href="#" class="text-warning deleteMovie">删除</a>
                                        </div>
                                    </li>
                                    <li class="item clear-border">
                                        <div class="name">视频名称</div>
                                        <div class="function">
                                            <a href="#" class="text-danger playMovie">播放</a>
                                            <a href="#" class="text-danger editMovie">编辑</a>
                                            <a href="#" class="text-warning deleteMovie">删除</a>
                                        </div>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
                <div style="text-align: center;">
                    <button type="button" class="newChapter btn btn-wide btn-danger" style="display: none">新增章节</button>
                    <button type="button" id="formSubmitAction" class="btn btn-wide btn-primary">提交保存</button>
                    <a href="${webPage.backPage}"  class="btn btn-back btn-wide">返回</a>
                </div>
            </form>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    function pageReady(doc) {
        $('[data-tab]').on('shown.zui.tab', function(e) {
            if($(e.target).attr("href")=="#tabContent2"){
                $(".newChapter").show();
            }else{
                $(".newChapter").hide();
            }
        });
    }
</script>
</body>
</html>