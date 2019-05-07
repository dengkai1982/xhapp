package kaiyi.app.xhapp.service.pages;

import kaiyi.app.xhapp.entity.pages.ExamInfo;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

@Service("examInfoService")
public class ExamInfoServiceImpl extends InjectDao<ExamInfo> implements ExamInfoService {
    @Override
    public void deleteById(String entityId) {
        deleteForPrimary(entityId);
    }
}
