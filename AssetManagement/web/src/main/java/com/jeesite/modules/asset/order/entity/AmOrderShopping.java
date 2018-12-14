/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 导购购物车Entity
 * @author len
 * @version 2018-11-13
 */
@Table(name="${_prefix}am_order_shopping", alias="a", columns={
		@Column(name="seq", attrName="seq", label="购物车商品序号", isPK=true),
		@Column(name="guide_code", attrName="guideCode", label="导购登录账号"),
		@Column(name="guide_name", attrName="guideName", label="导购昵称", queryType=QueryType.LIKE),
		@Column(name="num_iid", attrName="numIid", label="商品id"),
		@Column(name="title", attrName="title", label="商品名称"),
		@Column(name="sku_id", attrName="skuId", label="sku_id"),
		@Column(name="outer_id", attrName="outerId", label="商品sku", queryType=QueryType.LIKE),
		@Column(name="properties", attrName="properties", label="规格名称"),
		@Column(name="num", attrName="num", label="数量"),
		@Column(name="price", attrName="price", label="商品单价"),
		@Column(name="goods_status", attrName="goodsStatus", label="状态"),
		@Column(name="create_time", attrName="createTime", label="创建时间"),
		@Column(name="update_time", attrName="updateTime", label="更新时间"),
		@Column(name="pic_url", attrName="picUrl", label="商品主图"),
		@Column(name="quantity", attrName="quantity", label="库存数"),
		@Column(name="store_name", attrName="storeName", label="店铺"),
	}, orderBy="a.create_time DESC"
)
public class AmOrderShopping extends DataEntity<AmOrderShopping> {
	
	private static final long serialVersionUID = 1L;
	private String seq;		// 购物车商品序号
	private String guideCode;		// 导购登录账号
	private String guideName;		// 导购昵称
	private String numIid;		// 商品id
	private String title;		// 商品名称
	private String skuId;		// sku_id
	private String outerId;		// 商品sku
	private String properties;		// 规格名称
	private Integer num;		// 数量
	private String price;		// 商品单价
	private String goodsStatus;		// 状态
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String picUrl;			// 商品主图
	private Integer quantity;		// 库存数
	private String storeName;		// 店铺
	
	public AmOrderShopping() {
		this(null);
	}

	public AmOrderShopping(String id){
		super(id);
	}
	
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	@NotBlank(message="导购登录账号不能为空")
	@Length(min=0, max=64, message="导购登录账号长度不能超过 64 个字符")
	public String getGuideCode() {
		return guideCode;
	}

	public void setGuideCode(String guideCode) {
		this.guideCode = guideCode;
	}
	
	@Length(min=0, max=64, message="导购昵称长度不能超过 64 个字符")
	public String getGuideName() {
		return guideName;
	}

	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}
	
	@Length(min=0, max=64, message="商品id长度不能超过 64 个字符")
	public String getNumIid() {
		return numIid;
	}

	public void setNumIid(String numIid) {
		this.numIid = numIid;
	}
	
	@Length(min=0, max=128, message="商品名称长度不能超过 128 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=64, message="sku_id长度不能超过 64 个字符")
	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
	@Length(min=0, max=64, message="商品sku长度不能超过 64 个字符")
	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}
	
	@Length(min=0, max=255, message="规格名称长度不能超过 255 个字符")
	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@Length(min=0, max=15, message="商品单价长度不能超过 15 个字符")
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@Length(min=0, max=1, message="状态长度不能超过 1 个字符")
	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
}