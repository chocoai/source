/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 分销订单Entity
 * @author len
 * @version 2019-01-07
 */
@Table(name="distr_order", alias="a", columns={
		@Column(name="document_code", attrName="documentCode", label="单号", isPK=true),
		@Column(name="document_type", attrName="documentType", label="订单类型"),
		@Column(name="document_status", attrName="documentStatus", label="订单状态"),
		@Column(name="customer_name", attrName="customerName", label="客户姓名", queryType=QueryType.LIKE),
		@Column(name="mobile_phone", attrName="mobilePhone", label="移动电话", queryType=QueryType.LIKE),
		@Column(name="province", attrName="province", label="省"),
		@Column(name="city", attrName="city", label="市"),
		@Column(name="region", attrName="region", label="区"),
		@Column(name="detailed_address", attrName="detailedAddress", label="详细地址"),
		@Column(name="distribution", attrName="distribution", label="配送方式"),
		@Column(name="pay_type", attrName="payType", label="支付方式"),
		@Column(name="total_price", attrName="totalPrice", label="商品总额"),
		@Column(name="logistics_fee", attrName="logisticsFee", label="物流费"),
		@Column(name="total_fee", attrName="totalFee", label="合计应收"),
		@Column(name="create_by", attrName="createBy", label="创建人"),
		@Column(name="create_code", attrName="createCode", label="创建人账号"),
		@Column(name="create_time", attrName="createTime", label="创建时间"),
		@Column(name="submit_code", attrName="submitCode", label="提交人账号"),
		@Column(name="submit_by", attrName="submitBy", label="提交人"),
		@Column(name="submit_date", attrName="submitDate", label="提交时间"),
		@Column(name="confirm_code", attrName="confirmCode", label="确认人账号"),
		@Column(name="confirm_by", attrName="confirmBy", label="确认人"),
		@Column(name="confirm_date", attrName="confirmDate", label="确认时间"),
		@Column(name="office_code", attrName="officeCode", label="部门编码"),
		@Column(name="remarks", attrName="remarks", label="备注"),
		@Column(name="customer_source", attrName="customerSource", label="客户来源"),
	}, orderBy="a.document_code DESC"
)
public class DistrOrder extends DataEntity<DistrOrder> {
	
	private static final long serialVersionUID = 1L;
	private String documentCode;		// 单号
	private String documentType;		// 订单类型
	private String documentStatus;		// 订单状态
	private String customerName;		// 客户姓名
	private String mobilePhone;		// 移动电话
	private String province;		// 省
	private String city;		// 市
	private String region;		// 区
	private String detailedAddress;		// 详细地址
	private String distribution;		// 配送方式
	private String payType;		// 支付方式
	private Double totalPrice;		// 商品总额
	private Double logisticsFee;		// 物流费
	private Double totalFee;		// 合计应收
	private String createCode;		// 创建人账号
	private Date createTime;		// 创建时间
	private String submitCode;		// 提交人账号
	private String submitBy;		// 提交人
	private Date submitDate;		// 提交时间
	private String confirmCode;		// 确认人账号
	private String confirmBy;		// 确认人
	private Date confirmDate;		// 确认时间
	private String officeCode;		// 部门编码
	private String createBy;		// 创建人
	private String remarks;			// 备注
	private List<DistrOrderDetail> distrOrderDetailList = ListUtils.newArrayList();		// 子表列表
	private String officeCodes;
	private String customerSource;	// 客户来源

	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	public String getOfficeCodes() {
		return officeCodes;
	}

	public void setOfficeCodes(String officeCodes) {
		this.officeCodes = officeCodes;
	}

	@Override
	public String getRemarks() {
		return remarks;
	}

	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String getCreateBy() {
		return createBy;
	}

	@Override
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public DistrOrder() {
		this(null);
	}

	public DistrOrder(String id){
		super(id);
	}
	
	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	
	@Length(min=0, max=20, message="订单类型长度不能超过 20 个字符")
	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	@Length(min=0, max=10, message="订单状态长度不能超过 10 个字符")
	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	
	@NotBlank(message="客户姓名不能为空")
	@Length(min=0, max=100, message="客户姓名长度不能超过 100 个字符")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Length(min=0, max=20, message="移动电话长度不能超过 20 个字符")
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	@Length(min=0, max=50, message="省长度不能超过 50 个字符")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=50, message="市长度不能超过 50 个字符")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=50, message="区长度不能超过 50 个字符")
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	@NotBlank(message="详细地址不能为空")
	@Length(min=0, max=1000, message="详细地址长度不能超过 1000 个字符")
	public String getDetailedAddress() {
		return detailedAddress;
	}

	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}
	
	@Length(min=0, max=50, message="配送方式长度不能超过 50 个字符")
	public String getDistribution() {
		return distribution;
	}

	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}
	
	@Length(min=0, max=50, message="支付方式长度不能超过 50 个字符")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public Double getLogisticsFee() {
		return logisticsFee;
	}

	public void setLogisticsFee(Double logisticsFee) {
		this.logisticsFee = logisticsFee;
	}
	
	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	
	@Length(min=0, max=64, message="创建人账号长度不能超过 64 个字符")
	public String getCreateCode() {
		return createCode;
	}

	public void setCreateCode(String createCode) {
		this.createCode = createCode;
	}

	
	@Length(min=0, max=64, message="提交人账号长度不能超过 64 个字符")
	public String getSubmitCode() {
		return submitCode;
	}

	public void setSubmitCode(String submitCode) {
		this.submitCode = submitCode;
	}
	
	@Length(min=0, max=64, message="提交人长度不能超过 64 个字符")
	public String getSubmitBy() {
		return submitBy;
	}

	public void setSubmitBy(String submitBy) {
		this.submitBy = submitBy;
	}

	
	@Length(min=0, max=64, message="确认人账号长度不能超过 64 个字符")
	public String getConfirmCode() {
		return confirmCode;
	}

	public void setConfirmCode(String confirmCode) {
		this.confirmCode = confirmCode;
	}
	
	@Length(min=0, max=64, message="确认人长度不能超过 64 个字符")
	public String getConfirmBy() {
		return confirmBy;
	}

	public void setConfirmBy(String confirmBy) {
		this.confirmBy = confirmBy;
	}


	@Length(min=0, max=64, message="部门编码长度不能超过 64 个字符")
	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	
	public List<DistrOrderDetail> getDistrOrderDetailList() {
		return distrOrderDetailList;
	}

	public void setDistrOrderDetailList(List<DistrOrderDetail> distrOrderDetailList) {
		this.distrOrderDetailList = distrOrderDetailList;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
}