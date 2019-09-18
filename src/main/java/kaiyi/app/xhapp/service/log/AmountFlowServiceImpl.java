package kaiyi.app.xhapp.service.log;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.log.AmountFlow;
import kaiyi.app.xhapp.entity.log.enums.AmountType;
import kaiyi.app.xhapp.entity.log.enums.BorrowLend;
import kaiyi.app.xhapp.entity.log.enums.TradeCourse;
import kaiyi.app.xhapp.entity.pojo.FlowStatisticsPojo;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.data.Currency;
import kaiyi.puer.db.query.OrderBy;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.Objects;

@Service("amountFlowService")
public class AmountFlowServiceImpl extends InjectDao<AmountFlow> implements AmountFlowService {
    private static final long serialVersionUID = -739490503110820186L;


    @Override
    public void saveNote(Account account, AmountType amountType, TradeCourse tradeCourse, String orderId, int beforeAmount, int amount,
                         int afterAmount, BorrowLend borrowLend) {
        AmountFlow flow=new AmountFlow(account,amountType,tradeCourse,orderId,beforeAmount,amount,afterAmount,borrowLend);
        saveObject(flow);

    }

    @Override
    public FlowStatisticsPojo flowStatisticsPojo(QueryExpress queryExpress) {
        FlowStatisticsPojo pojo=new FlowStatisticsPojo();
        Query query=em.createQuery("select sum(o.amount)" +
                " from "+getEntityName(entityClass)+" o where "+queryExpress.build());
        queryExpress.setParameter(query);
        Object result=query.getSingleResult();
        long totamAmount=Objects.isNull(result)?0l:Long.valueOf(result.toString());
        pojo.setTotalAmount(Currency.noDecimalBuild(totamAmount,2).toString());
        return pojo;
    }

    @Override
    public OrderBy getDefaultOrderBy(String prefix) {
        return new OrderBy(prefix,"createTime",OrderBy.TYPE.DESC);
    }
}
