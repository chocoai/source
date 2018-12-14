/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.track.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;

/**
 * 退货跟踪单Entity
 * @author czy
 * @version 2018-06-21
 */
@Table(name="${_prefix}am_track_transfer", alias="a", columns={
		@Column(name="document_code", attrName="documentCode.documentCode", label="单据编号"),
		@Column(name="consumables_code", attrName="consumablesCode", label="物料编码"),
		@Column(name="consumables_name", attrName="consumablesName", label="物料名称", queryType=QueryType.LIKE),
		@Column(name="transfer_number", attrName="transferNumber", label="调货数量"),
		@Column(name="sales_order", attrName="salesOrder", label="销售订单"),
		@Column(name="client", attrName="client", label="客户"),
		@Column(name="seller", attrName="seller", label="销售员"),
		@Column(name="address", attrName="address", label="地址"),
		@Column(name="client_name", attrName="clientName", label="客户姓名", queryType=QueryType.LIKE),
		@Column(name="mobile_phone", attrName="mobilePhone", label="移动电话"),
		@Column(name="detail_address", attrName="detailAddress", label="详细地址"),
		@Column(name="entity_id", attrName="entityId", label="entity_id"),
		@Column(name="detail_code", attrName="detailCode", label="detail_code", isPK=true),
	}, orderBy="a.detail_code ASC"
)
public class AmTrackTransfer extends DataEntity<AmTrackTransfer> {
	
	private static final long serialVersionUID = 1L;
	private AmTrack documentCode;		// 单据编号 父类
	private String consumablesCode;		// 物料编码
	private String consumablesName;		// 物料名称
	private String transferNumber;		// 调货数量
	private String salesOrder;		// 销售订单
	private String client;		// 客户
	private String seller;		// 销售员
	private String address;		// 地址
	private String clientName;		// 客户姓名
	private String mobilePhone;		// 移动电话
	private String detailAddress;		// 详细地址
	private String entityId;		// entity_id
	private String detailCode;		// detail_code
	
	public AmTrackTransfer() {
		this(null);
	}


	public AmTrackTransfer(AmTrack documentCode){
		this.documentCode = documentCode;
	}
	
	public AmTrack getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(AmTrack documentCode) {
		this.documentCode = documentCode;
	}
	
	@Length(min=0, max=100, message="物料编码长度不能超过 100 个字符")
	public String getConsumablesCode() {
		return consumablesCode;
	}

	public void setConsumablesCode(String consumablesCode) {
		this.consumablesCode = consumablesCode;
	}
	
	@Length(min=0, max=200, message="物料名称长度不能超过 200 个字符")
	public String getConsumablesName() {
		return consumablesName;
	}

	public void setConsumablesName(String consumablesName) {
		this.consumablesName = consumablesName;
	}
	
	@Length(min=0, max=20, message="调货数量长度不能超过 20 个字符")
	public String getTransferNumber() {
		return transferNumber;
	}

	public void setTransferNumber(String transferNumber) {
		this.transferNumber = transferNumber;
	}
	
	@Length(min=0, max=200, message="销售订单长度不能超过 200 个字符")
	public String getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}
	
	@Length(min=0, max=200, message="客户长度不能超过 200 个字符")
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
	
	@Length(min=0, max=200, message="销售员长度不能超过 200 个字符")
	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}
	
	@Length(min=0, max=1000, message="地址长度不能超过 1000 个字符")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=100, message="客户姓名长度不能超过 100 个字符")
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
	
	@Length(min=0, max=1000, message="详细地址长度不能超过 1000 个字符")
	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	
	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	
	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	
}