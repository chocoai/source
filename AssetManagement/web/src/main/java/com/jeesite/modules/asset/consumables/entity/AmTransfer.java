/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.entity;

import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeesite.modules.asset.staff.entity.AmStaff;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 调拨单Entity
 * @author dwh
 * @version 2018-05-21
 */
@Table(name="${_prefix}am_transfer", alias="a", columns={
		@Column(name="documents_code", attrName="documentsCode", label="单据编码", isPK=true,queryType=QueryType.LIKE),
		@Column(name="transfer_date", attrName="transferDate", label="调拨日期"),
		@Column(name="document_type", attrName="documentType", label="单据类型"),
		@Column(name="in_warehouse_code", attrName="inWarehouseCode", label="入库仓库"),
		@Column(name="out_warehouse_code", attrName="outWarehouseCode", label="出库仓库"),
		@Column(name="staff_code", attrName="staffCode", label="处理人",queryType=QueryType.LIKE),
		@Column(name="document_status", attrName="documentStatus", label="单据状态",queryType=QueryType.LIKE),
		@Column(includeEntity=DataEntity.class),
	}, joinTable = {
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=AmStaff.class, alias="b",
				on="b.staff_code = a.staff_code", attrName="amStaff",
				columns={@Column(includeEntity=AmStaff.class),}),
//		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=AmTransferDetails.class, alias="c",
//				on="c.documents_code = a.documents_code", attrName="amTransferDetails",
//				columns={@Column(includeEntity=AmTransferDetails.class),}),

} , orderBy="a.update_date DESC"
)
public class AmTransfer extends DataEntity<AmTransfer> {
	
	private static final long serialVersionUID = 1L;
	private String documentsCode;		// 单据编码
	private Date transferDate;		// 调拨日期
	private String documentType;		// 单据类型
	private String inWarehouseCode;		// 入库仓库
	private String outWarehouseCode;		// 出库仓库
	private String staffCode;		// 处理人
	private String documentStatus;		// 单据状态
	private List<AmTransferDetails> amTransferDetailsList = ListUtils.newArrayList();		// 子表列表
	private AmStaff amStaff;
	private  String documentStatusName;
	private boolean isRoad;
	private  AmTransferDetails amTransferDetails;
	private  String inWarehouseName;
	private  String outWarehouseName;

	public String getInWarehouseName() {
		return inWarehouseName;
	}

	public void setInWarehouseName(String inWarehouseName) {
		this.inWarehouseName = inWarehouseName;
	}

	public String getOutWarehouseName() {
		return outWarehouseName;
	}

	public void setOutWarehouseName(String outWarehouseName) {
		this.outWarehouseName = outWarehouseName;
	}

	public AmTransferDetails getAmTransferDetails() {
		return amTransferDetails;
	}

	public void setAmTransferDetails(AmTransferDetails amTransferDetails) {
		this.amTransferDetails = amTransferDetails;
	}

	public boolean isRoad() {
		return isRoad;
	}

	public void setRoad(boolean road) {
		isRoad = road;
	}

	public AmStaff getAmStaff() {
		return amStaff;
	}

	public void setAmStaff(AmStaff amStaff) {
		this.amStaff = amStaff;
	}

	public String getDocumentStatusName() {
		return documentStatusName;
	}

	public void setDocumentStatusName(String documentStatusName) {
		this.documentStatusName = documentStatusName;
	}

	public AmTransfer() {
		this(null);
	}

	public AmTransfer(String id){
		super(id);
	}
	
	public String getDocumentsCode() {
		return documentsCode;
	}

	public void setDocumentsCode(String documentsCode) {
		this.documentsCode = documentsCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd ")
	@NotNull(message="调拨日期不能为空")
	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}
	
	@NotBlank(message="单据类型不能为空")
	@Length(min=0, max=1, message="单据类型长度不能超过 1 个字符")
	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	@NotBlank(message="入库仓库不能为空")
	@Length(min=0, max=64, message="入库仓库长度不能超过 64 个字符")
	public String getInWarehouseCode() {
		return inWarehouseCode;
	}

	public void setInWarehouseCode(String inWarehouseCode) {
		this.inWarehouseCode = inWarehouseCode;
	}
	
	@NotBlank(message="出库仓库不能为空")
	@Length(min=0, max=64, message="出库仓库长度不能超过 64 个字符")
	public String getOutWarehouseCode() {
		return outWarehouseCode;
	}

	public void setOutWarehouseCode(String outWarehouseCode) {
		this.outWarehouseCode = outWarehouseCode;
	}
	
	@NotBlank(message="处理人不能为空")
	@Length(min=0, max=64, message="处理人长度不能超过 64 个字符")
	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	
	@NotBlank(message="单据状态不能为空")
	@Length(min=0, max=1, message="单据状态长度不能超过 1 个字符")
	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	
	public List<AmTransferDetails> getAmTransferDetailsList() {
		return amTransferDetailsList;
	}

	public void setAmTransferDetailsList(List<AmTransferDetails> amTransferDetailsList) {
		this.amTransferDetailsList = amTransferDetailsList;
	}
	
}