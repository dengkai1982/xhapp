package kaiyi.app.xhapp.service.log;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.log.AmountFlow;
import kaiyi.app.xhapp.entity.log.enums.AmountType;
import kaiyi.app.xhapp.entity.log.enums.BorrowLend;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("amountFlowService")
public class AmountFlowServiceImpl extends InjectDao<AmountFlow> implements AmountFlowService {
    private static final long serialVersionUID = -739490503110820186L;


    @Override
    public void saveNote(Account account, AmountType amountType, String orderId,int beforeAmount, int amount,
                         int afterAmount,BorrowLend borrowLend) {
        AmountFlow flow=new AmountFlow(account,amountType,orderId,beforeAmount,amount,afterAmount,borrowLend);
        saveObject(flow);

    }
}
