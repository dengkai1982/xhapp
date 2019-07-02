package kaiyi.app.xhapp.service.pages;

import kaiyi.app.xhapp.entity.pages.ExamInfo;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.db.query.OrderBy;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("examInfoService")
public class ExamInfoServiceImpl extends InjectDao<ExamInfo> implements ExamInfoService {
    @Override
    public void deleteById(String entityId) {
        deleteForPrimary(entityId);
    }

    @Override
    public void readExamInfo(String entityId) {
        ExamInfo examInfo=findForPrimary(entityId);
        if(Objects.nonNull(examInfo)){
            examInfo.setReadVolume(examInfo.getReadVolume()+1);
        }
    }

    @Override
    public OrderBy getDefaultOrderBy(String prefix) {
        return new OrderBy(prefix,"publishDate", OrderBy.TYPE.DESC);
    }
}
