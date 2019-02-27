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
 * js_ding_user_departmentEntity
 * @author scarlett
 * @version 2018-09-22
 */
@Table(name="${_prefix}ding_user_department", alias="a", columns={
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="department_id", attrName="departmentId", label="部门id"),
		//@Column(name="id", attrName="id", label="id", isPK=true),
	}, orderBy="a.department_id DESC"
)
public class DingUserDepartment extends DataEntity<DingUserDepartment> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String departmentId;		// 部门id
	
	public DingUserDepartment() {
		this(null);
	}

	public DingUserDepartment(String userId, String departmentId) {
		this(null);
		this.userId = userId;
		this.departmentId = departmentId;
	}

	public DingUserDepartment(String id){
		super(id);
	}
	
	@Length(min=0, max=225, message="用户id长度不能超过 225 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=225, message="部门id长度不能超过 225 个字符")
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
}