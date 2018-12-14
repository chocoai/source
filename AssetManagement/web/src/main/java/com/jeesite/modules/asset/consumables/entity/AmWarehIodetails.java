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

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 耗材仓库进出明细表Entity
 * @author czy
 * @version 2018-05-24
 */
@Table(name="${_prefix}am_wareh_iodetails", alias="a", columns={
		@Column(name="id", attrName="id", label="编码", isPK=true),
		@Column(name="operation_date", attrName="operationDate", label="操作时间"),
		@Column(name="operation_by", attrName="operationBy", label="操作人"),
		@Column(name="sign", attrName="sign", label="进出标志"),
		@Column(name="bill_code", attrName="billCode", label="单据编号"),
		@Column(name="bill_date", attrName="billDate", label="单据日期"),
		@Column(name="bill_type", attrName="billType", label="单据类型"),
		@Column(name="warehouse_code", attrName="warehouseCode", label="仓库编码"),
		@Column(name="consumables_code", attrName="consumablesCode", label="耗材编码"),
		@Column(name="instorage_count", attrName="instorageCount", label="入库数量"),
		@Column(name="instorage_amount", attrName="instorageAmount", label="入库金额"),
		@Column(name="outstorage_count", attrName="outstorageCount", label="出库数量"),
		@Column(name="outstorage_amount", attrName="outstorageAmount", label="出库金额"),
		@Column(name="price", attrName="price", label="单价"),
	}, orderBy="a.id DESC"
)
public class AmWarehIodetails extends DataEntity<AmWarehIodetails> {
	
	private static final long serialVersionUID = 1L;
	private Date operationDate;		// 操作时间
	private String operationBy;		// 操作人
	private String sign;		// 进出标志
	private String billCode;		// 单据编号
	private Date billDate;		// 单据日期
	private String billType;		// 单据类型
	private String warehouseCode;		// 仓库编码
	private String consumablesCode;		// 耗材编码
	private Long instorageCount;		// 入库数量
	private Double instorageAmount;		// 入库金额
	private Long outstorageCount;		// 出库数量
	private Double outstorageAmount;		// 出库金额
	private Double price;		// 单价
	
	public AmWarehIodetails() {
		this(null);
	}

	public AmWarehIodetails(String id){
		super(id);
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="操作时间不能为空")
	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	
	@NotBlank(message="操作人不能为空")
	@Length(min=0, max=100, message="操作人长度不能超过 100 个字符")
	public String getOperationBy() {
		return operationBy;
	}

	public void setOperationBy(String operationBy) {
		this.operationBy = operationBy;
	}
	
	@NotBlank(message="进出标志不能为空")
	@Length(min=0, max=1, message="进出标志长度不能超过 1 个字符")
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	@NotBlank(message="单据编号不能为空")
	@Length(min=0, max=64, message="单据编号长度不能超过 64 个字符")
	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="单据日期不能为空")
	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
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
	
	@NotBlank(message="耗材编码不能为空")
	@Length(min=0, max=64, message="耗材编码长度不能超过 64 个字符")
	public String getConsumablesCode() {
		return consumablesCode;
	}

	public void setConsumablesCode(String consumablesCode) {
		this.consumablesCode = consumablesCode;
	}
	
	public Long getInstorageCount() {
		return instorageCount;
	}

	public void setInstorageCount(Long instorageCount) {
		this.instorageCount = instorageCount;
	}
	
	public Double getInstorageAmount() {
		return instorageAmount;
	}

	public void setInstorageAmount(Double instorageAmount) {
		this.instorageAmount = instorageAmount;
	}
	
	public Long getOutstorageCount() {
		return outstorageCount;
	}

	public void setOutstorageCount(Long outstorageCount) {
		this.outstorageCount = outstorageCount;
	}
	
	public Double getOutstorageAmount() {
		return outstorageAmount;
	}

	public void setOutstorageAmount(Double outstorageAmount) {
		this.outstorageAmount = outstorageAmount;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}