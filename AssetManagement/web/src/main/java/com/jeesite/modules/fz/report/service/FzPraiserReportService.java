/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.report.service;

import java.util.List;
import java.util.Optional;

import com.jeesite.modules.asset.ding.entity.AccessDepartMent;
import com.jeesite.modules.asset.ding.entity.DepartmentUtil;
import com.jeesite.modules.asset.ding.entity.DingUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.fz.report.entity.FzPraiserReport;
import com.jeesite.modules.fz.report.dao.FzPraiserReportDao;

/**
 * 获赞者获赞数统计表Service
 * @author len
 * @version 2018-10-22
 */
@Service
@Transactional(readOnly=true)
public class FzPraiserReportService extends CrudService<FzPraiserReportDao, FzPraiserReport> {
	
	/**
	 * 获取单条数据
	 * @param fzPraiserReport
	 * @return
	 */
	@Override
	public FzPraiserReport get(FzPraiserReport fzPraiserReport) {
		return super.get(fzPraiserReport);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param fzPraiserReport
	 * @return
	 */
	@Override
	public Page<FzPraiserReport> findPage(Page<FzPraiserReport> page, FzPraiserReport fzPraiserReport) {
		Page<FzPraiserReport> page1 = super.findPage(page, fzPraiserReport);
		AccessDepartMent accessDepartMent = DepartmentUtil.access();
		for (FzPraiserReport report : page1.getList()) {
			Optional<DingUser> optionalDingUser = accessDepartMent.getDingUserList().stream().filter(s ->s.getUserid().equals(report.getUserId())).findFirst();
			if (optionalDingUser.isPresent()) {
				DingUser dingUser = optionalDingUser.get();
				String department = DepartmentUtil.getDepartment(dingUser, accessDepartMent.getDingUserDepartmentList(), accessDepartMent.getDepartmentList());
				report.setDepartment(department);
			}
		}
		return page1;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param fzPraiserReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(FzPraiserReport fzPraiserReport) {
		super.save(fzPraiserReport);
	}
	
	/**
	 * 更新状态
	 * @param fzPraiserReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FzPraiserReport fzPraiserReport) {
		super.updateStatus(fzPraiserReport);
	}
	
	/**
	 * 删除数据
	 * @param fzPraiserReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(FzPraiserReport fzPraiserReport) {
		super.delete(fzPraiserReport);
	}
	
}