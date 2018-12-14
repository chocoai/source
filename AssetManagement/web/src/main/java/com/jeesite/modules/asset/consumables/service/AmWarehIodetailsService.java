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
import com.jeesite.modules.asset.consumables.entity.AmWarehIodetails;
import com.jeesite.modules.asset.consumables.dao.AmWarehIodetailsDao;

/**
 * 耗材仓库进出明细表Service
 * @author czy
 * @version 2018-05-24
 */
@Service
@Transactional(readOnly=true)
public class AmWarehIodetailsService extends CrudService<AmWarehIodetailsDao, AmWarehIodetails> {

	@Autowired
	private AmWarehIodetailsDao amWarehIodetailsDao;
	/**
	 * 获取单条数据
	 * @param amWarehIodetails
	 * @return
	 */
	@Override
	public AmWarehIodetails get(AmWarehIodetails amWarehIodetails) {
		return super.get(amWarehIodetails);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amWarehIodetails
	 * @return
	 */
	@Override
	public Page<AmWarehIodetails> findPage(Page<AmWarehIodetails> page, AmWarehIodetails amWarehIodetails) {
		return super.findPage(page, amWarehIodetails);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amWarehIodetails
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmWarehIodetails amWarehIodetails) {
		super.save(amWarehIodetails);
	}
	
	/**
	 * 更新状态
	 * @param amWarehIodetails
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmWarehIodetails amWarehIodetails) {
		super.updateStatus(amWarehIodetails);
	}
	
	/**
	 * 删除数据
	 * @param amWarehIodetails
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmWarehIodetails amWarehIodetails) {
		super.delete(amWarehIodetails);
	}
	@Transactional(readOnly=false)
	public void deleteDb(String billCode) {
		amWarehIodetailsDao.deleteDb(billCode);
	}
}