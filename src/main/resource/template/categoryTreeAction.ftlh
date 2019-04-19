<span data-id='${id}' data-name='${name}' data-weight="${weight}" data-level="${level}" data-enable="${enable?c}">
    <span class="menu_title">${name}</span>
    <span class='oper'>操作</span>
    <#if enable>
        <span style="color:#ff1111;font-size: 11px;">启用中</span>
    <#else>
        <span style="color:#666666;font-size: 11px;">已停用</span>
    </#if>
</span>