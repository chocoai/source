/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.modules.asset.amlocation.entity.AmLocation;
import com.jeesite.modules.asset.category.entity.Category;
import com.jeesite.modules.asset.warehouse.entity.AmWarehouse;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.util.List;

/**
 * 入库表Entity
 * @author dwh
 * @version 2018-05-03
 */
@Table(name="${_prefix}am_instorage_details", alias="a", columns={
		@Column(name="instorage_code", attrName="instorageCode.instorageCode", label="入库单号"),
		@Column(name="location_code", attrName="locationCode", label="库位编码"),
		@Column(name="consumables_code", attrName="consumablesCode", label="耗材编号"),
		@Column(name="consumables_name", attrName="consumablesName", label="耗材名称"),
		@Column(name="specifications", attrName="specifications", label="规格型号"),
		@Column(name="category_code", attrName="categoryCode", label="分类编码"),
		@Column(name="measure_unit", attrName="measureUnit", label="计量单位"),
		@Column(name="instorage_count", attrName="instorageCount", label="入库数量"),
		@Column(name="instorage_price", attrName="instoragePrice", label="入库单价"),
		@Column(name="instorage_amount", attrName="instorageAmount", label="入库金额"),
		@Column(name="remarks", attrName="remarks", label="备注"),
		@Column(name="detail_code", attrName="detailCode", label="明细编号", isPK=true),
	}, joinTable={
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=Consumables.class, alias="b",
				on="a.consumables_code=b.consumables_code ",
				columns={	@Column(name="consumables_code", label="档案编码", isPK=true),
						@Column(name="consumables_Name", label="档案名字", isQuery=false),}),
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=AmLocation.class, alias="c",
				on="a.location_code=c.location_code ",
				columns={	@Column(name="location_code", label="库位编码", isPK=true),
						@Column(name="location_name",
								label="库位名字", isQuery=false),}),
//		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=Category.class, alias="d",
//				on="d.category_code=a.category_code ",
//				columns={@Column(name="category_code", label="分类编号"),
//						@Column(name="category_name", attrName = "categoryName", label="分类名称")}),
}, orderBy="a.detail_code ASC"
)
public class AmInstorageDetails extends DataEntity<AmInstorageDetails> {
	
	private static final long serialVersionUID = 1L;
	private AmInstorage instorageCode;		// 入库单号 父类
	private String locationCode;		// 库位编码
	private String consumablesCode;		// 耗材编号
	private String consumablesName;		// 耗材名称
	private String specifications;		// 规格型号
	private String categoryCode;		// 分类编码
	private String measureUnit;		// 计量单位
	private Long instorageCount;		// 入库数量
	private Double instoragePrice;		// 入库单价
	private Double instorageAmount;		// 入库金额
	private String detailCode;		// 明细编号
	private Consumables consumables;
	private AmLocation amLocation;
	private String locationName;
	private String status;
	private String measureValue; //明细表只显示的计量单位
//	private Category category;

	private String imgPath;//明细表显示照片


//	public Category getCategory() {
//		return category;
//	}
//
//	public void setCategory(Category category) {
//		this.category = category;
//	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getMeasureValue() {
		return measureValue;
	}

	public void setMeasureValue(String measureValue) {
		this.measureValue = measureValue;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
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

	public Consumables getConsumables() {
		return consumables;
	}

	public void setConsumables(Consumables consumables) {
		this.consumables = consumables;
	}

	public AmInstorageDetails() {
		this(null);
	}


	public AmInstorageDetails(AmInstorage instorageCode){
		this.instorageCode = instorageCode;
	}
	
	@NotBlank(message="入库单号不能为空")
	@Length(min=0, max=64, message="入库单号长度不能超过 64 个字符")
	public AmInstorage getInstorageCode() {
		return instorageCode;
	}

	public void setInstorageCode(AmInstorage instorageCode) {
		this.instorageCode = instorageCode;
	}
	
	@NotBlank(message="库位编码不能为空")
	@Length(min=0, max=64, message="库位编码长度不能超过 64 个字符")
	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
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
	
	@NotNull(message="入库数量不能为空")
	public Long getInstorageCount() {
		return instorageCount;
	}

	public void setInstorageCount(Long instorageCount) {
		this.instorageCount = instorageCount;
	}
	
	@NotNull(message="入库单价不能为空")
	public Double getInstoragePrice() {
		return instoragePrice;
	}

	public void setInstoragePrice(Double instoragePrice) {
		this.instoragePrice = instoragePrice;
	}
	
	@NotNull(message="入库金额不能为空")
	public Double getInstorageAmount() {
		return instorageAmount;
	}

	public void setInstorageAmount(Double instorageAmount) {
		this.instorageAmount = instorageAmount;
	}
	
	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	
}