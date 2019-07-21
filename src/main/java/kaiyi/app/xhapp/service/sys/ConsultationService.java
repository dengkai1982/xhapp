package kaiyi.app.xhapp.service.sys;

import kaiyi.app.xhapp.entity.sys.Consultation;
import kaiyi.app.xhapp.service.curriculum.ReplyService;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;
import org.springframework.stereotype.Service;

@Service("consultationService")
public interface ConsultationService extends DatabaseQuery<Consultation>,ReplyService {
    /**
     * 提交留言咨询
     * @param accountId 账户ID
     * @param content 留言内容
     * @throws ServiceException
     */
    void commitConsultation(String accountId,String content)throws ServiceException;
}
