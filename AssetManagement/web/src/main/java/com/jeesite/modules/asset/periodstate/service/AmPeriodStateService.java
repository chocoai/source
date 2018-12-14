/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.periodstate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.periodstate.entity.AmPeriodState;
import com.jeesite.modules.asset.periodstate.dao.AmPeriodStateDao;

/**
 * 数据期间表Service
 * @author dwh
 * @version 2018-05-12
 */
@Service
@Transactional(readOnly=true)
public class AmPeriodStateService extends CrudService<AmPeriodStateDao, AmPeriodState> {
	@Autowired
	private AmPeriodStateDao amPeriodStateDao;
	/**
	 * 获取单条数据
	 * @param amPeriodState
	 * @return
	 */
	@Override
	public AmPeriodState get(AmPeriodState amPeriodState) {
		return super.get(amPeriodState);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amPeriodState
	 * @return
	 */
	@Override
	public Page<AmPeriodState> findPage(Page<AmPeriodState> page, AmPeriodState amPeriodState) {
		return super.findPage(page, amPeriodState);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amPeriodState
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmPeriodState amPeriodState) {
		super.save(amPeriodState);
	}
	
	/**
	 * 更新状态
	 * @param amPeriodState
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmPeriodState amPeriodState) {
		super.updateStatus(amPeriodState);
	}
	
	/**
	 * 删除数据
	 * @param amPeriodState
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmPeriodState amPeriodState) {
//		super.delete(amPeriodState);
		amPeriodStateDao.deleteDb(amPeriodState.getPeriodStateCode());
	}
	
}