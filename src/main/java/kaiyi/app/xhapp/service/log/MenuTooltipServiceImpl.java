package kaiyi.app.xhapp.service.log;

import kaiyi.app.xhapp.entity.log.MenuTooltip;
import kaiyi.app.xhapp.service.InjectDao;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("menuTooltipService")
public class MenuTooltipServiceImpl extends InjectDao<MenuTooltip> implements MenuTooltipService {
    private static final long serialVersionUID = -2442504133230299254L;

    @Override
    public void addMenuNotice(String menuId) {
        MenuTooltip menuTooltip=signleQuery("menuId",menuId);
        if(Objects.nonNull(menuTooltip)){
            menuTooltip.setNumber(menuTooltip.getNumber()+1);
            updateObject(menuTooltip);
        }else{
            menuTooltip=new MenuTooltip();
            menuTooltip.setMenuId(menuId);
            menuTooltip.setNumber(1);
            saveObject(menuTooltip);
        }
    }

    @Override
    public void clearMenuNotice(String menuId) {
        em.createQuery("update "+getEntityName(entityClass)+" o set o.number=0 where o.menuId=:menuId")
                .setParameter("menuId",menuId).executeUpdate();
    }
}
