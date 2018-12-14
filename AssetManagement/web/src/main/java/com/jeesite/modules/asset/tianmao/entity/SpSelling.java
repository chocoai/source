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
 * 出售中的商品列表Entity
 * @author dwh
 * @version 2018-08-18
 */
@Table(name="${_prefix}sp_selling", alias="a", columns={
		@Column(name="sell_code", attrName="sellCode", label="主键编码", isPK=true),
		@Column(name="item_id", attrName="itemId", label="商品数字id"),
		@Column(name="alias", attrName="alias", label="商品别名"),
		@Column(name="title", attrName="title", label="商品标题", queryType=QueryType.LIKE),
		@Column(name="price", attrName="price", label="价格"),
		@Column(name="item_type", attrName="itemType", label="商品类型"),
		@Column(name="item_no", attrName="itemNo", label="商家编码"),
		@Column(name="quantity", attrName="quantity", label="总库存"),
		@Column(name="post_type", attrName="postType", label="运费类型"),
		@Column(name="post_fee", attrName="postFee", label="运费"),
		@Column(name="origin", attrName="origin", label="商品划线价"),
		@Column(name="share_detail", attrName="shareDetail", label="同price"),
		@Column(name="detail_url", attrName="detailUrl", label="照片"),
		@Column(name="source_type", attrName="sourceType", label="数据来源"),
	}, orderBy="a.sell_code DESC"
)
public class SpSelling extends DataEntity<SpSelling> {
	
	private static final long serialVersionUID = 1L;
	private String sellCode;		// 主键编码
	private Long itemId;		// 商品数字id
	private String alias;		// 商品别名
	private String title;		// 商品标题
	private Long price;		// 价格
	private Long itemType;		// 商品类型
	private String itemNo;		// 商家编码
	private Long quantity;		// 总库存
	private Long postType;		// 运费类型
	private Long postFee;		// 运费
	private String origin;		// 商品划线价
	private Long shareDetail;		// 同price
	private String detailUrl;    //照片
	private String sourceType;//数据来源（在售/仓库）

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public SpSelling() {
		this(null);
	}

	public SpSelling(String id){
		super(id);
	}
	
	public String getSellCode() {
		return sellCode;
	}

	public void setSellCode(String sellCode) {
		this.sellCode = sellCode;
	}
	
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	@Length(min=0, max=255, message="商品别名长度不能超过 255 个字符")
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	@Length(min=0, max=255, message="商品标题长度不能超过 255 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}
	
	public Long getItemType() {
		return itemType;
	}

	public void setItemType(Long itemType) {
		this.itemType = itemType;
	}
	
	@Length(min=0, max=255, message="商家编码长度不能超过 255 个字符")
	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	
	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
	public Long getPostType() {
		return postType;
	}

	public void setPostType(Long postType) {
		this.postType = postType;
	}
	
	public Long getPostFee() {
		return postFee;
	}

	public void setPostFee(Long postFee) {
		this.postFee = postFee;
	}
	
	@Length(min=0, max=255, message="商品划线价长度不能超过 255 个字符")
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public Long getShareDetail() {
		return shareDetail;
	}

	public void setShareDetail(Long shareDetail) {
		this.shareDetail = shareDetail;
	}
	
}