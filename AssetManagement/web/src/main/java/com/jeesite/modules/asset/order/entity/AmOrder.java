/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.entity;

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
 * 订单管理Entity
 * @author czy
 * @version 2018-07-09
 */
@Table(name="${_prefix}am_order", alias="a", columns={
		@Column(name="document_code", attrName="documentCode", label="订单编号", isPK=true, queryType=QueryType.LIKE),
		@Column(name="document_type", attrName="documentType", label="订单类型"),
		@Column(name="document_status", attrName="documentStatus", label="订单状态"),
		@Column(name="customer_name", attrName="customerName", label="客户姓名", queryType=QueryType.LIKE),
		@Column(name="customer_nick", attrName="customerNick", label="客户昵称", queryType=QueryType.LIKE),
		@Column(name="mobile_phone", attrName="mobilePhone", label="移动电话", queryType=QueryType.LIKE),
		@Column(name="fixed_phone", attrName="fixedPhone", label="固定电话"),
		@Column(name="province", attrName="province", label="省"),
		@Column(name="city", attrName="city", label="市"),
		@Column(name="region", attrName="region", label="区"),
		@Column(name="detailed_address", attrName="detailedAddress", label="详细地址"),
		@Column(name="distribution", attrName="distribution", label="配送方式"),
		@Column(name="pay_type", attrName="payType", label="支付方式"),
	//	@Column(includeEntity=DataEntity.class),
		@Column(name="total_price", attrName="totalPrice", label="总价"),
		@Column(name="logistics_fee", attrName="logisticsFee", label="物流费"),
		@Column(name="three_charges", attrName="threeCharges", label="三包费"),
		@Column(name="preferential", attrName="preferential", label="优惠金额"),
		@Column(name="total_fee", attrName="totalFee", label="合计应收"),
		@Column(name="stage_status", attrName="stageStatus", label="分阶段状态"),
		@Column(name="submit_by", attrName="submitBy", label="提交人"),
		@Column(name="submit_date", attrName="submitDate", label="提交时间"),
		@Column(name="confirm_by", attrName="confirmBy", label="确认人"),
		@Column(name="confirm_date", attrName="confirmDate", label="确认时间"),
		@Column(name="printing_by", attrName="printingBy", label="打印人"),
		@Column(name="printing_date", attrName="printingDate", label="打印时间"),
		@Column(name="customer_source", attrName="customerSource", label="客户来源"),
		@Column(name="order_source", attrName="orderSource", label="订单来源"),
		@Column(name="privilege1", attrName="privilege1", label="特权定金单号1"),
		@Column(name="privilege2", attrName="privilege2", label="特权定金单号2"),
		@Column(name="privilege3", attrName="privilege3", label="特权定金单号3"),
		@Column(name="create_time", attrName="createTime", label="创建时间"),
		@Column(name="create_by", attrName="createBy", label="创建人"),
		@Column(name="remarks", attrName="remarks", label="备注"),
	//	@Column(name="discount", attrName="discount", label="优惠活动"),
//		@Column(name="discount_amount", attrName="discountAmount", label="优惠金额"),
		@Column(name="office_code", attrName="officeCode", label="部门编码"),
		@Column(name="quote_time", attrName="quoteTime", label="报价时间"),
		@Column(name="is_enjoy", attrName="isEnjoy", label="是否享受油补"),
		@Column(name="oil_subsidy", attrName="oilSubsidy", label="油费补贴"),
		@Column(name="privileges_total", attrName="privilegesTotal", label="特权金总额"),
		@Column(name="check_by", attrName="checkBy", label="查货人"),
		@Column(name="check_date", attrName="checkDate", label="查货时间"),
		@Column(name="coupon_code", attrName="couponCode", label="优惠码"),
	}, orderBy="str_to_date(a.create_time,'%Y-%m-%d %H:%i:%s') DESC"
)
public class AmOrder extends DataEntity<AmOrder> {
	
	private static final long serialVersionUID = 1L;
	private String documentCode;		// 订单编号
	private String documentType;		// 订单类型
	private String documentStatus;		// 订单状态
	private String customerName;		// 客户姓名
	private String customerNick;		// 客户昵称
	private String mobilePhone;		// 移动电话
	private String fixedPhone;		// 固定电话
	private String province;		// 省
	private String city;		// 市
	private String region;		// 区
	private String detailedAddress;		// 详细地址
	private String distribution;		// 配送方式
	private String payType;		// 支付方式
	private Double totalPrice;		// 总价
	private Double logisticsFee;		// 物流费
	private Double threeCharges;		// 三包费
	private Double preferential;		// 优惠金额
	private Double totalFee;		// 合计应收
	private String stageStatus;		// 分阶段状态
	private String submitBy;		// 提交人
	private String submitDate;		// 提交时间
	private String confirmBy;		// 确认人
	private String confirmDate;		// 确认时间
	private String printingBy;		// 打印人
	private String printingDate;		// 打印时间
	private String customerSource;		// 客户来源
	private String orderSource;		// 订单来源
	private String privilege1;		// 特权定金单号1
	private String privilege2;		// 特权定金单号2
	private String privilege3;		// 特权定金单号3
//	private String discount;		// 优惠活动
//	private Double discountAmount;		// 优惠金额
	private String createTime;		// 创建时间
	private String createBy;		// 创建人
	private boolean isConfirm;		// 是否时确认状态
	private List<AmOrderDiscount> amOrderDiscountList = ListUtils.newArrayList();		// 子表列表
	private List<AmOrderDetail> amOrderDetailList = ListUtils.newArrayList();		// 子表列表
	private List<AmOrderImg> imageList = ListUtils.newArrayList();		// 图片表列表
	private String nameOrId;  		// 客户呢称/淘宝单号
	private String remarks;			// 备注
	private String officeCode;		// 部门编码
	private String userName;
	private String officeCodes;
	private Double freight;			// 运费
	private Date quoteTime;			// 报价时间
	private String isEnjoy;			// 是否享受油补
	private String enjoyVal;		// 是否享受油费显示
	private Double oilSubsidy;		// 油费补贴
	private String otherDiscount;	// 用于其它优惠
	private String privilegesTotal;	// 特权金总额
	private String treasure;		// 导购宝付款
	private String checkBy;			// 查货人
	private Date checkDate;			// 查货时间
	private String couponCode;		// 优惠码

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getCheckBy() {
		return checkBy;
	}

	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getTreasure() {
		return treasure;
	}

	public void setTreasure(String treasure) {
		this.treasure = treasure;
	}

	public String getPrivilegesTotal() {
		return privilegesTotal;
	}

	public void setPrivilegesTotal(String privilegesTotal) {
		this.privilegesTotal = privilegesTotal;
	}

	public String getOtherDiscount() {
		return otherDiscount;
	}

	public void setOtherDiscount(String otherDiscount) {
		this.otherDiscount = otherDiscount;
	}

	public Date getQuoteTime() {
		return quoteTime;
	}

	public void setQuoteTime(Date quoteTime) {
		this.quoteTime = quoteTime;
	}

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

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

	@Override
	public String getRemarks() {
		return remarks;
	}

	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getNameOrId() {
		return nameOrId;
	}

	public void setNameOrId(String nameOrId) {
		this.nameOrId = nameOrId;
	}

	public boolean isConfirm() {
		return isConfirm;
	}

	public void setConfirm(boolean confirm) {
		isConfirm = confirm;
	}


	@Override
	public String getCreateBy() {
		return createBy;
	}

	@Override
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public AmOrder() {
		this(null);
	}

	public AmOrder(String id){
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
	
	@Length(min=0, max=100, message="客户昵称长度不能超过 100 个字符")
	public String getCustomerNick() {
		return customerNick;
	}

	public void setCustomerNick(String customerNick) {
		this.customerNick = customerNick;
	}
	
	@NotBlank(message="移动电话不能为空")
	@Length(min=0, max=20, message="移动电话长度不能超过 20 个字符")
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	@Length(min=0, max=20, message="固定电话长度不能超过 20 个字符")
	public String getFixedPhone() {
		return fixedPhone;
	}

	public void setFixedPhone(String fixedPhone) {
		this.fixedPhone = fixedPhone;
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
	
	public Double getThreeCharges() {
		return threeCharges;
	}

	public void setThreeCharges(Double threeCharges) {
		this.threeCharges = threeCharges;
	}
	
	public Double getPreferential() {
		return preferential;
	}

	public void setPreferential(Double preferential) {
		this.preferential = preferential;
	}
	
	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	
	@Length(min=0, max=50, message="分阶段状态长度不能超过 50 个字符")
	public String getStageStatus() {
		return stageStatus;
	}

	public void setStageStatus(String stageStatus) {
		this.stageStatus = stageStatus;
	}
	
	@Length(min=0, max=64, message="提交人长度不能超过 64 个字符")
	public String getSubmitBy() {
		return submitBy;
	}

	public void setSubmitBy(String submitBy) {
		this.submitBy = submitBy;
	}

	
	@Length(min=0, max=64, message="确认人长度不能超过 64 个字符")
	public String getConfirmBy() {
		return confirmBy;
	}

	public void setConfirmBy(String confirmBy) {
		this.confirmBy = confirmBy;
	}
	
	@Length(min=0, max=64, message="打印人长度不能超过 64 个字符")
	public String getPrintingBy() {
		return printingBy;
	}

	public void setPrintingBy(String printingBy) {
		this.printingBy = printingBy;
	}

	@Length(min=0, max=50, message="客户来源长度不能超过 50 个字符")
	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}
	
	@Length(min=0, max=20, message="订单来源长度不能超过 20 个字符")
	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	
	@Length(min=0, max=100, message="特权定金单号1长度不能超过 100 个字符")
	public String getPrivilege1() {
		return privilege1;
	}

	public void setPrivilege1(String privilege1) {
		this.privilege1 = privilege1;
	}
	
	@Length(min=0, max=100, message="特权定金单号2长度不能超过 100 个字符")
	public String getPrivilege2() {
		return privilege2;
	}

	public void setPrivilege2(String privilege2) {
		this.privilege2 = privilege2;
	}
	
	@Length(min=0, max=100, message="特权定金单号3长度不能超过 100 个字符")
	public String getPrivilege3() {
		return privilege3;
	}

	public void setPrivilege3(String privilege3) {
		this.privilege3 = privilege3;
	}

//	@Length(min=0, max=1000, message="优惠活动长度不能超过 1000 个字符")
//	public String getDiscount() {
//		return discount;
//	}
//
//	public void setDiscount(String discount) {
//		this.discount = discount;
//	}

//	public Double getDiscountAmount() {
//		return discountAmount;
//	}
//
//	public void setDiscountAmount(Double discountAmount) {
//		this.discountAmount = discountAmount;
//	}

	public List<AmOrderDiscount> getAmOrderDiscountList() {
		return amOrderDiscountList;
	}

	public void setAmOrderDiscountList(List<AmOrderDiscount> amOrderDiscountList) {
		this.amOrderDiscountList = amOrderDiscountList;
	}
	
	public List<AmOrderDetail> getAmOrderDetailList() {
		return amOrderDetailList;
	}

	public void setAmOrderDetailList(List<AmOrderDetail> amOrderDetailList) {
		this.amOrderDetailList = amOrderDetailList;
	}

	public String getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPrintingDate() {
		return printingDate;
	}
	public void setPrintingDate(String printingDate) {
		this.printingDate = printingDate;
	}

	public String getIsEnjoy() {
		return isEnjoy;
	}

	public void setIsEnjoy(String isEnjoy) {
		this.isEnjoy = isEnjoy;
	}

	public Double getOilSubsidy() {
		return oilSubsidy;
	}

	public void setOilSubsidy(Double oilSubsidy) {
		this.oilSubsidy = oilSubsidy;
	}

	public List<AmOrderImg> getImageList() {
		return imageList;
	}

	public void setImageList(List<AmOrderImg> imageList) {
		this.imageList = imageList;
	}

	public String getEnjoyVal() {
		return enjoyVal;
	}

	public void setEnjoyVal(String enjoyVal) {
		this.enjoyVal = enjoyVal;
	}
}