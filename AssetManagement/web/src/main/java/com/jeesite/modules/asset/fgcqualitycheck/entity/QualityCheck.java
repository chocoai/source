/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fgcqualitycheck.entity;

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
 * @author len
 * @version 2018-08-18
 */
@Table(name="${_prefix}fgc_quality_check", alias="a", columns={
		@Column(name="fid", attrName="fid", label="质检单Id", isPK=true),
		@Column(name="billno", attrName="billno", label="单据编号",queryType = QueryType.LIKE),
		@Column(name="document_status", attrName="documentStatus", label="单据状态",queryType = QueryType.LIKE),
		@Column(name="quality_time", attrName="qualityTime", label="质检时间", comment="质检时间(用于数据同步)"),
		@Column(name="qc_group_name", attrName="qcGroupName", label="质检组", queryType=QueryType.LIKE),
		@Column(name="purchasing_group_name", attrName="purchasingGroupName", label="采购组", queryType=QueryType.LIKE),
		@Column(name="supplier_name", attrName="supplierName", label="供应商", queryType=QueryType.LIKE),
		@Column(name="reservation_time", attrName="reservationTime", label="预约时间"),
		@Column(name="reservation_time_type", attrName="reservationTimeType", label="预约时间类型"),
		@Column(name="purchaser_name", attrName="purchaserName", label="采购员", queryType=QueryType.LIKE),
		@Column(name="quality_inspector_name", attrName="qualityInspectorName", label="质检员", queryType=QueryType.LIKE),
		@Column(name="create_time", attrName="createTime", label="创建时间"),
		@Column(name="creator_id", attrName="creatorId", label="创建用户Id"),
		@Column(name="creator_name", attrName="creatorName", label="创建用户", queryType=QueryType.LIKE),
		@Column(name="modify_time", attrName="modifyTime", label="修改时间"),
		@Column(name="modifier_id", attrName="modifierId", label="修改用户Id"),
		@Column(name="modifier_name", attrName="modifierName", label="修改用户", queryType=QueryType.LIKE),
}, orderBy="a.fid DESC"
)
public class QualityCheck extends DataEntity<QualityCheck> {

	private static final long serialVersionUID = 1L;
	private String fid;		// 质检单Id
	private String billno;		// 单据编号
	private String documentStatus;		// 单据状态
	private String qualityTime;		// 质检时间(用于数据同步)
	private String qcGroupName;		// 质检组
	private String purchasingGroupName;		// 采购组
	private String supplierName;		// 供应商
	private String reservationTime;		// 预约时间
	private String reservationTimeType;		// 预约时间类型
	private String purchaserName;		// 采购员
	private String qualityInspectorName;		// 质检员
	private Date createTime;		// 创建时间
	private String creatorId;		// 创建用户Id
	private String creatorName;		// 创建用户
	private Date modifyTime;		// 修改时间
	private String modifierId;		// 修改用户Id
	private String modifierName;		// 修改用户
	private String inputValue;
	private String userName;
	private String isCheck;
	private Date endDate;			// [创建时间]在当前时间往前30天内
	private String billStatus;		// 单据状态 用于列表接口已质检时
	private Date nowDate;			// 当前时间

	public Date getNowDate() {
		return nowDate;
	}

	public void setNowDate(Date nowDate) {
		this.nowDate = nowDate;
	}

	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}

	private List<QualityCheckDetails> qualityCheckDetailsList = ListUtils.newArrayList();		// 子表列表

	public QualityCheck() {
		this(null);
	}

	public QualityCheck(String id){
		super(id);
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	@NotBlank(message="单据编号不能为空")
	@Length(min=0, max=64, message="单据编号长度不能超过 64 个字符")
	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	@Length(min=0, max=10, message="单据状态长度不能超过 10 个字符")
	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getQualityTime() {
		return qualityTime;
	}

	public void setQualityTime(String qualityTime) {
		this.qualityTime = qualityTime;
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

	@Length(min=0, max=255, message="供应商长度不能超过 255 个字符")
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getReservationTime() {
		return reservationTime;
	}

	public void setReservationTime(String reservationTime) {
		this.reservationTime = reservationTime;
	}

	@Length(min=0, max=10, message="预约时间类型长度不能超过 10 个字符")
	public String getReservationTimeType() {
		return reservationTimeType;
	}

	public void setReservationTimeType(String reservationTimeType) {
		this.reservationTimeType = reservationTimeType;
	}

	@Length(min=0, max=255, message="采购员长度不能超过 255 个字符")
	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	@Length(min=0, max=255, message="质检员长度不能超过 255 个字符")
	public String getQualityInspectorName() {
		return qualityInspectorName;
	}

	public void setQualityInspectorName(String qualityInspectorName) {
		this.qualityInspectorName = qualityInspectorName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Length(min=0, max=64, message="创建用户Id长度不能超过 64 个字符")
	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

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

	@Length(min=0, max=64, message="修改用户Id长度不能超过 64 个字符")
	public String getModifierId() {
		return modifierId;
	}

	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}

	@Length(min=0, max=255, message="修改用户长度不能超过 255 个字符")
	public String getModifierName() {
		return modifierName;
	}

	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}

	public List<QualityCheckDetails> getQualityCheckDetailsList() {
		return qualityCheckDetailsList;
	}

	public void setQualityCheckDetailsList(List<QualityCheckDetails> qualityCheckDetailsList) {
		this.qualityCheckDetailsList = qualityCheckDetailsList;
	}

	public Date getDate_gte() {
		return sqlMap.getWhere().getValue("reservation_time", QueryType.GTE);
	}

	public void setDate_gte(Date reservationTime) {
		sqlMap.getWhere().and("reservation_time", QueryType.GTE, reservationTime);
	}
	public Date getDate_lte() {
		return sqlMap.getWhere().getValue("reservation_time", QueryType.LTE);
	}

	public void setDate_lte(Date reservationTime) {
		sqlMap.getWhere().and("reservation_time", QueryType.LTE, reservationTime);
	}

}