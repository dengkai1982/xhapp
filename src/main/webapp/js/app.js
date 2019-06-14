var Condition_EQUAL="0";
var Condition_NOT_EQUAL="1";
var Condition_GT="2";
var Condition_GT_AND_EQUAL="3";
var Condition_LS="4";
var Condition_LS_AND_EQUAL="5";
var Condition_CONTAINER="6";
var Condition_NOT_CONTAINER="7";
var LINK_AND="0";
var LINK_OR="1";
var carouselService="carouselService";
var noticeService="noticeService";
var imageTextMessageService="imageTextMessageService";
var shopCarService="shopCarService";
var memberService="memberService";
var bankInfoService="bankInfoService";
var withdrawApplyService="withdrawApplyService";
var memberAmountFlowService="memberAmountFlowService";
var customFreePostService="customFreePostService";
var expressService="expressService"
function baseQuery(app,serviceName,querys,success){
    queryData(app,serviceName,querys,0,100000,success);
}
function baseQueryOrderBy(app,serviceName,querys,orderBy,orderByType,success){
    queryDataOfOrderBy(app,serviceName,querys,0,10000,orderBy,orderByType,success);
}
function queryData(app,serviceName,querys,first,maxResult,success,fail){
    queryDataOfOrderBy(app,serviceName,querys,first,maxResult,"","",success,fail);
}
/**
 * 查询数据
 [{
	field:"",
	condition:"",
	value:"",
	link:""
 }]
 */
function queryDataOfOrderBy(app,serviceName,querys,first,maxResult,orderBy,orderByType,success,fail){
    var data={
        serviceName:serviceName,
        queryCondition:JSON.stringify(querys)
    };
    data["_p_f"]=first;
    data["_p_p_m_r"]=maxResult
    if(orderBy!=""){
        data["_o_s_b"]=orderBy;
        data["_o_d_t"]=orderByType;
        if(orderByType==""){
            data["_o_d_t"]="asc";
        }
    }
    app.request.json(getServerPage("/mobile/queryData"),data, success, fail);
}
function queryDataOfOrderBySetUrl(url,serviceName,querys,first,maxResult,orderBy,orderByType,success,fail){
    var data={
        serviceName:serviceName,
        queryCondition:JSON.stringify(querys)
    };
    data["_p_f"]=first;
    data["_p_p_m_r"]=maxResult
    if(orderBy!=""){
        data["_o_s_b"]=orderBy;
        data["_o_d_t"]=orderByType;
        if(orderByType==""){
            data["_o_d_t"]="asc";
        }
    }
    postJSON(url,data,"正在处理请稍后",success);
    //app.request.json(url,data, success, fail);
}
