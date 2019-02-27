/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 订单管理Entity
 * @author czy
 * @version 2018-07-09
 */
@Table(name="${_prefix}am_order_detail", alias="a", columns={
		@Column(name="detail_code", attrName="detailCode", label="明细编号", isPK=true),
		@Column(name="document_code", attrName="documentCode.documentCode", label="单据编号"),
		@Column(name="goods_name", attrName="goodsName", label="商品名称", queryType=QueryType.LIKE),
		@Column(name="sku_id", attrName="skuId"),
		@Column(name="spec", attrName="spec", label="规格"),
		@Column(name="quantity", attrName="quantity", label="数量"),
		@Column(name="stand_price", attrName="standPrice", label="标准售价"),
		@Column(name="price", attrName="price", label="单价"),
		@Column(name="amount", attrName="amount", label="金额"),
		@Column(name="sku", attrName="sku", label="sku"),
		@Column(name="shop", attrName="shop", label="店铺"),
		@Column(name="expected_delivery", attrName="expectedDelivery", label="预计交期"),
		@Column(name="purchase_cycle", attrName="purchaseCycle", label="采购周期"),
	}, orderBy="a.detail_code ASC"
)
public class AmOrderDetail extends DataEntity<AmOrderDetail> {
	private static final long serialVersionUID = 1L;
	private String detailCode;		// 明细编号
	private AmOrder documentCode;		// 单据编号 父类
	private String goodsName;		// 商品名称
	private String skuId;		// sku
	private String spec;		// 规格
	private Long quantity;		// 数量
	private Double standPrice;		// 标准售价
	private Double price;		// 单价
	private Double amount;		// 金额
	private String sku;
	private String picUrl;
	private String numId;
	private String shop;		// 店铺
	private Long virtualQuantity; // 虚拟的数量
	private String expectedDelivery;	// 预计交期
	private String purchaseCycle;		// 采购周期
	private String skuUrl;		// sku图片

	public String getSkuUrl() {
		return skuUrl;
	}

	public void setSkuUrl(String skuUrl) {
		this.skuUrl = skuUrl;
	}

	public String getExpectedDelivery() {
		return expectedDelivery;
	}

	public void setExpectedDelivery(String expectedDelivery) {
		this.expectedDelivery = expectedDelivery;
	}

	public String getPurchaseCycle() {
		return purchaseCycle;
	}

	public void setPurchaseCycle(String purchaseCycle) {
		this.purchaseCycle = purchaseCycle;
	}

	public String getShop() {
		return shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getNumId() {
		return numId;
	}

	public void setNumId(String numId) {
		this.numId = numId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public AmOrderDetail() {
		this(null);
	}


	public AmOrderDetail(AmOrder documentCode){
		this.documentCode = documentCode;
	}
	
	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	
	public AmOrder getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(AmOrder documentCode) {
		this.documentCode = documentCode;
	}
	
	@Length(min=0, max=100, message="商品名称长度不能超过 1000 个字符")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@Length(min=0, max=64, message="sku长度不能超过 64 个字符")
	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
	@Length(min=0, max=500, message="规格长度不能超过 500 个字符")
	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
	public Double getStandPrice() {
		return standPrice;
	}

	public void setStandPrice(Double standPrice) {
		this.standPrice = standPrice;
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

	public Long getVirtualQuantity() {
		return virtualQuantity;
	}

	public void setVirtualQuantity(Long virtualQuantity) {
		this.virtualQuantity = virtualQuantity;
	}
}