/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.appreciation.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 赞赏类型Entity
 * @author dwh
 * @version 2018-09-19
 */
@Table(name="fz_appreciation_type", alias="a", columns={
		@Column(name="type_code", attrName="typeCode", label="类型编码", isPK=true),
		@Column(name="type_name", attrName="typeName", label="类型名字", queryType=QueryType.LIKE),
		@Column(name="worth_concept", attrName="worthConcept", label="价值观"),
		@Column(name="privilege_phone", attrName="privilegePhone", label="特权手机号", queryType=QueryType.LIKE),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.worth_concept DESC"
)
public class FzAppreciationType extends DataEntity<FzAppreciationType> {
	
	private static final long serialVersionUID = 1L;
	private String typeCode;		// 类型编码
	private String typeName;		// 类型名字
	private String worthConcept;		// 价值观
	private String privilegePhone;		// 特权手机号
	
	public FzAppreciationType() {
		this(null);
	}

	public FzAppreciationType(String id){
		super(id);
	}
	
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	@Length(min=0, max=255, message="类型名字长度不能超过 255 个字符")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	@Length(min=0, max=2, message="价值观长度不能超过 2 个字符")
	public String getWorthConcept() {
		return worthConcept;
	}

	public void setWorthConcept(String worthConcept) {
		this.worthConcept = worthConcept;
	}
	
	@Length(min=0, max=1000, message="特权手机号长度不能超过 1000 个字符")
	public String getPrivilegePhone() {
		return privilegePhone;
	}

	public void setPrivilegePhone(String privilegePhone) {
		this.privilegePhone = privilegePhone;
	}
	
}