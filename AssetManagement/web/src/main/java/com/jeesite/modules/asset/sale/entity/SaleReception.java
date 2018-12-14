/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.sale.entity;

import com.jeesite.modules.asset.product.entity.ProductCategory;
import com.jeesite.modules.sys.entity.EmpUser;
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
 * 客户接待表Entity
 * @author Scarlett
 * @version 2018-07-26
 */
@Table(name="${_prefix}sale_reception", alias="a", columns={
		@Column(name="reception_code", attrName="receptionCode", label="接待编号", isPK=true),
		@Column(name="customer", attrName="customer", label="客户",queryType=QueryType.LIKE),
		@Column(name="gender", attrName="gender", label="性别"),
		@Column(name="phonenum", attrName="phonenum", label="移动电话",queryType=QueryType.LIKE),
		@Column(name="demands", attrName="demands", label="家具需求"),
		@Column(name="customer_type", attrName="customerType", label="客户来源"),
		@Column(name="buying_status", attrName="buyingStatus", label="购买状态"),
		@Column(name="login_code", attrName="loginCode", label="导购id"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="end_date", attrName="endDate", label="结束时间"),
		@Column(name="reception_status",attrName ="receptionStatus",label="接待状态")
	},joinTable= {
		@JoinTable(type = JoinTable.Type.LEFT_JOIN, entity = EmpUser.class, alias = "b",
				on = "b.user_code=a.login_code ",
				columns = {@Column(includeEntity = EmpUser.class)})
},orderBy="a.create_date DESC"
)
public class SaleReception extends DataEntity<SaleReception> {
	
	private static final long serialVersionUID = 1L;
	private String receptionCode;		// 接待编号
	private String customer;		// 客户
	private String gender;		// 性别
	private String phonenum;		// 移动电话
	private String demands;		// 家具需求
	private String customerType;		// 客户来源
	private String buyingStatus;		// 购买状态
	private String loginCode;		// 导购id
	private EmpUser empUser;
	private String receptionStatus;
	private String officeCode;

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getReceptionStatus() {
		return receptionStatus;
	}

	public void setReceptionStatus(String receptionStatus) {
		this.receptionStatus = receptionStatus;
	}


	public EmpUser getEmpUser() {
		return empUser;
	}

	public void setEmpUser(EmpUser empUser) {
		this.empUser = empUser;
	}


	private Date endDate;		// 结束时间
	
	public SaleReception() {
		this(null);
	}

	public SaleReception(String id){
		super(id);
	}
	
	public String getReceptionCode() {
		return receptionCode;
	}

	public void setReceptionCode(String receptionCode) {
		this.receptionCode = receptionCode;
	}
	
	@NotBlank(message="客户不能为空")
	@Length(min=0, max=64, message="客户长度不能超过 64 个字符")
	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	@Length(min=0, max=2, message="性别长度不能超过2个字符")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Length(min=0, max=25, message="移动电话长度不能超过 25 个字符")
	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	
	@Length(min=0, max=225, message="家具需求长度不能超过 225 个字符")
	public String getDemands() {
		return demands;
	}

	public void setDemands(String demands) {
		this.demands = demands;
	}
	
	@NotBlank(message="客户来源不能为空")
	@Length(min=0, max=1, message="客户来源长度不能超过 1 个字符")
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	@NotBlank(message="购买状态不能为空")
	@Length(min=0, max=1, message="购买状态长度不能超过 1 个字符")
	public String getBuyingStatus() {
		return buyingStatus;
	}

	public void setBuyingStatus(String buyingStatus) {
		this.buyingStatus = buyingStatus;
	}
	
	@NotBlank(message="导购id不能为空")
	@Length(min=0, max=64, message="导购id长度不能超过 64 个字符")
	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="结束时间不能为空")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}