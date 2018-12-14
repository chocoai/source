/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.order.entity.AmOrderLog;
import com.jeesite.modules.asset.order.dao.AmOrderLogDao;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单管理日志异常日志表Service
 * @author len
 * @version 2018-11-12
 */
@Service
@Transactional(readOnly=true)
public class AmOrderLogService extends CrudService<AmOrderLogDao, AmOrderLog> {
	
	/**
	 * 获取单条数据
	 * @param amOrderLog
	 * @return
	 */
	@Override
	public AmOrderLog get(AmOrderLog amOrderLog) {
		return super.get(amOrderLog);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amOrderLog
	 * @return
	 */
	@Override
	public Page<AmOrderLog> findPage(Page<AmOrderLog> page, AmOrderLog amOrderLog) {
		return super.findPage(page, amOrderLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amOrderLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmOrderLog amOrderLog) {
		super.save(amOrderLog);
	}
	
	/**
	 * 更新状态
	 * @param amOrderLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmOrderLog amOrderLog) {
		super.updateStatus(amOrderLog);
	}
	
	/**
	 * 删除数据
	 * @param amOrderLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmOrderLog amOrderLog) {
		super.delete(amOrderLog);
	}

	/**
	 * 插入异常信息
	 * @param e
	 * @param request
	 */
	@Transactional(readOnly = false)
	public void insertLog(Exception e, HttpServletRequest request) {
		String uri = request.getRequestURI();
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		AmOrderLog amOrderLog = new AmOrderLog();
		amOrderLog.setLogInfo(sw.toString());
		amOrderLog.setApiUri(uri);
		amOrderLog.setCreateTime(new Date());
		amOrderLog.setIsNewRecord(true);
		this.save(amOrderLog);
	}
}