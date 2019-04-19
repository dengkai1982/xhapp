//该数据表格必须和paginationPage配合使用
$('#remoteDataGrid').datagrid({
    onRender: function(d) {
        if(functionExist("changeGridHeight")){
            changeGridHeight();
        }else{
            if(functionExist("dataLoadSuccess")){
                dataLoadSuccess($("#query_search_from input[name='queryCondition']").val());
            }
        }
    },
    dataSource: {
        cols:[
        <#list tableFieldDatas as columnData>
            <#if (tableFieldDatas?size>8)>
                <#if columnData.field??>
                    {name:"${columnData.field.name}",html:true,valueType:"${columnData.tableColumnType}",sort:${columnData.orderBy?c},label:"${columnData.label}",width:${columnData.tableLength}},
                </#if>
            <#else>
                <#if columnData.field??>
                    {name:"${columnData.field.name}",html:true,valueType:"${columnData.tableColumnType}",sort:${columnData.orderBy?c},label:"${columnData.label}"},
                </#if>
            </#if>
        </#list>
            {name: 'oper', label: '操作',sort:false,html:true,width:110,valueOperator:{
                getter: function(dataValue, cell, dataGrid) {
                    if(dataValue==undefined){
                        return "<a data-id='" + cell.config.data.entityId + "' class='text-primary show-menu'>操作选项</a>";
                    }
                    return dataValue;
                }
            }}
        ],
        remote: function(params) {
            var requestParams=$("#query_search_from").formToJson();
            requestParams['${paginationCurrentPage}']=getPaginationCurrentPage();
            requestParams['${paginationMaxResult}']=params.recPerPage;
            requestParams['${paginationSortBy}']=params.sortBy;
            requestParams['${paginationOrderType}']=params.order;
            return {
                url:"${queryUrl}",
                type: 'POST',
                data: requestParams,
                dataType: 'json'
            };
        },
        remoteConverter:function(result,textStatus,jqXHR,datagrid){
            //如果存在构建字段方法,createPagination方法在paginationPage中
            var pagination=createPagination(result);
            setPagination(pagination);
            return pagination;
        },
        cache:false
    },
    valueOperator: {
        // date 值转换器会影响所以 valueType 为 `date` 的列
        date: {
            getter: function(dataValue, cell, dataGrid) {
                return tryConvertCell("date",dataValue,cell, dataGrid);
            }
        },
        string:{
            getter: function(dataValue, cell, dataGrid) {
                return tryConvertCell("string",dataValue,cell, dataGrid);
            }
        },
        number:{
            getter: function(dataValue, cell, dataGrid) {
                return tryConvertCell("number",dataValue,cell, dataGrid);
            }
        },
        boolean:{
            getter: function(dataValue, cell, dataGrid) {
                return tryConvertCell("boolean",dataValue,cell, dataGrid);
            }
        },
        document:{
            getter: function(dataValue, cell, dataGrid) {
                return tryConvertCell("document",dataValue,cell, dataGrid);
            }
        },
        chosen:{
            getter: function(dataValue, cell, dataGrid) {
                return tryConvertCell("chosen",dataValue,cell, dataGrid);
            }
        },
        reference:{
            getter: function(dataValue, cell, dataGrid) {
                return tryConvertCell("reference",dataValue,cell, dataGrid);
            }
        },
        collection:{
            getter: function(dataValue, cell, dataGrid) {
                return tryConvertCell("collection",dataValue,cell, dataGrid);
            }
        }
    },
    responsive: true,
    sortable: true,
    hoverCell: false,
    hoverCol: false,
    width: '100%',
    height:getDataGridHeight(),
    rowDefaultHeight:34,
    <#if tableMutilChosen?c=="true">
    checkable: true,
    checkByClickRow: false,
    <#else>
    showRowIndex: false,
    </#if>
    states: {
        pager:{
            page: getPaginationCurrentPage(),
            recPerPage: parseInt('${pageMaxResult}')
        },
        <#if (tableFieldDatas?size>8)>
        fixedLeftUntil: 1, // 固定左侧第一列
        fixedRightFrom: ${columnDataSize}
        </#if>
    }
});
//显示操作菜单
$("#remoteDataGrid").on("click", ".show-menu", function(e) {
    var $this = $(this);
    var dataId=$this.attr("data-id");
    var dataRow=$this.parent(".datagrid-cell").attr("data-row");
    var item=[];
    var datas=$("#remoteDataGrid").data('zui.datagrid').getData();
    if(functionExist("createMenuItems")){
        item=createMenuItems(dataId,dataRow,datas[dataRow-1]);
    }
    if(item.length==0){
        bootbox.alert({
            title:"警告",
            message:"暂无操作选项权限,请联系管理员"
        });
        return;
    }
    $.zui.ContextMenu.show(item);
    $(".contextmenu-menu").css("top", e.clientY + "px").css("left", (e.clientX - $(".contextmenu-menu").width()) + "px")
})
<#if tableMutilChosen?c=="true">
    //行选择事件
    $("#remoteDataGrid").on("click", ".datagrid-checkbox", function(e) {
        if(functionExist("nodifyDataGridLineChoose")){
            var myDataGrid = $('#remoteDataGrid').data('zui.datagrid');
            var selectedItems = myDataGrid.getCheckItems();
            //通知行被选中
            nodifyDataGridLineChoose(selectedItems.length>0);
        }
    })
</#if>