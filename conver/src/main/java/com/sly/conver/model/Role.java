package com.sly.conver.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class Role implements Serializable {

	private static final long serialVersionUID = 8056603615306314175L;

	/** `id` varchar(32) NOT NULL COMMENT '主键uuid', */
	private String id;
	/** `name` varchar(32) NOT NULL DEFAULT '' COMMENT '角色名称', */
	private String name;
	/** `status` varchar(2) NOT NULL DEFAULT '1' COMMENT '状态：0.删除 1.激活 2.禁用', */
	private String status;
	/** `systemId` varchar(32) NOT NULL COMMENT '系统id', */
	private String systemId;
	/** `createTime` datetime NOT NULL COMMENT '创建时间', */
	private Date createTime;
	/** `extend` json DEFAULT NULL COMMENT '扩展json字段用来存储个性化数据', */
	private String extend;
	/** `createUser` bigint(20) NOT NULL COMMENT '创建人id', */
	private BigDecimal createUser;
	/** `updateTime` datetime NOT NULL COMMENT '修改时间', */
	private Date updateTime;
	/** `updateUser` bigint(20) NOT NULL COMMENT '修改人id', */
	private BigDecimal updateUser;
	/** `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注', */
	private String remark;

	private String oldId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
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

}
