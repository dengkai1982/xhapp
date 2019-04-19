package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.ShopCar;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

public interface ShopCarService extends DatabaseQuery<ShopCar>{
    /**
     * 加入购物车
     * @param courseId
     * @param accountId
     */
    ShopCar joinToShopCar(String courseId,String accountId)throws ServiceException;
}
