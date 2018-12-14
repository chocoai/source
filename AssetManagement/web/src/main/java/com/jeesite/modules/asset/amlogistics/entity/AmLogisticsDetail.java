/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amlogistics.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 物流车查询单Entity
 * @author dwh
 * @version 2018-06-07
 */
@Table(name="${_prefix}am_logistics_detail", alias="a", columns={
		@Column(name="detail_code", attrName="detailCode", label="明细标号", isPK=true),
		@Column(name="document_code", attrName="documentCode.documentCode", label="单据编码"),
		@Column(name="consumables_code", attrName="consumablesCode", label="物料编号"),
		@Column(name="consumables_name", attrName="consumablesName", label="物料名字", queryType=QueryType.LIKE),
		@Column(name="specifications", attrName="specifications", label="规格型号"),
		@Column(name="actual_number", attrName="actualNumber", label="实发数量"),
		@Column(name="package_number", attrName="packageNumber", label="包件数"),
		@Column(name="is_gifts", attrName="isGifts", label="是否赠品"),
		@Column(name="stores", attrName="stores", label="店铺"),
		@Column(name="sales_order_code", attrName="salesOrderCode", label="销售订单号"),
		@Column(name="entry_id", attrName="entryId", label="明细表UUID"),


	}, orderBy="a.detail_code ASC"
)
public class AmLogisticsDetail extends DataEntity<AmLogisticsDetail> {
	
	private static final long serialVersionUID = 1L;
	private String detailCode;		// 明细标号
	private AmLogistics documentCode;		// 单据编码 父类
	private String consumablesCode;		// 物料编号
	private String consumablesName;		// 物料名字
	private String specifications;		// 规格型号
	private String actualNumber;		// 实发数量
	private String packageNumber;		// 包件数
	private String isGifts;		// 是否赠品
	private String stores;		// 店铺
	private String salesOrderCode;		// 销售订单号
	private  String entryId;


	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public AmLogisticsDetail() {
		this(null);
	}


	public AmLogisticsDetail(AmLogistics documentCode){
		this.documentCode = documentCode;
	}
	
	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	
	@NotBlank(message="单据编码不能为空")
	@Length(min=0, max=64, message="单据编码长度不能超过 64 个字符")
	public AmLogistics getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(AmLogistics documentCode) {
		this.documentCode = documentCode;
	}
	
	@Length(min=0, max=64, message="物料编号长度不能超过 64 个字符")
	public String getConsumablesCode() {
		return consumablesCode;
	}

	public void setConsumablesCode(String consumablesCode) {
		this.consumablesCode = consumablesCode;
	}
	
	@Length(min=0, max=100, message="物料名字长度不能超过 100 个字符")
	public String getConsumablesName() {
		return consumablesName;
	}

	public void setConsumablesName(String consumablesName) {
		this.consumablesName = consumablesName;
	}
	
	@Length(min=0, max=100, message="规格型号长度不能超过 100 个字符")
	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	
	public String getActualNumber() {
		return actualNumber;
	}

	public void setActualNumber(String actualNumber) {
		this.actualNumber = actualNumber;
	}
	
	public String getPackageNumber() {
		return packageNumber;
	}

	public void setPackageNumber(String packageNumber) {
		this.packageNumber = packageNumber;
	}
	
	@Length(min=0, max=1, message="是否赠品长度不能超过 1 个字符")
	public String getIsGifts() {
		return isGifts;
	}

	public void setIsGifts(String isGifts) {
		this.isGifts = isGifts;
	}
	
	@Length(min=0, max=200, message="店铺长度不能超过 200 个字符")
	public String getStores() {
		return stores;
	}

	public void setStores(String stores) {
		this.stores = stores;
	}
	
	@Length(min=0, max=64, message="销售订单号长度不能超过 64 个字符")
	public String getSalesOrderCode() {
		return salesOrderCode;
	}

	public void setSalesOrderCode(String salesOrderCode) {
		this.salesOrderCode = salesOrderCode;
	}
	
}