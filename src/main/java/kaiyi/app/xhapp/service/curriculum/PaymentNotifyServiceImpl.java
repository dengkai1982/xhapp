package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.PaymentNotify;
import kaiyi.app.xhapp.entity.pojo.FlowStatisticsPojo;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.data.Currency;
import kaiyi.puer.db.query.OrderBy;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.Objects;

@Service("paymentNotifyService")
public class PaymentNotifyServiceImpl extends InjectDao<PaymentNotify> implements PaymentNotifyService {
    private static final long serialVersionUID = 1315522015963396416L;

    @Override
    public FlowStatisticsPojo flowStatisticsPojo(QueryExpress queryExpress) {
        FlowStatisticsPojo pojo=new FlowStatisticsPojo();
        Query query=em.createQuery("select sum(o.totalAmount)" +
                " from "+getEntityName(entityClass)+" o where "+queryExpress.build());
        queryExpress.setParameter(query);
        Object result=query.getSingleResult();
        Double totamAmount=Objects.isNull(result)?0l:Double.valueOf(result.toString());
        pojo.setTotalAmount(totamAmount.toString());
        return pojo;
    }
    @Override
    public OrderBy getDefaultOrderBy(String prefix) {
        return new OrderBy(prefix,"finishTime",OrderBy.TYPE.DESC);
    }
}
