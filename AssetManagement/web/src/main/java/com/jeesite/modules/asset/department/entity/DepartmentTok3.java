/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.department.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.modules.asset.product.entity.ProductCategory;
import com.jeesite.modules.asset.product.entity.ProductSeries;
import com.jeesite.modules.sys.entity.Office;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 部门关联K3关系表Entity
 * @author Scarlett
 * @version 2018-08-04
 */
@Table(name="${_prefix}department_tok3", alias="a", columns={
		@Column(name="department_code", attrName="departmentCode", label="编号", isPK=true,queryType=QueryType.LIKE),
		@Column(includeEntity=DataEntity.class),
		@Column(name="department", attrName="department", label="所属部门"),
		@Column(name="k3_accountid", attrName="k3Accountid", label="对应部门的K3平台ID",queryType=QueryType.LIKE),
		@Column(name="k3_account", attrName="k3Account", label="K3帐号",queryType=QueryType.LIKE),
		@Column(name="k3_password", attrName="k3Password", label="K3密码"),
	},
		joinTable={
		/*@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=ProductCategory.class, alias="b",
				on="b.procategory_code=a.procategory_code ",
				columns={@Column(includeEntity=ProductCategory.class)}),*/
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=Office.class, alias="c",
				on="c.office_code=a.department",
				columns={@Column(includeEntity=Office.class)})
},orderBy="a.update_date DESC"
)
public class DepartmentTok3 extends DataEntity<DepartmentTok3> {
	
	private static final long serialVersionUID = 1L;
	private String departmentCode;		// 编号
	private String department;		// 所属部门
	private String k3Accountid;		// 对应部门的K3平台ID
	private String k3Account;		// K3帐号
	private String k3Password;		// K3密码
	private Office office;

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public DepartmentTok3() {
		this(null);
	}

	public DepartmentTok3(String id){
		super(id);
	}
	
	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	
	//@NotBlank(message="所属部门不能为空")
	@Length(min=0, max=64, message="所属部门长度不能超过 64 个字符")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	//@NotBlank(message="对应部门的K3平台ID不能为空")
	@Length(min=0, max=64, message="对应部门的K3平台ID长度不能超过 64 个字符")
	public String getK3Accountid() {
		return k3Accountid;
	}

	public void setK3Accountid(String k3Accountid) {
		this.k3Accountid = k3Accountid;
	}
	
	//@NotBlank(message="K3帐号不能为空")
	@Length(min=0, max=64, message="K3帐号长度不能超过 64 个字符")
	public String getK3Account() {
		return k3Account;
	}

	public void setK3Account(String k3Account) {
		this.k3Account = k3Account;
	}
	
	//@NotBlank(message="K3密码不能为空")
	@Length(min=0, max=64, message="K3密码长度不能超过 64 个字符")
	public String getK3Password() {
		return k3Password;
	}

	public void setK3Password(String k3Password) {
		this.k3Password = k3Password;
	}
	
}