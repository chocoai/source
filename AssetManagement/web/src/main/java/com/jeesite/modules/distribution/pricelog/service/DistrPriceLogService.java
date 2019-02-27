/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.pricelog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.distribution.pricelog.entity.DistrPriceLog;
import com.jeesite.modules.distribution.pricelog.dao.DistrPriceLogDao;

/**
 * 分销价修改日志Service
 * @author len
 * @version 2019-01-08
 */
@Service
@Transactional(readOnly=true)
public class DistrPriceLogService extends CrudService<DistrPriceLogDao, DistrPriceLog> {
	
	/**
	 * 获取单条数据
	 * @param distrPriceLog
	 * @return
	 */
	@Override
	public DistrPriceLog get(DistrPriceLog distrPriceLog) {
		return super.get(distrPriceLog);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param distrPriceLog
	 * @return
	 */
	@Override
	public Page<DistrPriceLog> findPage(Page<DistrPriceLog> page, DistrPriceLog distrPriceLog) {
		return super.findPage(page, distrPriceLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param distrPriceLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(DistrPriceLog distrPriceLog) {
		super.save(distrPriceLog);
	}
	
	/**
	 * 更新状态
	 * @param distrPriceLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(DistrPriceLog distrPriceLog) {
		super.updateStatus(distrPriceLog);
	}
	
	/**
	 * 删除数据
	 * @param distrPriceLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(DistrPriceLog distrPriceLog) {
		super.delete(distrPriceLog);
	}
	
}