package kaiyi.app.xhapp.service.access;

import kaiyi.app.xhapp.entity.access.VisitorMenu;
import kaiyi.app.xhapp.entity.access.VisitorRole;
import kaiyi.puer.commons.access.Access;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.web.service.AccessCompile;
import kaiyi.puer.web.springmvc.SpringContextHolder;

/**
 * 实现菜单插入
 */
public class MenuAccessComplie implements AccessCompile {

    private SpringContextHolder springContextHolder;

    public void setSpringContextHolder(SpringContextHolder springContextHolder) {
        this.springContextHolder = springContextHolder;
    }

    @Override
    public void complie(StreamCollection<Access> accesses) {
        VisitorMenuService visitorMenuService=springContextHolder.getBean(VisitorMenuService.class);
        visitorMenuService.clearMenus();
        for (Access access : accesses) {
            try {
                visitorMenuService.newMenu(access.getCode(),access.getName(),access.getDetail(),
                        access.getAction(),access.getCode(),access.getWeight(),access.getParent(),
                        access.isDefaultAuthor(),true);
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        VisitorMenuService menuService=springContextHolder.getBean(VisitorMenuService.class);
        RoleAuthorizationMenuService roleAuthorizationMenuService=springContextHolder
                .getBean(RoleAuthorizationMenuService.class);
        VisitorRoleService visitorRoleService=springContextHolder.getBean(VisitorRoleService.class);
        StreamCollection<VisitorMenu> menus=menuService.getEntitys();
        StreamCollection<VisitorRole> roles=visitorRoleService.getEntitys();
        roles.forEach(role->{
            menus.forEach(menu->{
                roleAuthorizationMenuService.addAuthorToRole(role,menu,role.getName().equals("administrators"));
            });
        });
    }
}
