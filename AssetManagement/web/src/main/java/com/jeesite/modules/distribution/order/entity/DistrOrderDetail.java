/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.order.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 分销订单Entity
 * @author len
 * @version 2019-01-07
 */
@Table(name="distr_order_detail", alias="a", columns={
		@Column(name="detail_code", attrName="detailCode", label="明细编号", isPK=true),
		@Column(name="document_code", attrName="documentCode.documentCode", label="单据编号"),
		@Column(name="num_iid", attrName="numIid", label="商品id", queryType=QueryType.LIKE),
		@Column(name="goods_name", attrName="goodsName", label="商品名称", queryType=QueryType.LIKE),
		@Column(name="spec", attrName="spec", label="规格"),
		@Column(name="sku", attrName="sku", label="sku"),
		@Column(name="sku_id", attrName="skuId", label="sku"),
		@Column(name="quantity", attrName="quantity", label="数量"),
		@Column(name="price", attrName="price", label="单价"),
		@Column(name="amount", attrName="amount", label="金额"),
		@Column(name="sku_url", attrName="skuUrl", label="sku图片"),
	}, orderBy="a.detail_code ASC"
)
public class DistrOrderDetail extends DataEntity<DistrOrderDetail> {
	
	private static final long serialVersionUID = 1L;
	private String detailCode;		// 明细编号
	private DistrOrder documentCode;		// 单据编号 父类
	private String goodsName;		// 商品名称
	private String spec;		// 规格
	private String sku;		// sku
	private String skuId;		// sku
	private Long quantity;		// 数量
	private Double price;		// 单价
	private Double amount;		// 金额
	private String numIid;		// 商品id
	private String billNo;		// 查询时用订单号
	private String skuUrl;

	public String getSkuUrl() {
		return skuUrl;
	}

	public void setSkuUrl(String skuUrl) {
		this.skuUrl = skuUrl;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getNumIid() {
		return numIid;
	}

	public void setNumIid(String numIid) {
		this.numIid = numIid;
	}

	public DistrOrderDetail() {
		this(null);
	}


	public DistrOrderDetail(DistrOrder documentCode){
		this.documentCode = documentCode;
	}
	
	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	
	public DistrOrder getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(DistrOrder documentCode) {
		this.documentCode = documentCode;
	}
	
	@Length(min=0, max=1000, message="商品名称长度不能超过 1000 个字符")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@Length(min=0, max=500, message="规格长度不能超过 500 个字符")
	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	@Length(min=0, max=1000, message="sku长度不能超过 1000 个字符")
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}