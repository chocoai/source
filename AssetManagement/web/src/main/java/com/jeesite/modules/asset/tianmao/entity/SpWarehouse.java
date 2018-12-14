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
 * 仓库中商品列表Entity
 * @author dwh
 * @version 2018-08-18
 */
@Table(name="${_prefix}sp_warehouse", alias="a", columns={
		@Column(name="table_id", attrName="tableId", label="主键", isPK=true),
		@Column(name="item_id", attrName="itemId", label="商品的数字id"),
		@Column(name="alias", attrName="alias", label="商品别名"),
		@Column(name="title", attrName="title", label="商品标题", queryType=QueryType.LIKE),
		@Column(name="price", attrName="price", label="价格"),
		@Column(name="item_type", attrName="itemType", label="商品类型"),
		@Column(name="quantity", attrName="quantity", label="总库存"),
		@Column(name="post_type", attrName="postType", label="运费类型"),
		@Column(name="post_fee", attrName="postFee", label="运费"),
		@Column(name="created_time", attrName="createdTime", label="创建时间"),
		@Column(name="update_time", attrName="updateTime", label="更新时间"),
		@Column(name="num", attrName="num", label="商家排序字段"),
		@Column(name="image", attrName="image", label="图片链接"),
	}, orderBy="a.table_id DESC"
)
public class SpWarehouse extends DataEntity<SpWarehouse> {
	
	private static final long serialVersionUID = 1L;
	private String tableId;		// 主键
	private Long itemId;		// 商品的数字id
	private String alias;		// 商品别名
	private String title;		// 商品标题
	private Long price;		// 价格
	private Long itemType;		// 商品类型
	private Long quantity;		// 总库存
	private Long postType;		// 运费类型
	private Long postFee;		// 运费
	private String createdTime;		// 创建时间
	private String updateTime;		// 更新时间
	private Long num;		// 商家排序字段
	private String image;		// 图片链接
	
	public SpWarehouse() {
		this(null);
	}

	public SpWarehouse(String id){
		super(id);
	}
	
	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
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
	
	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	
	@Length(min=0, max=255, message="更新时间长度不能超过 255 个字符")
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}
	
	@Length(min=0, max=255, message="图片链接长度不能超过 255 个字符")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
}