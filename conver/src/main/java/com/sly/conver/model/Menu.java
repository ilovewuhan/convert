package com.sly.conver.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class Menu implements Serializable {

	private static final long serialVersionUID = -9167039805946284218L;

	/** `id` varchar(32) NOT NULL COMMENT '主键uuid', */
	private String id;
	/** `parentId` varchar(32) NOT NULL DEFAULT '' COMMENT '菜单父id', */
	private String parentId;
	/** `name` varchar(32) NOT NULL DEFAULT '' COMMENT '菜单名称', */
	private String name;
	/** `auth` text COMMENT '菜单权限', */
	private String auth;
	/** `type` varchar(2) NOT NULL DEFAULT '2' COMMENT '菜单类型：0.根节点 1.菜单 2.按钮', */
	private String type;
	/** `icon` varchar(64) NOT NULL DEFAULT '' COMMENT '图标类型', */
	private String icon;
	/** `status` varchar(2) NOT NULL DEFAULT '1' COMMENT '状态：0.删除 1.激活 2.禁用', */
	private String status;
	/** `extend` json DEFAULT NULL COMMENT '扩展json字段用来存储个性化数据', */
	private String extend;
	/** `systemId` varchar(32) NOT NULL COMMENT '所属系统id', */
	private String systemId;
	/** `sort` int(10) NOT NULL DEFAULT '0' COMMENT '排序', */
	private Integer sort;
	/** `createTime` datetime NOT NULL COMMENT '创建时间', */
	private Date createTime;
	/** `createUser` bigint(20) NOT NULL COMMENT '创建人id', */
	private BigDecimal createUser;
	/** `updateTime` datetime NOT NULL COMMENT '修改时间', */
	private Date updateTime;
	/** `updateUser` bigint(20) DEFAULT NULL COMMENT '修改人id', */
	private BigDecimal updateUser;
	/** `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注', */
	private String remark;

	private String oldId;
	private String oldParentId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getCreateUser() {
		return createUser;
	}

	public void setCreateUser(BigDecimal createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BigDecimal getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(BigDecimal updateUser) {
		this.updateUser = updateUser;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOldId() {
		return oldId;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

	public String getOldParentId() {
		return oldParentId;
	}

	public void setOldParentId(String oldParentId) {
		this.oldParentId = oldParentId;
	}

}
