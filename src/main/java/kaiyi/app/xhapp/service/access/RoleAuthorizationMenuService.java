package kaiyi.app.xhapp.service.access;

import kaiyi.app.xhapp.entity.access.RoleAuthorizationMenu;
import kaiyi.app.xhapp.entity.access.VisitorMenu;
import kaiyi.app.xhapp.entity.access.VisitorRole;
import kaiyi.puer.db.orm.DatabaseQuery;

import java.util.List;

public interface RoleAuthorizationMenuService extends DatabaseQuery<RoleAuthorizationMenu> {
    /**
     * 为角色添加权限
     * @param role
     * @param menu
     * @param visit
     */
   void addAuthorToRole(VisitorRole role, VisitorMenu menu, boolean visit);
    /**
     * 修改访问权限
     * @param id
     * @param visit
     */
   void modifyVisit(String id, boolean visit);

    public void updateRoleAuthroity(String roleid, List<VisitorMenu> menus, boolean checked);
}
