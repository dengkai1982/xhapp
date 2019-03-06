package kaiyi.app.xhapp.entity.access;

import kaiyi.app.xhapp.entity.AbstractEntity;
import kaiyi.puer.commons.collection.StreamArray;
import kaiyi.puer.h5ui.annotations.FieldType;
import kaiyi.puer.h5ui.annotations.PageEntity;
import kaiyi.puer.h5ui.annotations.PageField;

import javax.persistence.*;
import java.util.Set;
/**
 * 角色
 */
@Entity(name= VisitorRole.TABLE_NAME)
@PageEntity(showName="角色",entityName = "visitorRole",serviceName = "visitorRoleService")
public class VisitorRole extends AbstractEntity {
    private static final long serialVersionUID = 8060707675770443708L;
    public static final String TABLE_NAME="visitor_role";
    @PageField(label = "角色名称",orderBy = false)
    private String name;
    @PageField(label = "角色描述",type = FieldType.AREATEXT,orderBy = false,showSearch = false,formColumnLength = 3)
    private String descript;
    private Set<RoleAuthorizationMenu> authors;
    @Override
    public StreamArray<String> filterField() {
        return new StreamArray<>(new String[]{"authors"});
    }

    @Column(length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Lob
    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "visitorRole")
    public Set<RoleAuthorizationMenu> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<RoleAuthorizationMenu> authors) {
        this.authors = authors;
    }

}
