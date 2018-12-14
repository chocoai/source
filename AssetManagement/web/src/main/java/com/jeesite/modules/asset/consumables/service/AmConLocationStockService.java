/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.consumables.entity.AmConLocationStock;
import com.jeesite.modules.asset.consumables.dao.AmConLocationStockDao;

import java.util.Date;

/**
 * 库位库存表Service
 * @author dwh
 * @version 2018-05-08
 */
@Service
@Transactional(readOnly=true)
public class AmConLocationStockService extends CrudService<AmConLocationStockDao, AmConLocationStock> {
	@Autowired
	private AmConLocationStockDao amConLocationStockDao;
	/**
	 * 获取单条数据
	 * @param amConLocationStock
	 * @return
	 */
	@Override
	public AmConLocationStock get(AmConLocationStock amConLocationStock) {
		return super.get(amConLocationStock);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amConLocationStock
	 * @return
	 */
	@Override
	public Page<AmConLocationStock> findPage(Page<AmConLocationStock> page, AmConLocationStock amConLocationStock) {
		return super.findPage(page, amConLocationStock);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amConLocationStock
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmConLocationStock amConLocationStock) {
		Date date=new Date();
		if (amConLocationStock.getIsNewRecord()){
			amConLocationStock.setCreateDate(date);
		}
		super.save(amConLocationStock);
	}
	
	/**
	 * 更新状态
	 * @param amConLocationStock
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmConLocationStock amConLocationStock) {
		super.updateStatus(amConLocationStock);
	}
	
	/**
	 * 删除数据
	 * @param amConLocationStock
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmConLocationStock amConLocationStock) {
		super.delete(amConLocationStock);
	}

	/**
	 * 查询库位名称
	 * @param locationCode
	 * @return
	 */
	public String findLocationName (String locationCode) {
		return amConLocationStockDao.findLocationName(locationCode);
	}
}