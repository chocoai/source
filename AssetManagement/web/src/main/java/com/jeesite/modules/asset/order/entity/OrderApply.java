/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 订单变更申请表Entity
 * @author czy
 * @version 2018-07-14
 */
@Table(name="${_prefix}am_order_apply", alias="a", columns={
		@Column(name="document_code", attrName="documentCode", label="单据编号", isPK=true),
		@Column(name="document_status", attrName="documentStatus", label="状态"),
		@Column(name="sales_order", attrName="salesOrder", label="销售订单", queryType=QueryType.LIKE),
		@Column(name="cancel_product", attrName="cancelProduct", label="取消产品"),
		@Column(name="add_product", attrName="addProduct", label="新增产品"),
		@Column(name="modify_cause", attrName="modifyCause", label="变更原因"),
		@Column(name="applicant", attrName="applicant", label="申请人"),
		@Column(name="apply_date", attrName="applyDate", label="申请时间"),
		@Column(name="confirm_by", attrName="confirmBy", label="确认人"),
		@Column(name="confirm_date", attrName="confirmDate", label="确认时间"),
		@Column(name="office_code", attrName="officeCode", label="部门编码"),
	}, orderBy="str_to_date(a.apply_date,'%Y-%m-%d %H:%i:%s') DESC"
)
public class OrderApply extends DataEntity<OrderApply> {

	private static final long serialVersionUID = 1L;
	private String documentCode;		// 单据编号
	private String documentStatus;		// 状态
	private String salesOrder;		// 销售订单
	private String cancelProduct;		// 取消产品
	private String addProduct;		// 新增产品
	private String modifyCause;		// 变更原因
	private String applicant;		// 申请人
	private String applyDate;		// 申请时间
	private String confirmBy;		// 确认人
	private String confirmDate;		// 确认时间
	private boolean isConfirm;		// 是否是确认状态
	private String officeCode;		// 部门编码
	private String userName;
	private String officeCodes;

	public String getOfficeCodes() {
		return officeCodes;
	}

	public void setOfficeCodes(String officeCodes) {
		this.officeCodes = officeCodes;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public boolean isConfirm() {
		return isConfirm;
	}

	public void setConfirm(boolean confirm) {
		isConfirm = confirm;
	}

	public OrderApply() {
		this(null);
	}

	public OrderApply(String id){
		super(id);
	}
	
	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	
	@NotBlank(message="状态不能为空")
	@Length(min=0, max=10, message="状态长度不能超过 10 个字符")
	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	
	@NotBlank(message="销售订单不能为空")
	@Length(min=0, max=64, message="销售订单长度不能超过 64 个字符")
	public String getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}
	
	@NotBlank(message="取消产品不能为空")
	@Length(min=0, max=500, message="取消产品长度不能超过 500 个字符")
	public String getCancelProduct() {
		return cancelProduct;
	}

	public void setCancelProduct(String cancelProduct) {
		this.cancelProduct = cancelProduct;
	}
	
	@Length(min=0, max=500, message="新增产品长度不能超过 500 个字符")
	public String getAddProduct() {
		return addProduct;
	}

	public void setAddProduct(String addProduct) {
		this.addProduct = addProduct;
	}
	
	@NotBlank(message="变更原因不能为空")
	@Length(min=0, max=1000, message="变更原因长度不能超过 1000 个字符")
	public String getModifyCause() {
		return modifyCause;
	}

	public void setModifyCause(String modifyCause) {
		this.modifyCause = modifyCause;
	}
	
	@Length(min=0, max=100, message="申请人长度不能超过 100 个字符")
	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	
	@Length(min=0, max=64, message="申请时间长度不能超过 64 个字符")
	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	
	@Length(min=0, max=64, message="确认人长度不能超过 64 个字符")
	public String getConfirmBy() {
		return confirmBy;
	}

	public void setConfirmBy(String confirmBy) {
		this.confirmBy = confirmBy;
	}
	
	@Length(min=0, max=64, message="确认时间长度不能超过 64 个字符")
	public String getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}
	
}