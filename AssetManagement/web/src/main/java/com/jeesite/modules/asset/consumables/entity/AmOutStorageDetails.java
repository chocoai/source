/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.modules.asset.amlocation.entity.AmLocation;
import com.jeesite.modules.asset.category.entity.Category;
import com.jeesite.modules.file.entity.FileEntity;
import com.jeesite.modules.file.entity.FileUpload;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;


/**
 * 耗材出库表Entity
 * @author czy
 * @version 2018-05-03
 */
@Table(name="${_prefix}am_outstorage_details", alias="a", columns= {
		@Column(name = "detail_code", attrName = "detailCode", label = "出库单号", isPK = true),
		@Column(name = "outstorage_code", attrName = "outstorageCode.outstorageCode", label = "出库单号"),
		@Column(name = "location_code", attrName = "locationCode", label = "库位编码"),
		@Column(name = "consumables_code", attrName = "consumablesCode", label = "耗材编号"),
		@Column(name = "consumables_name", attrName = "consumablesName", label = "耗材名称"),
		@Column(name = "specifications", attrName = "specifications", label = "规格型号"),
		@Column(name = "category_code", attrName = "categoryCode", label = "分类编码"),
		@Column(name = "measure_unit", attrName = "measureUnit", label = "计量单位"),
		@Column(name = "outstorage_count", attrName = "outstorageCount", label = "出库数量"),
		@Column(name = "outstorage_price", attrName = "outstoragePrice", label = "出库单价"),
		@Column(name = "outstorage_amount", attrName = "outstorageAmount", label = "出库金额"),
	}, joinTable = {
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=AmLocation.class, alias="b",
		on="b.location_code = a.location_code",
		columns={@Column(name="location_code", label="库位编码"),
				@Column(name="location_name", attrName = "locationName", label="库位名称")}),
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=Category.class, alias="c",
				on="c.category_code=a.category_code ",
				columns={@Column(name="category_code", label="分类编号"),
						@Column(name="category_name", attrName = "categoryName", label="分类名称")}),
	},orderBy="a.location_code ASC"
)
public class AmOutStorageDetails extends DataEntity<AmOutStorageDetails> {

	private static final long serialVersionUID = 1L;
	private AmOutStorage outstorageCode;		// 出库单号 父类
	private String detailCode;
	private String locationCode;		// 库位编码
	private String consumablesCode;		// 耗材编号
	private String consumablesName;		// 耗材名称
	private String specifications;		// 规格型号
	private String categoryCode;		// 分类编码
	private String measureUnit;		// 计量单位
	private Long outstorageCount;		// 出库数量
	private Double outstoragePrice;		// 出库单价
	private Double outstorageAmount;		// 出库金额
	private AmLocation amLocation;
	private String locationName;
	private Category category;
	private String categoryName;
	private String measureValue;    // 计量单位显示用

	public String getMeasureValue() {
		return measureValue;
	}

	public void setMeasureValue(String measureValue) {
		this.measureValue = measureValue;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public AmLocation getAmLocation() {
		return amLocation;
	}

	public void setAmLocation(AmLocation amLocation) {
		this.amLocation = amLocation;
	}

	public AmOutStorageDetails() {
		this(null);
	}

	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}

	public AmOutStorageDetails(AmOutStorage outstorageCode){
		this.outstorageCode = outstorageCode;
	}

	public AmOutStorage getOutstorageCode() {
		return outstorageCode;
	}

	public void setOutstorageCode(AmOutStorage outstorageCode) {
		this.outstorageCode = outstorageCode;
	}

	@NotBlank(message="耗材编号不能为空")
	@Length(min=0, max=64, message="耗材编号长度不能超过 64 个字符")
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

	@NotBlank(message="计量单位不能为空")
	@Length(min=0, max=1, message="计量单位长度不能超过 1 个字符")
	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	@NotNull(message="出库数量不能为空")
	@Length(min=1, max=20, message="计量单位长度不能超过 20 个字符")
	public Long getOutstorageCount() {
		return outstorageCount;
	}

	public void setOutstorageCount(Long outstorageCount) {
		this.outstorageCount = outstorageCount;
	}

//	@NotNull(message="出库单价不能为空")
	public Double getOutstoragePrice() {
		return outstoragePrice;
	}

	public void setOutstoragePrice(Double outstoragePrice) {
		this.outstoragePrice = outstoragePrice;
	}

//	@NotNull(message="出库金额不能为空")
	public Double getOutstorageAmount() {
		return outstorageAmount;
	}

	public void setOutstorageAmount(Double outstorageAmount) {
		this.outstorageAmount = outstorageAmount;
	}
	
}