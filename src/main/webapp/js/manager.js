/********************* 全局变量 ******************************/
var SUCCESS = "success";
var SESSION_INVALIDE="_session_invalide";
//如果有文件上传,那么提交完成后,必须清理uploaderConfig
var uploaderConfig=new Dictionary();
/********************* 操控相关 ******************************/
/*调用template7模板*/
var templateDictionary=new Dictionary();
/**
 * 调用template7模板
 * @param templateId 模板ID
 * @param jsonData json数据
 * @param Dom 可以操作的Dome
 * @returns 调用模板后返回的html字符串
 */
function getTemplateHtml(templateId,jsonData,Dom){
    var key="script#"+templateId;
    var compiledTemplate=templateDictionary.get(key);
    if(!compiledTemplate){
        var templateEl = Dom(key).html();
        compiledTemplate = Template7.compile(templateEl);
        templateDictionary.put(key,compiledTemplate);
    }
    return compiledTemplate(jsonData).trim();
}
//检查菜单是否具备权限
function checkPrivilege(items){
    var i = items.length;
    while(i--){
        var item=items[i];
        if(item.className=="privilege"){
            var visit=$(".subAuthors[data-value='"+item.access+"']").attr("visit");
            if(visit!="true"){
                items.splice(i,1);
            }
        }
    }
}
//尝试对表格字段进行值转换
function tryConvertCell(valueType,dataValue,cell, dataGrid){
    if(functionExist("customDataConvertCell")){
        var newValue=customDataConvertCell(valueType,dataValue,cell, dataGrid);
        if(newValue!=null){
            return newValue;
        }
    }
    if(dataValue){
        if(valueType=="boolean"){
            if(dataValue.ordinal=="true"){
                return "<span class='text-success'>"+dataValue.value+"</span>";
            }else{
                return "<span class='text-danger'>"+dataValue.value+"</span>";
            }
        }else if(valueType=="chosen"){
            return "<span style='color:"+dataValue.hexColor+"'>"+dataValue.value+"</span>";
        }
    }

    return dataValue;
}
//表单验证
//验证失败fn(el,hint) el:对象,hint:提示
$.fn.formValidate=function(fn)
{
    var tag="validate";
    var flag=true;
    this.find("input["+tag+"]").each(function(i,d){
        var $el=$(d);
        var $validates=$el.attr(tag).split(",");
        var $checkValue = $el.val();
        $.each($validates, function(idx, obj) {
            if(!flag){
                return false;
            }
            var $validate=obj.split(":");
            var $checkType=$validate[0];
            var hint=$validate[1];
            if($checkType=="min"){
                if($checkValue.length<parseInt(hint)){
                    fn($el,"长度必须大于"+hint);
                    flag=false;
                }
            }else if($checkType=="max"){
                if($checkValue.length>parseInt(hint)){
                    fn($el,"长度必须小于"+hint);
                    flag=false;
                }
            }else if($checkType=="length"){
                if($checkValue.length!=parseInt(hint)){
                    fn($el,"长度不足"+hint);
                    flag=false;
                }
            }else{
                if(!checkValue($checkType,$checkValue)){
                    fn($el,hint);
                    flag=false;
                }
            }
        });
    });
    this.find("select["+tag+"]").each(function(i,d){
        var $el=$(d);
        var $validates=$el.attr(tag).split(",");
        var $checkValue = $el.val();
        $.each($validates, function(idx, obj) {
            if(!flag){
                return false;
            }
            var $validate=obj.split(":");
            var $checkType=$validate[0];
            var hint=$validate[1];
            if(!checkValue($checkType,$checkValue)){
                fn($el,hint);
                flag=false;
            }
        })
    });
    return flag;
};
//表单内容到json
$.fn.formToJson = function(otherString) {
    var serializeObj = {},
        array = this.serializeArray();
    $(array).each(function() {
        if(serializeObj[this.name]) {
            serializeObj[this.name] += ';' + this.value;
        } else {
            serializeObj[this.name] = this.value;
        }
    });
    if(otherString != undefined) {
        var otherArray = otherString.split(';');
        $(otherArray).each(function() {
            var otherSplitArray = this.split(':');
            serializeObj[otherSplitArray[0]] = otherSplitArray[1];
        });
    }
    return serializeObj;
};
/**
 * 清除所有表单内容，包含hidden单不包含.notClear
 */
$.fn.clearFormAndHidden = function() {
    this.find("input").not(".notClear").not(":button, :submit, :reset").val("").removeAttr('checked').removeAttr('selected');
    this.find("select").val("");
    this.find("textarea").val("").html("");
    $('select.chosen-select').trigger('chosen:updated');
};
//清除表单内容
$.fn.clearForm = function() {
    this.find("input").not(":button, :submit, :reset, :hidden").val("").removeAttr('checked').removeAttr('selected');
    this.find("select").val("");
    this.find("textarea").val("").html("");
    $('select.chosen-select').trigger('chosen:updated');
};
/**
 * 显示toast
 * @param {String} msg 消息
 */
function toast(msg) {
    var myMessager = new $.zui.Messager(msg, {
        placement: "center",
        close: false,
        time: 3000,
        type: "danger"
    }).show();
}
//显示遮罩,需要引入jQuery.blockUI.js
function showMask(msg){
    $.blockUI({
        message:"<div style='padding:10px;text-align:center;width:140px'>" +
        "<i class='icon icon-spin icon-spinner-indicator' style='font-size:32px;color:#fff'></i>" +
        "<div style='color:#fff'>"+msg+"</div></div>",
        css:{
            border:'none',
            textAlign:'center',
            padding:'20px 140px 20px 140px',
            background:'none'
        }
    })
}
//关闭遮罩
function closeMask(){
    $(".blockUI").fadeOut("slow");
    $.unblockUI();
}
/**
 * 显示提示窗口
 * @param title 标题
 * @param msg 消息内容
 * @param fn 点击确认后回调
 */
function confirmOper(title,msg,fn) {
    bootbox.confirm({
        title:title,
        message: msg,
        buttons: {
            cancel: {
                label: '<i class="fa fa-times"></i> 取消'
            },
            confirm: {
                label: '<i class="fa fa-check"></i> 确定'
            }
        },
        callback: function (result) {
            if (result) {
                fn();
            }
        }
    })
}
/**
 * 请求json数据
 * @param url 请求url
 * @param method 请求方法
 * @param data 提交参数
 * @param loadingMsg mask显示文字
 * @param timeout 超时时间
 * @param sfn 成功后回调
 * @param efn 失败回调
 */
function jsonAjax(url,method,data,loadingMsg,timeout,sfn,efn){
    showMask(loadingMsg);
    $.ajax({
        url:url,
        type:method, //GET
        async:true,    //或false,是否异步
        data:data,
        timeout:timeout,
        dataType:"JSON",
        success:function(data,textStatus,xhr){
            closeMask();
            sfn(data,textStatus,xhr);
        },
        error:function(xhr,textStatus){
            closeMask();
            efn(xhr,textStatus);
        }
    })
}
/**
 * 用GET方法获取json数据
 * @param url 请求URL
 * @param data 请求参数
 * @param sfn 完成后回调
 */
function getJson(url,data,sfn){
    jsonAjax(url,"get",data,"正在加载,请稍后",5000,sfn,function(xhr,textStatus){

    });
}
function postJson(url,data,sfn){
    jsonAjax(url,"post",data,"正在加载,请稍后",5000,sfn,function(xhr,textStatus){

    });
}
/**
 * post请求,返回json数据
 * 统一Ajax后的json返回格式
 * 增加额外提交参数 ajax=ajax来表示是ajax请求，服务端需返回msg=_session_invalide来表示session失效
 {
     code:'success|fail',
     msg:'fail_msg',
     body:'other_info'
 }
 * @param url 请求地址
 * @param data 请求数据
 * @param loadingMsg 加载时显示数据
 * @param handler 成功后回调
 * @param timeout 超时时间
 * @returns
 */
function postJSON(url, data, loadingMsg, handler,timeout) {

    showBlock(loadingMsg);
    data["ajax"] = "ajax";
    $.ajax({
        url: url,
        type: "POST", //GET
        async: true,    //或false,是否异步
        data: data,
        timeout:timeout?timeout:6000,
        dataType: "JSON",
        success: function (result, textStatus, xhr) {
            hideBlock();
            if (result.code == "fail" && result.msg == SESSION_INVALIDE) {
                window.location.href = "privilege.jsp"
            } else if (typeof handler == 'function') {
                handler(result);
            }
        },
        error: function (xhr, textStatus) {
            hideBlock();
            showMessage("系统异常,请稍后再试",3000);
        }
    })
}
function showBlock(msg) {
    $.blockUI({
        message: "<div style='padding:10px;text-align:center;width:140px'>" +
        "<i class='icon icon-spin icon-spinner-indicator' style='font-size:32px;color:#fff'></i>" +
        "<div style='color:#fff'>" + msg + "</div></div>",
        css: {
            border: 'none',
            textAlign: 'center',
            padding: '20px 140px 20px 140px',
            background: 'none'
        }
    })
}

function hideBlock() {
    $(".blockUI").fadeOut("slow");
    $.unblockUI();
}
/**
 * 显示漂浮消息
 * @param text 消息内容
 * @param timeout 显示退出时间
 */
function showMessage(text, timeout) {
    var msg = new $.zui.Messager(text, {
        close: false,
        type: 'danger',
        placement: 'center',
        time: timeout,
    }).show();
}
/********************* 服务框架相关 ******************************/
//如果有文件上传,那么提交完成后,必须清理uploaderConfig
var uploaderConfig=new Dictionary();
$(document).ready(function(){
    initTheme();
    initApplication();
    $(".changeThemeAction").click(function(){
        var theme=$(this).attr("data-value");
        selectTheme(theme);
    });
    if(functionExist("pageReady")){
        pageReady($(this));
    }
});
function getDataGridHeight(){
    var bodyHeight=$("body").height();
    var dataGridTop=$("#remoteDataGrid").offset().top;
    if(bodyHeight<0){
        var body=$(window.parent.document).find("body");
        bodyHeight=body.height();
        dataGridTop=80;
        //alert(bodyHeight+":"+dataGridTop);
    }
    var height=  bodyHeight-dataGridTop-80;
    return height;
}
//初始化主题
function initTheme(){
    var theme=$.zui.store.get("theme");
    if(theme!=null){
        selectTheme(theme);
    }
}
//设置主题
function selectTheme(theme){
    if(theme=="default"){
        $("#linkChangeTheme").attr("href", "");
    }else{
        $("#linkChangeTheme").attr("href", contextPath+"/css/zui-theme-" + theme + ".css");
    }
    $.zui.store.set("theme",theme);
    var $actionTag=$(".changeThemeAction[data-value='"+theme+"']");
    $actionTag.parents("ul").find("li").each(function(){
        $(this).removeClass("selected");
    });
    $actionTag.parent("li").addClass("selected");
}
function setInputContol() {
    // 选择时间和日期
    $(".form-date").datetimepicker({
        //fuck
        language: "zh-CN",
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        format: "yyyy-mm-dd"
    });
    // 选择时间
    $(".form-time").datetimepicker({
        language: "zh-CN",
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 1,
        minView: 0,
        maxView: 1,
        forceParse: 0,
        format: 'hh:ii'
    });
    // 选择时间和日期
    $(".form-datetime").datetimepicker({
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        forceParse: 0,
        showMeridian: 1,
        format: "yyyy-mm-dd hh:ii"
    });
    //清除时间的按钮事件
    $(".clearDateTime").click(function () {
        $("input[name='" + $(this).attr("ref") + "']").val("");
    });
    $(".chosen-select").chosen({
        allow_single_deselect:true,
        width:'100%',
        no_results_text: '没有找到'
    });
    //清除日期输入框
    $(".clear_date_input").click(function () {
        $(this).prev("input").val("");
    });
    //清除查找框
    $(".clear_query_search_input").click(function () {
        var $group = $(this).parent(".input-group");
        $group.find("input").val("");
    });
    //清除表单中的查找框
    $(".clear_form_query_input").click(function () {
        $(this).parent(".input-group").find("input").val("");
    });
}
function openMultipleChooseTrigger(){

}

function openSingleChoosenTrigger(showName,showValue,serviceName,fieldName,searchTitleName,actionButtonName){
    var isCustomSelectModal=false;
    if(functionExist("interceptorSelectModal")){
        //拦截实体搜索框,采用自己的实现
        //isCustomSelectModal=interceptorSelectModal($showName,$showValue,referenceQueryId,referenceQueryName,entityName,actionName,showFieldName,fieldName,windowTitle);
    }
    //如果自己拦截实现,则返回
    if(isCustomSelectModal){
        return;
    }
    var extendParams="";
    if(functionExist("queryBoxExtendParams")){
        extendParams="&"+jsonObjectToQueryString(queryBoxExtendParams(serviceName,fieldName));
    }
    popupSingleChooseTrigger= new $.zui.ModalTrigger({
        url:manager+"/access/popupSingleChoose"+suffix+"?serviceName="+serviceName+"&"
        +"&actionButtonName="+actionButtonName+"&"
        +"fieldName="+fieldName+extendParams,
        size:'fullscreen',
        title:searchTitleName,
        backdrop:'static',
        type:'iframe',
        onShow:function(){
            var bodyWidth=$("body").width();
            $(".modal-fullscreen").css("max-width",bodyWidth+"px");
        }
    });
    popupSingleChooseTrigger.show({hidden: function(){
        var popupSelectValueResult=$("input[name='popupSelectValueResult']").val();
        if(popupSelectValueResult!=""){
            popupSelectValueResult=JSON.parse(popupSelectValueResult);
            showName.val(popupSelectValueResult.value);
            showValue.val(popupSelectValueResult.id);
        }
        $("input[name='popupSelectValueResult']").val("");
        $("#triggerModal").remove();
        if(functionExist("popupSingleChooseTriggerClose")){
            popupSingleChooseTriggerClose();
        }
    }});
}
function initApplication() {
    setInputContol();
    //详情页面板折叠
    $(".form_container .detail-title .icon").click(function(){
        var $this=$(this);
        var $content=$this.parent().next(".detail-content");
        if($content.hasClass("hidden")){
            $content.removeClass("hidden");
            $this.removeClass("icon-caret-right").addClass("icon-caret-down");
        }else{
            $content.addClass("hidden");
            $this.removeClass("icon-caret-down").addClass("icon-caret-right");
        }
    })
    //左侧面板收缩
    $(".sidebar-toggle").click(function(){
        var $mainRow=$(".main-row");
        if($mainRow.hasClass("hide-sidebar")){
            $mainRow.removeClass("hide-sidebar");
        }else{
            $mainRow.addClass("hide-sidebar");
        }
    });
    //滚动回顶部
    $("#scrollTopAction").click(function(){
        $("html,body").animate({scrollTop:0}, 200);
    });
    //显示隐藏搜索面板
    $("#show_or_hide_search").click(function(){
        var $this=$(this);
        var $queryBox=$("#queryBox");
        if($this.hasClass("querybox-opened")){
            $this.removeClass("querybox-opened");
            $queryBox.addClass("hidden").removeClass("show");
        }else{
            $this.addClass("querybox-opened");
            $queryBox.removeClass("hidden").addClass("show");
        }
    });
    //搜索面板显示更多
    $("#ext_search_bar").click(function(){
        var $extSearch=$("#query_search_from .ext_search");
        var $this=$(this);
        if($extSearch.is(':hidden')){
            $extSearch.show();
            $this.find(".icon").removeClass("icon-chevron-double-down").addClass("icon-chevron-double-up");
            $("#query_search_from .link_center").css("top","61%");
        }else{
            $extSearch.hide();
            $this.find(".icon").removeClass("icon-chevron-double-up").addClass("icon-chevron-double-down");
            $("#query_search_from .link_center").css("top","67%");
        }
    });
    //搜索框设置
    $("#queryBox .form-group-line").each(function(){
        var $this=$(this);
        if($this.attr("data-item")=="0"){
            $this.addClass("default_search");
        }else{
            $this.addClass("ext_search");
        }
    });
    //重置搜索表单
    $("#queryBox #resetForm").click(function(){
        $("#query_search_from input[name='queryCondition'],.query_value,.popupSingleChoose,input[name='query_value']").val("");
        $(".query_value").trigger('chosen:updated');
        reflashPageData();
    });
    //搜索表单事件改变
    $("#queryBox .query_field").on("change",function(e){
        var $this=$(this);
        var chosenField=$this.val();
        var dataEntity=$this.find("option:selected").attr("data-entity");
        var position=$this.attr("data-position");
        var fieldAttr=$this.attr("data-field-chosen");
        postJSON(manager+"/access/changeSearchBox"+suffix,{
            dataEntity:dataEntity,
            chosenField:chosenField,
            position:position
        },"请稍后",function(result){
            //更新 condition_+fieldAttr value_+fieldAttr
            var conditionSelect=$(".condition_"+fieldAttr).find("select");
            conditionSelect.empty().html(result.conditionHtml);
            conditionSelect.trigger('chosen:updated');
            var valueHtml=result.valueHtml;
            if(functionExist("filterQueryConditionValue")){
                valueHtml=filterQueryConditionValue(chosenField,valueHtml);
            }
            $(".value_"+fieldAttr).empty().html(valueHtml);
            setInputContol();
        });
    });
    //TAB事件
    $(".custom-nav-tabs a").click(function(){
        $(".custom-nav-tabs a").removeClass("btn-active-text");
        $(".tabContent").removeClass("active");
        var $this=$(this);
        $this.addClass("btn-active-text");
        $($this.attr("href")).addClass("active");
    });
    //筛选栏，弹出搜索实体框,单选,必须要求父类有name为popupSelectValueResult的input
    $("#queryBox,#editor_form").on("click",".popupSingleChoose",function(){
        var $showName=$(this);
        var data_ref_value=$showName.attr("data-ref-value");
        var $showValue=$showName.prev("input[data-ref-value='"+data_ref_value+"']");
        var serviceName=$showName.attr("data-service-name");//查询服务类
        var searchTitleName=$showName.attr("data-search-title-name");//弹出层的title
        var actionButtonName=$showName.attr("data-action-button-name");//弹出层选中的按钮值
        var fieldName=$showName.attr("data-field-name");//选中后返回的字段
        openSingleChoosenTrigger($showName,$showValue,serviceName,fieldName,searchTitleName,actionButtonName);
    })
    //文件上传
    $(".uploader").each(function(i,d){
        var $this=$(this);
        var formFieldId=$this.attr("data-field-id");
        uploaderConfig.put(formFieldId,{
            uploaderContainer:$this,
            files:JSON.parse($this.find(".fileJsonString").html()),
            imageMap:new Dictionary(),
            queueFiles:new Array(),
            uploadFileName:""
        });
        $.each(uploaderConfig.get(formFieldId).files,function(i,d){
            uploaderConfig.get(formFieldId).imageMap.put(d.id,d.id)
        });
        //data-mine-types="${columnData.fileElement.mineTypes}" data-mine-type-name="${columnData.fileElement.mineTypeName}"
        var mineTypes=$this.attr("data-mine-types");
        var mineTypeName=$this.attr("data-mine-type-name");
        var rename=$this.attr("data-rename")=="true";
        var multielection=$this.attr("data-multi-selection")=="true";
        var maxFileSize=$this.attr("data-max-size");
        var uploadUrl=$this.attr("data-upload-url");
        var accessTempUrl=$this.attr("data-access-temp-url");
        var storageUrl=$this.attr("data-access-storage-url");
        var limitFilesCount;
        if(multielection){
            limitFilesCount=false;
        }else{
            limitFilesCount=1;
        }
        var uploader=$this.uploader({
            autoUpload:!rename,            // 当选择文件后立即自动进行上传操作
            url:uploadUrl,  // 文件上传提交地址
            filters:{
                // 只允许上传图片或图标（.ico）
                mime_types:[
                    {title:mineTypeName,extensions:mineTypes}
                ],
                max_file_size:maxFileSize,
                prevent_duplicates: true
            },
            deleteActionOnDone:function(file, doRemoveFile){
                //处理 file.id
                var fileId=file.id.replace("file-","");
                uploaderConfig.get(formFieldId).imageMap.remove(fileId);
                uploaderConfig.get(formFieldId).queueFiles.remove(file);
                if(functionExist("fileUploadDeleteFile")){
                    //删除文件后,如需自行处理,则编写fileUploadDeleteFile方法
                    fileUploadDeleteFile(formFieldId,fileId);
                }
                return true;//返回true表示删除文件
            },
            rename:rename,
            renameExtension:rename,
            renameByClick:rename,
            unique_names:true,
            multi_selection:multielection,
            chunk_size:0,//不执行分片
            limitFilesCount:limitFilesCount,
            flash_swf_url:contextPath+"/zui/lib/uploader/Moxie.swf",
            silverlight_xap_url:contextPath+"/zui/lib/uploader/Moxie.xap",
            responseHandler:function(resp, file){
                var result=$.parseJSON(resp.response);
                if(result.msg=="success"){
                    uploaderConfig.get(formFieldId).imageMap.put(file.id.replace("file-",""),result.body)
                }
                uploaderConfig.get(formFieldId).queueFiles.push(file);
                //文件上传成功后,需要自行处理,编写该方法
                if(functionExist("fileUploadedHandler")){
                    fileUploadedHandler(formFieldId,file);
                }
            },
            staticFiles:uploaderConfig.get(formFieldId).files
        }).on("onFileUploaded",function(resp, file){
            var id=file.id;
            $this.find("#file-"+id).find(".btn-download-file").css("display","inline")
                .attr("click_url",accessTempUrl+"?hex="+uploaderConfig.get(formFieldId).imageMap.get(id));
            if(rename) {
                file.name=uploaderConfig.get(formFieldId).uploadFileName;
                //$this.find("#file-" + id).find(".file-name").html(uploaderConfig.get(formFieldId).uploadFileName);
            }
        }).on("onFilesAdded",function(uploader, files){
            if(rename){
                bootbox.prompt("请输入上传文件的名称", function(result){
                    var uploader=$this.data('zui.uploader');
                    if(result){
                        uploader.start();
                        uploaderConfig.get(formFieldId).uploadFileName=result;
                    }else{
                        uploader.removeFile(files[0]);
                    }
                });
            }
        });
        $this.on("click",".btn-download-file",function(){
            window.open($(this).attr("click_url"),"_blank");
        });
        $this.find(".icon-file-image").each(function(i,d){
            var id=$(this).parents(".file-static").attr("id").replace("file-","");
            var fileshow=$(this).parent(".file-icon");
            $(this).remove();
            var fileUrl=storageUrl+"?hex="+id;
            fileshow.html("<div class='file-icon-image' style='background-image: url("+fileUrl+")'></div>");
        });
        $this.find(".file-static").each(function(i,d){
            var id=$(this).attr("id").replace("file-","");
            var fileName=$(this).find(".file-name").html();
            $(this).find(".upload-name").html(fileName);
            $(this).find(".btn-download-file").css("display","inline")
                .attr("click_url",storageUrl+"?hex="+id);
        });
    })
    //表单提交
    $("#editor_form #formSubmitAction").click(function(){
        var $this=$(this);
        var $form=$("#editor_form");
        $this.addClass("disabled");
        var flag=$form.formValidate(function(el,hint){
            toast(hint);
            $("#ref_"+el.attr("name")).addClass("check-error");
            el.focus(function(){
                $("#ref_"+el.attr("name")).removeClass("check-error");
            })
        });
        var $formData=$form.formToJson();
        if(!flag){
            $this.removeClass("disabled");
            return;
        }
        //处理文件上传
        $(".uploader").each(function(){
            var $uploader=$(this);
            var fieldId=$uploader.attr("data-field-id");
            var config=uploaderConfig.get(fieldId);
            var attachArray=new Array();
            $uploader.find(".uploader-files tr").each(function(i,d){
                var fileId=$(d).attr("id").replace("file-","");
                var fileName=$(this).find(".file-name").html();
                var fileHex=config.imageMap.get(fileId);
                if(fileHex!=null&&fileHex!=undefined&&fileHex!=""){
                    attachArray.push({
                        name:fileName,
                        fileHex:fileHex
                    });
                }
            });
            $formData[fieldId]=JSON.stringify(attachArray);
        });
        if(functionExist("customFormValidate")){
            //自定义表单校验
            flag=customFormValidate($this,$formData);
        }
        if(functionExist("beforeEditFormCommit")){
            beforeEditFormCommit($formData);
        }
        if(flag){
            postJSON($form.attr("action"),$formData,"正在处理请稍后",function(result){
                $this.removeClass("disabled");
                var isEdit=checkValue("required",$formData.entityId);
                if(result.code==SUCCESS){
                    bootbox.alert({
                        title:'消息',
                        message: $form.attr("data-form-name")+"成功,点击确认返回",
                        size: 'small',
                        callback: function () {
                            if(functionExist("formCommited")){
                                //完成表单提交,可自行处理结果
                                if(isEdit){
                                    $("#editor_form input[name='"+tokenName+"']").val(result.body);
                                }
                                formCommited(isEdit);
                            }else{
                                if(!isEdit){
                                    window.location.reload();
                                }else{
                                    $("#editor_form input[name='"+tokenName+"']").val(result.body);
                                }
                            }
                        }
                    });
                }else{
                    bootbox.alert({
                        title:"错误",
                        message:result.msg,
                        callback:function(){
                            $("#editor_form input[name='"+tokenName+"']").val(result.body);
                        }
                    });
                }
            });
        }else{
            $this.removeClass("disabled");
        }
    });

}
//清理detail页面的相关项目
function clearDetailItem(name){
    var $th=$("th[name='"+name+"']");
    $th.next("td").remove();
    $th.remove();
}
/********************* 服务框架回调函数说明 ******************************/
/*
    1:filterQueryConditionValue(chosenField,valueHtml) 如果要对返回的填写值窗口进行过滤,那么重写该方法
        chosenField 查询的字段
        valueHtml 返回的html文本

    2:customDataConvertCell(valueType,dataValue,cell, dataGrid) 表格中的值转换规则
        valueType 字段类型
        dataValue 当前值
        cell 单元格数据
        dataGrid 表格数据
    3:createMenuItems(dataId,dataRow,dataValue) 构建表格右侧的操作菜单
        dataId 主键ID
        dataRow 当前行
        dataValue 当前行数据
    4: 利用元素 extar_oper_container 来增加批量操作菜单
    5: nodifyDataGridLineChoose(yes) 通知行被选中
        yes boolean值，true表示选中
    6:queryBoxExtendParams(serviceName,fieldName){
     //弹窗查询时返回自定义查询,需要返回单纯键值对的json
        return {
            name:"fatcher",
            age:44
        }
     }
    7:formCommited(isEdit) 表单提交后自行处理结果
    8:customFormValidate($this,$formData) 自定义表单校验，返回true表示表单处理成功
        $this 当前按钮
        $formData 表单数据
    9：fileUploadedHandler(formFieldId,file) 文件上传成功后，可重写该方法处理处理其他逻辑
    10：fileUploadDeleteFile(formFieldId,fileId); 点击删除文件后，自行处理方法
 */
