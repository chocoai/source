/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.product.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.TreeEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 商品分类表Entity
 * @author Scarlett
 * @version 2018-07-23
 */
@Table(name="${_prefix}product_category", alias="a", columns={
		@Column(name="procategory_code", attrName="procategoryCode", label="分类编码", isPK=true),
		@Column(name="procategory_status", attrName="procategoryStatus", label="审核状态"),
		@Column(includeEntity=TreeEntity.class),
		@Column(name="category_name", attrName="categoryName", label="分类名称", queryType=QueryType.LIKE, isTreeName=true),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.tree_sorts, a.procategory_code"
)
public class ProductCategory extends TreeEntity<ProductCategory> {
	
	private static final long serialVersionUID = 1L;
	private String procategoryCode;		// 分类编码
	private String procategoryStatus;		// 审核状态
	private String categoryName;		// 分类名称
	private String imgPath;   //照片绝对路径
	private String item;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	public ProductCategory() {
		this(null);
	}

	public ProductCategory(String id){
		super(id);
	}
	
	@Override
	public ProductCategory getParent() {
		return parent;
	}

	@Override
	public void setParent(ProductCategory parent) {
		this.parent = parent;
	}
	
	public String getProcategoryCode() {
		return procategoryCode;
	}

	public void setProcategoryCode(String procategoryCode) {
		this.procategoryCode = procategoryCode;
	}
	
	@NotBlank(message="审核状态不能为空")
	@Length(min=0, max=1, message="审核状态长度不能超过 1 个字符")
	public String getProcategoryStatus() {
		return procategoryStatus;
	}

	public void setProcategoryStatus(String procategoryStatus) {
		this.procategoryStatus = procategoryStatus;
	}
	
	@NotBlank(message="分类名称不能为空")
	@Length(min=0, max=100, message="分类名称长度不能超过 100 个字符")
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}