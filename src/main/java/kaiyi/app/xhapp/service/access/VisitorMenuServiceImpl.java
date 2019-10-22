package kaiyi.app.xhapp.service.access;
import kaiyi.app.xhapp.entity.access.RoleAuthorizationMenu;
import kaiyi.app.xhapp.entity.access.VisitorMenu;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.commons.access.AccessControl;
import kaiyi.puer.commons.collection.Cascadeable;
import kaiyi.puer.commons.collection.StreamCollection;
import kaiyi.puer.commons.data.StringEditor;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.LinkQueryExpress;
import kaiyi.puer.db.query.OrderBy;
import kaiyi.puer.db.query.QueryExpress;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("visitorMenuService")
public class VisitorMenuServiceImpl extends InjectDao<VisitorMenu> implements VisitorMenuService {

    private static final long serialVersionUID = -5924317086688496778L;

    @Override
    public VisitorMenu newMenu(String code, String name, String detail, AccessControl.Action action,
                        String actionFlag, float weight, String parentCode,boolean defaultAuthors,boolean showable){
        VisitorMenu parent=null;
        if(StringEditor.notEmpty(parentCode)){
            parent=findForPrimary(parentCode);
        }
        VisitorMenu menu=new VisitorMenu(name,detail,action,actionFlag,weight, Cascadeable.START_LEVEL);
        menu.setEntityId(code);
        menu.setDefaultAuthor(defaultAuthors);
        if(parent!=null){
            menu.setLevel(parent.getLevel()+1);
            menu.setParent(parent);
        }else{
            menu.setActionFlag(null);
        }
        menu.setShowable(showable);
        saveObject(menu);
        return menu;
    }

    @Override
    public void clearMenus() {
        int result=em.createQuery("update "+getEntityName(entityClass)+" o set o.parent=:parent")
                .setParameter("parent",null).executeUpdate();
        assert result>=0;
        em.createQuery("delete from "+getEntityName(RoleAuthorizationMenu.class)).executeUpdate();
        assert result>=0;
        em.createQuery("delete from "+getEntityName(entityClass)).executeUpdate();
    }

    @Override
    public StreamCollection<VisitorMenu> getRootMenus() {
        QueryExpress query=new CompareQueryExpress("showable",CompareQueryExpress.Compare.EQUAL,Boolean.TRUE);
        OrderBy orderby=new OrderBy(query.getPrefix(),"level",OrderBy.TYPE.ASC);
        orderby.add(query.getPrefix(),"weight",OrderBy.TYPE.DESC);
        StreamCollection<VisitorMenu> menuStrem=getEntitys(query,orderby);
        List<VisitorMenu> result=new ArrayList<>();
        Cascadeable.cascade(menuStrem.toList(),result);
        Collections.sort(result);
        return new StreamCollection<>(result);
    }

    @Override
    public StreamCollection<VisitorMenu> getRootMenusFilerDefault() {
        OrderBy orderby=new OrderBy("o","level",OrderBy.TYPE.ASC);
        orderby.add("o","weight",OrderBy.TYPE.DESC);
        QueryExpress query=new CompareQueryExpress("defaultAuthor",CompareQueryExpress.Compare.EQUAL,Boolean.FALSE);
        query=new LinkQueryExpress(query,LinkQueryExpress.LINK.OR,new CompareQueryExpress("level",
                CompareQueryExpress.Compare.EQUAL,Cascadeable.START_LEVEL));
        StreamCollection<VisitorMenu> menuStrem=getEntitys(query,orderby);
        List<VisitorMenu> result=new ArrayList<>();
        Cascadeable.cascade(menuStrem.toList(),result);
        Collections.sort(result);
        return new StreamCollection<>(result);
    }

    @Override
    public void deleteByMenu(String menuId) {
        em.createQuery("delete from "+getEntityName(VisitorMenu.class)+" o where o.id=:id")
                .setParameter("id",menuId).executeUpdate();
    }

}
