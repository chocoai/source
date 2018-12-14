/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amlocation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.amlocation.entity.AmLocation;
import com.jeesite.modules.asset.amlocation.dao.AmLocationDao;

/**
 * 库位管理Service
 * @author Mclaran
 * @version 2018-05-03
 */
@Service
@Transactional(readOnly=true)
public class AmLocationService extends CrudService<AmLocationDao, AmLocation> {
	@Autowired
	private AmLocationDao amLocationDao;
	/**
	 * 获取单条数据
	 * @param amLocation
	 * @return
	 */
	@Override
	public AmLocation get(AmLocation amLocation) {
		return super.get(amLocation);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amLocation
	 * @return
	 */
	@Override
	public Page<AmLocation> findPage(Page<AmLocation> page, AmLocation amLocation) {
		return super.findPage(page, amLocation);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amLocation
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmLocation amLocation) {
		super.save(amLocation);
	}
	
	/**
	 * 更新状态
	 * @param amLocation
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmLocation amLocation) {
		super.updateStatus(amLocation);
	}
	
	/**
	 * 删除数据
	 * @param amLocation
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmLocation amLocation) {
//		super.delete(amLocation);
		amLocationDao.deleteDb(amLocation.getLocationCode());
	}
	
}