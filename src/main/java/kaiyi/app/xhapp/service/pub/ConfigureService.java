package kaiyi.app.xhapp.service.pub;

import kaiyi.app.xhapp.entity.pub.Configure;
import kaiyi.app.xhapp.entity.pub.enums.ConfigureItem;
import kaiyi.puer.commons.log.Logger;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface ConfigureService extends DatabaseQuery<Configure> {
    int getIntegerValue(ConfigureItem item);

    String getStringValue(ConfigureItem item);

    float getFloatValue(ConfigureItem item);

    void updateConfig(ConfigureItem item, String value);

    Logger getLogger(Class<?> clz);
}
