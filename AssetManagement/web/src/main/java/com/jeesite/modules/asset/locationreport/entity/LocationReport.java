/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.locationreport.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.modules.asset.amlocation.entity.AmLocation;
import com.jeesite.modules.asset.category.entity.Category;
import com.jeesite.modules.asset.consumables.entity.AmConLocationStock;
import com.jeesite.modules.asset.consumables.entity.Consumables;
import com.jeesite.modules.asset.warehouse.entity.AmWarehouse;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 库位管理Entity
 * @author Mclaran
 * @version 2018-05-07
 */
@Table(name="${_prefix}am_con_location_stock", alias="a", columns={
		@Column(name="warehouse_code", attrName="warehouseCode", label="仓库编码", isPK=true),
		@Column(name="consumables_code", attrName="consumablesCode", label="耗材编号", isPK=true),
		@Column(name="location_code", attrName="locationCode", label="库位编码", isPK=true),
		@Column(name="stock", attrName="stock", label="库存"),
		@Column(name="stock_price", attrName="stockPrice", label="库存单价"),
		@Column(name="stock_atm", attrName="stockAtm", label="库存金额"),
	},
		/*joinTable = {
				@JoinTable(type = JoinTable.Type.JOIN,entity = AmWarehouse.class,alias = "b",
						on = "a.warehouse_code = b.warehouse_code",
						columns={@Column(includeEntity = AmWarehouse.class)}),

				@JoinTable(type = JoinTable.Type.JOIN,entity = Consumables.class,alias = "c",
						on = "a.consumables_code = c.consumables_code",
						columns={@Column(includeEntity = Consumables.class)}),
				@JoinTable(type = JoinTable.Type.JOIN,entity = AmLocation.class,alias = "d",
						on = "a.location_code = d.location_code",
						columns={@Column(includeEntity = AmLocation.class)}),
				@JoinTable(type = JoinTable.Type.JOIN,entity = Category.class,alias = "e",
						on = "c.category_code = e.category_code",
						columns={@Column(includeEntity = Category.class)}),
		},*/
		orderBy="a.create_date DESC"
)
public class LocationReport extends DataEntity<LocationReport> {
	/*a.consumables_code AS consumablesCode,
	a.consumables_name AS consumablesName,
	a.specifications AS specifications,
	a.measure_unit AS measureUnit,
	d.warehouse_name AS warehouseName,
	d.warehouse_code AS warehouseCode,
	c.stock AS stock,
	c.stock_price AS stockPrice,
	c.stock_atm AS stockAtm,
	b.category_name AS categoryName,
	b.category_code AS categoryCode,
	g.file_path AS filePath,
	g.file_extension AS fileExtension,
	g.file_id AS fileId*/

	private String id;
	private String specifications;
	private String measureUnit;
	private String warehouseName;
	private String categoryName;
	private String categoryCode;
	private String consumablesName;
	private String warehouseCode;		// 仓库编码
	private String consumablesCode;		// 耗材编号
	private String locationCode;		// 库位编码
	private Long stock;		// 库存
	private Double stockPrice;		// 库存单价
	private Double stockAtm;		// 库存金额
	private Long stockSum;          //库存合计
	private Double stockAtmSum;     //金额合计
	private String codeORname;
	private String filePath;
	private String fileId;
	private String fileExtension;
	private String photo;
	private String locationName;
	private String measureValue;

	public String getMeasureValue() {
		return measureValue;
	}

	public void setMeasureValue(String measureValue) {
		this.measureValue = measureValue;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getConsumablesName() {
		return consumablesName;
	}

	public void setConsumablesName(String consumablesName) {
		this.consumablesName = consumablesName;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getConsumablesCode() {
		return consumablesCode;
	}

	public void setConsumablesCode(String consumablesCode) {
		this.consumablesCode = consumablesCode;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public Double getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(Double stockPrice) {
		this.stockPrice = stockPrice;
	}

	public Double getStockAtm() {
		return stockAtm;
	}

	public void setStockAtm(Double stockAtm) {
		this.stockAtm = stockAtm;
	}

	public Long getStockSum() {
		return stockSum;
	}

	public void setStockSum(Long stockSum) {
		this.stockSum = stockSum;
	}

	public Double getStockAtmSum() {
		return stockAtmSum;
	}

	public void setStockAtmSum(Double stockAtmSum) {
		this.stockAtmSum = stockAtmSum;
	}

	public String getCodeORname() {
		return codeORname;
	}

	public void setCodeORname(String codeORname) {
		this.codeORname = codeORname;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	/* private String id;
	private String warehouseCode;		// 仓库编码
	private String consumablesCode;		// 耗材编号
	private String locationCode;		// 库位编码
	private Long stock;		// 库存
	private Double stockPrice;		// 库存单价
	private Double stockAtm;		// 库存金额
	private Long stockSum;          //库存合计
	private Double stockAtmSum;     //金额合计
	private AmWarehouse amWarehouse;
	private Consumables consumables;
	private AmLocation amLocation;
	private Category category;
	private String codeORname;
	private String filePath;
	private String fileId;
	private String fileExtension;
	private String photo;

	public LocationReport() {
		this(null);
	}

	public LocationReport(String id){
		super(id);
	}
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Long getStockSum() {
		return stockSum;
	}

	public void setStockSum(Long stockSum) {
		this.stockSum = stockSum;
	}

	public Double getStockAtmSum() {
		return stockAtmSum;
	}

	public void setStockAtmSum(Double stockAtmSum) {
		this.stockAtmSum = stockAtmSum;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getConsumablesCode() {
		return consumablesCode;
	}

	public void setConsumablesCode(String consumablesCode) {
		this.consumablesCode = consumablesCode;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public Double getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(Double stockPrice) {
		this.stockPrice = stockPrice;
	}

	public Double getStockAtm() {
		return stockAtm;
	}

	public void setStockAtm(Double stockAtm) {
		this.stockAtm = stockAtm;
	}

	public AmWarehouse getAmWarehouse() {
		return amWarehouse;
	}

	public void setAmWarehouse(AmWarehouse amWarehouse) {
		this.amWarehouse = amWarehouse;
	}

	public Consumables getConsumables() {
		return consumables;
	}

	public void setConsumables(Consumables consumables) {
		this.consumables = consumables;
	}

	public AmLocation getAmLocation() {
		return amLocation;
	}

	public void setAmLocation(AmLocation amLocation) {
		this.amLocation = amLocation;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getCodeORname() {
		return codeORname;
	}

	public void setCodeORname(String codeORname) {
		this.codeORname = codeORname;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}*/

}