/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.dispute.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import org.hibernate.validator.constraints.Length;

/**
 * 物流纠纷单图片Entity
 * @author czy
 * @version 2018-06-19
 */
@Table(name="${_prefix}am_dispute_img", alias="a", columns={
		@Column(name="document_code", attrName="documentCode", label="单据编码", isPK=true),
		@Column(name="fileid1", attrName="fileid1", label="图片1"),
		@Column(name="fileid2", attrName="fileid2", label="图片2"),
		@Column(name="fileid3", attrName="fileid3", label="图片3"),
		@Column(name="fileid4", attrName="fileid4", label="图片4"),
		@Column(name="fileid5", attrName="fileid5", label="图片5"),
		@Column(name="fileid6", attrName="fileid6", label="图片6"),
	}, orderBy="a.document_code DESC"
)

public class AmDisputeImg extends DataEntity<AmDisputeImg> {
	
	private static final long serialVersionUID = 1L;
	private String documentCode;		// 单据编码
	private String fileid1;		// 图片1
	private String fileid2;		// 图片2
	private String fileid3;		// 图片3
	private String fileid4;		// 图片4
	private String fileid5;		// 图片5
	private String fileid6;		// 图片6
	
	public AmDisputeImg() {
		this(null);
	}

	public AmDisputeImg(String id){
		super(id);
	}
	
	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
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
	
}