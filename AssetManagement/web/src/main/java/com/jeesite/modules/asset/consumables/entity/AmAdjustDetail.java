/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 耗材成本调整单Entity
 * @author dwh
 * @version 2018-05-31
 */
@Table(name="${_prefix}am_adjust_detail", alias="a", columns={
		@Column(name="detail_code", attrName="detailCode", label="单据编码", isPK=true),
		@Column(name="document_code", attrName="documentCode.documentCode", label="调整单据编码"),
		@Column(name="consumables_code", attrName="consumablesCode", label="耗材编码"),
		@Column(name="consumables_name", attrName="consumablesName", label="耗材名称", queryType=QueryType.LIKE),
		@Column(name="specifications", attrName="specifications", label="规格型号"),
		@Column(name="category_code", attrName="categoryCode", label="分类编码"),
		@Column(name="adjustment_amount", attrName="adjustmentAmount", label="调整金额"),
//		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.detail_code ASC"
)
public class AmAdjustDetail extends DataEntity<AmAdjustDetail> {
	
	private static final long serialVersionUID = 1L;
	private String detailCode;		// 单据编码
	private AmCostAdjustment documentCode;		// 调整单据编码 父类
	private String consumablesCode;		// 耗材编码
	private String consumablesName;		// 耗材名称
	private String specifications;		// 规格型号
	private String categoryCode;		// 分类编码
	private Double adjustmentAmount;		// 调整金额
	
	public AmAdjustDetail() {
		this(null);
	}


	public AmAdjustDetail(AmCostAdjustment documentCode){
		this.documentCode = documentCode;
	}
	
	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	
	@NotBlank(message="调整单据编码不能为空")
	@Length(min=0, max=64, message="调整单据编码长度不能超过 64 个字符")
	public AmCostAdjustment getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(AmCostAdjustment documentCode) {
		this.documentCode = documentCode;
	}
	
	@NotBlank(message="耗材编码不能为空")
	@Length(min=0, max=64, message="耗材编码长度不能超过 64 个字符")
	public String getConsumablesCode() {
		return consumablesCode;
	}

	public void setConsumablesCode(String consumablesCode) {
		this.consumablesCode = consumablesCode;
	}
	
	@NotBlank(message="耗材名称不能为空")
	@Length(min=0, max=100, message="耗材名称长度不能超过 100 个字符")
	public String getConsumablesName() {
		return consumablesName;
	}

	public void setConsumablesName(String consumablesName) {
		this.consumablesName = consumablesName;
	}
	
	@Length(min=0, max=100, message="规格型号长度不能超过 100 个字符")
	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	
	@NotBlank(message="分类编码不能为空")
	@Length(min=0, max=64, message="分类编码长度不能超过 64 个字符")
	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	
	@NotNull(message="调整金额不能为空")
	public Double getAdjustmentAmount() {
		return adjustmentAmount;
	}

	public void setAdjustmentAmount(Double adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}
	
}