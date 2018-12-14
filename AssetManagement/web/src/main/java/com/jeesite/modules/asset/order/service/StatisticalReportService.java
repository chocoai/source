/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.service;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.asset.util.TimeUtils;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.EmpUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.order.entity.StatisticalReport;
import com.jeesite.modules.asset.order.dao.StatisticalReportDao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 订单业绩统计Service
 * @author len
 * @version 2018-12-13
 */
@Service
@Transactional(readOnly=true)
public class StatisticalReportService extends CrudService<StatisticalReportDao, StatisticalReport> {
	
	/**
	 * 获取单条数据
	 * @param statisticalReport
	 * @return
	 */
	@Override
	public StatisticalReport get(StatisticalReport statisticalReport) {
		return super.get(statisticalReport);
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param statisticalReport
	 * @return
	 */
	@Override
	public Page<StatisticalReport> findPage(Page<StatisticalReport> page, StatisticalReport statisticalReport) {
		// 如果是员工
		if (User.USER_TYPE_EMPLOYEE.equals(UserUtils.getUser().getUserType())) {
			// 当前登录的部门编码
			String officeCode = EmpUtils.getOffice().getOfficeCode();
			// 开始时间
			String startTime = null;
			// 结束时间
			String endTime = null;
			if ("0".equals(statisticalReport.getDimensionality())) {
				if (statisticalReport.getDate_gte() == null) {
					// 如果开始时间为空 默认本月的第一天
					startTime = DateUtils.formatDate(TimeUtils.getBeginDayOfMonth());
				} else {
					startTime = DateUtils.formatDate(statisticalReport.getDate_gte());
				}
				if (statisticalReport.getDate_lte() == null) {
					// 如果结束时间为空 默认本月的最后一天
					endTime = DateUtils.formatDate(TimeUtils.getEndDayOfMonth());
				} else {
					endTime = DateUtils.formatDate(statisticalReport.getDate_lte());
				}
				statisticalReport.setDateList(TimeUtils.process(startTime, endTime));
			} else if ("1".equals(statisticalReport.getDimensionality())) {
				if (statisticalReport.getDate_gte() == null) {
					startTime = DateUtils.formatDate(new Date(), "yyyy-MM") ;
				} else {
					startTime = DateUtils.formatDate(statisticalReport.getDate_gte(), "yyyy-MM");
				}
				if (statisticalReport.getDate_lte() == null) {
					// 如果结束时间为空 默认本月的最后一天
					endTime = DateUtils.formatDate(new Date(), "yyyy-MM");
				} else {
					endTime = DateUtils.formatDate(statisticalReport.getDate_lte());
				}
				statisticalReport.setDateList(TimeUtils.getMonthBetween(startTime, endTime));
			}
			statisticalReport.setOfficeCode(officeCode);
			Page<StatisticalReport> page1 = super.findPage(page, statisticalReport);
			for (StatisticalReport statisticalReport1 : page1.getList()) {
				// 如果按月统计 日期显示年月
				if ("1".equals(statisticalReport.getDimensionality())) {
					String month = DateUtils.formatDate(statisticalReport1.getOrderDate(), "yyyy-MM");
					statisticalReport1.setOrderTime(month);
				} else {
					// 如果按日统计 日期显示年月日
					String date = DateUtils.formatDate(statisticalReport1.getOrderDate(), "yyyy-MM-dd");
					statisticalReport1.setOrderTime(date);
				}
			}
			return page1;
		} else {
			// 如果非员工 即管理员等 返回空
			return null;
		}
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param statisticalReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(StatisticalReport statisticalReport) {
		super.save(statisticalReport);
	}
	
	/**
	 * 更新状态
	 * @param statisticalReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(StatisticalReport statisticalReport) {
		super.updateStatus(statisticalReport);
	}
	
	/**
	 * 删除数据
	 * @param statisticalReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(StatisticalReport statisticalReport) {
		super.delete(statisticalReport);
	}
	
}