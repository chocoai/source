/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.picture.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 图片审核单Entity
 * @author Scarllet
 * @version 2018-05-31
 */
@Table(name="${_prefix}pic_review", alias="a", columns={
		@Column(name="review_code", attrName="reviewCode", label="单据单号", isPK=true,queryType=QueryType.LIKE),
		@Column(name="review_date", attrName="reviewDate", label="单据日期"),
		@Column(name="review_type", attrName="reviewType", label="方案类型",queryType=QueryType.LIKE),
		@Column(name="review_status", attrName="reviewStatus", label="状态", comment="状态（0创建 1审核 2重新审核)"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class PicReview extends DataEntity<PicReview> {
	
	private static final long serialVersionUID = 1L;
	private String reviewCode;		// 单据单号
	private Date reviewDate;		// 单据日期
	private String reviewType;		// 方案类型
    private String filePath;
	private String fileId;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	private String fileExtension;




	public void setReviewStatusName(String reviewStatusName) {
		this.reviewStatusName = reviewStatusName;
	}

	private String reviewStatus;		// 状态（0创建 1审核 2重新审核)

	public String getReviewStatusName() {
		return reviewStatusName;
	}

	private String reviewStatusName;
	
	public PicReview() {
		this(null);
	}

	public PicReview(String id){
		super(id);
	}
	
	public String getReviewCode() {
		return reviewCode;
	}

	public void setReviewCode(String reviewCode) {
		this.reviewCode = reviewCode;
	}



	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="单据日期不能为空")
	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	
	@NotBlank(message="方案类型不能为空")
	@Length(min=0, max=100, message="方案类型长度不能超过 100 个字符")
	public String getReviewType() {
		return reviewType;
	}

	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
	}
	
	@NotBlank(message="状态不能为空")
	@Length(min=0, max=1, message="状态长度不能超过 1 个字符")
	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	
}