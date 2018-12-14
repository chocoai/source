/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.trainning.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 培训反馈Entity
 * @author scarlett
 * @version 2018-06-11
 */
@Table(name="${_prefix}am_trainning_feedback", alias="a", columns={
		@Column(name="pkey", attrName="pkey", label="主键",isPK = true),
		@Column(name="trainning_code", attrName="trainningCode", label="培训单号",queryType=QueryType.LIKE),
		@Column(name="trainning_course", attrName="trainningCourse", label="培训课程",queryType=QueryType.LIKE),
		@Column(name="lecturer", attrName="lecturer", label="讲师"),
		@Column(name="written_by", attrName="writtenBy", label="填写人员",queryType=QueryType.LIKE),
		@Column(name="begin_time", attrName="beginTime", label="开始时间"),
		@Column(name="edit_time", attrName="editTime", label="填写时间"),
		@Column(name="training_method", attrName="trainingMethod", label="授课方式"),
		@Column(name="training_application", attrName="trainingApplication", label="培训应用"),
		@Column(name="comprehension", attrName="comprehension", label="课程理解"),
		@Column(name="notes", attrName="notes", label="学习笔记"),
	}, orderBy="a.trainning_code DESC"
)
public class AmTrainningFeedback extends DataEntity<AmTrainningFeedback> {
	
	private static final long serialVersionUID = 1L;
	private String pkey;
	private String trainningCode;		// 培训单号
	private String trainningCourse;		// 培训课程
	private String lecturer;		// 讲师
	private Date beginTime;		// 开始时间
	private Date editTime;		// 填写时间
	private Long trainingMethod;		// 授课方式
	private Long trainingApplication;		// 培训应用
	private Long comprehension;		// 课程理解
	private String notes;		// 学习笔记
	private String writtenBy;		// 填写人员
	
	public AmTrainningFeedback() {
		this(null);
	}

	public AmTrainningFeedback(String id){
		super(id);
	}
	
	public String getTrainningCode() {
		return trainningCode;
	}

	public void setTrainningCode(String trainningCode) {
		this.trainningCode = trainningCode;
	}
	

	@Length(min=0, max=64, message="培训课程长度不能超过 64 个字符")
	public String getTrainningCourse() {
		return trainningCourse;
	}

	public void setTrainningCourse(String trainningCourse) {
		this.trainningCourse = trainningCourse;
	}
	

	@Length(min=0, max=25, message="讲师长度不能超过 25 个字符")
	public String getLecturer() {
		return lecturer;
	}

	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}
	
	public Long getTrainingMethod() {
		return trainingMethod;
	}

	public void setTrainingMethod(Long trainingMethod) {
		this.trainingMethod = trainingMethod;
	}
	
	public Long getTrainingApplication() {
		return trainingApplication;
	}

	public void setTrainingApplication(Long trainingApplication) {
		this.trainingApplication = trainingApplication;
	}
	
	public Long getComprehension() {
		return comprehension;
	}

	public void setComprehension(Long comprehension) {
		this.comprehension = comprehension;
	}
	
	@Length(min=0, max=1000, message="学习笔记长度不能超过 1000 个字符")
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	@Length(min=0, max=64, message="填写人员长度不能超过 64 个字符")
	public String getWrittenBy() {
		return writtenBy;
	}
	public void setWrittenBy(String writtenBy) {
		this.writtenBy = writtenBy;
	}
	public String getPkey() { return pkey; }

	public void setPkey(String pkey) { this.pkey = pkey; }

}