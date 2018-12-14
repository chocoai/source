/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.supplier.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 供应商资料Entity
 * @author Scarlett
 * @version 2018-07-04
 */
@Table(name="${_prefix}sup_supplier_customers", alias="a", columns={
		@Column(name="customer_code", attrName="customerCode", label="客户编号", isPK=true),
		@Column(name="supplier_code", attrName="supplierCode.supplierCode", label="供应商编号"),
		@Column(name="customer_name", attrName="customerName", label="客户名称", queryType=QueryType.LIKE),
		@Column(name="cooperation_share", attrName="cooperationShare", label="年合作份额", comment="年合作份额(万元)"),
		@Column(name="effective_date", attrName="effectiveDate", label="主要合作产品类型"),
	}, orderBy="a.customer_code ASC"
)
public class SupSupplierCustomers extends DataEntity<SupSupplierCustomers> {

	private static final long serialVersionUID = 1L;
	private String customerCode;        // 客户编号
	private SupSupplier supplierCode;        // 供应商编号 父类
	private String customerName;        // 客户名称
	private Long cooperationShare;        // 年合作份额(万元)



	private String effectiveDate;        // 主要合作产品类型

	public SupSupplierCustomers() {
		this(null);
	}


	public SupSupplierCustomers(SupSupplier supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public SupSupplier getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(SupSupplier supplierCode) {
		this.supplierCode = supplierCode;
	}

	@NotBlank(message = "客户名称不能为空")
	@Length(min = 0, max = 64, message = "客户名称长度不能超过 64 个字符")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@NotNull(message = "年合作份额不能为空")
	public Long getCooperationShare() {
		return cooperationShare;
	}

	public void setCooperationShare(Long cooperationShare) {
		this.cooperationShare = cooperationShare;
	}

	@NotBlank(message="主要合作产品类型不能为空")
	@Length(min=0, max=225, message="主要合作产品类型长度不能超过 225 个字符")
	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

}
