/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.dispute.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 * 物流纠纷表Entity
 * @author czy
 * @version 2018-06-09
 */
@Table(name="${_prefix}am_dispute", alias="a", columns={
		@Column(name="document_code", attrName="documentCode", label="单据编号", isPK=true),
		@Column(name="fid", attrName="fid"),
		@Column(name="single_tranport_code", attrName="singleTranportCode", label="运输单号"),
		@Column(name="seller", attrName="seller", label="销售员"),
		@Column(name="document_status", attrName="documentStatus", label="单据状态"),
		@Column(name="sales_library_code", attrName="salesLibraryCode", label="销售出库单号"),
		@Column(name="loading_time", attrName="loadingTime", label="装车时间"),
		@Column(name="client", attrName="client", label="客户"),
		@Column(name="dispute_type", attrName="disputeType", label="纠纷类型"),
		@Column(name="duty_type", attrName="dutyType", label="责任类型"),
		@Column(name="damages", attrName="damages", label="赔偿金额"),
		@Column(name="problem_description", attrName="problemDescription", label="问题描述"),
		@Column(name="process_result", attrName="processResult", label="处理结果"),
		@Column(name="reject_remarks", attrName="rejectRemarks", label="驳回备注"),
		@Column(name="stores", attrName="stores", label="店铺"),
		@Column(name="platform", attrName="platform", label="平台"),
		@Column(name="process_person", attrName="processPerson", label="处理人"),
		@Column(name="is_timed_out", attrName="isTimedOut", label="是否超时"),
		@Column(name="completion_person", attrName="completionPerson", label="完结人"),
		@Column(name="completion_date", attrName="completionDate", label="完结时间"),
		@Column(name="cp_ustomer", attrName="cpUstomer", label="CP客服"),
		@Column(name="accept_date", attrName="acceptDate", label="受理时间"),
		@Column(name="client_name", attrName="clientName", label="客户姓名", queryType=QueryType.LIKE),
		@Column(name="delivery_method", attrName="deliveryMethod", label="配送方式"),
		@Column(name="province", attrName="province", label="省份", queryType=QueryType.LIKE),
		@Column(name="city", attrName="city", label="城市"),
		@Column(name="county", attrName="county", label="区县"),
		@Column(name="logistics_company", attrName="logisticsCompany", label="物流公司"),
		@Column(name="mobile_phone", attrName="mobilePhone", label="移动电话"),
		@Column(name="address", attrName="address", label="详细地址"),
		@Column(name="create_date", attrName="createDate", label="创建时间"),
		@Column(name="update_date", attrName="updateDate", label="最近修改时间"),
		@Column(name="update_by", attrName="updateBy", label="最近修改人"),
	//	@Column(includeEntity=DataEntity.class),
	}, joinTable = {
		@JoinTable(type = Type.LEFT_JOIN, entity = AmDisputeImg.class, alias = "b",
				on = "a.document_code = b.document_code",
				columns = {@Column(includeEntity = AmDisputeImg.class)}
		),
},orderBy="a.fid DESC"
)

public class AmDispute extends DataEntity<AmDispute> {
	private static final long serialVersionUID = 1L;
	private String documentCode;		// 单据编号
	private String singleTranportCode;		// 运输单号
	private String seller;		// 销售员
	private String documentStatus;		// 单据状态
	private String salesLibraryCode;		// 销售出库单号
	private Date loadingTime;		// 装车时间
	private String client;		// 客户
	private String disputeType;		// 纠纷类型
	private String dutyType;		// 责任类型
	private Double damages;		// 赔偿金额
	private String problemDescription;		// 问题描述
	private String processResult;		// 处理结果
	private String rejectRemarks;		// 驳回备注
	private String stores;		// 店铺
	private String platform;		// 平台
	private String isTimedOut;		// 是否超时
	private String completionPerson;		// 完结人
	private Date completionDate;		// 完结时间
	private String cpUstomer;		// CP客服
	private Date acceptDate;		// 受理时间
	private String clientName;		// 客户姓名
	private String deliveryMethod;		// 配送方式
	private String province;		// 省份
	private String city;		// 城市
	private String county;		// 区县
	private String logisticsCompany;		// 物流公司
	private String mobilePhone;		// 移动电话
	private String address;		// 详细地址
	private List<AmDisputeDispose> amDisputeDisposeList = ListUtils.newArrayList();		// 子表列表
	private List<AmDisputeDetail> amDisputeDetailList = ListUtils.newArrayList();		// 子表列表
	private Date createDate;
	private Date updateDate;       // 最近更新时间
	private String updateBy;       // 最近更新人
	private String processPerson;  // 处理人
	private boolean isUpdate;  // 是否是受理
	private String dutyValue;
	private boolean isCreate;  // 是否是创建状态
	private String fid;
	private String userName;    // 当前用户
	private boolean isEnd;      // 是否是完结状态
	private AmDisputeImg amDisputeImg;


	public AmDisputeImg getAmDisputeImg() {
		return amDisputeImg;
	}

	public void setAmDisputeImg(AmDisputeImg amDisputeImg) {
		this.amDisputeImg = amDisputeImg;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean end) {
		isEnd = end;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public boolean isCreate() {
		return isCreate;
	}

	public void setCreate(boolean create) {
		isCreate = create;
	}

	public String getDutyValue() {
		return dutyValue;
	}

	public void setDutyValue(String dutyValue) {
		this.dutyValue = dutyValue;
	}

	public boolean isUpdate() {
		return isUpdate;
	}

	public void setUpdate(boolean update) {
		isUpdate = update;
	}

	public AmDispute() {
		this(null);
	}

	public AmDispute(String id){
		super(id);
	}
	
	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}

	public String getProcessPerson() {
		return processPerson;
	}

	public void setProcessPerson(String processPerson) {
		this.processPerson = processPerson;
	}

	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public Date getUpdateDate() {
		return updateDate;
	}

	@Override
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String getUpdateBy() {
		return updateBy;
	}

	@Override
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Length(min=0, max=64, message="运输单号长度不能超过 64 个字符")
	public String getSingleTranportCode() {
		return singleTranportCode;
	}

	public void setSingleTranportCode(String singleTranportCode) {
		this.singleTranportCode = singleTranportCode;
	}
	
	@Length(min=0, max=200, message="销售员长度不能超过 200 个字符")
	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}
	
	@Length(min=0, max=10, message="单据状态长度不能超过 10 个字符")
	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	
	@Length(min=0, max=64, message="销售出库单号长度不能超过 64 个字符")
	public String getSalesLibraryCode() {
		return salesLibraryCode;
	}

	public void setSalesLibraryCode(String salesLibraryCode) {
		this.salesLibraryCode = salesLibraryCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLoadingTime() {
		return loadingTime;
	}

	public void setLoadingTime(Date loadingTime) {
		this.loadingTime = loadingTime;
	}
	
	@Length(min=0, max=200, message="客户长度不能超过 200 个字符")
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
	
	@Length(min=0, max=200, message="纠纷类型长度不能超过 200 个字符")
	public String getDisputeType() {
		return disputeType;
	}

	public void setDisputeType(String disputeType) {
		this.disputeType = disputeType;
	}
	
	@Length(min=0, max=200, message="责任类型长度不能超过 200 个字符")
	public String getDutyType() {
		return dutyType;
	}

	public void setDutyType(String dutyType) {
		this.dutyType = dutyType;
	}
	
	public Double getDamages() {
		return damages;
	}

	public void setDamages(Double damages) {
		this.damages = damages;
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
	
	@Length(min=0, max=2000, message="驳回备注长度不能超过 2000 个字符")
	public String getRejectRemarks() {
		return rejectRemarks;
	}

	public void setRejectRemarks(String rejectRemarks) {
		this.rejectRemarks = rejectRemarks;
	}
	
	@Length(min=0, max=200, message="店铺长度不能超过 200 个字符")
	public String getStores() {
		return stores;
	}

	public void setStores(String stores) {
		this.stores = stores;
	}
	
	@Length(min=0, max=100, message="平台长度不能超过 100 个字符")
	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	@Length(min=0, max=10, message="是否超时长度不能超过 10 个字符")
	public String getIsTimedOut() {
		return isTimedOut;
	}

	public void setIsTimedOut(String isTimedOut) {
		this.isTimedOut = isTimedOut;
	}
	
	@Length(min=0, max=200, message="完结人长度不能超过 200 个字符")
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
	
	@Length(min=0, max=100, message="CP客服长度不能超过 100 个字符")
	public String getCpUstomer() {
		return cpUstomer;
	}

	public void setCpUstomer(String cpUstomer) {
		this.cpUstomer = cpUstomer;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}
	
	@Length(min=0, max=100, message="客户姓名长度不能超过 100 个字符")
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	@Length(min=0, max=200, message="配送方式长度不能超过 200 个字符")
	public String getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
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
	
	@Length(min=0, max=200, message="移动电话长度不能超过 200 个字符")
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	@Length(min=0, max=200, message="详细地址长度不能超过 200 个字符")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public List<AmDisputeDispose> getAmDisputeDisposeList() {
		return amDisputeDisposeList;
	}

	public void setAmDisputeDisposeList(List<AmDisputeDispose> amDisputeDisposeList) {
		this.amDisputeDisposeList = amDisputeDisposeList;
	}
	
	public List<AmDisputeDetail> getAmDisputeDetailList() {
		return amDisputeDetailList;
	}

	public void setAmDisputeDetailList(List<AmDisputeDetail> amDisputeDetailList) {
		this.amDisputeDetailList = amDisputeDetailList;
	}
	
}