/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.fzlogin.service;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.asset.ding.FzTask;
import com.jeesite.modules.asset.ding.entity.DingUser;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.fz.fzlogin.entity.FzLoginRecord;
import com.jeesite.modules.fz.fzlogin.dao.FzLoginRecordDao;

/**
 * 梵赞登录记录Service
 * @author len
 * @version 2018-10-09
 */
@Service
@Transactional(readOnly=true)
public class FzLoginRecordService extends CrudService<FzLoginRecordDao, FzLoginRecord> {
	@Autowired
	private AmqpTemplate rabbitTemplate;
	/**
	 * 获取单条数据
	 * @param fzLoginRecord
	 * @return
	 */
	@Override
	public FzLoginRecord get(FzLoginRecord fzLoginRecord) {
		return super.get(fzLoginRecord);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param fzLoginRecord
	 * @return
	 */
	@Override
	public Page<FzLoginRecord> findPage(Page<FzLoginRecord> page, FzLoginRecord fzLoginRecord) {
		return super.findPage(page, fzLoginRecord);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param fzLoginRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(FzLoginRecord fzLoginRecord) {
		super.save(fzLoginRecord);
	}
	
	/**
	 * 更新状态
	 * @param fzLoginRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FzLoginRecord fzLoginRecord) {
		super.updateStatus(fzLoginRecord);
	}
	
	/**
	 * 删除数据
	 * @param fzLoginRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(FzLoginRecord fzLoginRecord) {
		super.delete(fzLoginRecord);
	}

	/**
	 * 根据登录获取的信息插入登录记录表
	 * @param dingUser
	 */
	@Transactional(readOnly = false)
	public void saveData(DingUser dingUser) {
		FzLoginRecord fzLoginRecord = new FzLoginRecord();
		// 用户id
		fzLoginRecord.setUserId(dingUser.getUserid());
		// 英文名
		fzLoginRecord.setEmpName(dingUser.getName());
		// 工号
		fzLoginRecord.setJobNumber(dingUser.getJobnumber());
		// 办公地点
		fzLoginRecord.setWorkPlace(dingUser.getWorkPlace());
		// 登录时间
		fzLoginRecord.setLoginDate(new Date());
		String extattr = dingUser.getExtattr();
		String chineseName = JSONObject.parseObject(extattr).get("中文名").toString();
		fzLoginRecord.setChineseName(chineseName);
		// 插入登录记录表\
//		rabbitTemplate.convertAndSend("fzLoginRecord",fzLoginRecord);
		rabbitTemplate.convertAndSend(FzTask.loginQueuesP, fzLoginRecord);
	}
}