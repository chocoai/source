/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.order.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 梵赞内购订单商品明细表Entity
 * @author easter
 * @version 2018-12-12
 */
@Table(name="fz_neigou_order_item", alias="a", columns={
		@Column(name="item_id", attrName="itemId", label="商品明细表主键", isPK=true),
		@Column(name="title", attrName="title", label="货品名称", queryType=QueryType.LIKE),
		@Column(name="nums", attrName="nums", label="购买数量"),
		@Column(name="price", attrName="price", label="货品单价"),
		@Column(name="amount", attrName="amount", label="货品总价"),
		@Column(name="product_bn", attrName="productBn", label="货品bn"),
		@Column(name="order_id", attrName="orderId", label="订单id"),
	}, orderBy="a.item_id DESC"
)
public class FzNeigouOrderItem extends DataEntity<FzNeigouOrderItem> {
	
	private static final long serialVersionUID = 1L;
	private String itemId;		// 商品明细表主键
	private String title;		// 货品名称
	private Long nums;		// 购买数量
	private Double price;		// 货品单价
	private Double amount;		// 货品总价
	private String productBn;		// 货品bn
	private String orderId;		// 订单id
	
	public FzNeigouOrderItem() {
		this(null);
	}

	public FzNeigouOrderItem(String id){
		super(id);
	}
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@Length(min=0, max=64, message="货品名称长度不能超过 64 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Long getNums() {
		return nums;
	}

	public void setNums(Long nums) {
		this.nums = nums;
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
	
	@Length(min=0, max=64, message="货品bn长度不能超过 64 个字符")
	public String getProductBn() {
		return productBn;
	}

	public void setProductBn(String productBn) {
		this.productBn = productBn;
	}
	
	@Length(min=0, max=64, message="订单id长度不能超过 64 个字符")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}