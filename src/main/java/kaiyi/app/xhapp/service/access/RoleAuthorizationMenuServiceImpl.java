package kaiyi.app.xhapp.service.access;

import kaiyi.app.xhapp.entity.access.RoleAuthorizationMenu;
import kaiyi.app.xhapp.entity.access.VisitorMenu;
import kaiyi.app.xhapp.entity.access.VisitorRole;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;
@Service("roleAuthorizationMenuService")
public class RoleAuthorizationMenuServiceImpl extends InjectDao<RoleAuthorizationMenu> implements RoleAuthorizationMenuService {
    private static final long serialVersionUID = -4323865808762118177L;

    @Override
    public void addAuthorToRole(VisitorRole role, VisitorMenu menu, boolean visit) {
        RoleAuthorizationMenu auth=new RoleAuthorizationMenu(role,menu,visit);
        saveObject(auth);
    }

    @Override
    public void modifyVisit(String id, boolean visit) {
        em.createQuery("update "+getEntityName(entityClass)+" o set o.visit=:visit")
                .setParameter("visit",visit).executeUpdate();
    }
    @Transactional(propagation= Propagation.REQUIRED)
    @Override
    public void updateRoleAuthroity(String roleid, List<VisitorMenu> menus, boolean checked) {
        VisitorRole visitorRole=new VisitorRole();
        visitorRole.setEntityId(roleid);
        if(menus!=null&&!menus.isEmpty()){
            Query query=em.createQuery("update "+getEntityName(entityClass)+" o set o.visit=:visit where o.visitorRole=:visitorRole and "
                    + "o.menu in(:menus)").setParameter("visit", checked).setParameter("visitorRole", visitorRole)
                    .setParameter("menus", menus);
            query.executeUpdate();
        }

    }
}
