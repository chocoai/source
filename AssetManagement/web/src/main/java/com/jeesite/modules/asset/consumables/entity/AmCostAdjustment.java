/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.entity;

import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 耗材成本调整单Entity
 * @author dwh
 * @version 2018-05-31
 */
@Table(name="${_prefix}am_cost_adjustment", alias="a", columns={
		@Column(name="document_code", attrName="documentCode", label="单据单号", isPK=true, queryType=QueryType.LIKE),
		@Column(name="adjustment_data", attrName="adjustmentData", label="单据日期"),
		@Column(name="document_type", attrName="documentType", label="单据类型"),
		@Column(name="warehouse_code", attrName="warehouseCode", label="仓库编码"),
		@Column(name="warehouse_name", attrName="warehouseName", label="仓库名称"),
		@Column(name="document_status", attrName="documentStatus", label="单据状态"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class AmCostAdjustment extends DataEntity<AmCostAdjustment> {
	
	private static final long serialVersionUID = 1L;
	private String documentCode;		// 单据单号
	private Date adjustmentData;		// 单据日期
	private String documentType;		// 单据类型
	private String warehouseCode;		// 仓库编码
	private String warehouseName;		// 仓库名称
	private String documentStatus;		// 单据状态
	private List<AmAdjustDetail> amAdjustDetailList = ListUtils.newArrayList();		// 子表列表
	private AmAdjustDetail amAdjustDetail;

	public AmAdjustDetail getAmAdjustDetail() {
		return amAdjustDetail;
	}

	public void setAmAdjustDetail(AmAdjustDetail amAdjustDetail) {
		this.amAdjustDetail = amAdjustDetail;
	}

	public AmCostAdjustment() {
		this(null);
	}

	public AmCostAdjustment(String id){
		super(id);
	}
	
	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	

	@NotNull(message="单据日期不能为空")
	@JsonFormat(pattern = "yyyy-MM-dd ")
	public Date getAdjustmentData() {
		return adjustmentData;
	}
	@JsonFormat(pattern = "yyyy-MM-dd ")
	public void setAdjustmentData(Date adjustmentData) {
		this.adjustmentData = adjustmentData;
	}
	
	@NotBlank(message="单据类型不能为空")
	@Length(min=0, max=10, message="单据类型长度不能超过 10 个字符")
	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	@NotBlank(message="仓库编码不能为空")
	@Length(min=0, max=64, message="仓库编码长度不能超过 64 个字符")
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	
	@Length(min=0, max=100, message="仓库名称长度不能超过 100 个字符")
	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	
	@NotBlank(message="单据状态不能为空")
	@Length(min=0, max=1, message="单据状态长度不能超过 1 个字符")
	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	
	public List<AmAdjustDetail> getAmAdjustDetailList() {
		return amAdjustDetailList;
	}

	public void setAmAdjustDetailList(List<AmAdjustDetail> amAdjustDetailList) {
		this.amAdjustDetailList = amAdjustDetailList;
	}
	
}