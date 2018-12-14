/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables_report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.consumables_report.entity.ConsumablesReport;
import com.jeesite.modules.asset.consumables_report.dao.ConsumablesReportDao;

/**
 * consumables_reportService
 * @author Mclaran
 * @version 2018-05-03
 */
@Service
@Transactional(readOnly=true)
public class ConsumablesReportService extends CrudService<ConsumablesReportDao, ConsumablesReport> {
	@Autowired
	private ConsumablesReportDao consumablesReportDao;

	/**
	 * 获取单条数据
	 * @param consumablesReport
	 * @return*/
	@Override
	public ConsumablesReport get(ConsumablesReport consumablesReport) {
		return super.get(consumablesReport);
	}
	
/*	*
	 * 查询分页数据
	 * @param page 分页对象
	 * @param consumablesReport
	 * @return*/

	@Override
	public Page<ConsumablesReport> findPage(Page<ConsumablesReport> page, ConsumablesReport consumablesReport) {
		return super.findPage(page, consumablesReport);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param consumablesReport*/

	@Override
	@Transactional(readOnly=false)
	public void save(ConsumablesReport consumablesReport) {
		super.save(consumablesReport);
	}
	
	/**
	 * 更新状态
	 * @param consumablesReport*/

	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ConsumablesReport consumablesReport) {
		super.updateStatus(consumablesReport);
	}
	
	/**
	 * 删除数据
	 * @param consumablesReport*/

	@Override
	@Transactional(readOnly=false)
	public void delete(ConsumablesReport consumablesReport) {
		super.delete(consumablesReport);
	}

	public List findList(ConsumablesReport consumablesReport){

	//List list =	super.getList();
		return consumablesReportDao.findList(consumablesReport);
	}

/*
	public Integer findCount(){

		return consumablesReportDao.findCount();
	}
*/

}