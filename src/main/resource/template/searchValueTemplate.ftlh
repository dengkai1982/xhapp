<#if searchCurrentData.type.fieldType.showName=='DATE'>
    <div class="input-group">
        <input type="text" class="form-control form-date query_value" data-position="${position}" readonly placeholder="点击选择">
        <span class="input-group-addon border_right clear_date_input"><i class="icon icon-close"></i></span>
    </div>
<#elseif searchCurrentData.type.fieldType.showName=='DATETIME'>
    <div class="input-group">
        <input type="text" class="form-control form-datetime query_value" data-position="${position}" readonly placeholder="点击选择">
        <span class="input-group-addon border_right clear_date_input"><i class="icon icon-close"></i></span>
    </div>
<#elseif searchCurrentData.type.fieldType.showName=='TIME'>
    <div class="input-group">
        <input type="text" class="form-control form-time query_value" data-position="${position}" readonly placeholder="点击选择">
        <span class="input-group-addon border_right clear_date_input"><i class="icon icon-close"></i></span>
    </div>
<#elseif searchCurrentData.type.fieldType.showName=='NUMBER'>
    <input type="number" class="form-control query_value" data-position="${position}">
<#elseif searchCurrentData.type.fieldType.showName=='BOOLEAN'>
    <select data-placeholder="请选择" class="chosen-select form-control query_value" data-position="${position}">
        <option></option>
        <#list searchCurrentData.type.booleanValues as booleanValue>
        <option value="${booleanValue_index}">${booleanValue}</option>
        </#list>
    </select>
<#elseif searchCurrentData.type.fieldType.showName=='CHOSEN'>
    <select data-placeholder="请选择" class="chosen-select form-control query_value" data-position="${position}">
        <option></option>
        <#list searchCurrentData.type.chosens as chosenValue>
        <option value="${chosenValue.itemNumber}" data-keys="${chosenValue.dataKeys}">${chosenValue.value}</option>
        </#list>
    </select>
<#elseif searchCurrentData.type.fieldType.showName=='REFERENCE'>
    <#if searchCurrentData.type.pupupSelect>
        <div class="input-group">
            <input type="hidden" id="${searchCurrentData.field.name}" name="query_value" data-position="${position}" class="query_value" data-ref-value="${searchCurrentData.field.name}"/>
            <input type="text" id="${searchCurrentData.field.name}Reference" name="${searchCurrentData.field.name}Reference" class="form-control popupSingleChoose" data-ref-value="${searchCurrentData.field.name}" readonly placeholder="点击选择"
            data-service-name="${searchCurrentData.type.serviceName}"
            data-search-title-name="${searchCurrentData.type.searchTitleName}"
            data-action-button-name="${searchCurrentData.type.actionButtonName}"
            data-field-name="${searchCurrentData.type.fieldName}"/>
            <span class="input-group-addon border_right clear_query_search_input"><i class="icon icon-close"></i></span>
        </div>
    <#else>
        <select data-placeholder="请选择" class="chosen-select form-control query_value" data-position="${position}">
            <option></option>
            <#list searchCurrentData.type.chosenValues as chosenValue>
            <option value="${chosenValue.value}" data-keys="${chosenValue.value}">${chosenValue.showName}</option>
            </#list>
        </select>
    </#if>
<#else>
    <input type="text" class="form-control query_value" data-position="${position}">
</#if>