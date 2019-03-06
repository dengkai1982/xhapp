<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/share.jsp" %>
<c:choose>
    <c:when test="${fieldData.type.fieldType.showName=='TEXT'}">
        <c:choose>
            <c:when test="${fieldData.require}">
                <input type="text" value="${fieldData.value}" name="${fieldData.field.name}"  validate="required:${fieldData.hint}" class="form-control" id="${fieldData.field.name}" placeholder="${fieldData.placeholder}">
            </c:when>
            <c:otherwise>
                <input type="text" value="${fieldData.value}" name="${fieldData.field.name}"  class="form-control" id="${fieldData.field.name}" placeholder="请输入${fieldData.label}">
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:when test="${fieldData.type.fieldType.showName=='AREATEXT'}">
        <c:choose>
            <c:when test="${fieldData.require}">
                <textarea name="${fieldData.field.name}" rows="4" validate="required:${fieldData.hint}" id="${fieldData.field.name}" class="form-control" placeholder="${fieldData.placeholder}">${fieldData.value}</textarea>
            </c:when>
            <c:otherwise>
                <textarea name="${fieldData.field.name}" rows="4" id="${fieldData.field.name}" class="form-control" placeholder="请输入...">${fieldData.value}</textarea>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:when test="${fieldData.type.fieldType.showName=='NUMBER'}">
        <c:choose>
            <c:when test="${fieldData.require}">
                <input type="number" value="${fieldData.value}" name="${fieldData.field.name}"  validate="required:${fieldData.hint}" class="form-control" id="${fieldData.field.name}" placeholder="${fieldData.placeholder}">
            </c:when>
            <c:otherwise>
                <input type="number" value="${fieldData.value}" name="${fieldData.field.name}"  class="form-control" id="${fieldData.field.name}" placeholder="请输入${fieldData.label}">
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:when test="${fieldData.type.fieldType.showName=='DATE'}">
        <c:choose>
            <c:when test="${fieldData.require}">
                <div class="input-group">
                    <input type="text" name="${fieldData.field.name}" id="${fieldData.field.name}" validate="required:${fieldData.hint}" value="${fieldData.value}" class="form-control form-date" readonly placeholder="点击选择">
                    <span class="input-group-addon border_right clear_date_input"><i class="icon icon-close"></i></span>
                </div>
            </c:when>
            <c:otherwise>
                <div class="input-group">
                    <input type="text" name="${fieldData.field.name}" id="${fieldData.field.name}" value="${fieldData.value}" class="form-control form-date" readonly placeholder="点击选择">
                    <span class="input-group-addon border_right clear_date_input"><i class="icon icon-close"></i></span>
                </div>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:when test="${fieldData.type.fieldType.showName=='DATETIME'}">
        <c:choose>
            <c:when test="${fieldData.require}">
                <div class="input-group">
                    <input type="text" name="${fieldData.field.name}" id="${fieldData.field.name}" validate="required:${fieldData.hint}" value="${fieldData.value}" class="form-control form-datetime" readonly placeholder="点击选择">
                    <span class="input-group-addon border_right clear_date_input"><i class="icon icon-close"></i></span>
                </div>
            </c:when>
            <c:otherwise>
                <div class="input-group">
                    <input type="text" name="${fieldData.field.name}" id="${fieldData.field.name}" value="${fieldData.value}" class="form-control form-datetime" readonly placeholder="点击选择">
                    <span class="input-group-addon border_right clear_date_input"><i class="icon icon-close"></i></span>
                </div>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:when test="${fieldData.type.fieldType.showName=='TIME'}">
        <c:choose>
            <c:when test="${fieldData.require}">
                <div class="input-group">
                    <input type="text" name="${fieldData.field.name}" id="${fieldData.field.name}" validate="required:${fieldData.hint}" value="${fieldData.value}" class="form-control form-time" readonly placeholder="点击选择">
                    <span class="input-group-addon border_right clear_date_input"><i class="icon icon-close"></i></span>
                </div>
            </c:when>
            <c:otherwise>
                <div class="input-group">
                    <input type="text" name="${fieldData.field.name}" id="${fieldData.field.name}" value="${fieldData.value}" class="form-control form-time" readonly placeholder="点击选择">
                    <span class="input-group-addon border_right clear_date_input"><i class="icon icon-close"></i></span>
                </div>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:when test="${fieldData.type.fieldType.showName=='CHOSEN'||fieldData.type.fieldType.showName=='BOOLEAN'}">
        <c:choose>
            <c:when test="${fieldData.require}">
                <select name="${fieldData.field.name}" id="${fieldData.field.name}"  validate="required:${fieldData.hint}" data-placeholder="${fieldData.placeholder}" class='form-control chosen-select'>
                    <option></option>
                    <c:forEach items="${fieldData.value}" var="sl">
                        <c:choose>
                            <c:when test="${sl.selected}">
                                <option value='${sl.value}' key="${sl.dataKeys}" selected="selected">${sl.html}</option>
                            </c:when>
                            <c:otherwise>
                                <option value='${sl.value}' key="${sl.dataKeys}">${sl.html}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </c:when>
            <c:otherwise>
                <select name="${fieldData.field.name}" id="${fieldData.field.name}"  data-placeholder="${fieldData.placeholder}" class='form-control chosen-select'>
                    <option></option>
                    <c:forEach items="${fieldData.value}" var="sl">
                        <c:choose>
                            <c:when test="${sl.selected}">
                                <option value='${sl.value}' key="${sl.dataKeys}" selected="selected">${sl.html}</option>
                            </c:when>
                            <c:otherwise>
                                <option value='${sl.value}' key="${sl.dataKeys}">${sl.html}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:when test="${fieldData.type.fieldType.showName=='REFERENCE'}">
        <c:choose>
            <c:when test="${fieldData.type.pupupSelect}">
                <c:choose>
                    <c:when test="${fieldData.require}">
                        <div class="input-group">
                            <input type="hidden" name="${fieldData.field.name}" value="${fieldData.value.value}" class="query_value" data-ref-value="${fieldData.field.name}"/>
                            <input type="text" class="form-control popupSingleChoose" data-ref-value="${fieldData.field.name}" readonly placeholder="点击选择"
                                   value="${fieldData.value.html}"
                                   data-service-name="${fieldData.type.serviceName}"
                                   data-search-title-name="${fieldData.type.searchTitleName}"
                                   data-action-button-name="${fieldData.type.actionButtonName}"
                                   data-field-name="${fieldData.type.fieldName}" validate="required:${fieldData.hint}"/>
                            <span class="input-group-addon border_right clear_query_search_input"><i class="icon icon-close"></i></span>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="input-group">
                            <input type="hidden" name="${fieldData.field.name}" value="${fieldData.value.value}" class="query_value" data-ref-value="${fieldData.field.name}"/>
                            <input type="text" class="form-control popupSingleChoose" data-ref-value="${fieldData.field.name}" readonly placeholder="点击选择"
                                   value="${fieldData.value.html}"
                                   data-service-name="${fieldData.type.serviceName}"
                                   data-search-title-name="${fieldData.type.searchTitleName}"
                                   data-action-button-name="${fieldData.type.actionButtonName}"
                                   data-field-name="${fieldData.type.fieldName}"/>
                            <span class="input-group-addon border_right clear_query_search_input"><i class="icon icon-close"></i></span>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${fieldData.require}">
                        <select name="${fieldData.field.name}" id="${fieldData.field.name}"  validate="required:${fieldData.hint}" data-placeholder="${fieldData.placeholder}" class='form-control chosen-select'>
                            <option></option>
                            <c:forEach items="${fieldData.value}" var="sl">
                                <c:choose>
                                    <c:when test="${sl.selected}">
                                        <option value='${sl.value}' key="${sl.dataKeys}" selected="selected">${sl.html}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value='${sl.value}' key="${sl.dataKeys}">${sl.html}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </c:when>
                    <c:otherwise>
                        <select name="${fieldData.field.name}" id="${fieldData.field.name}"  data-placeholder="${fieldData.placeholder}" class='form-control chosen-select'>
                            <option></option>
                            <c:forEach items="${fieldData.value}" var="sl">
                                <c:choose>
                                    <c:when test="${sl.selected}">
                                        <option value='${sl.value}' key="${sl.dataKeys}" selected="selected">${sl.html}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value='${sl.value}' key="${sl.dataKeys}">${sl.html}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:when test="${fieldData.type.fieldType.showName=='DOCUMENT'}">
        <div class="uploader" data-max-size="${fieldData.type.maxFileSize}"
             data-multi-selection="${fieldData.type.mutil}"
             data-mine-types="${fieldData.type.mineTypes}"
             data-mine-type-name="${fieldData.type.mineTypeName}"
             data-rename="${fieldData.type.rename}"
             data-field-id="${fieldData.field.name}"
             data-upload-url="${managerPath}/access/tempUpload${suffix}"
             data-access-temp-url="${managerPath}/access/accessTempFile${suffix}"
             data-access-storage-url="${managerPath}/access/accessStorageFile${suffix}">
            <div class="fileJsonString hidden"><c:choose><c:when test="${empty fieldData.value}">{}</c:when><c:otherwise>${fieldData.value}</c:otherwise>
            </c:choose></div>
            <div class="uploader-message text-center">
                <div class="content"></div>
                <button type="button" class="close">×</button>
            </div>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th colspan="2">文件名</th>
                    <th style="width: 100px">大小</th>
                    <th style="width: 160px; text-align: center;">状态/操作</th>
                </tr>
                </thead>
                <tbody class="uploader-files">
                <tr class="file template">
                    <td style="width: 38px; padding: 3px"><div class="file-icon"></div></td>
                    <td style="padding: 0">
                        <div style="position: relative; padding: 8px;">
                            <strong class="file-name"></strong>
                            <div class="file-progress-bar"></div>
                        </div>
                    </td>
                    <td><span class="file-size text-muted"></span></td>
                    <td class="actions text-right" style="padding: 0 4px;">
                        <div class="file-status" data-toggle="tooltip" style="margin: 8px;"><i class="icon"></i> <span class="text"></span></div>
                        <a href="#" data-toggle="tooltip" class="btn btn-link btn-download-file" target="_blank"><i class="icon icon-import"></i></a>
                        <button type="button" data-toggle="tooltip" class="btn btn-link btn-reset-file" title="Repeat"><i class="icon icon-repeat"></i></button>
                        <button type="button" data-toggle="tooltip" title="Remove" class="btn btn-link btn-delete-file"><i class="icon icon-trash text-danger"></i></button>
                    </td>
                </tr>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="4" style="padding: 4px 0">
                        <div style="position: relative;">
                            <div class="uploader-status pull-right text-muted" style="margin-top: 5px;"></div>
                            <button type="button" class="btn btn-link uploader-btn-browse"><i class="icon icon-plus"></i> 选择文件</button>
                        </div>
                    </td>
                </tr>
                </tfoot>
            </table>
        </div>
    </c:when>
</c:choose>