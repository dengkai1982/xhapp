package kaiyi.app.xhapp.service;

import kaiyi.puer.db.Pagination;
import kaiyi.puer.json.creator.JsonBuilder;
import kaiyi.puer.json.creator.ObjectJsonCreator;

/**
 * 自定义json数据返回
 */
public interface CustomerPaginationJson {
    /**
     * 自定义json返回
     * @param pagination
     * @return
     */
    ObjectJsonCreator<Pagination> getCustomerCreator(Pagination<? extends JsonBuilder> pagination);

}
