/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.pricelog.entity;

import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 分销价修改日志Entity
 * @author len
 * @version 2019-01-08
 */
@Table(name="distr_price_log", alias="a", columns={
		@Column(name="log_code", attrName="logCode", label="日志编号", isPK=true),
		@Column(name="original_price", attrName="originalPrice", label="原价"),
		@Column(name="current_price", attrName="currentPrice", label="现价"),
		@Column(name="num_iid", attrName="numIid",label = "商品id"),
		@Column(name="time", attrName="time", label="更新时间"),
		@Column(name="update_by", attrName="updateBy", label="更新人"),
		@Column(name="sku", attrName="sku", label="sku", queryType=QueryType.LIKE),
		@Column(name="sku_id", attrName="skuId", label="sku_id", queryType=QueryType.LIKE),
		@Column(name="goods_name", attrName="goodsName", label="商品名称", queryType=QueryType.LIKE),
	}, orderBy="a.log_code DESC"
)
public class DistrPriceLog extends DataEntity<DistrPriceLog> {
	
	private static final long serialVersionUID = 1L;
	private String logCode;		// 日志编号
	private Double originalPrice;		// 原价
	private Double currentPrice;		// 现价
	private Date time;		// 更新时间
	private String sku;		// sku
	private String skuId;		// sku_id
	private String goodsName;		// 商品名称
	private String updateBy;		// 更新人
	private String numIid;		// 商品id

	public String getNumIid() {
		return numIid;
	}

	public void setNumIid(String numIid) {
		this.numIid = numIid;
	}

	@Override
	public String getUpdateBy() {
		return updateBy;
	}

	@Override
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public DistrPriceLog() {
		this(null);
	}

	public DistrPriceLog(String id){
		super(id);
	}
	
	public String getLogCode() {
		return logCode;
	}

	public void setLogCode(String logCode) {
		this.logCode = logCode;
	}
	
	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}
	
	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	@Length(min=0, max=64, message="sku长度不能超过 64 个字符")
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	@Length(min=0, max=64, message="sku_id长度不能超过 64 个字符")
	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
	@Length(min=0, max=255, message="商品名称长度不能超过 255 个字符")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
}