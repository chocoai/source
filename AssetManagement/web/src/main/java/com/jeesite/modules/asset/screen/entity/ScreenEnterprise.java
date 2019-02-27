/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.screen.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 零售家企业Entity
 * @author len
 * @version 2019-01-08
 */
@Table(name="${_prefix}screen_enterprise", alias="a", columns={
		@Column(name="enterprise_code", attrName="enterpriseCode", label="企业编码", isPK=true),
		@Column(name="enterprise_name", attrName="enterpriseName", label="企业名称", queryType=QueryType.LIKE),
	}, orderBy="a.enterprise_code"
)
public class ScreenEnterprise extends DataEntity<ScreenEnterprise> {
	
	private static final long serialVersionUID = 1L;
	private String enterpriseCode;		// 企业编码
	private String enterpriseName;		// 企业名称
	
	public ScreenEnterprise() {
		this(null);
	}

	public ScreenEnterprise(String id){
		super(id);
	}
	
	public String getEnterpriseCode() {
		return enterpriseCode;
	}

	public void setEnterpriseCode(String enterpriseCode) {
		this.enterpriseCode = enterpriseCode;
	}
	
	@Length(min=0, max=128, message="企业名称长度不能超过 128 个字符")
	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	
}