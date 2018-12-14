/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.periodstate.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 数据期间表Entity
 * @author dwh
 * @version 2018-05-12
 */
@Table(name="${_prefix}am_period_state", alias="a", columns={
		@Column(name="period_state_code", attrName="periodStateCode", label="数据期间编码", isPK=true),
		@Column(name="data_period", attrName="dataPeriod", label="数据期间"),
		@Column(name="begin_data", attrName="beginData", label="开始日期"),
		@Column(name="end_data", attrName="endData", label="结束日期"),
		@Column(name="period_stutrs", attrName="periodStutrs", label="期间状态"),
	}, orderBy="a.period_state_code DESC"
)
public class AmPeriodState extends DataEntity<AmPeriodState> {
	
	private static final long serialVersionUID = 1L;
	private String periodStateCode;		// 数据期间编码
	private String dataPeriod;		// 数据期间
	private Date beginData;		// 开始日期
	private Date endData;		// 结束日期
	private String periodStutrs;		// 期间状态
	
	public AmPeriodState() {
		this(null);
	}

	public AmPeriodState(String id){
		super(id);
	}
	
	public String getPeriodStateCode() {
		return periodStateCode;
	}

	public void setPeriodStateCode(String periodStateCode) {
		this.periodStateCode = periodStateCode;
	}
	
	@NotBlank(message="数据期间不能为空")
	@Length(min=0, max=1000, message="数据期间长度不能超过 1000 个字符")
	public String getDataPeriod() {
		return dataPeriod;
	}

	public void setDataPeriod(String dataPeriod) {
		this.dataPeriod = dataPeriod;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd ")
	@NotNull(message="开始日期不能为空")
	public Date getBeginData() {
		return beginData;
	}

	public void setBeginData(Date beginData) {
		this.beginData = beginData;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd ")
	@NotNull(message="结束日期不能为空")
	public Date getEndData() {
		return endData;
	}

	public void setEndData(Date endData) {
		this.endData = endData;
	}
	
	@NotBlank(message="期间状态不能为空")
	@Length(min=0, max=1, message="期间状态长度不能超过 1 个字符")
	public String getPeriodStutrs() {
		return periodStutrs;
	}

	public void setPeriodStutrs(String periodStutrs) {
		this.periodStutrs = periodStutrs;
	}
	
}