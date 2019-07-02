package kaiyi.app.xhapp.service.pub;
import kaiyi.app.xhapp.entity.pub.Notice;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.db.query.OrderBy;
import org.springframework.stereotype.Service;

@Service("noticeService")
public class NoticeServiceImpl extends InjectDao<Notice> implements NoticeService {

    @Override
    public void deleteById(String entityId) {
        deleteForPrimary(entityId);
    }

    @Override
    public OrderBy getDefaultOrderBy(String prefix) {
        return new OrderBy(prefix,"publishDate", OrderBy.TYPE.DESC);
    }
}
