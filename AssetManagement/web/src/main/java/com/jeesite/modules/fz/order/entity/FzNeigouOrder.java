/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.order.entity;

import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelFields;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.Valid;

/**
 * 梵赞兑换订单表Entity
 * @author easter
 * @version 2018-11-26
 */
@Table(name="fz_neigou_order", alias="a", columns={
		@Column(name="order_id", attrName="orderId", label="订单id", isPK=true,queryType = QueryType.LIKE),
		@Column(name="pay_status", attrName="payStatus", label="支付状态 ", comment="支付状态 1:未支付 2:已支付 3:已退款 4:退款一部分"),
		@Column(name="order_status", attrName="orderStatus", label="订单状态 ", comment="订单状态 1:未完成 2:已取消 3:已完成"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="bn", attrName="bn", label="商品sku_id"),
		@Column(name="nums", attrName="nums", label="兑换数量"),
		@Column(name="receiver_mobile", attrName="receiverMobile", label="收货人手机号码"),
		@Column(name="detail_address", attrName="detailAddress", label="收货详细地址"),
		@Column(name="province", attrName="province", label="收货省名"),
		@Column(name="city", attrName="city", label="收货市名"),
		@Column(name="county", attrName="county", label="收货县名"),
		@Column(name="town", attrName="town", label="收货城镇名"),
		@Column(name="money", attrName="money", label="兑换消耗的钱"),
		@Column(name="fz_num", attrName="fzNum", label="兑换消耗的梵砖"),
		@Column(name="userid", attrName="userid", label="创建订单的用户id"),
		@Column(name="refund_num", attrName="refundNum", label="此订单退款积分"),
		@Column(name="p_id", attrName="pId", label="父订单id"),
		@Column(name="ship_status", attrName="shipStatus", label="发货状态"),
		@Column(name="user_name", attrName="userName", label="用户名",queryType = QueryType.LIKE),
		@Column(name="ship_name", attrName="shipName", label="收货人姓名"),
		@Column(name="logi_name", attrName="logiName", label="快递公司"),
		@Column(name="logi_no", attrName="logiNo", label="快递号"),
		@Column(name="title", attrName="title", label="商品名称"),
	}, orderBy="a.update_date DESC"
)
public class FzNeigouOrder extends DataEntity<FzNeigouOrder> {

	@Valid
	@ExcelFields({
			@ExcelField(title="订单id", attrName="orderId", align=ExcelField.Align.CENTER, sort=10),
			@ExcelField(title="用户名", attrName="userName", align=ExcelField.Align.CENTER, sort=10),
			@ExcelField(title="支付状态", attrName="payStatus", align = ExcelField.Align.CENTER, sort=20, dictType = "fz_neigou_order_status"),
			@ExcelField(title="订单状态", attrName="orderStatus", align=ExcelField.Align.CENTER, sort=30,dictType = "fz_neigou_order_payStatus"),
			@ExcelField(title="创建时间", attrName="createDate", align=ExcelField.Align.CENTER, sort=40,dataFormat = "yyyy-MM-dd HH:mm:ss"),
			@ExcelField(title="收货人", attrName="shipName", align=ExcelField.Align.LEFT, sort=50),
			@ExcelField(title="收货人电话", attrName="receiverMobile", align=ExcelField.Align.CENTER, sort=60),
			@ExcelField(title="收货地址", attrName="detailAddress", align=ExcelField.Align.CENTER, sort=70),
			@ExcelField(title="收货省", attrName="province", align=ExcelField.Align.CENTER, sort=80),
			@ExcelField(title="收货市", attrName="city", align=ExcelField.Align.CENTER, sort=95),
			@ExcelField(title="收货县", attrName="county", align=ExcelField.Align.LEFT, sort=100),
			@ExcelField(title="收货城镇", attrName="town", align=ExcelField.Align.CENTER, sort=100),
			@ExcelField(title="支付金额", attrName="money", align=ExcelField.Align.CENTER, sort=100, dataFormat = "0.00"),
			@ExcelField(title="消耗梵钻", attrName="fzNum", align=ExcelField.Align.CENTER, sort=100, dataFormat = "0.00"),
			@ExcelField(title="退回梵钻", attrName="refundNum", align=ExcelField.Align.CENTER, sort=100, dataFormat = "0.00"),
			@ExcelField(title="发货状态", attrName="shipStatus", align=ExcelField.Align.CENTER, sort=100, dictType = "fz_neigou_order_ship_status"),
	})
	private static final long serialVersionUID = 1L;
	private String orderId;		// 订单id
	private String payStatus;		// 支付状态 1:未支付 2:已支付 3:已退款
	private String orderStatus;		// 订单状态 1:未完成 2:已取消 3:已完成
	private String bn;		// 商品sku_id
	private Long nums;		// 兑换数量
	private String receiverMobile;		// 收货人手机号码
	private String detailAddress;		// 收货详细地址
	private String province;		// 收货省名
	private String city;		// 收货市名
	private String county;		// 收货县名
	private String town;		// 收货城镇名
	private Double money;		// 兑换消耗的钱
	private Double fzNum;		// 兑换消耗的梵砖
	private String userid;		// 创建订单的用户id
	private Double refundNum;  //此订单退款积分
	private String pId;		//父订单,为0的时候代表没有父订单
	private String shipStatus;  //发货状态 1：未发货 2：已发货
	private String userName;   //用户名
	private String shipName;   //收货人姓名
	private String 	logiName;	//快递公司
	private String logiNo;	//快递号
	private String title;      //商品名称
	private String query;		// 用于查询子订单

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLogiName() {
		return logiName;
	}

	public void setLogiName(String logiName) {
		this.logiName = logiName;
	}

	public String getLogiNo() {
		return logiNo;
	}

	public void setLogiNo(String logiNo) {
		this.logiNo = logiNo;
	}

	public FzNeigouOrder() {
		this(null);
	}

	public String getpId() {
		return pId;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getShipStatus() {
		return shipStatus;
	}

	public void setShipStatus(String shipStatus) {
		this.shipStatus = shipStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Double getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(Double refundNum) {
		this.refundNum = refundNum;
	}

	public FzNeigouOrder(String id){
		super(id);
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=1, message="支付状态 1长度不能超过 1 个字符")
	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	@Length(min=0, max=1, message="订单状态 1长度不能超过 1 个字符")
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	@Length(min=0, max=30, message="商品sku_id长度不能超过 30 个字符")
	public String getBn() {
		return bn;
	}

	public void setBn(String bn) {
		this.bn = bn;
	}
	
	public Long getNums() {
		return nums;
	}

	public void setNums(Long nums) {
		this.nums = nums;
	}
	
	@Length(min=0, max=32, message="收货人手机号码长度不能超过 32 个字符")
	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	
	@Length(min=0, max=64, message="收货详细地址长度不能超过 64 个字符")
	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	
	@Length(min=0, max=64, message="收货省名长度不能超过 64 个字符")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=64, message="收货市名长度不能超过 64 个字符")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=64, message="收货县名长度不能超过 64 个字符")
	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}
	
	@Length(min=0, max=64, message="收货城镇名长度不能超过 64 个字符")
	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}
	
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
	public Double getFzNum() {
		return fzNum;
	}

	public void setFzNum(Double fzNum) {
		this.fzNum = fzNum;
	}
	
	@Length(min=0, max=255, message="创建订单的用户id长度不能超过 255 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}