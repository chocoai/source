/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.wechat.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * js_wechat_k3_mechanismEntity
 * @author jace
 * @version 2018-07-27
 */
@Table(name="${_prefix}wechat_k3_mechanism", alias="a", columns={
		@Column(name="id", attrName="id", label="系统自动编号", isPK=true),
		@Column(name="create_time", attrName="createTime", label="创建时间"),
		@Column(name="create_user", attrName="createUser", label="创建用户"),
		@Column(name="update_time", attrName="updateTime", label="更新时间"),
		@Column(name="update_user", attrName="updateUser", label="更新用户"),
		@Column(name="department_id", attrName="departmentId", label="所属机构", comment="所属机构(部门)"),
		@Column(name="relative_department_id", attrName="relativeDepartmentId", label="对应机构", comment="对应机构(部门)"),
		@Column(name="k3_department_id", attrName="k3DepartmentId", label="对应机构", comment="对应机构(部门)的K3平台ID"),
		@Column(name="k3_username", attrName="k3Username", label="K3帐号"),
		@Column(name="k3_password", attrName="k3Password", label="K3密码"),
		@Column(name="wechat_openid", attrName="wechatOpenid", label="微信openid"),
	}, orderBy="a.id DESC"
)
public class WechatK3Mechanism extends DataEntity<WechatK3Mechanism> {
	
	private static final long serialVersionUID = 1L;
	private String createTime;		// 创建时间
	private String createUser;		// 创建用户
	private String updateTime;		// 更新时间
	private String updateUser;		// 更新用户
	private String departmentId;		// 所属机构(部门)
	private String relativeDepartmentId;		// 对应机构(部门)
	private String k3DepartmentId;		// 对应机构(部门)的K3平台ID
	private String k3Username;		// K3帐号
	private String k3Password;		// K3密码
	private String wechatOpenid;		// 微信openid
	
	public WechatK3Mechanism() {
		this(null);
	}

	public WechatK3Mechanism(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="创建时间长度不能超过 255 个字符")
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=255, message="创建用户长度不能超过 255 个字符")
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	@Length(min=0, max=255, message="更新时间长度不能超过 255 个字符")
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=255, message="更新用户长度不能超过 255 个字符")
	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	@Length(min=0, max=255, message="所属机构长度不能超过 255 个字符")
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	@Length(min=0, max=255, message="对应机构长度不能超过 255 个字符")
	public String getRelativeDepartmentId() {
		return relativeDepartmentId;
	}

	public void setRelativeDepartmentId(String relativeDepartmentId) {
		this.relativeDepartmentId = relativeDepartmentId;
	}
	
	@Length(min=0, max=255, message="对应机构长度不能超过 255 个字符")
	public String getK3DepartmentId() {
		return k3DepartmentId;
	}

	public void setK3DepartmentId(String k3DepartmentId) {
		this.k3DepartmentId = k3DepartmentId;
	}
	
	@Length(min=0, max=255, message="K3帐号长度不能超过 255 个字符")
	public String getK3Username() {
		return k3Username;
	}

	public void setK3Username(String k3Username) {
		this.k3Username = k3Username;
	}
	
	@Length(min=0, max=255, message="K3密码长度不能超过 255 个字符")
	public String getK3Password() {
		return k3Password;
	}

	public void setK3Password(String k3Password) {
		this.k3Password = k3Password;
	}
	
	@Length(min=0, max=255, message="微信openid长度不能超过 255 个字符")
	public String getWechatOpenid() {
		return wechatOpenid;
	}

	public void setWechatOpenid(String wechatOpenid) {
		this.wechatOpenid = wechatOpenid;
	}
	
}