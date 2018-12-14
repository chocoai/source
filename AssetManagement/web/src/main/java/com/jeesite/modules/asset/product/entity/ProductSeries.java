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
 * 商品系列表Entity
 * @author Scarlett
 * @version 2018-07-24
 */
@Table(name="${_prefix}product_series", alias="a", columns={
		@Column(name="proseries_code", attrName="proseriesCode", label="风格编码", isPK=true),
		@Column(name="proseries_status", attrName="proseriesStatus", label="审核状态"),
		@Column(includeEntity=TreeEntity.class),
		@Column(name="series_name", attrName="seriesName", label="风格名称", queryType=QueryType.LIKE, isTreeName=true),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.tree_sorts, a.proseries_code"
)
public class ProductSeries extends TreeEntity<ProductSeries> {
	
	private static final long serialVersionUID = 1L;
	private String proseriesCode;		// 风格编码
	private String proseriesStatus;		// 审核状态
	private String seriesName;		// 风格名称
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
	
	public ProductSeries() {
		this(null);
	}

	public ProductSeries(String id){
		super(id);
	}
	
	@Override
	public ProductSeries getParent() {
		return parent;
	}

	@Override
	public void setParent(ProductSeries parent) {
		this.parent = parent;
	}
	
	public String getProseriesCode() {
		return proseriesCode;
	}

	public void setProseriesCode(String proseriesCode) {
		this.proseriesCode = proseriesCode;
	}
	
	@NotBlank(message="审核状态不能为空")
	@Length(min=0, max=1, message="审核状态长度不能超过 1 个字符")
	public String getProseriesStatus() {
		return proseriesStatus;
	}

	public void setProseriesStatus(String proseriesStatus) {
		this.proseriesStatus = proseriesStatus;
	}
	
	@NotBlank(message="风格名称不能为空")
	@Length(min=0, max=100, message="风格名称长度不能超过 100 个字符")
	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	
}