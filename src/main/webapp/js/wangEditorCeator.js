function createEditor(contextPath,suffix,el,change){
    var E = window.wangEditor;
    var editor = new E("#"+el);
    editor.customConfig.zIndex = 1;
    editor.customConfig.onchange = function (html) {
        // html 即变化之后的内容
        if(typeof(change)==="function"){
            change(html);
        }
    };
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
        content:[{alt:'[NO]',src:contextPath+'/img/qq_biao_qing/88.gif'},{alt:'[OK]',src:contextPath+'/img/qq_biao_qing/89.gif'},{alt:'[爱情]',src:contextPath+'/img/qq_biao_qing/90.gif'},{alt:'[飞吻]',src:contextPath+'/img/qq_biao_qing/91.gif'},{alt:'[跳跳]',src:contextPath+'/img/qq_biao_qing/92.gif'},{alt:'[发抖]',src:contextPath+'/img/qq_biao_qing/93.gif'},{alt:'[怄火]',src:contextPath+'/img/qq_biao_qing/94.gif'},{alt:'[转圈]',src:contextPath+'/img/qq_biao_qing/95.gif'},{alt:'[磕头]',src:contextPath+'/img/qq_biao_qing/96.gif'},{alt:'[回头]',src:contextPath+'/img/qq_biao_qing/97.gif'},{alt:'[尴尬]',src:contextPath+'/img/qq_biao_qing/10.gif'},{alt:'[跳绳]',src:contextPath+'/img/qq_biao_qing/98.gif'},{alt:'[发怒]',src:contextPath+'/img/qq_biao_qing/11.gif'},{alt:'[挥手]',src:contextPath+'/img/qq_biao_qing/99.gif'},{alt:'[调皮]',src:contextPath+'/img/qq_biao_qing/12.gif'},{alt:'[呲牙]',src:contextPath+'/img/qq_biao_qing/13.gif'},{alt:'[惊讶]',src:contextPath+'/img/qq_biao_qing/14.gif'},{alt:'[难过]',src:contextPath+'/img/qq_biao_qing/15.gif'},{alt:'[酷]',src:contextPath+'/img/qq_biao_qing/16.gif'},{alt:'[冷汗]',src:contextPath+'/img/qq_biao_qing/17.gif'},{alt:'[抓狂]',src:contextPath+'/img/qq_biao_qing/18.gif'},{alt:'[吐]',src:contextPath+'/img/qq_biao_qing/19.gif'},{alt:'[微笑]',src:contextPath+'/img/qq_biao_qing/0.gif'},{alt:'[撇嘴]',src:contextPath+'/img/qq_biao_qing/1.gif'},{alt:'[色]',src:contextPath+'/img/qq_biao_qing/2.gif'},{alt:'[发呆]',src:contextPath+'/img/qq_biao_qing/3.gif'},{alt:'[得意]',src:contextPath+'/img/qq_biao_qing/4.gif'},{alt:'[流泪]',src:contextPath+'/img/qq_biao_qing/5.gif'},{alt:'[害羞]',src:contextPath+'/img/qq_biao_qing/6.gif'},{alt:'[闭嘴]',src:contextPath+'/img/qq_biao_qing/7.gif'},{alt:'[睡]',src:contextPath+'/img/qq_biao_qing/8.gif'},{alt:'[大哭]',src:contextPath+'/img/qq_biao_qing/9.gif'},{alt:'[偷笑]',src:contextPath+'/img/qq_biao_qing/20.gif'},{alt:'[可爱]',src:contextPath+'/img/qq_biao_qing/21.gif'},{alt:'[白眼]',src:contextPath+'/img/qq_biao_qing/22.gif'},{alt:'[傲慢]',src:contextPath+'/img/qq_biao_qing/23.gif'},{alt:'[饥饿]',src:contextPath+'/img/qq_biao_qing/24.gif'},{alt:'[困]',src:contextPath+'/img/qq_biao_qing/25.gif'},{alt:'[惊恐]',src:contextPath+'/img/qq_biao_qing/26.gif'},{alt:'[流汗]',src:contextPath+'/img/qq_biao_qing/27.gif'},{alt:'[憨笑]',src:contextPath+'/img/qq_biao_qing/28.gif'},{alt:'[大兵]',src:contextPath+'/img/qq_biao_qing/29.gif'},{alt:'[奋斗]',src:contextPath+'/img/qq_biao_qing/30.gif'},{alt:'[咒骂]',src:contextPath+'/img/qq_biao_qing/31.gif'},{alt:'[疑问]',src:contextPath+'/img/qq_biao_qing/32.gif'},{alt:'[嘘]',src:contextPath+'/img/qq_biao_qing/33.gif'},{alt:'[晕]',src:contextPath+'/img/qq_biao_qing/34.gif'},{alt:'[折磨]',src:contextPath+'/img/qq_biao_qing/35.gif'},{alt:'[衰]',src:contextPath+'/img/qq_biao_qing/36.gif'},{alt:'[骷髅]',src:contextPath+'/img/qq_biao_qing/37.gif'},{alt:'[敲打]',src:contextPath+'/img/qq_biao_qing/38.gif'},{alt:'[再见]',src:contextPath+'/img/qq_biao_qing/39.gif'},{alt:'[擦汗]',src:contextPath+'/img/qq_biao_qing/40.gif'},{alt:'[抠鼻]',src:contextPath+'/img/qq_biao_qing/41.gif'},{alt:'[鼓掌]',src:contextPath+'/img/qq_biao_qing/42.gif'},{alt:'[糗大了]',src:contextPath+'/img/qq_biao_qing/43.gif'},{alt:'[坏笑]',src:contextPath+'/img/qq_biao_qing/44.gif'},{alt:'[左哼哼]',src:contextPath+'/img/qq_biao_qing/45.gif'},{alt:'[右哼哼]',src:contextPath+'/img/qq_biao_qing/46.gif'},{alt:'[哈欠]',src:contextPath+'/img/qq_biao_qing/47.gif'},{alt:'[鄙视]',src:contextPath+'/img/qq_biao_qing/48.gif'},{alt:'[委屈]',src:contextPath+'/img/qq_biao_qing/49.gif'},{alt:'[快哭了]',src:contextPath+'/img/qq_biao_qing/50.gif'},{alt:'[阴险]',src:contextPath+'/img/qq_biao_qing/51.gif'},{alt:'[亲亲]',src:contextPath+'/img/qq_biao_qing/52.gif'},{alt:'[吓]',src:contextPath+'/img/qq_biao_qing/53.gif'},{alt:'[可怜]',src:contextPath+'/img/qq_biao_qing/54.gif'},{alt:'[菜刀]',src:contextPath+'/img/qq_biao_qing/55.gif'},{alt:'[西瓜]',src:contextPath+'/img/qq_biao_qing/56.gif'},{alt:'[啤酒]',src:contextPath+'/img/qq_biao_qing/57.gif'},{alt:'[篮球]',src:contextPath+'/img/qq_biao_qing/58.gif'},{alt:'[乒乓]',src:contextPath+'/img/qq_biao_qing/59.gif'},{alt:'[咖啡]',src:contextPath+'/img/qq_biao_qing/60.gif'},{alt:'[饭]',src:contextPath+'/img/qq_biao_qing/61.gif'},{alt:'[猪头]',src:contextPath+'/img/qq_biao_qing/62.gif'},{alt:'[玫瑰]',src:contextPath+'/img/qq_biao_qing/63.gif'},{alt:'[凋谢]',src:contextPath+'/img/qq_biao_qing/64.gif'},{alt:'[示爱]',src:contextPath+'/img/qq_biao_qing/65.gif'},{alt:'[爱心]',src:contextPath+'/img/qq_biao_qing/66.gif'},{alt:'[心碎]',src:contextPath+'/img/qq_biao_qing/67.gif'},{alt:'[蛋糕]',src:contextPath+'/img/qq_biao_qing/68.gif'},{alt:'[闪电]',src:contextPath+'/img/qq_biao_qing/69.gif'},{alt:'[炸弹]',src:contextPath+'/img/qq_biao_qing/70.gif'},{alt:'[刀]',src:contextPath+'/img/qq_biao_qing/71.gif'},{alt:'[足球]',src:contextPath+'/img/qq_biao_qing/72.gif'},{alt:'[瓢虫]',src:contextPath+'/img/qq_biao_qing/73.gif'},{alt:'[便便]',src:contextPath+'/img/qq_biao_qing/74.gif'},{alt:'[月亮]',src:contextPath+'/img/qq_biao_qing/75.gif'},{alt:'[太阳]',src:contextPath+'/img/qq_biao_qing/76.gif'},{alt:'[礼物]',src:contextPath+'/img/qq_biao_qing/77.gif'},{alt:'[拥抱]',src:contextPath+'/img/qq_biao_qing/78.gif'},{alt:'[强]',src:contextPath+'/img/qq_biao_qing/79.gif'},{alt:'[激动]',src:contextPath+'/img/qq_biao_qing/100.gif'},{alt:'[街舞]',src:contextPath+'/img/qq_biao_qing/101.gif'},{alt:'[献吻]',src:contextPath+'/img/qq_biao_qing/102.gif'},{alt:'[左太极]',src:contextPath+'/img/qq_biao_qing/103.gif'},{alt:'[右太极]',src:contextPath+'/img/qq_biao_qing/104.gif'},{alt:'[弱]',src:contextPath+'/img/qq_biao_qing/80.gif'},{alt:'[握手]',src:contextPath+'/img/qq_biao_qing/81.gif'},{alt:'[胜利]',src:contextPath+'/img/qq_biao_qing/82.gif'},{alt:'[抱拳]',src:contextPath+'/img/qq_biao_qing/83.gif'},{alt:'[勾引]',src:contextPath+'/img/qq_biao_qing/84.gif'},{alt:'[拳头]',src:contextPath+'/img/qq_biao_qing/85.gif'},{alt:'[差劲]',src:contextPath+'/img/qq_biao_qing/86.gif'},{alt:'[爱你]',src:contextPath+'/img/qq_biao_qing/87.gif'}]
    },{
        title:"emoji",
        type:"emoji",
        content:['😀','😁','😂','😃','😄','😅','😆','😉','😊','😋','😎','😍','😘','😗','😙','😚','😇','😐','😑','😶','😏','😣','😥','😮','😯','😪','😫','😴','😌','😛','😜','😝','😒','😓','😔','😕','😲','😷','😖','😞','😟','😤','😢','😭','😦','😧','😨','😬','😰','😱','😳','😵','😡','😠']
    }];
    //关闭图片链接
    editor.customConfig.showLinkImg = false;
    //上传图片配置
    //editor.customConfig.uploadImgShowBase64 = true
    editor.customConfig.uploadImgServer = contextPath+'/mgr/access/tempUpload'+suffix;
    editor.customConfig.uploadImgHooks = {
        customInsert: function (insertImg, result, editor) {
            var url = contextPath+"/mgr/access/accessTempFile"+suffix+"?hex="+result.body
            insertImg(url)
        },
        error: function (xhr, editor) {
            toast("图片上传错误");
        },
        timeout: function (xhr, editor) {
            toast("图片上传超时");
        }
    };
    editor.create();
    return editor;
}