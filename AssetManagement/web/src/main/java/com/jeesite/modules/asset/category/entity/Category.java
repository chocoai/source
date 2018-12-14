/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.category.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.TreeEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 耗材分类表Entity
 * @author czy
 * @version 2018-04-23
 */
@Table(name="${_prefix}am_category", alias="a", columns={
		@Column(name="category_code", attrName="categoryCode", label="分类编码", isPK=true),
		@Column(includeEntity=TreeEntity.class),
		@Column(name="category_name", attrName="categoryName", label="分类名称", queryType=QueryType.LIKE, isTreeName=true),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.tree_sorts, a.category_code"
)
public class Category extends TreeEntity<Category> {
	
	private static final long serialVersionUID = 1L;
	private String categoryCode;		// 分类编码
	private String categoryName;		// 分类名称


	public Category() {
		this(null);
	}

	public Category(String id){
		super(id);
	}
	
	@Override
	public Category getParent() {
		return parent;
	}

	@Override
	public void setParent(Category parent) {
		this.parent = parent;
	}
	
	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
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