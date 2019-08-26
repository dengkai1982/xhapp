package kaiyi.app.xhapp.service.distribution;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.app.xhapp.entity.distribution.BankInfo;
import kaiyi.app.xhapp.entity.distribution.WithdrawApply;
import kaiyi.app.xhapp.entity.distribution.enums.BankType;
import kaiyi.app.xhapp.entity.distribution.enums.WithdrawStatus;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.pub.ConfigureService;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.time.DateTimeRange;
import kaiyi.puer.commons.time.DateTimeUtil;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.OrderBy;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service("withdrawApplyService")
public class WithdrawApplyServiceImpl extends InjectDao<WithdrawApply> implements WithdrawApplyService {
    private static final long serialVersionUID = -2506754461973652024L;
    @Resource
    private BankInfoService bankInfoService;
    @Resource
    private AccountService accountService;
    @Resource
    private ConfigureService configureService;
    @Override
    public WithdrawApply apply(String bankInfoId, String accountId, int amount,String phone) throws ServiceException {
        if(existDay(accountId)){
            throw ServiceExceptionDefine.dayWithdrawOne;
        }
        BankInfo bankInfo=bankInfoService.findForPrimary(bankInfoId);
        if(Objects.isNull(bankInfo)){
            throw ServiceExceptionDefine.entityNotExist;
        }
        //int limit=500000;
        int limit=configureService.getCurrency(ConfigureItem.LIMIT_WITHDRAW_AMOUNT).getNoDecimalPointToInteger();
        if(bankInfo.getBankType().equals(BankType.weixin)&&amount>limit){
            throw ServiceExceptionDefine.limitWithdraw;
        }
        String orderId=randomIdentifier();
        Account account=accountService.applyWithdraw(accountId,amount,orderId);
        WithdrawApply apply=new WithdrawApply();
        apply.setApplyer(account);
        apply.setOrderId(orderId);
        apply.setStatus(WithdrawStatus.WAIT_VERIFY);
        apply.setCardNumber(bankInfo.getBankAccount());
        apply.setApplyAmount(amount);
        apply.setBankType(bankInfo.getBankType());
        apply.setBranchName(bankInfo.getBranchName());
        apply.setBankAccountName(bankInfo.getBankAccountName());
        apply.setApplyTime(new Date());
        apply.setContractPhone(phone);
        apply.setMark("");
        saveObject(apply);
        return apply;
    }

    @Override
    public QueryExpress getCustomerQuery(Map<String, JavaDataTyper> params) {
        QueryExpress query = super.getCustomerQuery(params);
        if(Objects.nonNull(params.get("onlyEntityId"))){
            query=new LinkQueryExpress(query, LinkQueryExpress.LINK.AND,
                    new CompareQueryExpress("entityId",CompareQueryExpress.Compare.EQUAL,
                            params.get("onlyEntityId").stringValue()));
        }
        return query;
    }

    @Override
    public void dispose(String entityId, boolean disposeResult, String transBank,
                        String voucher, String mark, VisitorUser disposeUser) throws ServiceException {
        WithdrawApply apply=findForPrimary(entityId);
        if(Objects.nonNull(apply)){
            if(disposeResult){
                apply.setStatus(WithdrawStatus.FINISH);
            }else{
                apply.setStatus(WithdrawStatus.REFUSED);
                accountService.unWithdraw(apply.getApplyer().getEntityId(),apply.getApplyAmount(),apply.getOrderId());
            }
            apply.setTransBank(transBank);
            apply.setVoucher(voucher);
            apply.setMark(mark);
            apply.setDisposeUser(disposeUser);
            apply.setDisposeTime(new Date());
            updateObject(apply);
        }
    }

    @Override
    public boolean existDay(String memberId) {
        Account applyer=new Account();
        applyer.setEntityId(memberId);
        QueryExpress query=new CompareQueryExpress("applyer",CompareQueryExpress.Compare.EQUAL,applyer);
        DateTimeRange range=DateTimeUtil.getCurrenDayRange();
        query=new LinkQueryExpress(query, LinkQueryExpress.LINK.AND,new CompareQueryExpress("applyTime",
                CompareQueryExpress.Compare.GT_AND_EQUAL,range.getDayStartDate()));
        query=new LinkQueryExpress(query, LinkQueryExpress.LINK.AND,new CompareQueryExpress("applyTime",
                CompareQueryExpress.Compare.LS_AND_EQUAL,range.getDayEndDate()));
        return exist(query);
    }

    @Override
    public OrderBy getDefaultOrderBy(String prefix) {
        return new OrderBy(prefix,"applyTime",OrderBy.TYPE.DESC);
    }

}
