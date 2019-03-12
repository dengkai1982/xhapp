package kaiyi.app.xhapp.service.pub;

import kaiyi.app.xhapp.entity.pub.Configure;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.log.DiskLogger;
import kaiyi.puer.commons.log.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service("configureService")
public class ConfigureServiceImpl extends InjectDao<Configure> implements ConfigureService {
    private static final long serialVersionUID = 5609047214983353856L;

    private StreamCollection<Configure> configures;

    @PostConstruct
    public void postConstruct(){
        configures=getEntitys();
    }

    @Override
    public int getIntegerValue(ConfigureItem item) {
        Configure configure=configures.find(c->{
            return c.getItem().equals(item);
        });
        if(configure!=null){
            return Integer.valueOf(configure.getValue());
        }
        return 0;
    }

    @Override
    public String getStringValue(ConfigureItem item) {
        Configure configure=configures.find(c->{
            return c.getItem().equals(item);
        });
        if(configure!=null){
            return configure.getValue();
        }
        return "";
    }

    @Override
    public float getFloatValue(ConfigureItem item) {
        Configure configure=configures.find(c->{
            return c.getItem().equals(item);
        });
        if(configure!=null){
            return Float.valueOf(configure.getValue());
        }
        return 0;
    }

    @Override
    public void updateConfig(ConfigureItem item, String value) {
        em.createQuery("update "+getEntityName(entityClass)+" o set o.value=:value" +
                " where o.item=:item").setParameter("value",value).setParameter("item",item)
                .executeUpdate();
    }

    @Override
    public Logger getLogger(Class<?> clz) {
        return new DiskLogger(clz,getStringValue(ConfigureItem.LOG_DIR));
    }
}
