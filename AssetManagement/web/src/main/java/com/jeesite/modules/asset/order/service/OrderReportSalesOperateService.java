/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.order.entity.OrderReportSalesOperate;
import com.jeesite.modules.asset.order.dao.OrderReportSalesOperateDao;

/**
 * 梵导购操作统计报表Service
 * @author Albert
 * @version 2019-01-11
 */
@Service
@Transactional(readOnly=true)
public class OrderReportSalesOperateService extends CrudService<OrderReportSalesOperateDao, OrderReportSalesOperate> {
	
	/**
	 * 获取单条数据
	 * @param orderReportSalesOperate
	 * @return
	 */
	@Override
	public OrderReportSalesOperate get(OrderReportSalesOperate orderReportSalesOperate) {
		return super.get(orderReportSalesOperate);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param orderReportSalesOperate
	 * @return
	 */
	@Override
	public Page<OrderReportSalesOperate> findPage(Page<OrderReportSalesOperate> page, OrderReportSalesOperate orderReportSalesOperate) {
		return super.findPage(page, orderReportSalesOperate);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param orderReportSalesOperate
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(OrderReportSalesOperate orderReportSalesOperate) {
		super.save(orderReportSalesOperate);
	}
	
	/**
	 * 更新状态
	 * @param orderReportSalesOperate
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(OrderReportSalesOperate orderReportSalesOperate) {
		super.updateStatus(orderReportSalesOperate);
	}
	
	/**
	 * 删除数据
	 * @param orderReportSalesOperate
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(OrderReportSalesOperate orderReportSalesOperate) {
		super.delete(orderReportSalesOperate);
	}
	
}