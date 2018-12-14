/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.consumables.entity.AmLibIodetails;
import com.jeesite.modules.asset.consumables.dao.AmLibIodetailsDao;

/**
 * 耗材库位进出明细表Service
 * @author czy
 * @version 2018-05-24
 */
@Service
@Transactional(readOnly=true)
public class AmLibIodetailsService extends CrudService<AmLibIodetailsDao, AmLibIodetails> {

	@Autowired
	private AmLibIodetailsDao amLibIodetailsDao;
	
	/**
	 * 获取单条数据
	 * @param amLibIodetails
	 * @return
	 */
	@Override
	public AmLibIodetails get(AmLibIodetails amLibIodetails) {
		return super.get(amLibIodetails);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amLibIodetails
	 * @return
	 */
	@Override
	public Page<AmLibIodetails> findPage(Page<AmLibIodetails> page, AmLibIodetails amLibIodetails) {
		return super.findPage(page, amLibIodetails);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amLibIodetails
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmLibIodetails amLibIodetails) {
		super.save(amLibIodetails);
	}
	
	/**
	 * 更新状态
	 * @param amLibIodetails
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmLibIodetails amLibIodetails) {
		super.updateStatus(amLibIodetails);
	}
	
	/**
	 * 删除数据
	 * @param amLibIodetails
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmLibIodetails amLibIodetails) {
		super.delete(amLibIodetails);
	}
	@Transactional(readOnly=false)
	public void deleteDb(String billCode) {
		amLibIodetailsDao.deleteDb(billCode);
	}
	
}