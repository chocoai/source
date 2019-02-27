/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.material.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 分销素材Entity
 * @author len
 * @version 2019-01-05
 */
@Table(name="distr_material_detail", alias="a", columns={
		@Column(name="detail_code", attrName="detailCode", label="detail_code", isPK=true),
		@Column(name="material_code", attrName="materialCode.materialCode", label="素材编码"),
		@Column(name="goods_name", attrName="goodsName", label="商品名称", queryType=QueryType.LIKE),
		@Column(name="num_iid", attrName="numIid", label="商品id"),
	}, orderBy="a.detail_code ASC"
)
public class DistrMaterialDetail extends DataEntity<DistrMaterialDetail> {
	
	private static final long serialVersionUID = 1L;
	private String detailCode;		// detail_code
	private DistrMaterial materialCode;		// 素材编码 父类
	private String goodsName;		// 商品名称
	private String numIid;		// 商品id
	
	public DistrMaterialDetail() {
		this(null);
	}


	public DistrMaterialDetail(DistrMaterial materialCode){
		this.materialCode = materialCode;
	}
	
	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	
	public DistrMaterial getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(DistrMaterial materialCode) {
		this.materialCode = materialCode;
	}
	
	@Length(min=0, max=255, message="商品名称长度不能超过 255 个字符")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@Length(min=0, max=64, message="商品id长度不能超过 64 个字符")
	public String getNumIid() {
		return numIid;
	}

	public void setNumIid(String numIid) {
		this.numIid = numIid;
	}
	
}