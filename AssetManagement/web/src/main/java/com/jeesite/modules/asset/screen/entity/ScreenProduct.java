/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.screen.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 零售家产品详情Entity
 * @author len
 * @version 2019-01-08
 */
@Table(name="${_prefix}screen_product", alias="a", columns= {
		@Column(name = "product_code", attrName = "productCode", label = "产品编码", isPK = true),
		@Column(name = "product_name", attrName = "productName", label = "产品名称", queryType = QueryType.LIKE),
		@Column(name = "enterprise_name", attrName = "enterpriseName", label = "企业名称", queryType = QueryType.LIKE),
		@Column(name = "enterprise_code", attrName = "enterpriseCode", label = "企业编码"),
},joinTable = {
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=ScreenEnterprise.class, alias="b",
				on="a.enterprise_code = b.enterprise_code", attrName="screenEnterprise",
				columns={
						@Column(name="enterprise_code", label="企业编码"),
						@Column(name="enterprise_name", label="企业名称"),
				}),
	}, orderBy="a.product_code DESC"
)
public class ScreenProduct extends DataEntity<ScreenProduct> {
	
	private static final long serialVersionUID = 1L;
	private String productCode;		// 产品编码
	private String productName;		// 产品名称
	private String enterpriseName;		// 企业名称
	private String enterpriseCode;		// 企业编码
	private ScreenEnterprise screenEnterprise;

	public ScreenEnterprise getScreenEnterprise() {
		return screenEnterprise;
	}

	public void setScreenEnterprise(ScreenEnterprise screenEnterprise) {
		this.screenEnterprise = screenEnterprise;
	}

	public ScreenProduct() {
		this(null);
	}

	public ScreenProduct(String id){
		super(id);
	}
	
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=0, max=255, message="产品名称长度不能超过 255 个字符")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Length(min=0, max=128, message="企业名称长度不能超过 128 个字符")
	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	
	@Length(min=0, max=64, message="企业编码长度不能超过 64 个字符")
	public String getEnterpriseCode() {
		return enterpriseCode;
	}

	public void setEnterpriseCode(String enterpriseCode) {
		this.enterpriseCode = enterpriseCode;
	}
	
}