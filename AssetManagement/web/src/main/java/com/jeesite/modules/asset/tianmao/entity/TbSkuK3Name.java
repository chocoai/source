/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tb_sku_k3_nameEntity
 * @author jace
 * @version 2018-07-20
 */
@Table(name="tb_sku_k3_name", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="sku_id", attrName="skuId", label="关联tb_sku表"),
		@Column(name="outer_id", attrName="outerId", label="outer_id"),
		@Column(name="sku_name", attrName="skuName", label="sku对应的k3物料名称", queryType=QueryType.LIKE),
		@Column(name="date", attrName="date", label="数据最新时间"),
	}, orderBy="a.id DESC"
)
public class TbSkuK3Name extends DataEntity<TbSkuK3Name> {
	
	private static final long serialVersionUID = 1L;
	private Long skuId;		// 关联tb_sku表
	private String outerId;		// outer_id
	private String skuName;		// sku对应的k3物料名称
	private String date;		// 数据最新时间
	
	public TbSkuK3Name() {
		this(null);
	}

	public TbSkuK3Name(String id){
		super(id);
	}
	
	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	
	@Length(min=0, max=50, message="outer_id长度不能超过 50 个字符")
	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}
	@Length(min=0, max=255, message="sku对应的k3物料名称长度不能超过 255 个字符")
	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	@Length(min=0, max=50, message="数据最新时间长度不能超过 50 个字符")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}