/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.locationreport.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.locationreport.entity.LocationReport;
import com.jeesite.modules.asset.locationreport.dao.LocationReportDao;

/**
 * 库位管理Service
 * @author Mclaran
 * @version 2018-05-07
 */
@Service
@Transactional(readOnly=true)
public class LocationReportService extends CrudService<LocationReportDao, LocationReport> {
	
	/**
	 * 获取单条数据
	 * @param locationReport
	 * @return
	 */
	@Override
	public LocationReport get(LocationReport locationReport) {
		return super.get(locationReport);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param locationReport
	 * @return
	 */
	@Override
	public Page<LocationReport> findPage(Page<LocationReport> page, LocationReport locationReport) {
		return super.findPage(page, locationReport);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param locationReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(LocationReport locationReport) {
		super.save(locationReport);
	}
	
	/**
	 * 更新状态
	 * @param locationReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(LocationReport locationReport) {
		super.updateStatus(locationReport);
	}
	
	/**
	 * 删除数据
	 * @param locationReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(LocationReport locationReport) {
		super.delete(locationReport);
	}
	
}