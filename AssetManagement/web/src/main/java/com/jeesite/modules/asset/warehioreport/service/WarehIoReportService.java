/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.warehioreport.service;

import java.util.Date;

import com.jeesite.modules.asset.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.warehioreport.entity.WarehIoReport;
import com.jeesite.modules.asset.warehioreport.dao.WarehIoReportDao;

/**
 * 耗材仓库进出明细表Service
 * @author czy
 * @version 2018-05-25
 */
@Service
@Transactional(readOnly=true)
public class WarehIoReportService extends CrudService<WarehIoReportDao, WarehIoReport> {
	@Autowired
	private WarehIoReportDao warehIoReportDao;
	/**
	 * 获取单条数据
	 * @param warehIoReport
	 * @return
	 */
	@Override
	public WarehIoReport get(WarehIoReport warehIoReport) {
		return super.get(warehIoReport);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param warehIoReport
	 * @return
	 */
	@Override
	public Page<WarehIoReport> findPage(Page<WarehIoReport> page, WarehIoReport warehIoReport) {
		if ((warehIoReport.getDate_gte() == null || "".equals(warehIoReport.getDate_gte())) &&
				(warehIoReport.getDate_lte()== null || "".equals(warehIoReport.getDate_lte()))) {   // 默认值为系统时间当月第一天以及当月最后一天。
			warehIoReport.setDate_gte(TimeUtils.getBeginOfMonthByDate(new Date()));
			warehIoReport.setDate_lte(TimeUtils.getEndDayOfMonthByDate(new Date()));
		}
		return super.findPage(page, warehIoReport);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param warehIoReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(WarehIoReport warehIoReport) {
		super.save(warehIoReport);
	}
	
	/**
	 * 更新状态
	 * @param warehIoReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(WarehIoReport warehIoReport) {
		super.updateStatus(warehIoReport);
	}
	
	/**
	 * 删除数据
	 * @param warehIoReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(WarehIoReport warehIoReport) {
		super.delete(warehIoReport);
	}

	@Transactional(readOnly=true)
	public WarehIoReport findSum(WarehIoReport warehIoReport) {
		return warehIoReportDao.findSum(warehIoReport);
	}
}