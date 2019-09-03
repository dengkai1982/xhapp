/**
 * 后台类别查询
 * @param windowTitle
 * @param referenceQueryId
 * @param referenceQueryName
 * @param windowClose
 */
function queryCategory(windowTitle,referenceQueryId,referenceQueryName,windowClose){
    var time=new Date().getTime();
    var url=contextPath+"/mgr/curriculum/categoryEnableQuery"+suffix+"?referenceQueryId="+referenceQueryId+"&referenceQueryName="+referenceQueryName+"&t="+time;
    categoryQueryModalTrigger = new $.zui.ModalTrigger({
        url:url,
        size:'sm',
        title:windowTitle,
        backdrop:'static',
        type:'iframe',
        width:400,
        height:280
    });
    categoryQueryModalTrigger.show({hidden: function() {
            if(typeof windowClose=='function'){
                windowClose();
            }
            $("#triggerModal").remove();
        }});
}
function queryQuestionCategory(windowTitle,referenceQueryId,referenceQueryName,windowClose){
    var time=new Date().getTime();
    var url=contextPath+"/mgr/examination/categoryEnableQuery"+suffix+"?referenceQueryId="+referenceQueryId+"&referenceQueryName="+referenceQueryName+"&t="+time;
    queryCategoryQueryModalTrigger = new $.zui.ModalTrigger({
        url:url,
        size:'sm',
        title:windowTitle,
        backdrop:'static',
        type:'iframe',
        width:400,
        height:280
    });
    queryCategoryQueryModalTrigger.show({hidden: function() {
            if(typeof windowClose=='function'){
                windowClose();
            }
            $("#triggerModal").remove();
        }});
}
function simulationQuestionCategory(windowTitle,referenceQueryId,referenceQueryName,windowClose){
    var time=new Date().getTime();
    var url=contextPath+"/mgr/examination/simulationCategoryEnableQuery"+suffix+"?referenceQueryId="+referenceQueryId+"&referenceQueryName="+referenceQueryName+"&t="+time;
    simulationCategoryQueryModalTrigger = new $.zui.ModalTrigger({
        url:url,
        size:'sm',
        title:windowTitle,
        backdrop:'static',
        type:'iframe',
        width:400,
        height:280
    });
    simulationCategoryQueryModalTrigger.show({hidden: function() {
            if(typeof windowClose=='function'){
                windowClose();
            }
            $("#triggerModal").remove();
        }});
}