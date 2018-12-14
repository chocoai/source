/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amspecimen.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * 构建样品进度Entity
 * @author dwh
 * @version 2018-07-11
 */
@Table(name="${_prefix}am_specimen_record", alias="a", columns={
		@Column(name="record_code", attrName="recordCode", label="编号", isPK=true),
		@Column(name="operator", attrName="operator", label="操作人"),
		@Column(name="operator_time", attrName="operatorTime", label="时间"),
		@Column(name="remark", attrName="remark", label="操作人"),
		@Column(name="specimen_code", attrName="specimenCode.specimenCode", label="单据编号"),
	}, orderBy="a.record_code ASC"
)
public class AmSpecimenRecord extends DataEntity<AmSpecimenRecord> {
	
	private static final long serialVersionUID = 1L;
	private String recordCode;		// 编号
	private String operator;		// 操作人
	private Date operatorTime;		// 时间
	private String remark;		// 操作人
	private AmSpecimen specimenCode;		// 单据编号 父类
	
	public AmSpecimenRecord() {
		this(null);
	}


	public AmSpecimenRecord(AmSpecimen specimenCode){
		this.specimenCode = specimenCode;
	}
	
	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}
	
	@Length(min=0, max=100, message="操作人长度不能超过 100 个字符")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}
	
	@Length(min=0, max=1000, message="操作人长度不能超过 1000 个字符")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@NotBlank(message="单据编号不能为空")
	@Length(min=0, max=100, message="单据编号长度不能超过 100 个字符")
	public AmSpecimen getSpecimenCode() {
		return specimenCode;
	}

	public void setSpecimenCode(AmSpecimen specimenCode) {
		this.specimenCode = specimenCode;
	}
	
}