/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.file.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 文件上传表Entity
 * @author len
 * @version 2018-08-10
 */
@Table(name="${_prefix}am_file_upload", alias="a", columns={
		@Column(name="id", attrName="id", label="编号", isPK=true),
		@Column(name="file_id", attrName="fileId", label="文件编号"),
		@Column(name="file_name", attrName="fileName", label="文件名称", queryType=QueryType.LIKE),
		@Column(name="file_type", attrName="fileType", label="文件分类", comment="文件分类（image、media、file）"),
		@Column(name="biz_key", attrName="bizKey", label="业务主键"),
		@Column(name="biz_type", attrName="bizType", label="业务类型"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="file_content_type", attrName="fileContentType", label="文件内容类型"),
		@Column(name="file_extension", attrName="fileExtension", label="文件后缀扩展名"),
		@Column(name="file_md5", attrName="fileMd5", label="文件MD5"),
		@Column(name="file_path", attrName="filePath", label="文件相对路径"),
		@Column(name="file_real_path", attrName="fileRealPath", label="文件绝对路径"),
		@Column(name="file_size", attrName="fileSize", label="file_size"),
		@Column(name="file_size_format", attrName="fileSizeFormat", label="文件大小"),
		@Column(name="file_url", attrName="fileUrl", label="文件路径"),
		@Column(name="pic_status", attrName="picStatus", label="图片状态"),
		@Column(name="pic_remarks", attrName="picRemarks", label="图片批注"),
	}, orderBy="a.update_date ASC"
)
public class AmFileUpload extends DataEntity<AmFileUpload> {
	
	private static final long serialVersionUID = 1L;
	private String fileId;		// 文件编号
	private String fileName;		// 文件名称
	private String fileType;		// 文件分类（image、media、file）
	private String bizKey;		// 业务主键
	private String bizType;		// 业务类型
	private String fileContentType;		// 文件内容类型
	private String fileExtension;		// 文件后缀扩展名
	private String fileMd5;		// 文件MD5
	private String filePath;		// 文件相对路径
	private String fileRealPath;		// 文件绝对路径
	private Long fileSize;		// file_size
	private String fileSizeFormat;		// 文件大小
	private String fileUrl;		// 文件路径
	private String picStatus;//图片状态
	private String picRemarks;//图片批注
	private FileEntity fileEntity;

	public String getPicStatus() {
		return picStatus;
	}

	public void setPicStatus(String picStatus) {
		this.picStatus = picStatus;
	}

	public String getPicRemarks() {
		return picRemarks;
	}

	public void setPicRemarks(String picRemarks) {
		this.picRemarks = picRemarks;
	}

	public FileEntity getFileEntity() {
		return fileEntity;
	}

	public void setFileEntity(FileEntity fileEntity) {
		this.fileEntity = fileEntity;
	}

	public AmFileUpload() {
		this(null);
	}

	public AmFileUpload(String id){
		super(id);
	}
	
	@NotBlank(message="文件编号不能为空")
	@Length(min=0, max=64, message="文件编号长度不能超过 64 个字符")
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	@NotBlank(message="文件名称不能为空")
	@Length(min=0, max=500, message="文件名称长度不能超过 500 个字符")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@NotBlank(message="文件分类不能为空")
	@Length(min=0, max=20, message="文件分类长度不能超过 20 个字符")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	@Length(min=0, max=64, message="业务主键长度不能超过 64 个字符")
	public String getBizKey() {
		return bizKey;
	}

	public void setBizKey(String bizKey) {
		this.bizKey = bizKey;
	}
	
	@Length(min=0, max=64, message="业务类型长度不能超过 64 个字符")
	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	
	@Length(min=0, max=200, message="文件内容类型长度不能超过 200 个字符")
	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	
	@Length(min=0, max=100, message="文件后缀扩展名长度不能超过 100 个字符")
	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	
	@Length(min=0, max=64, message="文件MD5长度不能超过 64 个字符")
	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}
	
	@Length(min=0, max=1000, message="文件相对路径长度不能超过 1000 个字符")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Length(min=0, max=1000, message="文件绝对路径长度不能超过 1000 个字符")
	public String getFileRealPath() {
		return fileRealPath;
	}

	public void setFileRealPath(String fileRealPath) {
		this.fileRealPath = fileRealPath;
	}
	
	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
	@Length(min=0, max=100, message="文件大小长度不能超过 100 个字符")
	public String getFileSizeFormat() {
		return fileSizeFormat;
	}

	public void setFileSizeFormat(String fileSizeFormat) {
		this.fileSizeFormat = fileSizeFormat;
	}
	
	@Length(min=0, max=300, message="文件路径长度不能超过 300 个字符")
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
}