<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/htmlHeader.jsp"%>
    <link rel="stylesheet" href="${contextPath}/wangEditor/wangEditor.min.css" />
    <script type="text/javascript" charset="utf-8" src="${contextPath}/wangEditor/wangEditor.min.js"></script>
    <style>
        .w-e-emoticon-container .w-e-item img{
            width:20px;
            height:20px;
        }
    </style>
</head>
<body>
<%@include file="/WEB-INF/headerMenus.jsp"%>
<main id="main">
    <div class="container">
        <div class="form_container">
            <%@include file="/WEB-INF/mgr/access/newOrEditBaseTitle.jsp"%>
            <form id="editor_form" data-form-name="${webPage.pageTitle}" class="form-horizontal" method="post"
                  action="${contextPath}${webPage.commitEntityAction}${suffix}">
                <c:set var="entity" value="${requestScope.entity}"/>
                <input type="hidden" name="entityId" value="${entity.entityId}">
                <div class="form-group">
                    <label for="title" class="col-sm-1 required">公告标题</label>
                    <div data-column-type="TEXT" class="col-sm-5">
                        <input type="text" data-validate="required:公告标题必须输入" value="${entity.title}" name="title" class="form-control" id="title" placeholder="请输入公告标题">
                    </div>
                </div>
                <div class="form-group" id="contentContainer">
                    <label for="contentEditor" class="col-sm-1 required">公告内容</label>
                    <div class="col-sm-11">
                        <div id="contentEditor">

                        </div>
                    </div>
                </div>
                <div style="text-align: center;">
                    <button type="button" id="formSubmitAction" class="btn btn-wide btn-primary">提交保存</button>
                    <a href="${webPage.backPage}"  class="btn btn-back btn-wide">返回</a>
                </div>
            </form>
        </div>
    </div>
</main>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript">
    var editor;
    function pageReady(doc) {
        var E = window.wangEditor;
        editor = new E('#contentEditor');
        editor.customConfig.zIndex = 1;
        //配置菜单
        editor.customConfig.menus = [
            'head',  // 标题
            'bold',  // 粗体
            'fontSize',  // 字号
            'fontName',  // 字体
            'italic',  // 斜体
            'underline',  // 下划线
            'strikeThrough',  // 删除线
            'foreColor',  // 文字颜色
            'backColor',  // 背景颜色
            'link',  // 插入链接
            'list',  // 列表
            'justify',  // 对齐方式
            'emoticon',  // 表情
            'image',  // 插入图片
            'undo',  // 撤销
            'redo'  // 重复
        ];
        //设置背景颜色
        editor.customConfig.colors = [
            '#000000',
            '#eeece0',
            '#1c487f',
            '#4d80bf',
            '#c24f4a',
            '#8baa4a',
            '#7b5ba1',
            '#46acc8',
            '#f9963b',
            '#ffffff'
        ];
        //设置表情
        editor.customConfig.emotions = [{
            title:'QQ表情',
            type:'image',
            content:[{alt:'[NO]',src:'${contextPath}/image/qq_biao_qing/88.gif'},{alt:'[OK]',src:'${contextPath}/image/qq_biao_qing/89.gif'},{alt:'[爱情]',src:'${contextPath}/image/qq_biao_qing/90.gif'},{alt:'[飞吻]',src:'${contextPath}/image/qq_biao_qing/91.gif'},{alt:'[跳跳]',src:'${contextPath}/image/qq_biao_qing/92.gif'},{alt:'[发抖]',src:'${contextPath}/image/qq_biao_qing/93.gif'},{alt:'[怄火]',src:'${contextPath}/image/qq_biao_qing/94.gif'},{alt:'[转圈]',src:'${contextPath}/image/qq_biao_qing/95.gif'},{alt:'[磕头]',src:'${contextPath}/image/qq_biao_qing/96.gif'},{alt:'[回头]',src:'${contextPath}/image/qq_biao_qing/97.gif'},{alt:'[尴尬]',src:'${contextPath}/image/qq_biao_qing/10.gif'},{alt:'[跳绳]',src:'${contextPath}/image/qq_biao_qing/98.gif'},{alt:'[发怒]',src:'${contextPath}/image/qq_biao_qing/11.gif'},{alt:'[挥手]',src:'${contextPath}/image/qq_biao_qing/99.gif'},{alt:'[调皮]',src:'${contextPath}/image/qq_biao_qing/12.gif'},{alt:'[呲牙]',src:'${contextPath}/image/qq_biao_qing/13.gif'},{alt:'[惊讶]',src:'${contextPath}/image/qq_biao_qing/14.gif'},{alt:'[难过]',src:'${contextPath}/image/qq_biao_qing/15.gif'},{alt:'[酷]',src:'${contextPath}/image/qq_biao_qing/16.gif'},{alt:'[冷汗]',src:'${contextPath}/image/qq_biao_qing/17.gif'},{alt:'[抓狂]',src:'${contextPath}/image/qq_biao_qing/18.gif'},{alt:'[吐]',src:'${contextPath}/image/qq_biao_qing/19.gif'},{alt:'[微笑]',src:'${contextPath}/image/qq_biao_qing/0.gif'},{alt:'[撇嘴]',src:'${contextPath}/image/qq_biao_qing/1.gif'},{alt:'[色]',src:'${contextPath}/image/qq_biao_qing/2.gif'},{alt:'[发呆]',src:'${contextPath}/image/qq_biao_qing/3.gif'},{alt:'[得意]',src:'${contextPath}/image/qq_biao_qing/4.gif'},{alt:'[流泪]',src:'${contextPath}/image/qq_biao_qing/5.gif'},{alt:'[害羞]',src:'${contextPath}/image/qq_biao_qing/6.gif'},{alt:'[闭嘴]',src:'${contextPath}/image/qq_biao_qing/7.gif'},{alt:'[睡]',src:'${contextPath}/image/qq_biao_qing/8.gif'},{alt:'[大哭]',src:'${contextPath}/image/qq_biao_qing/9.gif'},{alt:'[偷笑]',src:'${contextPath}/image/qq_biao_qing/20.gif'},{alt:'[可爱]',src:'${contextPath}/image/qq_biao_qing/21.gif'},{alt:'[白眼]',src:'${contextPath}/image/qq_biao_qing/22.gif'},{alt:'[傲慢]',src:'${contextPath}/image/qq_biao_qing/23.gif'},{alt:'[饥饿]',src:'${contextPath}/image/qq_biao_qing/24.gif'},{alt:'[困]',src:'${contextPath}/image/qq_biao_qing/25.gif'},{alt:'[惊恐]',src:'${contextPath}/image/qq_biao_qing/26.gif'},{alt:'[流汗]',src:'${contextPath}/image/qq_biao_qing/27.gif'},{alt:'[憨笑]',src:'${contextPath}/image/qq_biao_qing/28.gif'},{alt:'[大兵]',src:'${contextPath}/image/qq_biao_qing/29.gif'},{alt:'[奋斗]',src:'${contextPath}/image/qq_biao_qing/30.gif'},{alt:'[咒骂]',src:'${contextPath}/image/qq_biao_qing/31.gif'},{alt:'[疑问]',src:'${contextPath}/image/qq_biao_qing/32.gif'},{alt:'[嘘]',src:'${contextPath}/image/qq_biao_qing/33.gif'},{alt:'[晕]',src:'${contextPath}/image/qq_biao_qing/34.gif'},{alt:'[折磨]',src:'${contextPath}/image/qq_biao_qing/35.gif'},{alt:'[衰]',src:'${contextPath}/image/qq_biao_qing/36.gif'},{alt:'[骷髅]',src:'${contextPath}/image/qq_biao_qing/37.gif'},{alt:'[敲打]',src:'${contextPath}/image/qq_biao_qing/38.gif'},{alt:'[再见]',src:'${contextPath}/image/qq_biao_qing/39.gif'},{alt:'[擦汗]',src:'${contextPath}/image/qq_biao_qing/40.gif'},{alt:'[抠鼻]',src:'${contextPath}/image/qq_biao_qing/41.gif'},{alt:'[鼓掌]',src:'${contextPath}/image/qq_biao_qing/42.gif'},{alt:'[糗大了]',src:'${contextPath}/image/qq_biao_qing/43.gif'},{alt:'[坏笑]',src:'${contextPath}/image/qq_biao_qing/44.gif'},{alt:'[左哼哼]',src:'${contextPath}/image/qq_biao_qing/45.gif'},{alt:'[右哼哼]',src:'${contextPath}/image/qq_biao_qing/46.gif'},{alt:'[哈欠]',src:'${contextPath}/image/qq_biao_qing/47.gif'},{alt:'[鄙视]',src:'${contextPath}/image/qq_biao_qing/48.gif'},{alt:'[委屈]',src:'${contextPath}/image/qq_biao_qing/49.gif'},{alt:'[快哭了]',src:'${contextPath}/image/qq_biao_qing/50.gif'},{alt:'[阴险]',src:'${contextPath}/image/qq_biao_qing/51.gif'},{alt:'[亲亲]',src:'${contextPath}/image/qq_biao_qing/52.gif'},{alt:'[吓]',src:'${contextPath}/image/qq_biao_qing/53.gif'},{alt:'[可怜]',src:'${contextPath}/image/qq_biao_qing/54.gif'},{alt:'[菜刀]',src:'${contextPath}/image/qq_biao_qing/55.gif'},{alt:'[西瓜]',src:'${contextPath}/image/qq_biao_qing/56.gif'},{alt:'[啤酒]',src:'${contextPath}/image/qq_biao_qing/57.gif'},{alt:'[篮球]',src:'${contextPath}/image/qq_biao_qing/58.gif'},{alt:'[乒乓]',src:'${contextPath}/image/qq_biao_qing/59.gif'},{alt:'[咖啡]',src:'${contextPath}/image/qq_biao_qing/60.gif'},{alt:'[饭]',src:'${contextPath}/image/qq_biao_qing/61.gif'},{alt:'[猪头]',src:'${contextPath}/image/qq_biao_qing/62.gif'},{alt:'[玫瑰]',src:'${contextPath}/image/qq_biao_qing/63.gif'},{alt:'[凋谢]',src:'${contextPath}/image/qq_biao_qing/64.gif'},{alt:'[示爱]',src:'${contextPath}/image/qq_biao_qing/65.gif'},{alt:'[爱心]',src:'${contextPath}/image/qq_biao_qing/66.gif'},{alt:'[心碎]',src:'${contextPath}/image/qq_biao_qing/67.gif'},{alt:'[蛋糕]',src:'${contextPath}/image/qq_biao_qing/68.gif'},{alt:'[闪电]',src:'${contextPath}/image/qq_biao_qing/69.gif'},{alt:'[炸弹]',src:'${contextPath}/image/qq_biao_qing/70.gif'},{alt:'[刀]',src:'${contextPath}/image/qq_biao_qing/71.gif'},{alt:'[足球]',src:'${contextPath}/image/qq_biao_qing/72.gif'},{alt:'[瓢虫]',src:'${contextPath}/image/qq_biao_qing/73.gif'},{alt:'[便便]',src:'${contextPath}/image/qq_biao_qing/74.gif'},{alt:'[月亮]',src:'${contextPath}/image/qq_biao_qing/75.gif'},{alt:'[太阳]',src:'${contextPath}/image/qq_biao_qing/76.gif'},{alt:'[礼物]',src:'${contextPath}/image/qq_biao_qing/77.gif'},{alt:'[拥抱]',src:'${contextPath}/image/qq_biao_qing/78.gif'},{alt:'[强]',src:'${contextPath}/image/qq_biao_qing/79.gif'},{alt:'[激动]',src:'${contextPath}/image/qq_biao_qing/100.gif'},{alt:'[街舞]',src:'${contextPath}/image/qq_biao_qing/101.gif'},{alt:'[献吻]',src:'${contextPath}/image/qq_biao_qing/102.gif'},{alt:'[左太极]',src:'${contextPath}/image/qq_biao_qing/103.gif'},{alt:'[右太极]',src:'${contextPath}/image/qq_biao_qing/104.gif'},{alt:'[弱]',src:'${contextPath}/image/qq_biao_qing/80.gif'},{alt:'[握手]',src:'${contextPath}/image/qq_biao_qing/81.gif'},{alt:'[胜利]',src:'${contextPath}/image/qq_biao_qing/82.gif'},{alt:'[抱拳]',src:'${contextPath}/image/qq_biao_qing/83.gif'},{alt:'[勾引]',src:'${contextPath}/image/qq_biao_qing/84.gif'},{alt:'[拳头]',src:'${contextPath}/image/qq_biao_qing/85.gif'},{alt:'[差劲]',src:'${contextPath}/image/qq_biao_qing/86.gif'},{alt:'[爱你]',src:'${contextPath}/image/qq_biao_qing/87.gif'}]
        },{
            title:"emoji",
            type:"emoji",
            content:['😀','😁','😂','😃','😄','😅','😆','😉','😊','😋','😎','😍','😘','😗','😙','😚','😇','😐','😑','😶','😏','😣','😥','😮','😯','😪','😫','😴','😌','😛','😜','😝','😒','😓','😔','😕','😲','😷','😖','😞','😟','😤','😢','😭','😦','😧','😨','😬','😰','😱','😳','😵','😡','😠']
        }];
        //关闭图片链接
        editor.customConfig.showLinkImg = false;
        //上传图片配置
        //editor.customConfig.uploadImgShowBase64 = true
        editor.customConfig.uploadImgServer = '${managerPath}/access/tempUpload${suffix}';
        editor.customConfig.uploadImgHooks = {
            customInsert: function (insertImg, result, editor) {
                var url = "${managerPath}/access/accessTempFile${suffix}?hex="+result.body
                insertImg(url)
            },
            error: function (xhr, editor) {
                toast("图片上传错误");
            },
            timeout: function (xhr, editor) {
                toast("图片上传超时");
            },
        }
        editor.create();
        editor.txt.html(HTMLDecode("${entity.content}"));
        $("#previewContent").click(function(){
            //获取含有html标签的内容
            //console.log(editor.txt.html());
            //console.log(editor.txt.text());
            $("#previewContainer").html(editor.txt.html());
            $("#previewModal").modal("show")
        })
        $(".file-icon").css("width","100px").css("height","100px");
    }
    function fileUploadedHandler(formFieldId,file){
        $(".file-icon").css("width","100px").css("height","100px");
    }
    function customFormValidate($this,$formData){
        if($formData.title==""){
            toast("公告标题必须输入")
            return false;
        }
        var contentHtml=editor.txt.html();
        if(contentHtml=="<p><br></p>"){
            toast("公告内容必须输入")
            return false;
        }
        $formData["content"]=contentHtml;
        return true;
    }
</script>
</body>
</html>