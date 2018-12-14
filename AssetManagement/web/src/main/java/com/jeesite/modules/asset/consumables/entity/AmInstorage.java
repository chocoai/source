/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.entity;

import com.jeesite.modules.asset.amlocation.entity.AmLocation;
import com.jeesite.modules.asset.warehouse.entity.AmWarehouse;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 入库表Entity
 * @author dwh
 * @version 2018-05-03
 */
@Table(name="${_prefix}am_instorage", alias="a", columns={
		@Column(name="instorage_code", attrName="instorageCode", label="入库单号", isPK=true, queryType=QueryType.LIKE),
		@Column(name="document_type", attrName="documentType", label="单据类型"),
		@Column(name="warehouse_code", attrName="warehouseCode", label="仓库编码"),
		@Column(name="document_status", attrName="documentStatus", label="单据状态"),
		@Column(name="manager", attrName="manager", label="经办人" ,queryType=QueryType.LIKE),
		@Column(name="supplier", attrName="supplier", label="供应商" ,queryType=QueryType.LIKE),
		@Column(name="incoming_date", attrName="incomingDate", label="入库日期"),
		@Column(includeEntity=DataEntity.class),
	}, joinTable={
		@JoinTable(type=Type.LEFT_JOIN, entity=AmWarehouse.class, alias="c",
				on="a.warehouse_code=c.warehouse_code ",
				columns={	@Column(name="warehouse_code", label="仓库编码", isPK=true),
						   @Column(name="warehouse_name", label="仓库名字", isQuery=false),})
}, orderBy="a.create_date DESC"
)
public class AmInstorage extends DataEntity<AmInstorage> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String instorageCode;		// 入库单号
	private String documentType;		// 单据类型
	private String warehouseCode;		// 仓库编码
	private String documentStatus;		// 单据状态
	private String manager;		// 经办人
	private String supplier;		// 供应商
	private Date incomingDate;		// 入库日期
	private List<AmInstorageDetails> amInstorageDetailsList = ListUtils.newArrayList();		// 子表列表
	private AmInstorageDetails amInstorageDetails;
	private AmWarehouse amWarehouse;
	private String documentStatusName;            //页面显示的状态
//	private String measureUnit;            //表单显示的状态
	private String param;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getDocumentStatusName() {
		return documentStatusName;
	}

	public void setDocumentStatusName(String documentStatusName) {
		this.documentStatusName = documentStatusName;
	}

	public AmWarehouse getAmWarehouse() {
		return amWarehouse;
	}

	public void setAmWarehouse(AmWarehouse amWarehouse) {
		this.amWarehouse = amWarehouse;
	}

	public AmInstorageDetails getAmInstorageDetails() {
		return amInstorageDetails;
	}

	public void setAmInstorageDetails(AmInstorageDetails amInstorageDetails) {
		this.amInstorageDetails = amInstorageDetails;
	}

	public AmInstorage() {
		this(null);
	}

	public AmInstorage(String id){
		super(id);
	}
	
	public String getInstorageCode() {
		return instorageCode;
	}

	public void setInstorageCode(String instorageCode) {
		this.instorageCode = instorageCode;
	}
	
	@NotBlank(message="单据类型不能为空")
	@Length(min=0, max=1, message="单据类型长度不能超过 1 个字符")
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
	
	@NotBlank(message="单据状态不能为空")
	@Length(min=0, max=1, message="单据状态长度不能超过 1 个字符")
	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	
	@Length(min=0, max=100, message="经办人长度不能超过 100 个字符")
	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}
	
	@Length(min=0, max=100, message="供应商长度不能超过 100 个字符")
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="入库日期不能为空")
	public Date getIncomingDate() {
		return incomingDate;
	}

	public void setIncomingDate(Date incomingDate) {
		this.incomingDate = incomingDate;
	}
	
	public List<AmInstorageDetails> getAmInstorageDetailsList() {
		return amInstorageDetailsList;
	}

	public void setAmInstorageDetailsList(List<AmInstorageDetails> amInstorageDetailsList) {
		this.amInstorageDetailsList = amInstorageDetailsList;
	}
	
}