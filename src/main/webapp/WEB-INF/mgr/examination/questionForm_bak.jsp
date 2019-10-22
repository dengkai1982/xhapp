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
                <div class="form-group" id="detailContainer">
                    <label for="detailEditor" class="col-sm-1 required">试题题目</label>
                    <div class="col-sm-11">
                        <div id="detailEditor">

                        </div>
                    </div>
                </div>
                <%@include file="/WEB-INF/mgr/access/newOrEditForm.jsp"%>
                <div class="form-group" id="analysisContainer">
                    <label for="analysisEditor" class="col-sm-1">问题解析</label>
                    <div class="col-sm-11">
                        <div id="analysisEditor">

                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="answer" class="col-sm-1 required">正确答案</label>
                    <div class="col-sm-8">
                        <input type="text" validate="required:正确答案未选择" value="${entity.answer}" name="answer" class="form-control" id="answer" readonly>
                    </div>
                </div>
                <div id="answerContainer" style="display: none;">
                    <button type="button" id="newAnswerButton" class="btn btn-wide btn-danger">新增答案</button>
                    <table class="table table-striped table-bordered table-hover" style="margin-top:10px">
                        <thead>
                        <tr>
                            <th style="width: 10%">答案编号</th>
                            <th>答案描述</th>
                            <th style="width: 10%">正确答案</th>
                            <th style="width: 10%">操作</th>
                        </tr>
                        </thead>
                        <tbody id="answerListContainer">
                            <c:forEach items="${entity.choiceAnswerStream}" var="answer">
                                <tr data-id="${answer.entityId}">
                                    <td class="optionName">${answer.optionName}</td>
                                    <td class="detailValue">${answer.detailValue}</td>
                                    <c:choose>
                                        <c:when test="${entity.questionType.itemNumber==0}">
                                            <c:choose>
                                                <c:when test="${answer.checked}">
                                                    <td><input type="radio" checked="checked" class="singleRadio" name="answerRadio" optionName="${answer.optionName}"></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td><input type="radio" class="singleRadio" name="answerRadio" optionName="${answer.optionName}"></td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${answer.checked}">
                                                    <td><input type="checkbox" checked="checked" optionName="${answer.optionName}" class="multipleCheckBox"/></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td><input type="checkbox" optionName="${answer.optionName}" class="multipleCheckBox"/></td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                    <td><a href="###" class="text-danger editAnswer"><i class="icon icon-edit"></i></a>&nbsp;<a href="###" class="text-danger deleteAnswer"><i class="icon icon-trash"></i></a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <%@include file="/WEB-INF/mgr/access/formCommitButton.jsp"%>
            </form>
        </div>
    </div>
</main>
<div class="modal fade" id="newOrEditAnswerModal">
    <div class="modal-dialog" style="width:520px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title" >新增修改答案</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="newOrEditAnswerForm">
                    <input type="hidden" name="modal_answerId">
                    <input type="hidden" name="modal_is_editor">
                    <div class="form-group">
                        <label for="optionName_name" class="col-sm-2 required">答案编号</label>
                        <div class="col-sm-9 ref_optionName_name">
                            <input type="text"  class="form-control" validate="required:答案编号必须输入" name="optionName_name" id="optionName_name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="detailValue_name" class="col-sm-2 required">答案描述</label>
                        <div class="col-sm-9 ref_detailValue_name">
                            <input type="text"  class="form-control" validate="required:答案描述必须输入" name="detailValue_name" id="detailValue_name">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">取消返回</button>
                <button type="button" class="btn btn-danger" id="newOrEditAnswerFormAction">确定</button>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/footerPage.jsp"%>
<script type="text/javascript" src="${contextPath}/js/category.js"></script>
<script type="text/javascript">
    var detailEditor;
    var analysisEditor;
    var single=false;
    function pageReady(doc) {
        $("#categoryReference").removeClass("popupSingleChoose").click(function(){
            queryQuestionCategory("选择试题所属类别","category","categoryReference",function(){
                console.log("close");
            })
        })
        $("#simulationCategoryReference").removeClass("popupSingleChoose").click(function(){
            simulationQuestionCategory("选择试题模拟考试分类","simulationCategory","simulationCategoryReference",function(){
                console.log("close");
            })
        })
        $("#answerListContainer").on("click",".singleRadio",function(){
            $("#answer").val($(this).attr("optionName"));
        });
        $("#answerListContainer").on("click",".multipleCheckBox",function(){
            var checkArray=new Array();
            $("#answerListContainer .multipleCheckBox").each(function(){
                var $this=$(this);
                if($this.is(':checked')){
                    checkArray.push($this.attr("optionName"))
                }
            })
            $("#answer").val(checkArray.join(","));
        });
        $("#answerListContainer").on("click",".deleteAnswer",function(){
            var $tr=$(this).parents("tr");
            var answerId=$tr.attr("data-id");
            confirmOper("消息","确实要删除选中的答案?",function(){
                if(answerId!=""){
                    postJSON("${managerPath}/examination/question/deleteAnswer${suffix}",{
                        entityId:answerId
                    },"正在执行,请稍后...",function(result){
                        if(result.code==SUCCESS){
                            $("#answer").val("");
                            $tr.remove();
                        }else{
                            showMessage(result.msg,1500);
                        }
                    });
                }else{
                    $("#answer").val("");
                    $tr.remove();
                }
            })
        });
        $("#answerListContainer").on("click",".editAnswer",function(){
            var $tr=$(this).parents("tr");
            var answerId=$tr.attr("data-id");
            var optionName=$tr.find(".optionName").html();
            var detailValue=$tr.find(".detailValue").html();
            $("#newOrEditAnswerModal input[name='modal_answerId']").val(answerId);
            $("#newOrEditAnswerModal input[name='optionName_name']").val(optionName);
            $("#newOrEditAnswerModal input[name='detailValue_name']").val(detailValue);
            $("#newOrEditAnswerModal input[name='modal_is_editor']").val("true");
            $("#newOrEditAnswerModal").modal("show");
        });
        var $questionType=$("#questionType").val();
        if($questionType=="0"){
            $("#answerContainer").show();
            single=true;
        }else if($questionType=="1"){
            $("#answerContainer").show();
            single=false;
        }else{
            $("#answerContainer").hide();

        }

        $("#questionType").change(function(){
            var questionType=$(this).val();
            if(questionType=="0"){
                $("#answerContainer").show();
                single=true;
                $("#answer").val("");
            }else if(questionType=="1"){
                $("#answerContainer").show();
                $("#answer").val("");
                single=false;
            }else{
                $("#answerContainer").hide();
                $("#answer").val("-");
            }
            $("#answerListContainer").empty();

        })
        $("#newAnswerButton").click(function(){
            $("#newOrEditAnswerModal input[name='modal_answerId']").val("");
            $("#newOrEditAnswerModal input[name='optionName_name']").val("");
            $("#newOrEditAnswerModal input[name='detailValue_name']").val("");
            $("#newOrEditAnswerModal input[name='modal_is_editor']").val("false");
            $("#newOrEditAnswerModal").modal("show");
        })
        $("#newOrEditAnswerFormAction").click(function(){
            var flag=$("#newOrEditAnswerForm").formValidate(function(el,hint){
                $(".ref_"+el.attr("name")).addClass("has-error");
                toast(hint);
                el.focus().keydown(function(){
                    $(".ref_"+el.attr("name")).removeClass("has-error");
                })
            });
            if(flag){
                $("#newOrEditAnswerModal").modal("hide");
                var $form=$("#newOrEditAnswerForm").formToJson();
                if($form.modal_is_editor=="true"){
                    var dataId=$form.modal_answerId;
                    var $tr=$("#answerListContainer").find("tr[data-id='"+dataId+"']");
                    $tr.find(".optionName").html($form.optionName_name);
                    $tr.find(".detailValue").html($form.detailValue_name);
                }else{
                    var context={
                        answer:{
                            entityId:new Date().getTime(),
                            optionName:$form.optionName_name,
                            detailValue:$form.detailValue_name,
                            single:single
                        }
                    }
                    var html=getTemplateHtml("answerList",context,$);
                    $("#answerListContainer").append(html);
                }
            }
        });
        detailEditor=setEditorContent("#detailEditor","${entity.detail}");
        analysisEditor=setEditorContent("#analysisEditor","${entity.analysis}")
        $(".w-e-text-container").height(150);
    }

    function setEditorContent(contentEditor,content){
        var E = window.wangEditor;
        editor = new E(contentEditor);
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
        editor.txt.html(HTMLDecode(content));
        $("#previewContent").click(function(){
            //获取含有html标签的内容
            //console.log(editor.txt.html());
            //console.log(editor.txt.text());
            $("#previewContainer").html(editor.txt.html());
            $("#previewModal").modal("show")
        })
        $(".file-icon").css("width","100px").css("height","100px");
        return editor;
    }

    function fileUploadedHandler(formFieldId,file){
        $(".file-icon").css("width","100px").css("height","100px");
    }
    function customFormValidate($this,$formData){
        var choiceAnswer=new Array();
        $("#answerListContainer tr").each(function(){
            var $tr=$(this);
            choiceAnswer.push({
                entityId:$tr.attr("data-id"),
                optionName:$tr.find(".optionName").html(),
                detailValue:$tr.find(".detailValue").html()
            })
        });
        $formData["choiceAnswer"]=JSON.stringify(choiceAnswer);
        var detailHtml=detailEditor.txt.html();
        if(detailHtml=="<p><br></p>"){
            toast("试题题目必须输入")
            return false;
        }
        $formData["detail"]=detailHtml;
        var analysisHtml=analysisEditor.txt.html();
        /*if(analysisHtml=="<p><br></p>"){
            toast("问题解析必须输入")
            return false;
        }*/
        $formData["analysis"]=analysisHtml;
        return true;
    }
</script>
</body>
</html>