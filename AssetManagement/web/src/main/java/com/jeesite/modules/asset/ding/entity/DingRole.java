/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ding.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 人员管理Entity
 * @author scarlett
 * @version 2018-09-19
 */
@Table(name="${_prefix}ding_role", alias="a", columns={
		/*@Column(name="role_id", attrName="roleId.userid", label="角色唯一标识ID", isPK=true),*/
		@Column(name="role_id", attrName="roleId", label="角色唯一标识ID", isPK=true),
		@Column(name="role_name", attrName="roleName", label="角色名称", queryType=QueryType.LIKE),
		@Column(name="group_name", attrName="groupName", label="角色分组名称", queryType=QueryType.LIKE),
		//@Column(name="group_name", attrName="groupName", label="角色分组名称", queryType=QueryType.LIKE),
	}, orderBy="a.role_id ASC"
)
public class DingRole extends DataEntity<DingRole> {
	
	private static final long serialVersionUID = 1L;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	private String roleId;		// 角色唯一标识ID 父类
	private String roleName;		// 角色名称
	private String groupName;		// 角色分组名称
	/*public DingRole(DingUser roleId){
		this.roleId = roleId;
	}*/
	/*public DingRole(String id){
		super(id);
	}
	
	public DingUser getRoleId() {
		return roleId;
	}*/
	public DingRole() {
		this(null);
	}

	public DingRole(String id){
		super(id);
	}

	/*public void setRoleId(DingUser roleId) {
		this.roleId = roleId;
	}*/
	
	@Length(min=0, max=225, message="角色名称长度不能超过 225 个字符")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Length(min=0, max=225, message="角色分组名称长度不能超过 225 个字符")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
}