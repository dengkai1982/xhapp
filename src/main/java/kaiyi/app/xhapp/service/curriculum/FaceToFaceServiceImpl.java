package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.app.xhapp.entity.curriculum.CourseProblem;
import kaiyi.app.xhapp.entity.curriculum.FaceToFace;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.access.VisitorUserService;
import kaiyi.puer.db.orm.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Service("faceToFaceService")
public class FaceToFaceServiceImpl extends InjectDao<FaceToFace> implements FaceToFaceService {
    @Resource
    private VisitorUserService visitorUserSerivce;
    @Override
    public void make(String name, String phone, String course, Date faceTime) {
        FaceToFace faceToFace=new FaceToFace(name,phone,course,faceTime);
        saveObject(faceToFace);
    }

    @Override
    public void reply(String entityId, String replierId, String replyContent) throws ServiceException {
        FaceToFace problem=findForPrimary(entityId);
        VisitorUser replier=visitorUserSerivce.findForPrimary(replierId);
        if(Objects.isNull(problem)){
            throw ServiceExceptionDefine.entityNotExist;
        }
        problem.setReplier(replier);
        problem.setReplyTime(new Date());
        problem.setReply(replyContent);
        problem.setAnswer(true);
    }
}
