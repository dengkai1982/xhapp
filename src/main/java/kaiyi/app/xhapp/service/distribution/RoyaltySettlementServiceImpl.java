package kaiyi.app.xhapp.service.distribution;

import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.app.xhapp.entity.distribution.RoyaltySettlement;
import kaiyi.app.xhapp.entity.distribution.RoyaltyType;
import kaiyi.app.xhapp.entity.log.enums.TradeCourse;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.access.VisitorUserService;
import kaiyi.puer.commons.data.Currency;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.OrderBy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service("royaltySettlementService")
public class RoyaltySettlementServiceImpl extends InjectDao<RoyaltySettlement> implements RoyaltySettlementService {
    private static final long serialVersionUID = -3951765064985299874L;

    @Resource
    private AccountService accountService;
    @Resource
    private RoyaltyTypeService royaltyTypeService;
    @Resource
    private VisitorUserService visitorUserService;
    @Override
    protected void objectBeforePersistHandler(RoyaltySettlement royaltySettlement, Map<String, JavaDataTyper> params) throws ServiceException {
        royaltySettlement.setCreateTime(new Date());
        royaltySettlement.setGrant(false);
        royaltySettlement.setOrderId(randomIdentifier());
        RoyaltySettlement computer=generatorRoyaltySettlement(royaltySettlement.getAccount().getEntityId(),
                royaltySettlement.getRoyaltyType().getEntityId());
        royaltySettlement.setRecommend1(computer.getRecommend1());
        royaltySettlement.setRecommend2(computer.getRecommend2());
        royaltySettlement.setLevel1Amount(computer.getLevel1Amount());
        royaltySettlement.setLevel2Amount(computer.getLevel2Amount());
    }

    @Override
    public void grant(String entityId, String granterId) {
        RoyaltySettlement royaltySettlement=findForPrimary(entityId);
        VisitorUser granter=visitorUserService.findForPrimary(granterId);
        if(Objects.nonNull(royaltySettlement)&&Objects.nonNull(granter)&&!royaltySettlement.isGrant()){
            royaltySettlement.setGrant(true);
            royaltySettlement.setGranter(granter);
            royaltySettlement.setGrantTime(new Date());
            Account recommend=royaltySettlement.getRecommend1();
            String orderId=royaltySettlement.getOrderId();
            if(Objects.nonNull(recommend)){
                accountService.grantRoyalty(recommend.getEntityId(),orderId,TradeCourse.MANUAL_CACULATION,royaltySettlement.getLevel1Amount());
            }
            recommend=royaltySettlement.getRecommend2();
            if(Objects.nonNull(recommend)){
                accountService.grantRoyalty(recommend.getEntityId(),orderId,TradeCourse.MANUAL_CACULATION,royaltySettlement.getLevel2Amount());
            }
            updateObject(royaltySettlement);
        }
    }

    @Override
    public RoyaltySettlement generatorRoyaltySettlement(String accountId, String royaltyTypeId) {
        Account account=accountService.findForPrimary(accountId);
        RoyaltyType royaltyType=royaltyTypeService.findForPrimary(royaltyTypeId);
        RoyaltySettlement rs=new RoyaltySettlement();
        Currency price=Currency.noDecimalBuild(royaltyType.getPrice(),2);
        Currency royalty;
        if(Objects.nonNull(account)){
            Account recommend=account.getRecommend();
            if(Objects.nonNull(recommend)){
                rs.setRecommend1(recommend);
                royalty=Currency.computerPercentage(royaltyType.getFirstRate(),price.doubleValue());
                rs.setLevel1Amount(royalty.getNoDecimalPointToInteger());
                recommend=recommend.getRecommend();
                if(Objects.nonNull(recommend)){
                    royalty=Currency.computerPercentage(royaltyType.getSecondRate(),price.doubleValue());
                    rs.setLevel2Amount(royalty.getNoDecimalPointToInteger());
                    rs.setRecommend2(recommend);
                }
            }
        }
        return rs;
    }

    @Override
    public void deleteById(String entityId) {
        RoyaltySettlement rs=findForPrimary(entityId);
        if(Objects.nonNull(rs)&&!rs.isGrant()){
            deleteForPrimary(entityId);
        }
    }

    @Override
    public OrderBy getDefaultOrderBy(String prefix) {
        return new OrderBy(prefix,"createTime",OrderBy.TYPE.DESC);
    }
}
