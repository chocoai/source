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
 * 商品-SKU管理Entity
 * @author jace
 * @version 2018-07-09
 */
@Table(name="tb_sku", alias="b", columns= {
		@Column(name = "sku_id", attrName = "skuId", label = "sku_id", isPK = true),
		@Column(name = "num_iid", attrName = "numIid", label = "sku所属商品id"),
		@Column(name = "properties_name", attrName = "propertiesName", label = "规格名称", queryType = QueryType.LIKE),
		@Column(name = "price", attrName = "price", label = "标准售价"),
		@Column(name = "quantity", attrName = "quantity", label = "数量"),
		@Column(name = "properties", attrName = "properties", label = "sku的销售属性组合字符串"),
		@Column(name = "created", attrName = "created", label = "sku创建日期"),
		@Column(name = "outer_id", attrName = "outerId", label = "商家设置的外部id"),
		@Column(name = "modified", attrName = "modified", label = "sku最后修改日期"),
//		@Column(includeEntity=DataEntity.class),
		@Column(name = "barcode", attrName = "barcode", label = "商品级别的条形码"),
		@Column(name = "k3_name", attrName = "k3Name", label = "k3物料名称"),
		@Column(name = "real_price", attrName = "realPrice", label = "真实价格"),
		@Column(name = "pre_sale", attrName = "preSale", label = "预售时间")
	}, orderBy="a.title ASC"
)
public class TbSku extends DataEntity<TbSku> {
	
	private static final long serialVersionUID = 1L;
	private Long skuId;		// sku_id
	private Long numIid;		// sku所属商品id
	private String propertiesName;		// 规格名称
	private String price;		// 价格
	private Long quantity;		// 数量
	private String properties;		// sku的销售属性组合字符串
	private String created;		// sku创建日期
	private String outerId;		// 商家设置的外部id
	private String modified;		// sku最后修改日期
	private String barcode;		// 商品级别的条形码
	private String title;
	private String k3Name;
	private String realPrice;
	private String skuName;
	private String picUrl;
	private String shop;		// 店铺(卖家昵称)
	private String preSale;		// 预售时间

	public String getPreSale() {
		return preSale;
	}

	public void setPreSale(String preSale) {
		this.preSale = preSale;
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

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	//	private TbSkuK3Name tbSkuK3Name;
//
//	public TbSkuK3Name getTbSkuK3Name() {
//		return tbSkuK3Name;
//	}
//
//	public void setTbSkuK3Name(TbSkuK3Name tbSkuK3Name) {
//		this.tbSkuK3Name = tbSkuK3Name;
//	}

	public String getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(String realPrice) {
		this.realPrice = realPrice;
	}

	public String getK3Name() {
		return k3Name;
	}

	public void setK3Name(String k3Name) {
		this.k3Name = k3Name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public TbSku() {
		this(null);
	}

	public TbSku(String id){
		super(id);
	}
	
	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	
	public Long getNumIid() {
		return numIid;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}
	
	@Length(min=0, max=128, message="规格名称长度不能超过 128 个字符")
	public String getPropertiesName() {
		return propertiesName;
	}

	public void setPropertiesName(String propertiesName) {
		this.propertiesName = propertiesName;
	}
	
	@Length(min=0, max=32, message="价格长度不能超过 32 个字符")
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
	@Length(min=0, max=255, message="sku的销售属性组合字符串长度不能超过 255 个字符")
	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}
	
	@Length(min=0, max=255, message="sku创建日期长度不能超过 255 个字符")
	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}
	
	@Length(min=0, max=255, message="商家设置的外部id长度不能超过 255 个字符")
	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}
	
	@Length(min=0, max=255, message="sku最后修改日期长度不能超过 255 个字符")
	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}
	
	@Length(min=0, max=255, message="商品级别的条形码长度不能超过 255 个字符")
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
}