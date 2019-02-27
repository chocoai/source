/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.entity;

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
 * 梵导购操作统计报表Entity
 * @author Albert
 * @version 2019-01-11
 */
@Table(name="order_report_sales_operate", alias="a", columns={
		@Column(name="report_id", attrName="reportId", label="主键", isPK=true),
		@Column(name="store_name", attrName="storeName", label="门店", queryType=QueryType.LIKE),
		@Column(name="operation_user_name", attrName="operationUserName", label="操作用户", queryType=QueryType.LIKE),
		@Column(name="operation_time", attrName="operationTime", label="操作时间"),
		@Column(name="browser_name", attrName="browserName", label="浏览器名", queryType=QueryType.LIKE),
		@Column(name="response_time", attrName="responseTime", label="响应时长", comment="响应时长(毫秒)"),
	}, orderBy="a.report_id DESC"
)
public class OrderReportSalesOperate extends DataEntity<OrderReportSalesOperate> {
	
	private static final long serialVersionUID = 1L;
	private String reportId;		// 主键
	private String storeName;		// 门店
	private String operationUserName;		// 操作用户
	private Date operationTime;		// 操作时间
	private String browserName;		// 浏览器名
	private Long responseTime;		// 响应时长(毫秒)
	private Date date_gte;		// 开始时间
	private Date date_lte;		// 结束时间
	
	public OrderReportSalesOperate() {
		this(null);
	}

	public OrderReportSalesOperate(String id){
		super(id);
	}
	
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	
	@Length(min=0, max=255, message="门店长度不能超过 255 个字符")
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	@Length(min=0, max=100, message="操作用户长度不能超过 100 个字符")
	public String getOperationUserName() {
		return operationUserName;
	}

	public void setOperationUserName(String operationUserName) {
		this.operationUserName = operationUserName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	
	@Length(min=0, max=100, message="浏览器名长度不能超过 100 个字符")
	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}
	
	public Long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Long responseTime) {
		this.responseTime = responseTime;
	}

	public Date getDate_gte() {
		return date_gte;
	}

	public void setDate_gte(Date date_gte) {
		this.date_gte = date_gte;
	}

	public Date getDate_lte() {
		return date_lte;
	}

	public void setDate_lte(Date date_lte) {
		this.date_lte = date_lte;
	}
}