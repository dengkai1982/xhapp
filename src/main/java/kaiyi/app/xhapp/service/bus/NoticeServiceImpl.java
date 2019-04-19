package kaiyi.app.xhapp.service.bus;
import kaiyi.app.xhapp.entity.pub.Notice;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("noticeService")
public class NoticeServiceImpl extends InjectDao<Notice> implements NoticeService {

    @Override
    public void deleteById(String entityId) {
        deleteForPrimary(entityId);
    }
}
