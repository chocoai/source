/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.screen.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;

/**
 * 屏幕配置Entity
 * @author len
 * @version 2018-12-21
 */
@Table(name="${_prefix}screen_enterprise_detail", alias="a", columns={
		@Column(name="detail_code", attrName="detailCode", label="明细编码", isPK=true),
		@Column(name="configure_code", attrName="configureCode.configureCode", label="配置项编码"),
		@Column(name="enterprise_code", attrName="enterpriseCode", label="企业编码"),
		@Column(name="enterprise_name", attrName="enterpriseName", label="企业名称"),
	}, orderBy="a.detail_code ASC"
)
public class ScreenEnterpriseDetail extends DataEntity<ScreenEnterpriseDetail> {

	private static final long serialVersionUID = 1L;
	private String detailCode;		// 明细编码
	private ScreenConfig configureCode;		// 配置项编码 父类
	private String enterpriseCode;
	private String enterpriseName;

	public ScreenEnterpriseDetail() {
		this(null);
	}


	public ScreenEnterpriseDetail(ScreenConfig configureCode){
		this.configureCode = configureCode;
	}
	
	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	
	public ScreenConfig getConfigureCode() {
		return configureCode;
	}

	public void setConfigureCode(ScreenConfig configureCode) {
		this.configureCode = configureCode;
	}

	public String getEnterpriseCode() {
		return enterpriseCode;
	}

	public void setEnterpriseCode(String enterpriseCode) {
		this.enterpriseCode = enterpriseCode;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
}