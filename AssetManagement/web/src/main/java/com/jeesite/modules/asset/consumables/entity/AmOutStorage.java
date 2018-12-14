/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.entity;

import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeesite.modules.asset.staff.entity.AmStaff;
import com.jeesite.modules.asset.warehouse.entity.AmWarehouse;
import com.jeesite.modules.sys.entity.Office;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 耗材出库表Entity
 * @author czy
 * @version 2018-05-07
 */

@Table(name="${_prefix}am_outstorage", alias="a", columns={
		@Column(name="outstorage_code", attrName="outstorageCode", label="出库单号", queryType=QueryType.LIKE,isPK=true),
		@Column(name="out_date", attrName="outDate", label="出库日期"),
		@Column(name="bill_type", attrName="billType", label="单据类型"),
		@Column(name="warehouse_code", attrName="warehouseCode", label="仓库编码", queryType=QueryType.LIKE),
		@Column(name="office_code", attrName="officeCode", label="领用部门"),
		@Column(name="staff_code", attrName="staffCode", label="领用人", queryType=QueryType.LIKE),
		@Column(name="bill_status", attrName="billStatus", label="单据状态"),
		@Column(includeEntity=DataEntity.class),
}, joinTable = {
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=AmStaff.class, alias="c",
				on="c.staff_code = a.staff_code", attrName="amStaff",
				columns={@Column(includeEntity=AmStaff.class),}),
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=AmWarehouse.class, alias="d",
				on="d.warehouse_code = a.warehouse_code", attrName="amWarehouse",
				columns={@Column(name="warehouse_code", label="仓库编码"),
						@Column(name="warehouse_name", label="仓库名称")}),
		@JoinTable(type=JoinTable.Type.LEFT_JOIN, entity=Office.class, alias="e",
				on="e.office_code = a.office_code", attrName="office",
				columns={@Column(name="office_code", label="部门编码"),
						@Column(name="office_name", label="部门名称")}),
}, orderBy="a.create_date DESC"
)
public class AmOutStorage extends DataEntity<AmOutStorage> {
	
	private static final long serialVersionUID = 1L;
	private String outstorageCode;		// 出库单号
	private Date outDate;		// 出库日期
	private String billType;		// 单据类型
	private String warehouseCode;		// 仓库编码
	private String billStatus;		// 单据状态
	private String officeCode;		// 领用部门
	private String staffCode;		// 领用人
	private List<AmOutStorageDetails> amOutStorageDetailsList = ListUtils.newArrayList();		// 子表列表
	private AmStaff amStaff;
	private AmWarehouse amWarehouse;
	private Office office;
	private AmOutStorageDetails outStorageDetails;
	private String statusLabel;  // 单据状态 页面显示用
	private String typeLabel;    //单据类型 页面显示用
	private String param;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getTypeLabel() {
		return typeLabel;
	}

	public void setTypeLabel(String typeLabel) {
		this.typeLabel = typeLabel;
	}

	public String getStatusLabel() {
		return statusLabel;
	}

	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel;
	}

	public AmOutStorageDetails getOutStorageDetails() {
		return outStorageDetails;
	}

	public void setOutStorageDetails(AmOutStorageDetails outStorageDetails) {
		this.outStorageDetails = outStorageDetails;
	}

	public AmStaff getAmStaff() {
		return amStaff;
	}

	public void setAmStaff(AmStaff amStaff) {
		this.amStaff = amStaff;
	}

	public AmWarehouse getAmWarehouse() {
		return amWarehouse;
	}

	public void setAmWarehouse(AmWarehouse amWarehouse) {
		this.amWarehouse = amWarehouse;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public AmOutStorage() {
		this(null);
	}

	public AmOutStorage(String id){
		super(id);
	}
	
	public String getOutstorageCode() {
		return outstorageCode;
	}

	public void setOutstorageCode(String outstorageCode) {
		this.outstorageCode = outstorageCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="出库日期不能为空")
	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
	
	@NotBlank(message="单据类型不能为空")
	@Length(min=0, max=1, message="单据类型长度不能超过 1 个字符")
	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
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
	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	
	@NotBlank(message="领用部门不能为空")
	@Length(min=0, max=64, message="领用部门长度不能超过 64 个字符")
	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	
	@NotBlank(message="领用人不能为空")
	@Length(min=0, max=64, message="领用人长度不能超过 64 个字符")
	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	
	public List<AmOutStorageDetails> getAmOutStorageDetailsList() {
		return amOutStorageDetailsList;
	}

	public void setAmOutStorageDetailsList(List<AmOutStorageDetails> amOutStorageDetailsList) {
		this.amOutStorageDetailsList = amOutStorageDetailsList;
	}
	
}