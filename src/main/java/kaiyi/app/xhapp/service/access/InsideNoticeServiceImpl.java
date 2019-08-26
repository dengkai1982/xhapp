package kaiyi.app.xhapp.service.access;
import kaiyi.app.xhapp.entity.access.InsideNotice;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.db.query.OrderBy;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Objects;

@Service("insideNoticeService")
public class InsideNoticeServiceImpl extends InjectDao<InsideNotice> implements InsideNoticeService {
    @Override
    public void sendMessage(String content,String actionUrl) {
        InsideNotice notice=new InsideNotice(content,actionUrl);
        notice.setPublishDate(new Date());
        saveObject(notice);
    }


    @Override
    public InsideNotice readMessage(String id) {
        InsideNotice insideNotice=findForPrimary(id);
        if(Objects.nonNull(insideNotice)){
            insideNotice.setReaded(true);
        }
        return insideNotice;
    }

    @Override
    public void deleteById(String id) {
        super.deleteForPrimary(id);
    }

    @Override
    public void deleteByUrl(String url) {
        em.createQuery("delete from "+getEntityName(entityClass)+" o where o.actionUrl=:actionUrl")
                .setParameter("actionUrl",url).executeUpdate();
    }

    @Override
    public OrderBy getDefaultOrderBy(String prefix) {
        OrderBy orderBy=new OrderBy(prefix,"readed",OrderBy.TYPE.DESC);
        orderBy.add(prefix,"publishDate",OrderBy.TYPE.DESC);
        return orderBy;
    }
}
