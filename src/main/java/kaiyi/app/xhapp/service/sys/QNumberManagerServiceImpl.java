package kaiyi.app.xhapp.service.sys;

import kaiyi.app.xhapp.entity.sys.QNumberManager;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.db.query.ContainQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service("qNumberManagerService")
public class QNumberManagerServiceImpl extends InjectDao<QNumberManager> implements QNumberManagerService {
    private static final long serialVersionUID = 7989477520462202071L;

    private static final List<String> qNumberManagerList;
    static{
        qNumberManagerList=new ArrayList<>();
    }

    @Override
    public String getRandomQQNumber() {
        int count=count();
        StreamCollection<QNumberManager> qNumberManagers=null;
        if(qNumberManagerList.size()==count){
            qNumberManagerList.clear();
            qNumberManagers= getEntitys();
        }else{
            if(qNumberManagerList.isEmpty()){
                qNumberManagers=getEntitys();
            }else{
                QueryExpress query=new ContainQueryExpress<>("number",ContainQueryExpress.CONTAINER.NOT_IN,
                        qNumberManagerList);
                qNumberManagers=getEntitys(query);
            }
        }
        Random random=new Random();
        int location=random.nextInt(qNumberManagers.size());
        String number=qNumberManagers.get(location).getNumber();
        qNumberManagerList.add(number);
        return number;
    }
}
