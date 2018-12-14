/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amspecimen.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 构建样品进度Entity
 * @author mclaran
 * @version 2018-06-29
 */
@Table(name="${_prefix}am_specimen_schedule", alias="a", columns={
		@Column(name="code", attrName="code", label="code", isPK=true),
		@Column(name="dispose", attrName="dispose", label="处理人"),
		@Column(name="date", attrName="date", label="处理时间"),
		@Column(name="node", attrName="node", label="处理节点"),
		@Column(name="product_code", attrName="productCode", label="产品明细code"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="start_date", attrName="startDate", label="开始时间"),
		@Column(name="estimated_days", attrName="estimatedDays", label="预计天数"),
		@Column(name="specimen_code", attrName="specimenCode.specimenCode", label="样品进度code"),
	}, orderBy="a.create_date ASC"
)
public class AmSpecimenSchedule extends DataEntity<AmSpecimenSchedule> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// code
	private String dispose;		// 处理人
	private String date;		// 完成时间
	private String node;		// 处理节点
	private AmSpecimen specimenCode;		// 样品进度code 父类
	private String productCode;
	private  String startDate;
	private Long estimatedDays;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getEstimatedDays() {
		return estimatedDays;
	}

	public void setEstimatedDays(Long estimatedDays) {
		this.estimatedDays = estimatedDays;
	}

	public AmSpecimenSchedule() {
		this(null);
	}


	public AmSpecimenSchedule(AmSpecimen specimenCode){
		this.specimenCode = specimenCode;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Length(min=0, max=32, message="处理人长度不能超过 32 个字符")
	public String getDispose() {
		return dispose;
	}

	public void setDispose(String dispose) {
		this.dispose = dispose;
	}
	
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	public Date getDate() {
//		return date;
//	}
//
//	public void setDate(Date date) {
//		this.date = date;
//	}
//
	@Length(min=0, max=64, message="处理节点长度不能超过 64 个字符")
	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}
	
	@Length(min=0, max=32, message="样品进度code长度不能超过 32 个字符")
	public AmSpecimen getSpecimenCode() {
		return specimenCode;
	}

	public void setSpecimenCode(AmSpecimen specimenCode) {
		this.specimenCode = specimenCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
}