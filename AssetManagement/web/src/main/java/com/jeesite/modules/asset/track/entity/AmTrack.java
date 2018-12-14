/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.track.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 * 退货跟踪单Entity
 * @author czy
 * @version 2018-06-21
 */
@Table(name="${_prefix}am_track", alias="a", columns={
		@Column(name="document_code", attrName="documentCode", label="单据编号", isPK=true),
		@Column(name="fid", attrName="fid", label="fid"),
		@Column(name="document_status", attrName="documentStatus", label="单据状态"),
		@Column(name="retreat", attrName="retreat", label="退回点"),
		@Column(name="three_worker", attrName="threeWorker", label="三包师傅"),
		@Column(name="worker_phone", attrName="workerPhone", label="师傅电话"),
		@Column(name="logistics_company", attrName="logisticsCompany", label="物流公司", queryType=QueryType.LIKE),
		@Column(name="logistics_code", attrName="logisticsCode", label="物流单号"),
		@Column(name="warehouse", attrName="warehouse", label="仓库"),
		@Column(name="return_address", attrName="returnAddress", label="退回地址"),
		@Column(name="delivery_type", attrName="deliveryType", label="退货类型", queryType=QueryType.LIKE),
		@Column(name="stores", attrName="stores", label="店铺"),
		@Column(name="platform", attrName="platform", label="平台"),
		@Column(name="return_policy", attrName="returnPolicy", label="退货说明"),
		@Column(name="responsibility", attrName="responsibility", label="退货责任方"),
		@Column(name="estimate_date", attrName="estimateDate", label="预计到货时间"),
		@Column(name="return_logistics_code", attrName="returnLogisticsCode", label="退货物流单号"),
		@Column(name="return_method", attrName="returnMethod", label="退货入仓方式"),
		@Column(name="return_remarks", attrName="returnRemarks", label="退货备注"),
		@Column(name="logistics_cost", attrName="logisticsCost", label="物流费用"),
		@Column(name="settlement_mode", attrName="settlementMode", label="物流结算方式"),
		@Column(name="processing_person", attrName="processingPerson", label="处理人"),
		@Column(name="create_date", attrName="createDate", label="创建时间"),
		@Column(name="accept_person", attrName="acceptPerson", label="受理人"),
		@Column(name="accept_date", attrName="acceptDate", label="受理时间"),
		@Column(name="completion_person", attrName="completionPerson", label="完结人"),
		@Column(name="completion_date", attrName="completionDate", label="完结时间"),
		@Column(name="after_sale_code", attrName="afterSaleCode", label="售后单号"),
		@Column(name="outstorage_code", attrName="outstorageCode", label="出库单号"),
		@Column(name="order_status", attrName="orderStatus", label="订单状态"),
		@Column(name="seller", attrName="seller", label="销售员"),
		@Column(name="client", attrName="client", label="客户", queryType=QueryType.LIKE),
		@Column(name="delivery_method", attrName="deliveryMethod", label="配送方式"),
		@Column(name="logistics_company1", attrName="logisticsCompany1", label="售后物流公司"),
		@Column(name="client_name", attrName="clientName", label="客户姓名", queryType=QueryType.LIKE),
		@Column(name="mobile_phone", attrName="mobilePhone", label="移动电话"),
		@Column(name="address", attrName="address", label="详细地址"),
		@Column(name="problem_description", attrName="problemDescription", label="问题描述"),
		@Column(name="province", attrName="province", label="省份"),
		@Column(name="city", attrName="city", label="城市"),
		@Column(name="region", attrName="region", label="区县"),
		@Column(name="fileid1", attrName="fileid1", label="图片1"),
		@Column(name="fileid2", attrName="fileid2", label="图片2"),
		@Column(name="fileid3", attrName="fileid3", label="图片3"),
		@Column(name="fileid4", attrName="fileid4", label="图片4"),
		@Column(name="fileid5", attrName="fileid5", label="图片5"),
		@Column(name="fileid6", attrName="fileid6", label="图片6"),
		@Column(name="audit_time", attrName="auditTime", label="审核时间"),
	}, orderBy="a.audit_time DESC"
)
public class AmTrack extends DataEntity<AmTrack> {
	
	private static final long serialVersionUID = 1L;
	private String documentCode;		// 单据编号
	private String fid;		// fid
	private String documentStatus;		// 单据状态
	private String retreat;		// 退回点
	private String threeWorker;		// 三包师傅
	private String workerPhone;		// 师傅电话
	private String logisticsCompany;		// 物流公司
	private String logisticsCode;		// 物流单号
	private String warehouse;		// 仓库
	private String returnAddress;		// 退回地址
	private String deliveryType;		// 退货类型
	private String stores;		// 店铺
	private String platform;		// 平台
	private String returnPolicy;		// 退货说明
	private String responsibility;		// 退货责任方
	private Date estimateDate;		// 预计到货时间
	private String returnLogisticsCode;		// 退货物流单号
	private String returnMethod;		// 退货入仓方式
	private String returnRemarks;		// 退货备注
	private Double logisticsCost;		// 物流费用
	private String settlementMode;		// 物流结算方式
	private String processingPerson;		// 处理人
	private String acceptPerson;		// 受理人
	private Date acceptDate;		// 受理时间
	private String completionPerson;		// 完结人
	private Date completionDate;		// 完结时间
	private String afterSaleCode;		// 售后单号
	private String outstorageCode;		// 出库单号
	private String orderStatus;		// 订单状态
	private String seller;		// 销售员
	private String client;		// 客户
	private String deliveryMethod;		// 配送方式
	private String logisticsCompany1;		// 售后物流公司
	private String clientName;		// 客户姓名
	private String mobilePhone;		// 移动电话
	private String address;		// 详细地址
	private String problemDescription;		// 问题描述
	private String province;		// 省份
	private String city;		// 城市
	private String region;		// 区县
	private String fileid1;		// 图片1
	private String fileid2;		// 图片2
	private String fileid3;		// 图片3
	private String fileid4;		// 图片4
	private String fileid5;		// 图片5
	private String fileid6;		// 图片6
	private List<AmTrackDetail> amTrackDetailList = ListUtils.newArrayList();		// 子表列表
	private List<AmTrackTransfer> amTrackTransferList = ListUtils.newArrayList();		// 子表列表
	private List<AmTrackSpeed> amTrackSpeedList = ListUtils.newArrayList();		// 子表列表
	private Date createDate;    // 创建时间
	private boolean isEnd;      // 是否时完结状态
	private String userName;    // 当前用户名
	private Date auditTime;		// 审核时间

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean end) {
		isEnd = end;
	}

	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public AmTrack() {
		this(null);
	}

	public AmTrack(String id){
		super(id);
	}
	
	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	
	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}
	
	@Length(min=0, max=10, message="单据状态长度不能超过 10 个字符")
	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	
	@Length(min=0, max=100, message="退回点长度不能超过 100 个字符")
	public String getRetreat() {
		return retreat;
	}

	public void setRetreat(String retreat) {
		this.retreat = retreat;
	}
	
	@Length(min=0, max=100, message="三包师傅长度不能超过 100 个字符")
	public String getThreeWorker() {
		return threeWorker;
	}

	public void setThreeWorker(String threeWorker) {
		this.threeWorker = threeWorker;
	}
	
	@Length(min=0, max=100, message="师傅电话长度不能超过 100 个字符")
	public String getWorkerPhone() {
		return workerPhone;
	}

	public void setWorkerPhone(String workerPhone) {
		this.workerPhone = workerPhone;
	}
	
	@Length(min=0, max=100, message="物流公司长度不能超过 100 个字符")
	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}
	
	@Length(min=0, max=100, message="物流单号长度不能超过 100 个字符")
	public String getLogisticsCode() {
		return logisticsCode;
	}

	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
	}
	
	@Length(min=0, max=100, message="仓库长度不能超过 100 个字符")
	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	@Length(min=0, max=200, message="退回地址长度不能超过 200 个字符")
	public String getReturnAddress() {
		return returnAddress;
	}

	public void setReturnAddress(String returnAddress) {
		this.returnAddress = returnAddress;
	}
	
	@Length(min=0, max=200, message="退货类型长度不能超过 200 个字符")
	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	
	@Length(min=0, max=100, message="店铺长度不能超过 100 个字符")
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
	
	@Length(min=0, max=2000, message="退货说明长度不能超过 2000 个字符")
	public String getReturnPolicy() {
		return returnPolicy;
	}

	public void setReturnPolicy(String returnPolicy) {
		this.returnPolicy = returnPolicy;
	}
	
	@Length(min=0, max=100, message="退货责任方长度不能超过 100 个字符")
	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEstimateDate() {
		return estimateDate;
	}

	public void setEstimateDate(Date estimateDate) {
		this.estimateDate = estimateDate;
	}
	
	@Length(min=0, max=100, message="退货物流单号长度不能超过 100 个字符")
	public String getReturnLogisticsCode() {
		return returnLogisticsCode;
	}

	public void setReturnLogisticsCode(String returnLogisticsCode) {
		this.returnLogisticsCode = returnLogisticsCode;
	}
	
	@Length(min=0, max=100, message="退货入仓方式长度不能超过 100 个字符")
	public String getReturnMethod() {
		return returnMethod;
	}

	public void setReturnMethod(String returnMethod) {
		this.returnMethod = returnMethod;
	}
	
	@Length(min=0, max=2000, message="退货备注长度不能超过 2000 个字符")
	public String getReturnRemarks() {
		return returnRemarks;
	}

	public void setReturnRemarks(String returnRemarks) {
		this.returnRemarks = returnRemarks;
	}
	
	public Double getLogisticsCost() {
		return logisticsCost;
	}

	public void setLogisticsCost(Double logisticsCost) {
		this.logisticsCost = logisticsCost;
	}
	
	@Length(min=0, max=10, message="物流结算方式长度不能超过 10 个字符")
	public String getSettlementMode() {
		return settlementMode;
	}

	public void setSettlementMode(String settlementMode) {
		this.settlementMode = settlementMode;
	}
	
	@Length(min=0, max=100, message="处理人长度不能超过 100 个字符")
	public String getProcessingPerson() {
		return processingPerson;
	}

	public void setProcessingPerson(String processingPerson) {
		this.processingPerson = processingPerson;
	}
	
	@Length(min=0, max=100, message="受理人长度不能超过 100 个字符")
	public String getAcceptPerson() {
		return acceptPerson;
	}

	public void setAcceptPerson(String acceptPerson) {
		this.acceptPerson = acceptPerson;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}
	
	@Length(min=0, max=100, message="完结人长度不能超过 100 个字符")
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
	
	@Length(min=0, max=64, message="售后单号长度不能超过 64 个字符")
	public String getAfterSaleCode() {
		return afterSaleCode;
	}

	public void setAfterSaleCode(String afterSaleCode) {
		this.afterSaleCode = afterSaleCode;
	}
	
	@Length(min=0, max=64, message="出库单号长度不能超过 64 个字符")
	public String getOutstorageCode() {
		return outstorageCode;
	}

	public void setOutstorageCode(String outstorageCode) {
		this.outstorageCode = outstorageCode;
	}
	
	@Length(min=0, max=100, message="订单状态长度不能超过 100 个字符")
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	@Length(min=0, max=200, message="销售员长度不能超过 200 个字符")
	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}
	
	@Length(min=0, max=200, message="客户长度不能超过 200 个字符")
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
	
	@Length(min=0, max=200, message="配送方式长度不能超过 200 个字符")
	public String getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	
	@Length(min=0, max=200, message="售后物流公司长度不能超过 200 个字符")
	public String getLogisticsCompany1() {
		return logisticsCompany1;
	}

	public void setLogisticsCompany1(String logisticsCompany1) {
		this.logisticsCompany1 = logisticsCompany1;
	}
	
	@Length(min=0, max=200, message="客户姓名长度不能超过 200 个字符")
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	@Length(min=0, max=100, message="移动电话长度不能超过 100 个字符")
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	@Length(min=0, max=2000, message="详细地址长度不能超过 2000 个字符")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=2000, message="问题描述长度不能超过 2000 个字符")
	public String getProblemDescription() {
		return problemDescription;
	}

	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
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
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	@Length(min=0, max=100, message="图片1长度不能超过 100 个字符")
	public String getFileid1() {
		return fileid1;
	}

	public void setFileid1(String fileid1) {
		this.fileid1 = fileid1;
	}
	
	@Length(min=0, max=100, message="图片2长度不能超过 100 个字符")
	public String getFileid2() {
		return fileid2;
	}

	public void setFileid2(String fileid2) {
		this.fileid2 = fileid2;
	}
	
	@Length(min=0, max=100, message="图片3长度不能超过 100 个字符")
	public String getFileid3() {
		return fileid3;
	}

	public void setFileid3(String fileid3) {
		this.fileid3 = fileid3;
	}
	
	@Length(min=0, max=100, message="图片4长度不能超过 100 个字符")
	public String getFileid4() {
		return fileid4;
	}

	public void setFileid4(String fileid4) {
		this.fileid4 = fileid4;
	}
	
	@Length(min=0, max=100, message="图片5长度不能超过 100 个字符")
	public String getFileid5() {
		return fileid5;
	}

	public void setFileid5(String fileid5) {
		this.fileid5 = fileid5;
	}
	
	@Length(min=0, max=100, message="图片6长度不能超过 100 个字符")
	public String getFileid6() {
		return fileid6;
	}

	public void setFileid6(String fileid6) {
		this.fileid6 = fileid6;
	}
	
	public List<AmTrackDetail> getAmTrackDetailList() {
		return amTrackDetailList;
	}

	public void setAmTrackDetailList(List<AmTrackDetail> amTrackDetailList) {
		this.amTrackDetailList = amTrackDetailList;
	}
	
	public List<AmTrackTransfer> getAmTrackTransferList() {
		return amTrackTransferList;
	}

	public void setAmTrackTransferList(List<AmTrackTransfer> amTrackTransferList) {
		this.amTrackTransferList = amTrackTransferList;
	}
	
	public List<AmTrackSpeed> getAmTrackSpeedList() {
		return amTrackSpeedList;
	}

	public void setAmTrackSpeedList(List<AmTrackSpeed> amTrackSpeedList) {
		this.amTrackSpeedList = amTrackSpeedList;
	}
	
}