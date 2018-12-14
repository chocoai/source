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
 * js_wechat_k3_sys_userEntity
 * @author jace
 * @version 2018-07-27
 */
@Table(name="${_prefix}wechat_k3_sys_user", alias="a", columns={
		@Column(name="id", attrName="id", label="系统自动编号", isPK=true),
		@Column(name="create_time", attrName="createTime", label="创建日期"),
		@Column(name="create_user", attrName="createUser", label="创建人"),
		@Column(name="update_time", attrName="updateTime", label="更新日期"),
		@Column(name="update_user", attrName="updateUser", label="更新人"),
		@Column(name="department_id", attrName="departmentId", label="信息管理平台中的部门编号"),
		@Column(name="user_code", attrName="userCode", label="信息管理平台中的用户编号"),
		@Column(name="k3_department_id", attrName="k3DepartmentId", label="本部门的K3平台ID"),
		@Column(name="k3_username", attrName="k3Username", label="K3帐号名"),
		@Column(name="k3_password", attrName="k3Password", label="K3密码"),
		@Column(name="wechat_openid", attrName="wechatOpenid", label="微信openId"),
	}, orderBy="a.id DESC"
)
public class WechatK3SysUser extends DataEntity<WechatK3SysUser> {
	
	private static final long serialVersionUID = 1L;
	private String createTime;		// 创建日期
	private String createUser;		// 创建人
	private String updateTime;		// 更新日期
	private String updateUser;		// 更新人
	private String departmentId;		// 信息管理平台中的部门编号
	private String userCode;		// 信息管理平台中的用户编号
	private String k3DepartmentId;		// 本部门的K3平台ID
	private String k3Username;		// K3帐号名
	private String k3Password;		// K3密码
	private String wechatOpenid;		// 微信openId
	
	public WechatK3SysUser() {
		this(null);
	}

	public WechatK3SysUser(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="创建日期长度不能超过 255 个字符")
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=255, message="创建人长度不能超过 255 个字符")
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	@Length(min=0, max=255, message="更新日期长度不能超过 255 个字符")
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=255, message="更新人长度不能超过 255 个字符")
	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	@Length(min=0, max=255, message="信息管理平台中的部门编号长度不能超过 255 个字符")
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	@Length(min=0, max=255, message="信息管理平台中的用户编号长度不能超过 255 个字符")
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	@Length(min=0, max=255, message="本部门的K3平台ID长度不能超过 255 个字符")
	public String getK3DepartmentId() {
		return k3DepartmentId;
	}

	public void setK3DepartmentId(String k3DepartmentId) {
		this.k3DepartmentId = k3DepartmentId;
	}
	
	@Length(min=0, max=255, message="K3帐号名长度不能超过 255 个字符")
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
	
	@Length(min=0, max=255, message="微信openId长度不能超过 255 个字符")
	public String getWechatOpenid() {
		return wechatOpenid;
	}

	public void setWechatOpenid(String wechatOpenid) {
		this.wechatOpenid = wechatOpenid;
	}
	
}