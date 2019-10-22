package kaiyi.app.xhapp.service.access;
import kaiyi.app.xhapp.entity.access.VisitorMenu;
import kaiyi.puer.commons.access.AccessControl;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.db.orm.DatabaseQuery;
import kaiyi.puer.db.orm.ServiceException;

public interface VisitorMenuService extends DatabaseQuery<VisitorMenu> {
    /**
     * 新增菜单
     * @param code 菜单编码，作为主键使用
     * @param name 菜单名称
     * @param detail 菜单明细
     * @param action 菜单动作
     * @param actionFlag 菜单动作Action
     * @param weight 排序权重
     * @param parentCode 父级菜单编码
     */
    VisitorMenu newMenu(String code, String name, String detail, AccessControl.Action action,
                        String actionFlag, float weight, String parentCode, boolean defaultAuthors, boolean showable)throws ServiceException;
    /**
     * 清理所有菜单项
     */
    void clearMenus();
    /**
     * 获取根菜单
     * @return
     */
    StreamCollection<VisitorMenu> getRootMenus();
    /**
     * 获取菜单,清除默认项
     * @return
     */
    StreamCollection<VisitorMenu> getRootMenusFilerDefault();

    void deleteByMenu(String menuId);

}
