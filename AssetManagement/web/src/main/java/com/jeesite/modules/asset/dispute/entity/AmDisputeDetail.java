/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.dispute.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 物流纠纷表Entity
 * @author czy
 * @version 2018-06-09
 */
@Table(name="${_prefix}am_dispute_detail", alias="a", columns={
		@Column(name="detail_code", attrName="detailCode", label="明细编码", isPK=true),
		@Column(name="document_code", attrName="documentCode.documentCode", label="单据编码"),
		@Column(name="consumables_code", attrName="consumablesCode", label="物料编号"),
		@Column(name="consumables_name", attrName="consumablesName", label="物料名称", queryType=QueryType.LIKE),
		@Column(name="specifications", attrName="specifications", label="规格型号"),
		@Column(name="amount", attrName="amount", label="数量"),
		@Column(name="dispute_reason", attrName="disputeReason", label="纠纷原因"),
		@Column(name="sign_status", attrName="signStatus", label="签收状态"),
		@Column(name="entry_id", attrName="entryId", label="明细表UUID"),
	}, orderBy="a.detail_code ASC"
)
public class AmDisputeDetail extends DataEntity<AmDisputeDetail> {
	
	private static final long serialVersionUID = 1L;
	private String detailCode;		// 明细编码
	private AmDispute documentCode;		// 单据编码 父类
	private String consumablesCode;		// 物料编号
	private String consumablesName;		// 物料名称
	private String specifications;		// 规格型号
	private Long amount;		// 数量
	private String disputeReason;		// 纠纷原因
	private String signStatus;		// 签收状态
	private String entryId;

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public AmDisputeDetail() {
		this(null);
	}


	public AmDisputeDetail(AmDispute documentCode){
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
	public AmDispute getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(AmDispute documentCode) {
		this.documentCode = documentCode;
	}
	
	@Length(min=0, max=64, message="物料编号长度不能超过 64 个字符")
	public String getConsumablesCode() {
		return consumablesCode;
	}

	public void setConsumablesCode(String consumablesCode) {
		this.consumablesCode = consumablesCode;
	}
	
	@Length(min=0, max=100, message="物料名称长度不能超过 100 个字符")
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
	
	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
	@Length(min=0, max=2000, message="纠纷原因长度不能超过 2000 个字符")
	public String getDisputeReason() {
		return disputeReason;
	}

	public void setDisputeReason(String disputeReason) {
		this.disputeReason = disputeReason;
	}
	
	@Length(min=0, max=200, message="签收状态长度不能超过 200 个字符")
	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}
	
}