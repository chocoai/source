/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.track.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 退货跟踪单Entity
 * @author czy
 * @version 2018-06-21
 */
@Table(name="${_prefix}am_track_detail", alias="a", columns={
		@Column(name="document_code", attrName="documentCode.documentCode", label="单据编码"),
		@Column(name="entry_id", attrName="entryId", label="entry_id"),
		@Column(name="consumables_code", attrName="consumablesCode", label="物料编码"),
		@Column(name="consumables_name", attrName="consumablesName", label="物料名称", queryType=QueryType.LIKE),
		@Column(name="specifications", attrName="specifications", label="规格型号"),
		@Column(name="is_gifts", attrName="isGifts", label="是否赠品"),
		@Column(name="is_parts", attrName="isParts", label="是否配件"),
		@Column(name="parts_explain", attrName="partsExplain", label="配件说明"),
		@Column(name="actual_number", attrName="actualNumber", label="实发数量"),
		@Column(name="retreat_number", attrName="retreatNumber", label="实退数量"),
		@Column(name="package_number", attrName="packageNumber", label="包件数"),
		@Column(name="volume", attrName="volume", label="体积"),
		@Column(name="detail_code", attrName="detailCode", label="detail_code", isPK=true),
	}, orderBy="a.detail_code ASC"
)
public class AmTrackDetail extends DataEntity<AmTrackDetail> {
	
	private static final long serialVersionUID = 1L;
	private AmTrack documentCode;		// 单据编码 父类
	private String entryId;		// entry_id
	private String consumablesCode;		// 物料编码
	private String consumablesName;		// 物料名称
	private String specifications;		// 规格型号
	private String isGifts;		// 是否赠品
	private String isParts;		// 是否配件
	private String partsExplain;		// 配件说明
	private String actualNumber;		// 实发数量
	private String retreatNumber;		// 实退数量
	private String packageNumber;		// 包件数
	private String volume;		// 体积
	private String detailCode;		// detail_code
	
	public AmTrackDetail() {
		this(null);
	}


	public AmTrackDetail(AmTrack documentCode){
		this.documentCode = documentCode;
	}
	
	@NotBlank(message="单据编码不能为空")
	@Length(min=0, max=64, message="单据编码长度不能超过 64 个字符")
	public AmTrack getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(AmTrack documentCode) {
		this.documentCode = documentCode;
	}
	
	@Length(min=0, max=64, message="entry_id长度不能超过 64 个字符")
	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}
	
	@Length(min=0, max=64, message="物料编码长度不能超过 64 个字符")
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
	
	@Length(min=0, max=100, message="规格型号长度不能超过 100 个字符")
	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	
	@Length(min=0, max=100, message="是否赠品长度不能超过 100 个字符")
	public String getIsGifts() {
		return isGifts;
	}

	public void setIsGifts(String isGifts) {
		this.isGifts = isGifts;
	}
	
	@Length(min=0, max=100, message="是否配件长度不能超过 100 个字符")
	public String getIsParts() {
		return isParts;
	}

	public void setIsParts(String isParts) {
		this.isParts = isParts;
	}
	
	@Length(min=0, max=2000, message="配件说明长度不能超过 2000 个字符")
	public String getPartsExplain() {
		return partsExplain;
	}

	public void setPartsExplain(String partsExplain) {
		this.partsExplain = partsExplain;
	}
	
	@Length(min=0, max=100, message="实发数量长度不能超过 100 个字符")
	public String getActualNumber() {
		return actualNumber;
	}

	public void setActualNumber(String actualNumber) {
		this.actualNumber = actualNumber;
	}
	
	@Length(min=0, max=100, message="实退数量长度不能超过 100 个字符")
	public String getRetreatNumber() {
		return retreatNumber;
	}

	public void setRetreatNumber(String retreatNumber) {
		this.retreatNumber = retreatNumber;
	}
	
	@Length(min=0, max=100, message="包件数长度不能超过 100 个字符")
	public String getPackageNumber() {
		return packageNumber;
	}

	public void setPackageNumber(String packageNumber) {
		this.packageNumber = packageNumber;
	}
	
	@Length(min=0, max=100, message="体积长度不能超过 100 个字符")
	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}
	
	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	
}