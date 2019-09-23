package kaiyi.app.xhapp.service.sys;

import kaiyi.app.xhapp.entity.sys.QNumberManager;
import kaiyi.app.xhapp.entity.sys.enums.CustomerType;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.ContainQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("qNumberManagerService")
public class QNumberManagerServiceImpl extends InjectDao<QNumberManager> implements QNumberManagerService {
    private static final long serialVersionUID = 7989477520462202071L;

    @Override
    public String getRandomQQNumber(CustomerType customerType) {
        StreamCollection<QNumberManager> qNumberManagers=getEntitys(new CompareQueryExpress("customerType",
                CompareQueryExpress.Compare.EQUAL,customerType));
        if(qNumberManagers.assertNotEmpty()){
            Random random=new Random();
            int index=random.nextInt(qNumberManagers.size());
            return qNumberManagers.get(index).getNumber();
        }else{
            qNumberManagers=getEntitys();
            return qNumberManagers.assertNotEmpty()?qNumberManagers.get(0).getNumber():"";
        }
    }
}
