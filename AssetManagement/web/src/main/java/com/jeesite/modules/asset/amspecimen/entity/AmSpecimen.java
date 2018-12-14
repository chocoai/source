/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amspecimen.entity;

import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 构建样品进度Entity
 * @author mclaran
 * @version 2018-06-29
 */
@Table(name="${_prefix}am_specimen", alias="a", columns={
		@Column(name="specimen_code", attrName="specimenCode", label="code", isPK=true,queryType=QueryType.LIKE),
		@Column(name="date", attrName="date", label="单据时间"),
		@Column(name="data_status", attrName="dataStatus", label="单据类型",queryType=QueryType.LIKE),
		@Column(name="factory", attrName="factory", label="制造工厂",queryType=QueryType.LIKE),
		@Column(name="bills_status", attrName="billsStatus", label="单据状态"),
		@Column(name="offer_code", attrName="offerCode", label="工厂编码"),
		@Column(includeEntity=DataEntity.class),
}, orderBy="a.update_date DESC"
)
public class AmSpecimen extends DataEntity<AmSpecimen> {

	private static final long serialVersionUID = 1L;
	private String specimenCode;		// code
	private Date date;		// 单据时间
	private String dataStatus;		// 单据类型
	private String factory;		// 制造工厂
	private List<AmSpecimenRecord> amSpecimenRecordList = ListUtils.newArrayList();		// 子表列表
	private List<AmSpecimenSchedule> amSpecimenScheduleList = ListUtils.newArrayList();		// 子表列表
	private List<AmSpecimenProduct> amSpecimenProductList = ListUtils.newArrayList();		// 子表列表
	private AmSpecimenProduct amSpecimenProduct;
	private String billsStatus;
	private String documentStatusName;
	private String documentTypeName;
	private String offerCode;

	public String getOfferCode() {
		return offerCode;
	}

	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}

	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}

	public String getDocumentStatusName() {
		return documentStatusName;
	}

	public void setDocumentStatusName(String documentStatusName) {
		this.documentStatusName = documentStatusName;
	}

	public AmSpecimen() {
		this(null);
	}

	public AmSpecimen(String specimenCode){
		this.specimenCode = specimenCode;
	}

	public String getSpecimenCode() {
		return specimenCode;
	}

	public void setSpecimenCode(String specimenCode) {
		this.specimenCode = specimenCode;
	}



	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Length(min=0, max=1, message="单据类型长度不能超过 1 个字符")
	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	@Length(min=0, max=32, message="制造工厂长度不能超过 32 个字符")
	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public List<AmSpecimenRecord> getAmSpecimenRecordList() {
		return amSpecimenRecordList;
	}

	public void setAmSpecimenRecordList(List<AmSpecimenRecord> amSpecimenRecordList) {
		this.amSpecimenRecordList = amSpecimenRecordList;
	}

	public List<AmSpecimenSchedule> getAmSpecimenScheduleList() {
		return amSpecimenScheduleList;
	}

	public void setAmSpecimenScheduleList(List<AmSpecimenSchedule> amSpecimenScheduleList) {
		this.amSpecimenScheduleList = amSpecimenScheduleList;
	}

	public List<AmSpecimenProduct> getAmSpecimenProductList() {
		return amSpecimenProductList;
	}

	public void setAmSpecimenProductList(List<AmSpecimenProduct> amSpecimenProductList) {
		this.amSpecimenProductList = amSpecimenProductList;
	}

	public AmSpecimenProduct getAmSpecimenProduct() {
		return amSpecimenProduct;
	}

	public void setAmSpecimenProduct(AmSpecimenProduct amSpecimenProduct) {
		this.amSpecimenProduct = amSpecimenProduct;
	}

	@Length(min=0, max=1, message="单据状态长度不能超过 1 个字符")
	public String getBillsStatus() {
		return billsStatus;
	}

	public void setBillsStatus(String billsStatus) {
		this.billsStatus = billsStatus;
	}
}