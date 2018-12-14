/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userroleconfig.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 用户数据权限Entity
 * @author dwh
 * @version 2018-07-18
 */
@Table(name="${_prefix}sys_user_data_permission", alias="a", columns={
		@Column(name="permission_code", attrName="permissionCode", label="id", isPK=true),
		@Column(name="user_code", attrName="userCode", label="用户id"),
		@Column(name="interface_code", attrName="interfaceCode", label="接口id"),
		@Column(name="interface_sql", attrName="interfaceSql", label="sql"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="create_by_id", attrName="createById", label="创建人id"),
		@Column(name="update_by_id", attrName="updateById", label="修改人ID"),
		@Column(name="stitching", attrName="stitching", label="拼接条件"),

	}, orderBy="a.update_date DESC"
)
public class UserDataPermission extends DataEntity<UserDataPermission> {
	
	private static final long serialVersionUID = 1L;
	private String permissionCode;		// id
	private String userCode;		// 用户id
	private String interfaceCode;		// 接口id
	private String interfaceSql;		// sql
	private String createById;		// 创建人id
	private String updateById;		// 修改人ID
	private String stitching;    //拼接sql （1 and 2）

	public String getStitching() {
		return stitching;
	}

	public void setStitching(String stitching) {
		this.stitching = stitching;
	}

	public UserDataPermission() {
		this(null);
	}

	public UserDataPermission(String id){
		super(id);
	}

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    @NotBlank(message="用户id不能为空")
	@Length(min=0, max=32, message="用户id长度不能超过 32 个字符")
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	@NotBlank(message="接口id不能为空")
	@Length(min=0, max=32, message="接口id长度不能超过 32 个字符")
	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	@NotNull(message="sql不能为空")
	public String getInterfaceSql() {
		return interfaceSql;
	}

	public void setInterfaceSql(String interfaceSql) {
		this.interfaceSql = interfaceSql;
	}



	
	@Length(min=0, max=32, message="创建人id长度不能超过 32 个字符")
	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}
	
	@Length(min=0, max=50, message="修改人ID长度不能超过 50 个字符")
	public String getUpdateById() {
		return updateById;
	}

	public void setUpdateById(String updateById) {
		this.updateById = updateById;
	}
	
}