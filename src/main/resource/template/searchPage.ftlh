<!--搜索栏 ftlh-->
<div id="queryBox" class="cell hidden">
    <form class="form-horizontal" id="query_search_from" method="post">
        <input type="hidden" name="serviceName" value="${serviceName}"/>
        <input type="hidden" name="queryCondition">
        <input type="hidden" name="popupSelectValueResult">
        <input type="hidden" name="downloadFileName">
        <#if formElementHiddens??>
            <#list formElementHiddens as formElementHidden>
                <input type="hidden" name="${formElementHidden.name}" value="${formElementHidden.value}">
            </#list>
        </#if>
        <div class="link_center">
            <!--组链接-->
            <select name="groupLink" class="chosen-select form-control">
                <option value="0">并且</option>
                <option value="1">或者</option>
            </select>
        </div>
        <!--循环三次-->
        <#list 0..2 as item>
        <div class="form-group form-group-line" data-item="${item}">
            <div class="left_group">
                <#if item==0>
                    <label class="col">第一组</label>
                <#else>
                    <div class="col">
                        <select name="itemLink" data-position="left" class="chosen-select form-control query_item_link">
                            <option value="0">并且</option>
                            <option value="1">或者</option>
                        </select>
                    </div>
                </#if>
                <div class="col column_chosen">
                    <select  data-position="left" data-field-chosen="left_${item}" class="chosen-select form-control query_field">
                        <#list searchableFieldData as fieldData>
                            <option value="${fieldData.field.name}" data-entity="${fieldData.entityName}">${fieldData.label}</option>
                        </#list>
                    </select>
                </div>
                <div class="col compare_chosen condition_left_${item}">
                    <select class="chosen-select form-control query_condition" data-position="left">
                        <#list conditions as condition>
                            <option value="${condition.itemNumber}">${condition.value}</option>
                        </#list>
                    </select>
                </div>
                <div class="col-sm-2 value_left_${item}">
                    <#if searchCurrentData.type.fieldType.showName=='DATE'>
                        <div class="input-group">
                            <input type="text" class="form-control form-date query_value" data-position="left" readonly placeholder="点击选择">
                            <span class="input-group-addon border_right clear_date_input"><i class="icon icon-close"></i></span>
                        </div>
                    <#elseif searchCurrentData.type.fieldType.showName=='DATETIME'>
                        <div class="input-group">
                            <input type="text" class="form-control form-datetime query_value" data-position="left" readonly placeholder="点击选择">
                            <span class="input-group-addon border_right clear_date_input"><i class="icon icon-close"></i></span>
                        </div>
                    <#elseif searchCurrentData.type.fieldType.showName=='TIME'>
                        <div class="input-group">
                            <input type="text" class="form-control form-time query_value" data-position="left" readonly placeholder="点击选择">
                            <span class="input-group-addon border_right clear_date_input"><i class="icon icon-close"></i></span>
                        </div>
                    <#elseif searchCurrentData.type.fieldType.showName=='NUMBER'>
                        <input type="number" class="form-control query_value" data-position="left">
                    <#elseif searchCurrentData.type.fieldType.showName=='BOOLEAN'>
                        <select data-placeholder="请选择" class="chosen-select form-control query_value" data-position="left">
                            <option></option>
                            <#list searchCurrentData.type.booleanValues as booleanValue>
                            <option value="${booleanValue_index}">${booleanValue}</option>
                            </#list>
                        </select>
                    <#elseif searchCurrentData.type.fieldType.showName=='CHOSEN'>
                        <select data-placeholder="请选择" class="chosen-select form-control query_value" data-position="left">
                            <option></option>
                            <#list searchCurrentData.type.chosens as chosenValue>
                            <option value="${chosenValue.itemNumber}" data-keys="${chosenValue.dataKeys}">${chosenValue.value}</option>
                            </#list>
                        </select>
                    <#elseif searchCurrentData.type.fieldType.showName=='REFERENCE'>
                        <#if searchCurrentData.type.pupupSelect>
                            <div class="input-group">
                                <input type="hidden" name="query_value" data-position="left" class="query_value" data-ref-value="${searchCurrentData.field.name}"/>
                                <input type="text" class="form-control popupSingleChoose" data-ref-value="${searchCurrentData.field.name}" readonly placeholder="点击选择"
                                       data-service-name="${searchCurrentData.type.serviceName}"
                                       data-search-title-name="${searchCurrentData.type.searchTitleName}"
                                       data-action-button-name="${searchCurrentData.type.actionButtonName}"
                                       data-field-name="${searchCurrentData.type.fieldName}"/>
                                <span class="input-group-addon border_right clear_query_search_input"><i class="icon icon-close"></i></span>
                            </div>
                        <#else>
                            <select data-placeholder="请选择" class="chosen-select form-control query_value" data-position="left">
                                <option></option>
                                <#list searchCurrentData.type.chosenValues as chosenValue>
                                <option value="${chosenValue.value}" data-keys="${chosenValue.value}">${chosenValue.showName}</option>
                                </#list>
                            </select>
                        </#if>
                    <#else>
                        <input type="text" class="form-control query_value" data-position="left">
                    </#if>
                </div>
            </div>
            <div class="col link_container">

            </div>
            <div class="right_group">
                <#if item==0>
                    <label class="col">第二组</label>
                <#else>
                    <div class="col">
                        <select name="itemLink" data-position="right" class="chosen-select form-control query_item_link">
                            <option value="0">并且</option>
                            <option value="1">或者</option>
                        </select>
                    </div>
                </#if>
                <div class="col column_chosen">
                    <select  data-position="right" data-field-chosen="right_${item}" class="chosen-select form-control query_field">
                        <#list searchableFieldData as fieldData>
                            <option value="${fieldData.field.name}" data-entity="${fieldData.entityName}">${fieldData.label}</option>
                        </#list>
                    </select>
                </div>
                <div class="col compare_chosen condition_right_${item}">
                    <select class="chosen-select form-control query_condition" data-position="right">
                        <#list conditions as condition>
                            <option value="${condition.itemNumber}">${condition.value}</option>
                        </#list>
                    </select>
                </div>
                <div class="col-sm-2 value_right_${item}">
                    <#if searchCurrentData.type.fieldType.showName=='DATE'>
                        <div class="input-group">
                            <input type="text" class="form-control form-date query_value" data-position="right" readonly placeholder="点击选择">
                            <span class="input-group-addon border_right clear_date_input"><i class="icon icon-close"></i></span>
                        </div>
                    <#elseif searchCurrentData.type.fieldType.showName=='DATETIME'>
                        <div class="input-group">
                            <input type="text" class="form-control form-datetime query_value" data-position="right" readonly placeholder="点击选择">
                            <span class="input-group-addon border_right clear_date_input"><i class="icon icon-close"></i></span>
                        </div>
                    <#elseif searchCurrentData.type.fieldType.showName=='TIME'>
                        <div class="input-group">
                            <input type="text" class="form-control form-time query_value" data-position="right" readonly placeholder="点击选择">
                            <span class="input-group-addon border_right clear_date_input"><i class="icon icon-close"></i></span>
                        </div>
                    <#elseif searchCurrentData.type.fieldType.showName=='NUMBER'>
                        <input type="number" class="form-control query_value" data-position="right">
                    <#elseif searchCurrentData.type.fieldType.showName=='BOOLEAN'>
                        <select data-placeholder="请选择" class="chosen-select form-control query_value" data-position="right">
                            <option></option>
                            <#list searchCurrentData.type.booleanValues as booleanValue>
                            <option value="${booleanValue_index}">${booleanValue}</option>
                            </#list>
                        </select>
                    <#elseif searchCurrentData.type.fieldType.showName=='CHOSEN'>
                        <select data-placeholder="请选择" class="chosen-select form-control query_value" data-position="right">
                            <option></option>
                            <#list searchCurrentData.type.chosens as chosenValue>
                            <option value="${chosenValue.itemNumber}" data-keys="${chosenValue.dataKeys}">${chosenValue.value}</option>
                            </#list>
                        </select>
                    <#elseif searchCurrentData.type.fieldType.showName=='REFERENCE'>
                        <#if searchCurrentData.type.pupupSelect>
                            <div class="input-group">
                                <input type="hidden" name="query_value" data-position="right" class="query_value" data-ref-value="${searchCurrentData.field.name}"/>
                                <input type="text" class="form-control popupSingleChoose" data-ref-value="${searchCurrentData.field.name}" readonly placeholder="点击选择"
                                       data-service-name="${searchCurrentData.type.serviceName}"
                                       data-search-title-name="${searchCurrentData.type.searchTitleName}"
                                       data-action-button-name="${searchCurrentData.type.actionButtonName}"
                                       data-field-name="${searchCurrentData.type.fieldName}"/>
                                <span class="input-group-addon border_right clear_query_search_input"><i class="icon icon-close"></i></span>
                            </div>
                        <#else>
                            <select data-placeholder="请选择" class="chosen-select form-control query_value" data-position="right">
                                <option></option>
                                <#list searchCurrentData.type.chosenValues as chosenValue>
                                <option value="${chosenValue.value}" data-keys="${chosenValue.value}">${chosenValue.showName}</option>
                                </#list>
                            </select>
                        </#if>
                    <#else>
                        <input type="text" class="form-control query_value" data-position="right">
                    </#if>
                </div>
            </div>
        </div>
        </#list>
        <div class="search_btn_container">
            <button type="button" id="query_search_submit" class="btn btn-primary">搜索</button>
            <button type="button" class="btn-reset-form btn" id="resetForm">重置</button>
            <button type="button" id="ext_search_bar" class="btn-expand-form btn btn-link pull-right text-primary" style="margin-right: 20px;"><i class="icon icon-chevron-double-down"></i></button>
        </div>
    </form>
</div>