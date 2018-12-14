/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.entity;


import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

import java.util.Date;

/**
 * 耗材库存表Entity
 * @author Mclaran
 * @version 2018-05-05
 */
@Table(name="${_prefix}am_con_stock", alias="a", columns={
		@Column(name="warehouse_code", attrName="warehouseCode", label="仓库编码", isPK=true),
		@Column(name="consumables_code", attrName="consumablesCode", label="耗材编号", isPK=true),
		@Column(name="stock", attrName="stock", label="库存"),
		@Column(name="stock_price", attrName="stockPrice", label="库存单价"),
		@Column(name="stock_atm", attrName="stockAtm", label="库存金额"),
		@Column(name="create_date", attrName="createDate", label="创建时间"),
	}, orderBy="a.warehouse_code DESC, a.consumables_code DESC"
)
public class AmConStock extends DataEntity<AmConStock> {
	
	private static final long serialVersionUID = 1L;
	private String warehouseCode;		// 仓库编码
	private String consumablesCode;		// 耗材编号
	private Long stock;		// 库存
	private Double stockPrice;		// 库存单价
	private Double stockAtm;		// 库存金额
	private Date createDate;

	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public AmConStock()  {

	}

	public AmConStock(String warehouseCode, String consumablesCode){
		this.warehouseCode = warehouseCode;
		this.consumablesCode = consumablesCode;
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
	
}