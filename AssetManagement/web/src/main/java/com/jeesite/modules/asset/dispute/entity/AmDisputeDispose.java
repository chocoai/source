/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.dispute.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * 物流纠纷表Entity
 * @author czy
 * @version 2018-06-09
 */
@Table(name="${_prefix}am_dispute_dispose", alias="a", columns={
		@Column(name="detail_code", attrName="detailCode", label="明细编码", isPK=true),
		@Column(name="document_code", attrName="documentCode.documentCode", label="单据编码"),
		@Column(name="dispose_by", attrName="disposeBy", label="处理人"),
		@Column(name="dispose_date", attrName="disposeDate", label="处理时间"),
		@Column(name="process_content", attrName="processContent", label="处理跟进内容"),
		@Column(name="process_result", attrName="processResult", label="处理结果"),
	}, orderBy="a.detail_code ASC, a.detail_code ASC"
)
public class AmDisputeDispose extends DataEntity<AmDisputeDispose> {
	
	private static final long serialVersionUID = 1L;
	private String detailCode;		// 明细编码
	private AmDispute documentCode;		// 单据编码 父类
	private String disposeBy;		// 处理人
	private Date disposeDate;		// 处理时间
	private String processContent;		// 处理跟进内容
	private String processResult;		// 处理结果
	
	public AmDisputeDispose() {
		this(null);
	}

	public AmDisputeDispose(AmDispute documentCode){
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
	
	@Length(min=0, max=64, message="处理人长度不能超过 64 个字符")
	public String getDisposeBy() {
		return disposeBy;
	}

	public void setDisposeBy(String disposeBy) {
		this.disposeBy = disposeBy;
	}

	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDisposeDate() {
		return disposeDate;
	}

	public void setDisposeDate(Date disposeDate) {
		this.disposeDate = disposeDate;
	}

	
	@Length(min=0, max=2000, message="处理跟进内容长度不能超过 2000 个字符")
	public String getProcessContent() {
		return processContent;
	}

	public void setProcessContent(String processContent) {
		this.processContent = processContent;
	}

	
	@Length(min=0, max=2000, message="处理结果长度不能超过 2000 个字符")
	public String getProcessResult() {
		return processResult;
	}

	public void setProcessResult(String processResult) {
		this.processResult = processResult;
	}
	
}