/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amlogistics.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 物流车查询单Entity
 * @author dwh
 * @version 2018-06-07
 */
@Table(name="${_prefix}am_logistics", alias="a", columns={
		@Column(name="document_code", attrName="documentCode", label="单据编号", isPK=true,queryType=QueryType.LIKE),
		@Column(name="document_status", attrName="documentStatus", label="单据状态"),
		@Column(name="stores", attrName="stores", label="店铺"),
		@Column(name="sales_library_code", attrName="salesLibraryCode", label="销售出库单号"),
		@Column(name="single_tranport_code", attrName="singleTranportCode", label="运输单号"),
		@Column(name="maintenance_user", attrName="maintenanceUser", label="三包师傅"),
		@Column(name="maintenance_phone", attrName="maintenancePhone", label="三包电话"),
		@Column(name="type", attrName="type", label="类型"),
		@Column(name="client", attrName="client", label="客户",queryType=QueryType.LIKE),
		@Column(name="recipient", attrName="recipient", label="收件人",queryType=QueryType.LIKE),
		@Column(name="mobile_phone", attrName="mobilePhone", label="移动电话"),
		@Column(name="seller", attrName="seller", label="销售员"),
		@Column(name="province", attrName="province", label="省份", queryType=QueryType.LIKE),
		@Column(name="city", attrName="city", label="城市" ,queryType=QueryType.LIKE),
		@Column(name="county", attrName="county", label="区县"),
		@Column(name="logistics_company", attrName="logisticsCompany", label="物流公司",queryType=QueryType.LIKE),
		@Column(name="delivery_method", attrName="deliveryMethod", label="配送方式"),
		@Column(name="address", attrName="address", label="详细地址"),
		@Column(name="loading_time", attrName="loadingTime", label="装车时间"),
		@Column(name="problem_description", attrName="problemDescription", label="问题描述"),
		@Column(name="process_result", attrName="processResult", label="处理结果"),
		@Column(name="is_timed_out", attrName="isTimedOut", label="是否超时"),
		@Column(name="process_person", attrName="processPerson", label="处理人"),

//		@Column(includeEntity=DataEntity.class),
		@Column(name="modify_person", attrName="modifyPerson", label="最近修改人"),
		@Column(name="create_date", attrName="createDate", label="创建时间"),
		@Column(name="modify_date", attrName="modifyDate", label="最近修改时间"),
		@Column(name="completion_person", attrName="completionPerson", label="完成人"),
		@Column(name="completion_date", attrName="completionDate", label="完成时间"),
		@Column(name="f_id", attrName="fdId", label="单据的UUID"),
	}, orderBy="a.f_id DESC"
)
public class AmLogistics extends DataEntity<AmLogistics> {
	
	private static final long serialVersionUID = 1L;
	private String documentCode;		// 单据编号
	private String documentStatus;		// 单据状态
	private String stores;		// 店铺
	private String salesLibraryCode;		// 销售出库单号
	private String singleTranportCode;		// 运输单号
	private String maintenanceUser;		// 三包师傅
	private String maintenancePhone;		// 三包电话
	private String type;		// 类型
	private String client;		// 客户
	private String recipient;		// 收件人
	private String mobilePhone;		// 移动电话
	private String seller;		// 销售员
	private String province;		// 省份
	private String city;		// 城市
	private String county;		// 区县
	private String logisticsCompany;		// 物流公司
	private String deliveryMethod;		// 配送方式
	private String address;		// 详细地址
	private Date loadingTime;		// 装车时间
	private String problemDescription;		// 问题描述
	private String processResult;		// 处理结果
	private String isTimedOut;		// 是否超时
	private String processPerson;		// 处理人
	private String modifyPerson;		// 最近修改人
	private Date modifyDate;		// 最近修改时间
	private String completionPerson;		// 完成人
	private Date completionDate;		// 完成时间
	private Date createDate;
	private List<AmLogisticsDetail> amLogisticsDetailList = ListUtils.newArrayList();		// 子表列表
	private String fdId;

    public String getFdId() {
        return fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    @Override
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public AmLogistics() {
		this(null);
	}

	public AmLogistics(String id){
		super(id);
	}
	
	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	
	@Length(min=0, max=100, message="单据状态长度不能超过 100 个字符")
	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	
	@Length(min=0, max=200, message="店铺长度不能超过 200 个字符")
	public String getStores() {
		return stores;
	}

	public void setStores(String stores) {
		this.stores = stores;
	}
	
	@Length(min=0, max=64, message="销售出库单号长度不能超过 64 个字符")
	public String getSalesLibraryCode() {
		return salesLibraryCode;
	}

	public void setSalesLibraryCode(String salesLibraryCode) {
		this.salesLibraryCode = salesLibraryCode;
	}
	
	@Length(min=0, max=64, message="运输单号长度不能超过 64 个字符")
	public String getSingleTranportCode() {
		return singleTranportCode;
	}

	public void setSingleTranportCode(String singleTranportCode) {
		this.singleTranportCode = singleTranportCode;
	}
	
	@Length(min=0, max=200, message="三包师傅长度不能超过 200 个字符")
	public String getMaintenanceUser() {
		return maintenanceUser;
	}

	public void setMaintenanceUser(String maintenanceUser) {
		this.maintenanceUser = maintenanceUser;
	}
	
	@Length(min=0, max=200, message="三包电话长度不能超过 200 个字符")
	public String getMaintenancePhone() {
		return maintenancePhone;
	}

	public void setMaintenancePhone(String maintenancePhone) {
		this.maintenancePhone = maintenancePhone;
	}
	
	@Length(min=0, max=2, message="类型长度不能超过 2 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=200, message="客户长度不能超过 200 个字符")
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
	
	@Length(min=0, max=200, message="收件人长度不能超过 200 个字符")
	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	@Length(min=0, max=200, message="移动电话长度不能超过 200 个字符")
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	@Length(min=0, max=200, message="销售员长度不能超过 200 个字符")
	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}
	
	@Length(min=0, max=200, message="省份长度不能超过 200 个字符")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=200, message="城市长度不能超过 200 个字符")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=200, message="区县长度不能超过 200 个字符")
	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}
	
	@Length(min=0, max=200, message="物流公司长度不能超过 200 个字符")
	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}
	
	@Length(min=0, max=200, message="配送方式长度不能超过 200 个字符")
	public String getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	
	@Length(min=0, max=200, message="详细地址长度不能超过 200 个字符")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLoadingTime() {
		return loadingTime;
	}

	public void setLoadingTime(Date loadingTime) {
		this.loadingTime = loadingTime;
	}
	
	@Length(min=0, max=2000, message="问题描述长度不能超过 2000 个字符")
	public String getProblemDescription() {
		return problemDescription;
	}

	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}
	
	@Length(min=0, max=2000, message="处理结果长度不能超过 2000 个字符")
	public String getProcessResult() {
		return processResult;
	}

	public void setProcessResult(String processResult) {
		this.processResult = processResult;
	}
	
	@Length(min=0, max=2, message="是否超时长度不能超过 2 个字符")
	public String getIsTimedOut() {
		return isTimedOut;
	}

	public void setIsTimedOut(String isTimedOut) {
		this.isTimedOut = isTimedOut;
	}
	
	@Length(min=0, max=200, message="处理人长度不能超过 200 个字符")
	public String getProcessPerson() {
		return processPerson;
	}

	public void setProcessPerson(String processPerson) {
		this.processPerson = processPerson;
	}
	
	@Length(min=0, max=200, message="最近修改人长度不能超过 200 个字符")
	public String getModifyPerson() {
		return modifyPerson;
	}

	public void setModifyPerson(String modifyPerson) {
		this.modifyPerson = modifyPerson;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	@Length(min=0, max=200, message="完成人长度不能超过 200 个字符")
	public String getCompletionPerson() {
		return completionPerson;
	}

	public void setCompletionPerson(String completionPerson) {
		this.completionPerson = completionPerson;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}
	
	public List<AmLogisticsDetail> getAmLogisticsDetailList() {
		return amLogisticsDetailList;
	}

	public void setAmLogisticsDetailList(List<AmLogisticsDetail> amLogisticsDetailList) {
		this.amLogisticsDetailList = amLogisticsDetailList;
	}
	
}