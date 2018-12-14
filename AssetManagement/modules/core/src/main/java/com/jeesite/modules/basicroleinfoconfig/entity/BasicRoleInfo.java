/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.basicroleinfoconfig.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 角色表Entity
 * @author dwh
 * @version 2018-07-26
 */
@Table(name="${_prefix}sys_role", alias="a", columns={
		@Column(name="role_code", attrName="roleCode", label="角色编码", isPK=true),
		@Column(name="role_name", attrName="roleName", label="角色名称", queryType=QueryType.LIKE),
		@Column(name="role_type", attrName="roleType", label="角色分类", comment="角色分类（高管、中层、基层、其它）"),
		@Column(name="role_sort", attrName="roleSort", label="角色排序", comment="角色排序（升序）"),
		@Column(name="is_sys", attrName="isSys", label="系统内置", comment="系统内置（1是 0否）"),
		@Column(name="user_type", attrName="userType", label="用户类型", comment="用户类型（employee员工 member会员）"),
		@Column(name="data_scope", attrName="dataScope", label="数据范围设置", comment="数据范围设置（0未设置  1全部数据 2自定义数据）"),
		@Column(includeEntity=DataEntity.class),
//		@Column(includeEntity=BaseEntity.class),
	}, orderBy="a.update_date DESC"
)
public class BasicRoleInfo extends DataEntity<BasicRoleInfo> {

	private static final long serialVersionUID = 1L;
	private String roleCode;		// 角色编码
	private String roleName;		// 角色名称
	private String roleType;		// 角色分类（高管、中层、基层、其它）
	private Integer roleSort;		// 角色排序（升序）
	private String isSys;		// 系统内置（1是 0否）
	private String userType;		// 用户类型（employee员工 member会员）
	private String dataScope;		// 数据范围设置（0未设置  1全部数据 2自定义数据）

	public BasicRoleInfo() {
		this(null);
	}

	public BasicRoleInfo(String id){
		super(id);
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@NotBlank(message="角色名称不能为空")
	@Length(min=0, max=100, message="角色名称长度不能超过 100 个字符")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Length(min=0, max=100, message="角色分类长度不能超过 100 个字符")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Integer getRoleSort() {
		return roleSort;
	}

	public void setRoleSort(Integer roleSort) {
		this.roleSort = roleSort;
	}

	@Length(min=0, max=1, message="系统内置长度不能超过 1 个字符")
	public String getIsSys() {
		return isSys;
	}

	public void setIsSys(String isSys) {
		this.isSys = isSys;
	}

	@Length(min=0, max=16, message="用户类型长度不能超过 16 个字符")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Length(min=0, max=1, message="数据范围设置长度不能超过 1 个字符")
	public String getDataScope() {
		return dataScope;
	}

	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}

}