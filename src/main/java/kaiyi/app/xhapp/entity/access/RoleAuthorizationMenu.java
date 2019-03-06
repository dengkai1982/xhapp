package kaiyi.app.xhapp.entity.access;

import kaiyi.puer.db.entity.AutoGenerateSequence;

import javax.persistence.*;

/**
 * 角色授权菜单
 *
 */
@Entity(name=RoleAuthorizationMenu.TABLE_NAME)
public class RoleAuthorizationMenu implements AutoGenerateSequence {
	public static final String TABLE_NAME="role_author_menu";
	private static final long serialVersionUID = -6707239566629336215L;
	private String entityId;
	/**
	 * 角色
	 */
	private VisitorRole visitorRole;
	/**
	 * 菜单
	 */
	private VisitorMenu menu;
	/**
	 * 是否可访问
	 */
	private boolean visit;
	
	@Id
	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
	@JoinColumn(name="visitorRole")
	public VisitorRole getVisitorRole() {
		return visitorRole;
	}

	public void setVisitorRole(VisitorRole visitorRole) {
		this.visitorRole = visitorRole;
	}
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.EAGER)
	@JoinColumn(name="menu")
	public VisitorMenu getMenu() {
		return menu;
	}

	public void setMenu(VisitorMenu menu) {
		this.menu = menu;
	}

	public boolean isVisit() {
		return visit;
	}

	public void setVisit(boolean visit) {
		this.visit = visit;
	}

	public RoleAuthorizationMenu() {
	}

	public RoleAuthorizationMenu(VisitorRole visitorRole, VisitorMenu menu, boolean visit) {
		this.visitorRole = visitorRole;
		this.menu = menu;
		this.visit = visit;
	}
}
