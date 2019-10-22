package kaiyi.app.xhapp.service.access;

import kaiyi.app.xhapp.entity.access.RoleAuthorizationMenu;
import kaiyi.app.xhapp.entity.access.VisitorMenu;
import kaiyi.app.xhapp.entity.access.VisitorRole;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.db.orm.ORMException;
import kaiyi.puer.db.orm.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
@Transactional(propagation= Propagation.REQUIRED)
@Service("visitorRoleService")
public class VisitorRoleServiceImpl extends InjectDao<VisitorRole> implements VisitorRoleService {
    private static final long serialVersionUID = -7771634381961142727L;
    @Resource
    private VisitorMenuService visitorMenuService;

    @Override
    protected void objectBeforePersistHandler(VisitorRole visitorRole,Map<String, JavaDataTyper> params)throws ServiceException {
        Set<RoleAuthorizationMenu> authors = new HashSet<>();
        StreamCollection<VisitorMenu> menus=visitorMenuService.getEntitys();
        for(VisitorMenu menu:menus){
            RoleAuthorizationMenu author=new RoleAuthorizationMenu();
            author.setVisit(false);
            author.setVisitorRole(visitorRole);
            author.setMenu(menu);
            author.setEntityId(randomIdentifier());
            authors.add(author);
        }
        visitorRole.setAuthors(authors);
    }

    @Override
    public void deleteById(String entityId)throws ServiceException {
        try{
            deleteForPrimary(entityId);
        }catch(ORMException e){
            throw new ServiceException(ServiceException.CODE_FAIL,e.getMessage());
        }
    }
}

