/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.job.service;

import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.job.dao.SysJobLogDao;
import com.jeesite.modules.asset.job.entity.SysJobLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;

/**
 * 定时任务调度日志表Service
 * @author len
 * @version 2018-11-08
 */
@Service
@Transactional(readOnly=true)
public class SysJobLogService extends CrudService<SysJobLogDao, SysJobLog> {
	
	/**
	 * 获取单条数据
	 * @param sysJobLog
	 * @return
	 */
	@Override
	public SysJobLog get(SysJobLog sysJobLog) {
		return super.get(sysJobLog);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param sysJobLog
	 * @return
	 */
	@Override
	public Page<SysJobLog> findPage(Page<SysJobLog> page, SysJobLog sysJobLog) {
		return super.findPage(page, sysJobLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param sysJobLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SysJobLog sysJobLog) {
		super.save(sysJobLog);
	}
	
	/**
	 * 更新状态
	 * @param sysJobLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SysJobLog sysJobLog) {
		super.updateStatus(sysJobLog);
	}
	
	/**
	 * 删除数据
	 * @param sysJobLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SysJobLog sysJobLog) {
		super.delete(sysJobLog);
	}
	
}