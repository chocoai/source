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
@Table(name="${_prefix}am_order_discount", alias="a", columns={
		@Column(name="detail_code", attrName="detailCode", label="优惠明细编码", isPK=true),
		@Column(name="document_code", attrName="documentCode.documentCode", label="单据编码"),
		@Column(name="discount", attrName="discount", label="优惠活动"),
		@Column(name="discount_price", attrName="discountPrice", label="优惠金额"),
	}, orderBy="a.detail_code ASC"
)
public class AmOrderDiscount extends DataEntity<AmOrderDiscount> {
	
	private static final long serialVersionUID = 1L;
	private String detailCode;		// 优惠明细编码
	private AmOrder documentCode;		// 单据编码 父类
	private String discount;		// 优惠活动
	private Double discountPrice;		// 优惠金额
	
	public AmOrderDiscount() {
		this(null);
	}


	public AmOrderDiscount(AmOrder documentCode){
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
	
	@Length(min=0, max=2000, message="优惠活动长度不能超过 2000 个字符")
	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	public Double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}
	
}