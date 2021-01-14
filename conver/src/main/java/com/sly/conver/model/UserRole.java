package com.sly.conver.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserRole implements Serializable {

	private static final long serialVersionUID = -9139366257266129896L;

	/** `id` bigint(20) NOT NULL COMMENT '主键自增', */
	private BigDecimal id;
	/** `userId` bigint(20) NOT NULL COMMENT '用户id', */
	private String userId;
	/** `roleId` varchar(32) NOT NULL COMMENT '角色id', */
	private String roleId;

	private String oldUserId;
	private String oldRoleId;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getOldUserId() {
		return oldUserId;
	}

	public void setOldUserId(String oldUserId) {
		this.oldUserId = oldUserId;
	}

	public String getOldRoleId() {
		return oldRoleId;
	}

	public void setOldRoleId(String oldRoleId) {
		this.oldRoleId = oldRoleId;
	}

}
