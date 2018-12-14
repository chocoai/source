/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.entity;

import java.util.Date;
import java.util.List;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelFields;

/**
 * 订单业绩统计Entity
 * @author len
 * @version 2018-12-13
 */
@Table(name="${_prefix}order_statistics", alias="a", columns={
		@Column(name="order_date", attrName="orderDate", label="日期"),
		@Column(name="deal_order_num", attrName="dealOrderNum", label="成交订单数"),
		@Column(name="payment_amount", attrName="paymentAmount", label="支付金额"),
		@Column(name="deal_customer_num", attrName="dealCustomerNum", label="成交客户个数"),
		@Column(name="customer_price", attrName="customerPrice", label="客单价"),
		@Column(name="report_code", attrName="reportCode", label="report_code", isPK=true),
	}, orderBy="a.report_code DESC"
)
public class StatisticalReport extends DataEntity<StatisticalReport> {
	@Valid
	@ExcelFields({
			@ExcelField(title="日期", attrName="orderTime", align=ExcelField.Align.CENTER, sort=1),
			@ExcelField(title="成交订单数", attrName="dealOrderNum", align=ExcelField.Align.CENTER, sort=2),
			@ExcelField(title="支付金额", attrName="paymentAmount", align = ExcelField.Align.CENTER, sort=3),
			@ExcelField(title="成交客户个数", attrName="dealCustomerNum",align = ExcelField.Align.CENTER, sort=4),
			@ExcelField(title="客单价", attrName="customerPrice",align = ExcelField.Align.CENTER, sort=5),
	})
	private static final long serialVersionUID = 1L;
	private Date orderDate;		// 日期
	private Integer dealOrderNum;		// 成交订单数
	private Double paymentAmount;		// 支付金额
	private Integer dealCustomerNum;		// 成交客户个数
	private Double customerPrice;		// 客单价
	private String reportCode;		// report_code
	private Date date_gte;		// 开始时间
	private Date date_lte;		// 结束时间
	private List<String> dateList = ListUtils.newArrayList();
	private String officeCode;	// 部门编号
	private String dimensionality;	// 统计维度
	private String orderTime;		// 页面显示用

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getDimensionality() {
		return dimensionality;
	}

	public void setDimensionality(String dimensionality) {
		this.dimensionality = dimensionality;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public List<String> getDateList() {
		return dateList;
	}

	public void setDateList(List<String> dateList) {
		this.dateList = dateList;
	}

	public StatisticalReport() {
		this(null);
	}

	public StatisticalReport(String id){
		super(id);
	}
	
//	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="日期不能为空")
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	public Integer getDealOrderNum() {
		return dealOrderNum;
	}

	public void setDealOrderNum(Integer dealOrderNum) {
		this.dealOrderNum = dealOrderNum;
	}
	
	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	public Integer getDealCustomerNum() {
		return dealCustomerNum;
	}

	public void setDealCustomerNum(Integer dealCustomerNum) {
		this.dealCustomerNum = dealCustomerNum;
	}
	
	public Double getCustomerPrice() {
		return customerPrice;
	}

	public void setCustomerPrice(Double customerPrice) {
		this.customerPrice = customerPrice;
	}
	
	public String getReportCode() {
		return reportCode;
	}

	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
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