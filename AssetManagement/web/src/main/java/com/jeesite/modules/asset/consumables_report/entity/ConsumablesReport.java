/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables_report.entity;



import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.asset.amlocation.entity.AmLocation;

import com.jeesite.modules.asset.category.entity.Category;
import com.jeesite.modules.asset.consumables.entity.AmConStock;
import com.jeesite.modules.asset.consumables.entity.Consumables;
import com.jeesite.modules.asset.warehouse.entity.AmWarehouse;
import org.hibernate.validator.constraints.Length;

/**
 * consumables_reportEntity
 * @author Mclaran
 * @version 2018-05-03
 */
@Table(name="${_prefix}am_con_stock", alias="a", columns={
		@Column(name="warehouse_code", attrName="warehouseCode", label="", isPK=true),
		@Column(name="consumables_code", attrName="consumablesCode", label="", queryType=QueryType.LIKE),
		@Column(name="stock", attrName="stock", label=""),
		@Column(name="stock_price", attrName="stockPrice", label="计量单位"),
		@Column(name="stock_atm", attrName="stockAtm", label="计量单位"),
	},
		joinTable = {

				@JoinTable(type = JoinTable.Type.JOIN,entity = AmConStock.class,alias = "c",
						on="a.consumables_code = c.consumables_code",
						columns = {@Column(includeEntity = AmConStock.class)}
				),
				@JoinTable(type = JoinTable.Type.JOIN,entity = AmWarehouse.class,alias = "d",
						on="a.warehouse_code = d.warehouse_code",
						columns = {@Column(includeEntity = AmWarehouse.class)}
				),
				@JoinTable(type = JoinTable.Type.JOIN,entity = Consumables.class,alias = "e",
						on="a.consumables_code = e.consumables_code",
						columns = {@Column(includeEntity = Consumables.class)}
				),
				@JoinTable(type = JoinTable.Type.JOIN,entity = Category.class,alias = "b",
						on="e.category_code = b.category_code",
						columns = {@Column(includeEntity = Category.class)}
				),

},
		orderBy="a.create_date DESC"
)
public class ConsumablesReport extends DataEntity<Consumables>{
	private String id;
	private Category category;
	private AmConStock amConStock;
	private AmWarehouse amWarehouse;
	private Consumables consumables;
	private String warehouseCode;
	private String consumablesCode;
	private Double stockPrice;		// 库存单价
	private Long stock;		// 库存
	private Double stockAtm;		// 库存金额
	//private String consumablesCode;		// 耗材编号
	//private String consumablesName;		// 耗材名称
	//private String specifications;		// 规格型号
	//private String measureUnit;		// 计量单位
	private String filePath;
	private String fileId;
	private String fileExtension;
	private Long stockSum;         //库存合计
	private Double stockAtmSum;   //库存金额合计
	private String codeORname;
	private String photo;


	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Consumables getConsumables() {
		return consumables;
	}

	public void setConsumables(Consumables consumables) {
		this.consumables = consumables;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public Double getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(Double stockPrice) {
		this.stockPrice = stockPrice;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public Double getStockAtm() {
		return stockAtm;
	}

	public void setStockAtm(Double stockAtm) {
		this.stockAtm = stockAtm;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public AmConStock getAmConStock() {
		return amConStock;
	}

	public void setAmConStock(AmConStock amConStock) {
		this.amConStock = amConStock;
	}

	public AmWarehouse getAmWarehouse() {
		return amWarehouse;
	}

	public void setAmWarehouse(AmWarehouse amWarehouse) {
		this.amWarehouse = amWarehouse;
	}

	public String getConsumablesCode() {
		return consumablesCode;
	}

	public void setConsumablesCode(String consumablesCode) {
		this.consumablesCode = consumablesCode;
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

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getCodeORname() {
		return codeORname;
	}

	public void setCodeORname(String codeORname) {
		this.codeORname = codeORname;
	}
/*private static final long serialVersionUID = 1L;
    private String categoryCode;		// 分类编码
	private String consumablesName;     //耗材名称
	private String consumablesCode;		// 耗材编码
	private String categoryName;		// 分类名称
	private String specifications;		// 规格型号
	private String measureUnit;		// 计量单位
	private Long stock;		// 库存
	private Double stockPrice;		// 库存单价
	private Double stockAtm;		// 库存金额
	private String photo;		// photo
	private String property;    //接收前台查询条件
	private String stockSum;         //库存合计
	private Double stockAtmSum;   //库存金额合计
	private String warehouseName;
	private String warehouseCode;
	private String filePath;
	private String fileId;
	private String fileExtension;
	private String status; //判断图片状态

	public String getStockSum() {
		return stockSum;
	}

	public void setStockSum(String stockSum) {
		this.stockSum = stockSum;
	}

	public Double getStockAtmSum() {
		return stockAtmSum;
	}

	public void setStockAtmSum(Double stockAtmSum) {
		this.stockAtmSum = stockAtmSum;
	}

	@Length(min=0, max=64, message="warehouse_name长度不能超过 64 个字符")
	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getConsumablesName() {
		return consumablesName;
	}

	public void setConsumablesName(String consumablesName) {
		this.consumablesName = consumablesName;
	}

	public String getConsumablesCode() {
		return consumablesCode;
	}

	public void setConsumablesCode(String consumablesCode) {
		this.consumablesCode = consumablesCode;
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
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}*/
}