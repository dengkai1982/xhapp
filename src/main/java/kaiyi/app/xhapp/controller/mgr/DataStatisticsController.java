package kaiyi.app.xhapp.controller.mgr;

import kaiyi.app.xhapp.controller.mgr.enums.FlowType;
import kaiyi.app.xhapp.entity.pojo.FlowStatisticsPojo;
import kaiyi.app.xhapp.service.FlowStatisticsCount;
import kaiyi.app.xhapp.service.access.AccountRechargeService;
import kaiyi.app.xhapp.service.curriculum.PaymentNotifyService;
import kaiyi.app.xhapp.service.log.AmountFlowService;
import kaiyi.app.xhapp.service.log.CourseBrowseService;
import kaiyi.app.xhapp.service.log.ShortMessageSenderNoteService;
import kaiyi.app.xhapp.service.log.TeamJoinNoteService;
import kaiyi.puer.commons.access.AccessControl;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.db.orm.CustomQueryExpress;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.h5ui.bean.DynamicGridInfo;
import kaiyi.puer.json.creator.JsonBuilder;
import kaiyi.puer.json.creator.ObjectJsonCreator;
import kaiyi.puer.web.elements.ChosenElement;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import kaiyi.puer.web.springmvc.SpringContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping(DataStatisticsController.rootPath)
@AccessControl(name = "数据统计", weight = 8f, detail = "统计系统数据", code = DataStatisticsController.rootPath)
public class DataStatisticsController extends ManagerController{
    public static final String rootPath=prefix+"/statistics";
    @Resource
    private AmountFlowService amountFlowService;
    @Resource
    private CourseBrowseService courseBrowseService;
    @Resource
    private ShortMessageSenderNoteService shortMessageSenderNoteService;
    @Resource
    private TeamJoinNoteService teamJoinNoteService;
    @Resource
    private PaymentNotifyService paymentNotifyService;
    @Resource
    private AccountRechargeService accountRechargeService;
    @RequestMapping("/flow")
    @AccessControl(name = "系统流水", weight = 8.1f, code = rootPath+ "/flow", parent = rootPath)
    public String statisticsFlow(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        String prefixPath=rootPath+"/flow";
        FlowType flowType=interactive.getEnumParameterByOrdinal(FlowType.class,"flowType",FlowType.MEMBER_AMOUNT_FLOW);
        StreamCollection<ChosenElement> flowTypeChosen=ChosenElement.build(FlowType.values(), c->{
            return c.getValue().equals(flowType.getValue());
        });
        String backUrl="";
        DynamicGridInfo dynamicGridInfo=new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.none);
        dynamicGridInfo.setClassName("noneOperTable");
        DatabaseQuery databaseQuery=null;
        if(flowType.equals(FlowType.MEMBER_AMOUNT_FLOW)){
            databaseQuery=amountFlowService;
            backUrl="/amountFlow";
        }else if(flowType.equals(FlowType.COURSE_BROWSE)){
            databaseQuery=courseBrowseService;
            backUrl="/courseBrowse";
        }else if(flowType.equals(FlowType.SHORT_MESSAGE_SENDER_NOTE)){
            databaseQuery=shortMessageSenderNoteService;
            backUrl="/shortMessageSenderNote";
        }else if(flowType.equals(FlowType.MEMBER_JOIN_FLOW)){
            databaseQuery=teamJoinNoteService;
            backUrl="/teamJoinNote";
        }else if(flowType.equals(FlowType.PAYMENT_NOTE)){
            databaseQuery=paymentNotifyService;
            backUrl="/paymentNotify";
        }else if(flowType.equals(FlowType.ACCOUNT_RECHARGE)){
            databaseQuery=accountRechargeService;
            backUrl="/accountRecharge";
        }
        setDefaultPage(interactive,rootPath+"/fow");
        mainTablePage(interactive,databaseQuery,null,null, dynamicGridInfo);
        interactive.setRequestAttribute("flowTypeChosen",flowTypeChosen);
        return prefixPath+backUrl;
    }

    @RequestMapping("/order")
    @AccessControl(name = "销量统计", weight = 8.2f, code = rootPath+ "/order", parent = rootPath)
    public String statisticsOrder(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        return "order";
    }

    @RequestMapping("/memberAmountFlowCount")
    public void memberAmountFlowCount(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        FlowStatisticsPojo flowStatisticsPojo=flowStatisticsQuery(interactive,amountFlowService);
        ObjectJsonCreator<FlowStatisticsPojo> jsonCreator=new ObjectJsonCreator<>(flowStatisticsPojo,new String[]{
                "totalAmount"
        });
        jsonCreator.setObjectName("flowStatistics");
        interactive.writeUTF8Text(jsonCreator.build());

    }

    @RequestMapping("/paymentNoteFlowCount")
    public void paymentNoteFlowCount(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        FlowStatisticsPojo flowStatisticsPojo=flowStatisticsQuery(interactive,paymentNotifyService);
        ObjectJsonCreator<FlowStatisticsPojo> jsonCreator=new ObjectJsonCreator<>(flowStatisticsPojo,new String[]{
                "totalAmount"
        });
        jsonCreator.setObjectName("flowStatistics");
        interactive.writeUTF8Text(jsonCreator.build());
    }

    @RequestMapping("/accountRechargeCount")
    public void accountRechargeCount(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        FlowStatisticsPojo flowStatisticsPojo=flowStatisticsQuery(interactive,accountRechargeService);
        ObjectJsonCreator<FlowStatisticsPojo> jsonCreator=new ObjectJsonCreator<>(flowStatisticsPojo,new String[]{
                "totalAmount"
        });
        jsonCreator.setObjectName("flowStatistics");
        interactive.writeUTF8Text(jsonCreator.build());
    }

    private FlowStatisticsPojo flowStatisticsQuery(WebInteractive interactive, FlowStatisticsCount flowStatisticsCount){
        String queryCondition=interactive.getHttpServletRequest().getParameter("queryCondition");
        String serviceName=interactive.getStringParameter("serviceName","");
        SpringContextHolder springContextHolder=applicationService.getSpringContextHolder();
        final DatabaseQuery<? extends JsonBuilder> databaseQuery=springContextHolder.getBean(serviceName);
        String className=databaseQuery.getEntityClassName();
        QueryExpress query=parseQueryExpress(className,queryCondition);
        query=getDefaultQuery(interactive,databaseQuery,query);
        return flowStatisticsCount.flowStatisticsPojo(query);
    }

    protected QueryExpress getDefaultQuery(WebInteractive interactive, DatabaseQuery databaseQuery, QueryExpress query){
        if(databaseQuery instanceof CustomQueryExpress){
            CustomQueryExpress customQueryExpress=(CustomQueryExpress)databaseQuery;
            if(Objects.isNull(query)){
                query=customQueryExpress.getCustomerQuery(interactive.getRequestParameterMap());
            }else{
                Map<String,JavaDataTyper> params=interactive.getRequestParameterMap();
                StreamCollection<String> fields=query.getQueryField();
                for(String fieldName:fields){
                    params.remove(fieldName);
                }
                query=new LinkQueryExpress(query,LinkQueryExpress.LINK.AND,customQueryExpress.getCustomerQuery(params));
            }
        }
        return query;
    }
}