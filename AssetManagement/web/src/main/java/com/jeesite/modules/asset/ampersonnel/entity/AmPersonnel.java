/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ampersonnel.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 有效人员维护表Entity
 * @author mclaran
 * @version 2018-06-26
 */
@Table(name="${_prefix}am_personnel", alias="a", columns={
		@Column(name="personnel_code", attrName="personnelCode", label="code", isPK=true),
		@Column(name="phone", attrName="phone", label="联系电话"),
		@Column(name="personnel", attrName="personnel", label="有效人员"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class AmPersonnel extends DataEntity<AmPersonnel> {
	
	private static final long serialVersionUID = 1L;
	private String personnelCode;		// code
	private String phone;		// 联系电话
	private String personnel;		// 有效人员
	
	public AmPersonnel() {
		this(null);
	}

	public AmPersonnel(String id){
		super(id);
	}

	public String getPersonnelCode() {
		return personnelCode;
	}

	public void setPersonnelCode(String personnelCode) {
		this.personnelCode = personnelCode;
	}

	@Length(min=0, max=32, message="联系电话长度不能超过 32 个字符")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=32, message="有效人员长度不能超过 32 个字符")
	public String getPersonnel() {
		return personnel;
	}

	public void setPersonnel(String personnel) {
		this.personnel = personnel;
	}
	
}