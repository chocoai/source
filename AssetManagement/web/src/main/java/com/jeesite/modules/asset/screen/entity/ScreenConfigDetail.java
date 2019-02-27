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
 * 屏幕配置Entity
 * @author len
 * @version 2018-12-21
 */
@Table(name="${_prefix}screen_config_detail", alias="a", columns={
		@Column(name="detail_code", attrName="detailCode", label="明细编码", isPK=true),
		@Column(name="configure_code", attrName="configureCode.configureCode", label="配置项编码"),
		@Column(name="num_iid", attrName="numIid", label="商品id"),
		@Column(name="detail_url", attrName="detailUrl", label="商品url"),
		@Column(name="goods_name", attrName="goodsName", label="商品名称", queryType=QueryType.LIKE),
	}, orderBy="a.detail_code ASC"
)
public class ScreenConfigDetail extends DataEntity<ScreenConfigDetail> {
	
	private static final long serialVersionUID = 1L;
	private String detailCode;		// 明细编码
	private ScreenConfig configureCode;		// 配置项编码 父类
	private String numIid;		// 商品id
	private String detailUrl;		// 商品url
	private String goodsName;		// 商品名称
	
	public ScreenConfigDetail() {
		this(null);
	}


	public ScreenConfigDetail(ScreenConfig configureCode){
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
	
	public String getNumIid() {
		return numIid;
	}

	public void setNumIid(String numIid) {
		this.numIid = numIid;
	}
	
	@Length(min=0, max=255, message="商品url长度不能超过 255 个字符")
	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	
	@Length(min=0, max=128, message="商品名称长度不能超过 128 个字符")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

}