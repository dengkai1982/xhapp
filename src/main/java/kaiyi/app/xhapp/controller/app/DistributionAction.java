package kaiyi.app.xhapp.controller.app;

import kaiyi.app.xhapp.controller.mgr.ManagerController;
import kaiyi.app.xhapp.entity.distribution.BankInfo;
import kaiyi.app.xhapp.entity.distribution.WithdrawApply;
import kaiyi.app.xhapp.entity.distribution.enums.BankType;
import kaiyi.app.xhapp.service.curriculum.CourseOrderService;
import kaiyi.app.xhapp.service.distribution.BankInfoService;
import kaiyi.app.xhapp.service.distribution.WithdrawApplyService;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.Currency;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.json.creator.CollectionJsonCreator;
import kaiyi.puer.json.creator.JsonMessageCreator;
import kaiyi.puer.json.creator.MutilJsonCreator;
import kaiyi.puer.json.creator.ObjectJsonCreator;
import kaiyi.puer.web.elements.ChosenElement;
import kaiyi.puer.web.servlet.WebInteractive;
import kaiyi.puer.web.springmvc.IWebInteractive;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(DistributionAction.rootPath)
public class DistributionAction extends SuperAction {

    public static final String rootPath=PREFIX+"/distribution";
    @Resource
    private BankInfoService bankInfoService;
    @Resource
    private WithdrawApplyService withdrawApplyService;
    @Resource
    private CourseOrderService courseOrderService;

    /**
     * 销量统计
     * accountId 当前账户ID
     * date 统计时间，格式为201901或201912
     * isTeam true 获取团队销量, false获取个人销量
     * @param interactive
     * @param response
     */
    @RequestMapping("totalSale")
    public void totalSale(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String accountId=interactive.getStringParameter("accountId","");
        String date=interactive.getStringParameter("date","");
        boolean isTeam=interactive.getBoolean("isTeam","true",false);
        Currency currency=null;
        if(isTeam){
            currency=courseOrderService.totalTeamSale(accountId,date);
        }else{
            currency=courseOrderService.totalPersonSale(accountId,date);
        }
        JsonMessageCreator jmc=getSuccessMessage();
        jmc.setBody(currency.toString());
        interactive.writeUTF8Text(jmc.build());
    }
    /**
     * 获取个人网银信息
     * entityId bankInfoId
     * @param interactive
     * @param response
     * @throws IOException
     */
    @RequestMapping("/bankInfo")
    public void bankInfo(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId = interactive.getStringParameter("entityId", "");
        BankInfo bankInfo = bankInfoService.findForPrimary(entityId);
        StreamCollection<ChosenElement> bankTypes = ChosenElement.build(BankType.values(), c -> {
            return c.getItemNumber() == bankInfo.getBankType().getItemNumber();
        });
        CollectionJsonCreator<ChosenElement> bankTypeJson = new CollectionJsonCreator<>(bankTypes, new String[]{
                "html", "value", "selected"
        });
        ObjectJsonCreator<BankInfo> bankInfoJson = new ObjectJsonCreator<>(bankInfo, new String[]{
                "bankAccount", "branchName", "bankAccountName", "entityId"
        });
        MutilJsonCreator mjc = new MutilJsonCreator(bankTypeJson, bankInfoJson);
        interactive.writeUTF8Text(mjc.build());
    }

    /**
     * 获取网银类型
     * @param interactive
     * @param response
     * @throws IOException
     */
    @RequestMapping("/bankType")
    public void bankType(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        StreamCollection<ChosenElement> bankTypes = ChosenElement.build(BankType.values());
        CollectionJsonCreator<ChosenElement> bankTypeJson = new CollectionJsonCreator<>(bankTypes, new String[]{
                "html", "value", "selected"
        });
        interactive.writeUTF8Text(bankTypeJson.build());
    }

    /**
     * 提交个人网银信息
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/commitBankInfo")
    public void commitBankInfo(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        JsonMessageCreator msg=executeNewOrUpdate(interactive,bankInfoService);
        interactive.writeUTF8Text(msg.build());
    }

    /**
     * 删除网银信息
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/deleteBankInfo")
    public void deleteBankInfo(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String entityId=interactive.getStringParameter("entityId","");
        bankInfoService.deleteForPrimary(entityId);
        interactive.writeUTF8Text(getSuccessMessage().build());
    }

    /**
     * 申请提现
     * bankInfoId 网银信息ID
     * @param interactive
     * @param response
     * @throws IOException
     */
    @PostMapping("/withdrawApply")
    public void withdrawApply(@IWebInteractive WebInteractive interactive, HttpServletResponse response) throws IOException {
        String bankInfoId=interactive.getStringParameter("bankInfoId","");
        String accountId=interactive.getStringParameter("accountId","");
        Currency amount=interactive.getCurrency("amount");
        String phone=interactive.getStringParameter("phone","");
        JsonMessageCreator jmc=getSuccessMessage();
        try {
            WithdrawApply apply=withdrawApplyService.apply(bankInfoId,accountId,amount.getNoDecimalPointToInteger(),phone);
            sendInsideMessage(interactive,"有一条新的提现申请,申请人:"+apply.getApplyer().getShowAccountName()+",申请金额:"+
            Currency.noDecimalBuild(apply.getApplyAmount(),2).toString(),
                    ManagerController.prefix+"/distribution/withdrawApply",apply.getEntityId());
        } catch (ServiceException e) {
            catchServiceException(jmc,e);
        }
        interactive.writeUTF8Text(jmc.build());
    }


}

