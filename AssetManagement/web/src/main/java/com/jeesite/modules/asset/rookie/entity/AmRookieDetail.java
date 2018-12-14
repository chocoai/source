/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.rookie.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 菜鸟对接记录Entity
 * @author czy
 * @version 2018-07-03
 */
@Table(name="${_prefix}am_rookie_detail", alias="a", columns={
		@Column(name="document_code", attrName="documentCode.documentCode", label="单据编号"),
		@Column(name="item_code", attrName="itemCode", label="商品编码"),
		@Column(name="item_name", attrName="itemName", label="商品名称", queryType=QueryType.LIKE),
		@Column(name="planqty", attrName="planqty", label="计划数量"),
		@Column(name="actualqty", attrName="actualqty", label="实际数量"),
		@Column(name="batch_code", attrName="batchCode", label="批次号"),
		@Column(name="orderline_code", attrName="orderlineCode", label="单据行号"),
		@Column(name="bom_ver", attrName="bomVer", label="Bom版本"),
		@Column(name="warehouse_code", attrName="warehouseCode", label="仓库"),
		@Column(name="detail_code", attrName="detailCode", label="明细编码", isPK=true),
	}, orderBy="a.detail_code ASC"
)
public class AmRookieDetail extends DataEntity<AmRookieDetail> {
	
	private static final long serialVersionUID = 1L;
	private AmRookie documentCode;		// 单据编号 父类
	private String itemCode;		// 商品编码
	private String itemName;		// 商品名称
	private Long planqty;		// 计划数量
	private Long actualqty;		// 实际数量
	private String batchCode;		// 批次号
	private String orderlineCode;		// 单据行号
	private String bomVer;		// Bom版本
	private String warehouseCode;		// 仓库
	private String detailCode;		// 明细编码
	
	public AmRookieDetail() {
		this(null);
	}


	public AmRookieDetail(AmRookie documentCode){
		this.documentCode = documentCode;
	}
	
	@NotBlank(message="单据编号不能为空")
	@Length(min=0, max=12, message="单据编号长度不能超过 12 个字符")
	public AmRookie getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(AmRookie documentCode) {
		this.documentCode = documentCode;
	}
	
	@Length(min=0, max=50, message="商品编码长度不能超过 50 个字符")
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	@Length(min=0, max=100, message="商品名称长度不能超过 100 个字符")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public Long getPlanqty() {
		return planqty;
	}

	public void setPlanqty(Long planqty) {
		this.planqty = planqty;
	}
	
	public Long getActualqty() {
		return actualqty;
	}

	public void setActualqty(Long actualqty) {
		this.actualqty = actualqty;
	}
	
	@Length(min=0, max=50, message="批次号长度不能超过 50 个字符")
	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	
	@Length(min=0, max=50, message="单据行号长度不能超过 50 个字符")
	public String getOrderlineCode() {
		return orderlineCode;
	}

	public void setOrderlineCode(String orderlineCode) {
		this.orderlineCode = orderlineCode;
	}
	
	@Length(min=0, max=50, message="Bom版本长度不能超过 50 个字符")
	public String getBomVer() {
		return bomVer;
	}

	public void setBomVer(String bomVer) {
		this.bomVer = bomVer;
	}
	
	@Length(min=0, max=50, message="仓库长度不能超过 50 个字符")
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	
	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	
}