/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * tb_logEntity
 * @author jace
 * @version 2018-07-23
 */
@Table(name="tb_log", alias="a", columns={
		@Column(name="sku_id", attrName="skuId", label="取sku_id", isPK=true,queryType=QueryType.LIKE),
		@Column(name="sku", attrName="sku", label="取sku的outer_id",queryType=QueryType.LIKE),
		@Column(name="user", attrName="user", label="操作用户",queryType=QueryType.LIKE),
		@Column(name="type", attrName="type", label="操作类型",queryType=QueryType.LIKE),
		@Column(name="describe", attrName="describe", label="描述",queryType=QueryType.LIKE),
		@Column(name="log_time", attrName="logTime", label="操作时间",queryType=QueryType.LIKE),
	}, orderBy="a.sku_id DESC"
)
public class TbLog extends DataEntity<TbLog> {
	
	private static final long serialVersionUID = 1L;
	private String skuId;		// 取sku_id
	private String sku;		// 取sku的outer_id
	private String user;		// 操作用户
	private String type;		// 操作类型
	private String describe;		// 描述
	private Date logTime;		// 操作时间
	
	public TbLog() {
		this(null);
	}

	public TbLog(String id){
		super(id);
	}
	
	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
	@Length(min=0, max=255, message="取sku的outer_id长度不能超过 255 个字符")
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	@Length(min=0, max=255, message="操作用户长度不能超过 255 个字符")
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	@Length(min=0, max=255, message="操作类型长度不能超过 255 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="描述长度不能超过 255 个字符")
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Length(min=0, max=255, message="操作时间长度不能超过 255 个字符")
    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }



	public Date getDate_gte() {
		return sqlMap.getWhere().getValue("log_time", QueryType.GTE);
	}

	public void setDate_gte(Date logTime) {
		sqlMap.getWhere().and("log_time", QueryType.GTE, logTime);
	}
	public Date getDate_lte() {
		return sqlMap.getWhere().getValue("log_time", QueryType.LTE);
	}

	public void setDate_lte(Date logTime) {
		sqlMap.getWhere().and("log_time", QueryType.LTE, logTime);
	}
}