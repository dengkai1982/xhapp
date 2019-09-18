package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.app.xhapp.entity.curriculum.FaceToFace;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.access.VisitorUserService;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service("faceToFaceService")
public class FaceToFaceServiceImpl extends InjectDao<FaceToFace> implements FaceToFaceService {
    @Resource
    private VisitorUserService visitorUserSerivce;
    @Resource
    private AccountService accountService;
    @Override
    public FaceToFace make(String accountId,String name, String phone, String course, Date faceTime) {
        Account account=accountService.findForPrimary(accountId);
        FaceToFace faceToFace=new FaceToFace(account,name,phone,course,faceTime);
        saveObject(faceToFace);
        return faceToFace;
    }
    @Override
    public QueryExpress getCustomerQuery(Map<String, JavaDataTyper> params) {
        QueryExpress query = super.getCustomerQuery(params);
        if(Objects.nonNull(params.get("onlyEntityId"))){
            query=new LinkQueryExpress(query, LinkQueryExpress.LINK.AND,
                    new CompareQueryExpress("entityId",CompareQueryExpress.Compare.EQUAL,
                            params.get("onlyEntityId").stringValue()));
        }
        return query;
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
