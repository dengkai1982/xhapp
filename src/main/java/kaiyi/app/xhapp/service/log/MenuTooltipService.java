package kaiyi.app.xhapp.service.log;

import kaiyi.app.xhapp.entity.log.MenuTooltip;
import kaiyi.puer.db.orm.DatabaseQuery;

public interface MenuTooltipService extends DatabaseQuery<MenuTooltip> {
    void addMenuNotice(String menuId);

    void clearMenuNotice(String menuId);
}
