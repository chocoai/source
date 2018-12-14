/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.modules.asset.category.entity.Category;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 调拨单Entity
 * @author dwh
 * @version 2018-05-21
 */
@Table(name="${_prefix}am_transfer_details", alias="a", columns={
		@Column(name="details_code", attrName="detailsCode", label="调拨明细编码", isPK=true),
		@Column(name="out_location_code", attrName="outLocationCode", label="出库库位"),
		@Column(name="consumables_code", attrName="consumablesCode", label="耗材分类"),
		@Column(name="consumables_name", attrName="consumablesName", label="耗材名字"),
		@Column(name="specifications", attrName="specifications", label="规格型号"),
		@Column(name="category_code", attrName="categoryCode", label="耗材分类"),
		@Column(name="measure_unit", attrName="measureUnit", label="计量单位"),
		@Column(name="in_location_code", attrName="inLocationCode", label="入库库位"),
		@Column(name="transfer_count", attrName="transferCount", label="数量"),
		@Column(name="transfer_price", attrName="transferPrice", label="单价"),
		@Column(name="transfer_amount", attrName="transferAmount", label="总金额"),
		@Column(name="documents_code", attrName="documentsCode.documentsCode", label="单据编码"),
	}, joinTable = {
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=Category.class, alias="c",
				on="c.category_code=a.category_code ",
				columns={@Column(name="category_code", label="分类编号"),
						@Column(name="category_name", attrName = "categoryName", label="分类名称")}),
}, orderBy="a.details_code ASC"
)
public class AmTransferDetails extends DataEntity<AmTransferDetails> {
	
	private static final long serialVersionUID = 1L;
	private String detailsCode;		// 调拨明细编码
	private String outLocationCode;		// 出库库位
	private String consumablesCode;		// 耗材分类
	private String consumablesName;		// 耗材名字
	private String specifications;		// 规格型号
	private String categoryCode;		// 耗材分类
	private String measureUnit;		// 计量单位
	private String inLocationCode;		// 入库库位
	private Long transferCount;		// 数量
	private Double transferPrice;		// 单价
	private Double transferAmount;		// 总金额
	private AmTransfer documentsCode;		// 单据编码 父类

	private  String outLocationName; //出库库位名字
	private  String inLocationName; //入库库位名字
	private String measureValue;//计量单位
	private String categoryName;
	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getMeasureValue() {
		return measureValue;
	}

	public void setMeasureValue(String measureValue) {
		this.measureValue = measureValue;
	}

	public String getOutLocationName() {
		return outLocationName;
	}

	public void setOutLocationName(String outLocationName) {
		this.outLocationName = outLocationName;
	}

	public String getInLocationName() {
		return inLocationName;
	}

	public void setInLocationName(String inLocationName) {
		this.inLocationName = inLocationName;
	}
	
	public AmTransferDetails() {
		this(null);
	}


	public AmTransferDetails(AmTransfer documentsCode){
		this.documentsCode = documentsCode;
	}
	
	public String getDetailsCode() {
		return detailsCode;
	}

	public void setDetailsCode(String detailsCode) {
		this.detailsCode = detailsCode;
	}
	
	@NotBlank(message="出库库位不能为空")
	@Length(min=0, max=64, message="出库库位长度不能超过 64 个字符")
	public String getOutLocationCode() {
		return outLocationCode;
	}

	public void setOutLocationCode(String outLocationCode) {
		this.outLocationCode = outLocationCode;
	}
	
	@NotBlank(message="耗材分类不能为空")
	@Length(min=0, max=64, message="耗材分类长度不能超过 64 个字符")
	public String getConsumablesCode() {
		return consumablesCode;
	}

	public void setConsumablesCode(String consumablesCode) {
		this.consumablesCode = consumablesCode;
	}
	
	@NotBlank(message="耗材名字不能为空")
	@Length(min=0, max=100, message="耗材名字长度不能超过 100 个字符")
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
	
	@NotBlank(message="耗材分类不能为空")
	@Length(min=0, max=64, message="耗材分类长度不能超过 64 个字符")
	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	
	@NotBlank(message="计量单位不能为空")
	@Length(min=0, max=1, message="计量单位长度不能超过 1 个字符")
	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	
	@NotBlank(message="入库库位不能为空")
	@Length(min=0, max=64, message="入库库位长度不能超过 64 个字符")
	public String getInLocationCode() {
		return inLocationCode;
	}

	public void setInLocationCode(String inLocationCode) {
		this.inLocationCode = inLocationCode;
	}
	
	@NotNull(message="数量不能为空")
	public Long getTransferCount() {
		return transferCount;
	}

	public void setTransferCount(Long transferCount) {
		this.transferCount = transferCount;
	}
	
	@NotNull(message="单价不能为空")
	public Double getTransferPrice() {
		return transferPrice;
	}

	public void setTransferPrice(Double transferPrice) {
		this.transferPrice = transferPrice;
	}
	
	@NotNull(message="总金额不能为空")
	public Double getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(Double transferAmount) {
		this.transferAmount = transferAmount;
	}
	
	@Length(min=0, max=64, message="单据编码长度不能超过 64 个字符")
	public AmTransfer getDocumentsCode() {
		return documentsCode;
	}

	public void setDocumentsCode(AmTransfer documentsCode) {
		this.documentsCode = documentsCode;
	}
	
}