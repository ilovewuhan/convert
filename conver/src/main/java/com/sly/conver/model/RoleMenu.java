package com.sly.conver.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class RoleMenu implements Serializable {

	private static final long serialVersionUID = 3933115029223578291L;

	/** `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增', */
	private BigDecimal id;
	/** `roleId` varchar(32) NOT NULL COMMENT '角色id', */
	private String roleId;
	/** `menuId` varchar(32) NOT NULL COMMENT '菜单id', */
	private String menuId;

	private String oldRoleId;
	private String oldMenuId;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getOldRoleId() {
		return oldRoleId;
	}

	public void setOldRoleId(String oldRoleId) {
		this.oldRoleId = oldRoleId;
	}

	public String getOldMenuId() {
		return oldMenuId;
	}

	public void setOldMenuId(String oldMenuId) {
		this.oldMenuId = oldMenuId;
	}

}
