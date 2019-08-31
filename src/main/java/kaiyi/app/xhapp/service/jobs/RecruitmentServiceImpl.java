package kaiyi.app.xhapp.service.jobs;

import kaiyi.app.xhapp.entity.jobs.Position;
import kaiyi.app.xhapp.entity.jobs.Recruitment;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.ContainQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service("recruitmentService")
public class RecruitmentServiceImpl extends InjectDao<Recruitment> implements RecruitmentService {
    private static final long serialVersionUID = -3756552231797515616L;

    @Resource
    private PositionService positionService;

    @Override
    protected void objectBeforePersistHandler(Recruitment recruitment, Map<String, JavaDataTyper> params) throws ServiceException {
        recruitment.setPublishTime(new Date());
    }

    @Override
    public QueryExpress getCustomerQuery(Map<String, JavaDataTyper> params) {
        QueryExpress query=super.getCustomerQuery(params);
        if(Objects.nonNull(params.get("parentPositionId"))){
            String parentPositionId=params.get("parentPositionId").stringValue();
            StreamCollection<Position> positions = positionService.getChild(parentPositionId);
            if(StreamCollection.assertNotEmpty(positions)){
                query=new LinkQueryExpress(query, LinkQueryExpress.LINK.AND,
                        new ContainQueryExpress("position",ContainQueryExpress.CONTAINER.IN,
                                positions.toList()));
            }
        }
        return query;
    }

    @Override
    public void changeRecommend(String entityId) {
        Recruitment recruitment=findForPrimary(entityId);
        if(Objects.nonNull(recruitment)){
            recruitment.setRecommend(!recruitment.isRecommend());
        }
    }

    @Override
    public void changeUpper(String entityId) {
        Recruitment recruitment=findForPrimary(entityId);
        if(Objects.nonNull(recruitment)){
            recruitment.setInfoUpper(!recruitment.isInfoUpper());
        }
    }
}
