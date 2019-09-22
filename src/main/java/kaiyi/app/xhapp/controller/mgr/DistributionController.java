package kaiyi.app.xhapp.controller.mgr;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.app.xhapp.entity.distribution.RoyaltySettlement;
import kaiyi.app.xhapp.entity.distribution.RoyaltyType;
import kaiyi.app.xhapp.service.distribution.RoyaltySettlementService;
import kaiyi.app.xhapp.service.distribution.RoyaltyTypeService;
import kaiyi.app.xhapp.service.distribution.WithdrawApplyService;
import kaiyi.puer.commons.access.AccessControl;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.Currency;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.data.StringEditor;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.h5ui.bean.DynamicGridInfo;
import kaiyi.puer.h5ui.controller.Accesser;
import kaiyi.puer.json.JsonCreator;
import kaiyi.puer.json.JsonValuePolicy;
import kaiyi.puer.json.creator.JsonBuilder;
import kaiyi.puer.json.creator.JsonMessageCreator;
import kaiyi.puer.json.creator.StringJsonCreator;
import kaiyi.puer.web.elements.ChosenElement;
import kaiyi.puer.web.elements.FormElementHidden;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping(DistributionController.rootPath)
@AccessControl(name = "分销管理", weight = 6f, detail = "分销管理内容功能", code = DistributionController.rootPath)
public class DistributionController extends ManagerController{
    public static final String rootPath=prefix+"/distribution";

    @Resource
    private RoyaltyTypeService royaltyTypeService;
    @Resource
    private RoyaltySettlementService royaltySettlementService;
    @Resource
    private WithdrawApplyService withdrawApplyService;
    @RequestMapping("/royaltyType")
    @AccessControl(name = "提成类型", weight = 6.1f, detail = "管理提成类型", code = rootPath+ "/royaltyType", parent = rootPath)
    public String royaltyType(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/royaltyType");
        mainTablePage(interactive,royaltyTypeService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/royaltyType";
    }

    @RequestMapping("/royaltyType/new")
    @AccessControl(name = "新增提成类型", weight = 6.11f, detail = "添加新的提成类型",
            code = rootPath+ "/royaltyType/new", parent = rootPath+"/royaltyType")
    public String royaltyTypeNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,royaltyTypeService,3);
        setDefaultPage(interactive,rootPath+"/royaltyType");
        return rootPath+"/royaltyTypeForm";
    }
    @RequestMapping("/royaltyType/modify")
    @AccessControl(name = "编辑提成类型", weight = 6.12f, detail = "编辑提成类型",
            code = rootPath+ "/royaltyType/modify", parent = rootPath+"/royaltyType")
    public String royaltyTypeModify(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,royaltyTypeService,3);
        setDefaultPage(interactive,rootPath+"/royaltyType");
        return rootPath+"/royaltyTypeForm";
    }
    @PostMapping("/royaltyType/commit")
    public void royaltyTypeCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=executeNewOrUpdate(interactive,royaltyTypeService);
        interactive.writeUTF8Text(msg.build());
    }

    @RequestMapping("/royaltySettlement")
    @AccessControl(name = "提成结算", weight = 6.2f, detail = "结算提成", code = rootPath+ "/royaltySettlement", parent = rootPath)
    public String royaltySettlement(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/royaltySettlement");
        mainTablePage(interactive,royaltySettlementService,null,null,
                new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup));
        return rootPath+"/royaltySettlement";
    }
    @RequestMapping("/royaltySettlement/new")
    @AccessControl(name = "新增提成结算", weight = 6.21f, detail = "添加新的提成类型",
            code = rootPath+ "/royaltySettlement/new", parent = rootPath+"/royaltySettlement")
    public String royaltySettlementNew(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,royaltySettlementService,3);
        setDefaultPage(interactive,rootPath+"/royaltySettlement");
        setRoyaltyTypes(interactive,null);
        return rootPath+"/royaltySettlementForm";
    }

    private void setRoyaltyTypes(WebInteractive interactive, RoyaltySettlement royaltySettlement){
        StreamCollection<RoyaltyType> types=royaltyTypeService.getEntitys();
        StreamCollection<ChosenElement> typeChosen=new StreamCollection<>();
        types.forEach(f->{
            ChosenElement chosenElement=new ChosenElement();
            chosenElement.setSelected(false);
            chosenElement.setHtml(f.getName());
            chosenElement.setValue(f.getEntityId());
            if(Objects.nonNull(royaltySettlement)&&royaltySettlement.getRoyaltyType().getEntityId().equals(f.getEntityId())){
                chosenElement.setSelected(true);
            }
            typeChosen.add(chosenElement);
        });

        interactive.setRequestAttribute("typeChosen",typeChosen);
    }

    @RequestMapping("/royaltySettlement/modify")
    @AccessControl(name = "编辑提成结算", weight = 6.22f, detail = "编辑提成结算",
            code = rootPath+ "/royaltySettlement/modify", parent = rootPath+"/royaltySettlement")
    public String royaltySettlementModify(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        RoyaltySettlement rs=newOrEditPage(interactive,royaltySettlementService,3);
        setRoyaltyTypes(interactive,rs);
        setDefaultPage(interactive,rootPath+"/royaltySettlement");
        return rootPath+"/royaltySettlementForm";
    }
    @RequestMapping("/royaltySettlement/delete")
    @AccessControl(name = "删除提成记录", weight = 6.23f, detail = "编辑提成结算",
            code = rootPath+ "/royaltySettlement/delete", parent = rootPath+"/royaltySettlement")
    public void royaltySettlementDelete(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        royaltySettlementService.deleteById(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }
    @RequestMapping("/royaltySettlement/grant")
    @AccessControl(name = "提成发放", weight = 6.24f, detail = "编辑提成结算",
            code = rootPath+ "/royaltySettlement/grant", parent = rootPath+"/royaltySettlement")
    public void royaltySettlementGrant(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        Accesser accesser=getLoginedUser(interactive);
        royaltySettlementService.grant(entityId,accesser.getEntityId());
        interactive.writeUTF8Text(getSuccessMessage().build());
    }
    @RequestMapping("/royaltySettlement/detail")
    @AccessControl(name = "提成发放详情", weight = 6.25f, detail = "查看提成发放详情",
            code = rootPath+ "/royaltySettlement/detail", parent = rootPath+"/royaltySettlement",defaultAuthor = true)
    public String royaltySettlementDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,royaltySettlementService,3);
        setDefaultPage(interactive,rootPath+"/royaltySettlement");
        interactive.getWebPage().setPageTitle("查看课程订单详情");
        return rootPath+"/royaltySettlementDetail";
    }
    @PostMapping("/royaltySettlement/commit")
    public void royaltySettlementCommit(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        Map<String,JavaDataTyper> params=interactive.getRequestParameterMap();
        params.put("creater",new JavaDataTyper(getLoginedUser(interactive).getEntityId()));
        JsonMessageCreator msg=executeNewOrUpdate(interactive,royaltySettlementService,params);
        interactive.writeUTF8Text(msg.build());
    }

    @PostMapping("/royaltySettlement/generator")
    public void royaltySettlementGenerator(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String accountId=interactive.getStringParameter("accountId","");
        String royaltyTypeId=interactive.getStringParameter("royaltyTypeId","");
        RoyaltySettlement royaltySettlement=royaltySettlementService.generatorRoyaltySettlement(accountId,royaltyTypeId);
        JsonCreator jsonCreator=defaultWriteObject(royaltySettlement, new JsonValuePolicy<JsonBuilder>() {
            @Override
            public JsonCreator getCreator(JsonBuilder entity, String field, Object fieldValue) {
                if(field.equals("level1Amount")||field.equals("level2Amount")||field.equals("insideAmount")){
                    long amount=Long.valueOf(fieldValue.toString());
                    return new StringJsonCreator(Currency.noDecimalBuild(amount,2).toString());
                }else if(field.equals("recommend1")||field.equals("recommend2")||field.equals("insideMember")){
                    Account account=(Account)fieldValue;
                    if(Objects.nonNull(account)){
                        return new StringJsonCreator(account.getShowAccountName());
                    }
                }
                return null;
            }
        });
        interactive.writeUTF8Text(jsonCreator.build());
    }


    @RequestMapping("/withdrawApply")
    @AccessControl(name = "提现管理", weight = 6.3f, code = rootPath+ "/withdrawApply", parent = rootPath)
    public String withdrawApply(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        setDefaultPage(interactive,rootPath+"/withdrawApply");
        FormElementHidden[] formElementHiddens=null;
        String onlyEntityId=interactive.getStringParameter("onlyEntityId","");
        if(StringEditor.notEmpty(onlyEntityId)){
            formElementHiddens=new FormElementHidden[]{
                    new FormElementHidden("onlyEntityId",onlyEntityId)
            };
        }
        DynamicGridInfo dynamicGridInfo=new DynamicGridInfo(false,DynamicGridInfo.OperMenuType.popup);
        mainTablePage(interactive,withdrawApplyService,null,formElementHiddens, dynamicGridInfo);
        return rootPath+"/withdrawApply";
    }
    @RequestMapping("/withdrawApply/modify")
    @AccessControl(name = "处理提现", weight = 6.31f, detail = "处理提现",
            code = rootPath+ "/withdrawApply/modify", parent = rootPath+"/withdrawApply")
    public String withdrawApplyModify(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        newOrEditPage(interactive,withdrawApplyService,3);
        setDefaultPage(interactive,rootPath+"/withdrawApply");
        return rootPath+"/withdrawApplyForm";
    }
    @RequestMapping("/withdrawApply/detail")
    @AccessControl(name = "提现详情", weight = 6.32f, detail = "提现详情",
            code = rootPath+ "/withdrawApply/detail", parent = rootPath+"/withdrawApply")
    public String withdrawApplyDetail(@IWebInteractive WebInteractive interactive, HttpServletResponse response){
        detailPage(interactive,withdrawApplyService,3);
        setDefaultPage(interactive,rootPath+"/withdrawApply");
        return rootPath+"/withdrawApplyDetail";
    }
    @PostMapping("/finishWithdraw")
    public void finishWithdraw(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        boolean disposeResult=interactive.getBoolean("disposeResult","true",false);
        String transBank=interactive.getStringParameter("transBank","");
        String voucher=interactive.getStringParameter("voucher","");
        String mark=interactive.getStringParameter("mark","");
        JsonMessageCreator jmc=getSuccessMessage();
        VisitorUser visitorUser=(VisitorUser)getLoginedUser(interactive.getHttpServletRequest());
        try {
            withdrawApplyService.dispose(entityId,disposeResult,transBank,voucher,mark,visitorUser);
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Json(jmc);
    }
}
