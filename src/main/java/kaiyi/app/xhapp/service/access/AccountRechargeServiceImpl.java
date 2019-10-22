package kaiyi.app.xhapp.service.access;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.AccountRecharge;
import kaiyi.app.xhapp.entity.access.enums.RechargeStatus;
import kaiyi.app.xhapp.entity.curriculum.PaymentNotify;
import kaiyi.app.xhapp.entity.pojo.FlowStatisticsPojo;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.data.Currency;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.OrderBy;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Query;
import java.util.Date;
import java.util.Objects;

@Service("accountRechargeService")
public class AccountRechargeServiceImpl extends InjectDao<AccountRecharge> implements AccountRechargeService {
    private static final long serialVersionUID = 3823425190981416964L;
    @Resource
    private AccountService accountService;
    @Override
    public AccountRecharge generatorOrder(String rechargerId, int price)throws ServiceException {
        Account recharge=accountService.findForPrimary(rechargerId);
        if(Objects.isNull(recharge)){
            throw ServiceExceptionDefine.entityNotExist;
        }
        if(price<=0){
            throw ServiceExceptionDefine.rechargeZreo;
        }
        AccountRecharge accountRecharge=new AccountRecharge();
        accountRecharge.setOrderId(randomIdentifier());
        accountRecharge.setOrderTime(new Date());
        accountRecharge.setPrice(price);
        accountRecharge.setRecharger(recharge);
        accountRecharge.setRechargeStatus(RechargeStatus.WAIT_PAYMENT);
        saveObject(accountRecharge);
        return accountRecharge;
    }

    @Override
    public AccountRecharge paymentSaleOrder(PaymentNotify paymentNotify) {
        String orderId=paymentNotify.getOrderId();
        AccountRecharge accountRecharge=signleQuery("orderId",orderId);
        if(Objects.nonNull(accountRecharge)&&
                accountRecharge.getRechargeStatus().equals(RechargeStatus.WAIT_PAYMENT)) {
            if(paymentNotify.isSuccess()){
                accountRecharge.setRechargeStatus(RechargeStatus.PAYMENTED);
                accountRecharge.setPaymentDate(new Date());
                accountService.rechargeGold(accountRecharge);
            }
            accountRecharge.setPlatformOrderId(paymentNotify.getThirdPartOrderId());
            updateObject(accountRecharge);
        }
        return accountRecharge;
    }


    @Override
    public FlowStatisticsPojo flowStatisticsPojo(QueryExpress queryExpress) {
        FlowStatisticsPojo pojo=new FlowStatisticsPojo();
        Query query=em.createQuery("select sum(o.price)" +
                " from "+getEntityName(entityClass)+" o where "+queryExpress.build());
        queryExpress.setParameter(query);
        Object result=query.getSingleResult();
        long totamAmount=Objects.isNull(result)?0l:Long.valueOf(result.toString());
        pojo.setTotalAmount(Currency.noDecimalBuild(totamAmount,2).toString());
        return pojo;
    }
    @Override
    public OrderBy getDefaultOrderBy(String prefix) {
        return new OrderBy(prefix,"orderTime",OrderBy.TYPE.DESC);
    }
}
