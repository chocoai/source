/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.track.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * 退货跟踪单Entity
 * @author czy
 * @version 2018-06-21
 */
@Table(name="${_prefix}am_track_speed", alias="a", columns={
		@Column(name="document_code", attrName="documentCode.documentCode", label="单据编号"),
		@Column(name="track_person", attrName="trackPerson", label="跟踪人"),
		@Column(name="follow_date", attrName="followDate", label="跟进时间"),
		@Column(name="progress_speed", attrName="progressSpeed", label="进度"),
		@Column(name="progress_explain", attrName="progressExplain", label="进度说明"),
		@Column(name="next_date", attrName="nextDate", label="下次跟进日期"),
		@Column(name="detail_code", attrName="detailCode", label="明细编号", isPK=true),
	}, orderBy="a.detail_code ASC"
)
public class AmTrackSpeed extends DataEntity<AmTrackSpeed> {
	
	private static final long serialVersionUID = 1L;
	private AmTrack documentCode;		// 单据编号 父类
	private String trackPerson;		// 跟踪人
	private Date followDate;		// 跟进时间
	private String progressSpeed;		// 进度
	private String progressExplain;		// 进度说明
	private Date nextDate;		// 下次跟进日期
	private String detailCode;		// 明细编号
	
	public AmTrackSpeed() {
		this(null);
	}


	public AmTrackSpeed(AmTrack documentCode){
		this.documentCode = documentCode;
	}
	
	@NotBlank(message="单据编号不能为空")
	@Length(min=0, max=64, message="单据编号长度不能超过 64 个字符")
	public AmTrack getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(AmTrack documentCode) {
		this.documentCode = documentCode;
	}
	
	@Length(min=0, max=100, message="跟踪人长度不能超过 100 个字符")
	public String getTrackPerson() {
		return trackPerson;
	}

	public void setTrackPerson(String trackPerson) {
		this.trackPerson = trackPerson;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFollowDate() {
		return followDate;
	}

	public void setFollowDate(Date followDate) {
		this.followDate = followDate;
	}
	
	@Length(min=0, max=100, message="进度长度不能超过 100 个字符")
	public String getProgressSpeed() {
		return progressSpeed;
	}

	public void setProgressSpeed(String progressSpeed) {
		this.progressSpeed = progressSpeed;
	}
	
	@Length(min=0, max=2000, message="进度说明长度不能超过 2000 个字符")
	public String getProgressExplain() {
		return progressExplain;
	}

	public void setProgressExplain(String progressExplain) {
		this.progressExplain = progressExplain;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getNextDate() {
		return nextDate;
	}

	public void setNextDate(Date nextDate) {
		this.nextDate = nextDate;
	}
	
	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}
	
}