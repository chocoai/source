/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.log.service;

import java.util.List;

import com.jeesite.modules.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.achievement.log.entity.AchLog;
import com.jeesite.modules.achievement.log.dao.AchLogDao;

/**
 * 绩效卡操作日志Service
 * @author Philip Guan
 * @version 2018-11-28
 */
@Service
@Transactional(readOnly=true)
public class AchLogService extends CrudService<AchLogDao, AchLog> {
	
	/**
	 * 获取单条数据
	 * @param achLog
	 * @return
	 */
	@Override
	public AchLog get(AchLog achLog) {
		return super.get(achLog);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param achLog
	 * @return
	 */
	@Override
	public Page<AchLog> findPage(Page<AchLog> page, AchLog achLog) {
		return super.findPage(page, achLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param achLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AchLog achLog) {
		super.save(achLog);
	}
	
	/**
	 * 更新状态
	 * @param achLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AchLog achLog) {
		super.updateStatus(achLog);
	}
	
	/**
	 * 删除数据
	 * @param achLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AchLog achLog) {
		super.delete(achLog);
	}

	@Transactional(readOnly=false)
	public void addLog(String userId, String userName, String action, String desc) {
		addLog(userId, userName, action, desc);
	}

	@Transactional(readOnly=false)
	public void addLog(String userId, String userName, String action, String desc, String request) {
		AchLog entity = new AchLog();
		entity.setUserId(userId);
		entity.setUserName(userName);
		entity.setDescription(desc);
		entity.setAction(action);
		entity.setRequest(request);
		super.save(entity);
	}
}