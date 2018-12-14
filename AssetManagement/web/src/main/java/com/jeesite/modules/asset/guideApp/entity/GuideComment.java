/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.guideApp.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 导购活动表Entity
 * @author len
 * @version 2018-12-07
 */
@Table(name="${_prefix}guide_comment", alias="a", columns={
		@Column(name="comment_code", attrName="commentCode", label="评论编码", isPK=true),
		@Column(name="activity_code", attrName="activityCode.activityCode", label="活动编码"),
		@Column(name="ask_by", attrName="askBy", label="提问人"),
		@Column(name="ask_shop", attrName="askShop", label="提问人所在门店"),
		@Column(name="ask_time", attrName="askTime", label="提问时间"),
		@Column(name="ask_id", attrName="askId", label="提问人id"),
		@Column(name="question", attrName="question", label="问题"),
		@Column(name="answer", attrName="answer", label="回复"),
		@Column(name="answer_by", attrName="answerBy", label="回复人"),
		@Column(name="answer_id", attrName="answerId", label="回复人id"),
		@Column(name="answer_time", attrName="answerTime", label="回复时间"),
	}, orderBy="a.comment_code ASC"
)
public class GuideComment extends DataEntity<GuideComment> {
	
	private static final long serialVersionUID = 1L;
	private String commentCode;		// 评论编码
	private GuideActivity activityCode;		// 活动编码 父类
	private String askBy;		// 提问人
	private Date askTime;		// 提问时间
	private String askId;		// 提问人id
	private String question;		// 问题
	private String answer;		// 回复
	private String answerBy;		// 回复人
	private String answerId;		// 回复人id
	private Date answerTime;		// 回复时间
	private String askShop;			// 提问人所在门店

	public String getAskShop() {
		return askShop;
	}

	public void setAskShop(String askShop) {
		this.askShop = askShop;
	}

	public GuideComment() {
		this(null);
	}


	public GuideComment(GuideActivity activityCode){
		this.activityCode = activityCode;
	}
	
	public String getCommentCode() {
		return commentCode;
	}

	public void setCommentCode(String commentCode) {
		this.commentCode = commentCode;
	}
	
	public GuideActivity getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(GuideActivity activityCode) {
		this.activityCode = activityCode;
	}
	
	@Length(min=0, max=64, message="提问人长度不能超过 64 个字符")
	public String getAskBy() {
		return askBy;
	}

	public void setAskBy(String askBy) {
		this.askBy = askBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAskTime() {
		return askTime;
	}

	public void setAskTime(Date askTime) {
		this.askTime = askTime;
	}
	
	@Length(min=0, max=64, message="提问人id长度不能超过 64 个字符")
	public String getAskId() {
		return askId;
	}

	public void setAskId(String askId) {
		this.askId = askId;
	}
	
	@Length(min=0, max=1000, message="问题长度不能超过 1000 个字符")
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	@Length(min=0, max=1000, message="回复长度不能超过 1000 个字符")
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@Length(min=0, max=64, message="回复人长度不能超过 64 个字符")
	public String getAnswerBy() {
		return answerBy;
	}

	public void setAnswerBy(String answerBy) {
		this.answerBy = answerBy;
	}
	
	@Length(min=0, max=64, message="回复人id长度不能超过 64 个字符")
	public String getAnswerId() {
		return answerId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
	}
	
}