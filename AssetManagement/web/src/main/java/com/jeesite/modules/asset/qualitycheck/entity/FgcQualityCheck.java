/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.qualitycheck.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 质检单Entity
 * @author Albert
 * @version 2018-07-25
 */
@Table(name="${_prefix}fgc_quality_check", alias="a", columns={
		@Column(name="id", attrName="id", label="质检单Id", isPK=true),
		@Column(name="billno", attrName="billno", label="单据编号"),
		@Column(name="document_status", attrName="documentStatus", label="单据状态"),
		@Column(name="quality_time", attrName="qualityTime", label="质检时间"),
		@Column(name="qc_group_name", attrName="qcGroupName", label="质检组", queryType=QueryType.LIKE),
		@Column(name="purchasing_group_name", attrName="purchasingGroupName", label="采购组", queryType=QueryType.LIKE),
		@Column(name="supplier_name", attrName="supplierName", label="供应商", queryType=QueryType.LIKE),
		@Column(name="reservation_time", attrName="reservationTime", label="预约时间"),
		@Column(name="reservation_time_type", attrName="reservationTimeType", label="预约时间类型"),
		@Column(name="purchaser_name", attrName="purchaserName", label="采购员", queryType=QueryType.LIKE),
		@Column(name="quality_inspector_name", attrName="qualityInspectorName", label="质检员"),
		@Column(name="create_time", attrName="createTime", label="创建时间"),
		@Column(name="creator_id", attrName="creatorId", label="创建用户Id"),
		@Column(name="creator_name", attrName="creatorName", label="创建用户", queryType=QueryType.LIKE),
		@Column(name="modify_time", attrName="modifyTime", label="修改时间"),
		@Column(name="modifier_id", attrName="modifierId", label="修改用户Id"),
		@Column(name="modifier_name", attrName="modifierName", label="修改用户", queryType=QueryType.LIKE),
	}, orderBy="a.billno DESC"
)
public class FgcQualityCheck extends DataEntity<FgcQualityCheck> {
	
	private static final long serialVersionUID = 1L;
	private String id;			//质检单Id
	private String billno;		// 单据编号
	private String documentStatus;		// 单据状态
//	private String billtypeId;		// 单据类型Id
	private String billtypeName;		// 单据类型
//	private String qcGroupId;		// 质检组Id
	private String qcGroupName;		// 质检组
//	private String purchasingGroupId;		// 采购组Id
	private String purchasingGroupName;		// 采购组
	private String supplierId;		// 供应商Id
	private String supplierName;		// 供应商
	private Date reservationTime;		// 预约时间
	private String reservationTimeType;		// 预约时间类型
	private String purchaserId;		// 采购员Id
	private String purchaserName;		// 采购员
//	private String qualityInspectorId;		// 质检员Id
	private String qualityInspectorName;		// 质检员
	private String approvalSatatus;		// 审批状态
	private String nowApprover;		// 当前审批者
	private Date createTime;		// 创建时间
	private String creatorId;		// 创建用户Id
	private String creatorName;		// 创建用户
	private Date modifyTime;		// 修改时间
	private String modifierId;		// 修改用户Id
	private String modifierName;		// 修改用户
	private String writeBillStatus;	// 填单状态
	private String inputValue;		// 梵工厂搜索框值
	private Date qualityTime;		// 质检时间(用于数据同步)

	public Date getQualityTime() {
		return qualityTime;
	}

	public void setQualityTime(Date qualityTime) {
		this.qualityTime = qualityTime;
	}

	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}

	private List<FgcQualityCheckDetails> fgcQualityCheckDetailsList = ListUtils.newArrayList();		// 子表列表
	
	public FgcQualityCheck() {
		this(null);
	}

	public FgcQualityCheck(String id){
		super(id);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}
	
	@NotBlank(message="单据状态不能为空")
	@Length(min=0, max=10, message="单据状态长度不能超过 10 个字符")
	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	

	
	@Length(min=0, max=255, message="质检组长度不能超过 255 个字符")
	public String getQcGroupName() {
		return qcGroupName;
	}

	public void setQcGroupName(String qcGroupName) {
		this.qcGroupName = qcGroupName;
	}

	
	@Length(min=0, max=255, message="采购组长度不能超过 255 个字符")
	public String getPurchasingGroupName() {
		return purchasingGroupName;
	}

	public void setPurchasingGroupName(String purchasingGroupName) {
		this.purchasingGroupName = purchasingGroupName;
	}
	
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	@NotBlank(message="供应商不能为空")
	@Length(min=0, max=255, message="供应商长度不能超过 255 个字符")
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReservationTime() {
		return reservationTime;
	}

	public void setReservationTime(Date reservationTime) {
		this.reservationTime = reservationTime;
	}
	
	@NotBlank(message="预约时间类型不能为空")
	@Length(min=0, max=10, message="预约时间类型长度不能超过 10 个字符")
	public String getReservationTimeType() {
		return reservationTimeType;
	}

	public void setReservationTimeType(String reservationTimeType) {
		this.reservationTimeType = reservationTimeType;
	}
	
	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}
	
	@NotBlank(message="采购员不能为空")
	@Length(min=0, max=255, message="采购员长度不能超过 255 个字符")
	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	
	@NotBlank(message="质检员不能为空")
	@Length(min=0, max=255, message="质检员长度不能超过 255 个字符")
	public String getQualityInspectorName() {
		return qualityInspectorName;
	}

	public void setQualityInspectorName(String qualityInspectorName) {
		this.qualityInspectorName = qualityInspectorName;
	}
	
	@Length(min=0, max=255, message="审批状态长度不能超过 255 个字符")
	public String getApprovalSatatus() {
		return approvalSatatus;
	}

	public void setApprovalSatatus(String approvalSatatus) {
		this.approvalSatatus = approvalSatatus;
	}
	
	@Length(min=0, max=255, message="当前审批者长度不能超过 255 个字符")
	public String getNowApprover() {
		return nowApprover;
	}

	public void setNowApprover(String nowApprover) {
		this.nowApprover = nowApprover;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	
	@NotBlank(message="创建用户不能为空")
	@Length(min=0, max=255, message="创建用户长度不能超过 255 个字符")
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public String getModifierId() {
		return modifierId;
	}

	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}
	
	@NotBlank(message="修改用户不能为空")
	@Length(min=0, max=255, message="修改用户长度不能超过 255 个字符")
	public String getModifierName() {
		return modifierName;
	}

	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}
	
	public List<FgcQualityCheckDetails> getFgcQualityCheckDetailsList() {
		return fgcQualityCheckDetailsList;
	}

	public void setFgcQualityCheckDetailsList(List<FgcQualityCheckDetails> fgcQualityCheckDetailsList) {
		this.fgcQualityCheckDetailsList = fgcQualityCheckDetailsList;
	}

	public String getWriteBillStatus() {
		return writeBillStatus;
	}

	public void setWriteBillStatus(String writeBillStatus) {
		this.writeBillStatus = writeBillStatus;
	}
}