package kaiyi.app.xhapp.service.sys;

import kaiyi.app.xhapp.ServiceExceptionDefine;
import kaiyi.app.xhapp.entity.access.Account;
import kaiyi.app.xhapp.entity.access.VisitorUser;
import kaiyi.app.xhapp.entity.sys.Consultation;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.app.xhapp.service.access.AccountService;
import kaiyi.app.xhapp.service.access.VisitorUserService;
import kaiyi.puer.db.orm.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Service("consultationService")
public class ConsultationServiceImpl extends InjectDao<Consultation> implements ConsultationService {
    private static final long serialVersionUID = 1349080246728663134L;
    @Resource
    private VisitorUserService visitorUserService;
    @Resource
    private AccountService accountService;

    @Override
    public void reply(String entityId, String replierId, String replyContent) throws ServiceException {
        Consultation consultation=findForPrimary(entityId);
        VisitorUser replier=visitorUserService.findForPrimary(replierId);
        if(Objects.isNull(replier)||Objects.isNull(replier)){
            throw ServiceExceptionDefine.entityNotExist;
        }
        consultation.setReplier(replier);
        consultation.setAnswer(true);
        consultation.setReplyTime(new Date());
        consultation.setReply(replyContent);
        updateObject(consultation);
    }

    @Override
    public void commitConsultation(String commentatorId, String content) throws ServiceException {
        Account commentator=accountService.findForPrimary(commentatorId);
        if(Objects.isNull(commentator)){
            throw ServiceExceptionDefine.entityNotExist;
        }
        Consultation consultation=new Consultation();
        consultation.setCommentator(commentator);
        consultation.setContent(content);
        consultation.setCommentTime(new Date());
        saveObject(consultation);
    }
}
