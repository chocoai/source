/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fgcimage.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 梵工厂图片表Entity
 * @author len
 * @version 2018-08-13
 */
@Table(name="${_prefix}fgc_img", alias="a", columns={
		@Column(name="id", attrName="id", label="主键唯一标识", isPK=true),
		@Column(name="fid", attrName="fid", label="FID"),
		@Column(name="fentry_id", attrName="fentryId", label="FEntryID"),
		@Column(name="document_code", attrName="documentCode", label="单据编号", queryType=QueryType.LIKE),
		@Column(name="materiel_code", attrName="materielCode", label="物料编码"),
		@Column(name="materiel_name", attrName="materielName", label="物料名称"),
		@Column(name="photo_source", attrName="photoSource", label="照片来源", queryType=QueryType.LIKE),
		@Column(name="photo_url", attrName="photoUrl", label="照片查看"),
		@Column(name="operation_by", attrName="operationBy", label="操作人"),
		@Column(name="operation_date", attrName="operationDate", label="操作时间"),
	}, orderBy="a.id DESC"
)
public class FgcImg extends DataEntity<FgcImg> {
	
	private static final long serialVersionUID = 1L;
	private String fid;		// FID
	private String fentryId;		// FEntryID
	private String documentCode;		// 单据编号
	private String materielCode;		// 物料编码
	private String materielName;		// 物料名称
	private String photoSource;		// 照片来源
	private String photoUrl;		// 照片查看
	private String operationBy;		// 操作人
	private Date operationDate;		// 操作时间
	private String materiel;		// 物料

	public String getMateriel() {
		return materiel;
	}

	public void setMateriel(String materiel) {
		this.materiel = materiel;
	}

	public FgcImg() {
		this(null);
	}

	public FgcImg(String id){
		super(id);
	}
	
	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}
	
	public String getFentryId() {
		return fentryId;
	}

	public void setFentryId(String fentryId) {
		this.fentryId = fentryId;
	}
	
	@Length(min=0, max=64, message="单据编号长度不能超过 64 个字符")
	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	
	@Length(min=0, max=64, message="物料编码长度不能超过 64 个字符")
	public String getMaterielCode() {
		return materielCode;
	}

	public void setMaterielCode(String materielCode) {
		this.materielCode = materielCode;
	}
	
	@Length(min=0, max=200, message="物料名称长度不能超过 200 个字符")
	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}
	
	@Length(min=0, max=50, message="照片来源长度不能超过 50 个字符")
	public String getPhotoSource() {
		return photoSource;
	}

	public void setPhotoSource(String photoSource) {
		this.photoSource = photoSource;
	}
	
	@Length(min=0, max=300, message="照片查看长度不能超过 300 个字符")
	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	@Length(min=0, max=100, message="操作人长度不能超过 100 个字符")
	public String getOperationBy() {
		return operationBy;
	}

	public void setOperationBy(String operationBy) {
		this.operationBy = operationBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	
}