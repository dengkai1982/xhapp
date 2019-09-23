package kaiyi.app.xhapp.service.sys;

import kaiyi.app.xhapp.entity.sys.QNumberManager;
import kaiyi.app.xhapp.entity.sys.enums.CustomerType;
import kaiyi.puer.db.orm.DatabaseFastOper;
import kaiyi.puer.db.orm.DatabaseOperator;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface QNumberManagerService extends DatabaseQuery<QNumberManager>, DatabaseFastOper<QNumberManager>,
        DatabaseOperator<QNumberManager> {
    /**
     * 随机获取QQ号码
     * @return
     */
    String getRandomQQNumber(CustomerType customerType);
}
