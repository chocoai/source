/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.guideApp.entity;

import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;

/**
 * 导购活动表Entity
 * @author len
 * @version 2018-12-07
 */
@Table(name="${_prefix}guide_faq", alias="a", columns={
		@Column(name="faq_code", attrName="faqCode", label="常见问题编码", isPK=true),
		@Column(name="activity_code", attrName="activityCode.activityCode", label="活动编码"),
		@Column(name="faq_question", attrName="faqQuestion", label="问题", queryType = QueryType.LIKE),
		@Column(name="faq_answer", attrName="faqAnswer", label="回答", queryType = QueryType.LIKE),
	}, orderBy="a.faq_code ASC"
)
public class GuideFaq extends DataEntity<GuideFaq> {
	
	private static final long serialVersionUID = 1L;
	private String faqCode;		// 常见问题编码
	private GuideActivity activityCode;		// 活动编码 父类
	private String faqQuestion;		// 问题
	private String faqAnswer;		// 回答
	
	public GuideFaq() {
		this(null);
	}


	public GuideFaq(GuideActivity activityCode){
		this.activityCode = activityCode;
	}
	
	public String getFaqCode() {
		return faqCode;
	}

	public void setFaqCode(String faqCode) {
		this.faqCode = faqCode;
	}
	
	public GuideActivity getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(GuideActivity activityCode) {
		this.activityCode = activityCode;
	}
	
	@Length(min=0, max=2000, message="问题长度不能超过 2000 个字符")
	public String getFaqQuestion() {
		return faqQuestion;
	}

	public void setFaqQuestion(String faqQuestion) {
		this.faqQuestion = faqQuestion;
	}
	
	@Length(min=0, max=2000, message="回答长度不能超过 2000 个字符")
	public String getFaqAnswer() {
		return faqAnswer;
	}

	public void setFaqAnswer(String faqAnswer) {
		this.faqAnswer = faqAnswer;
	}
	
}