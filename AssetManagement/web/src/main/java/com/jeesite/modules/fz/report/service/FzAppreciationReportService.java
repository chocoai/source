/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.report.service;

import java.util.List;
import java.util.Optional;

import com.jeesite.modules.util.redis.RedisUtil;
import com.jeesite.modules.asset.ding.entity.DepartmentData;
import com.jeesite.modules.asset.ding.entity.DepartmentUtil;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.entity.DingUserDepartment;
import com.jeesite.modules.fz.utils.common.Variable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.fz.report.entity.FzAppreciationReport;
import com.jeesite.modules.fz.report.dao.FzAppreciationReportDao;

import javax.annotation.Resource;

/**
 * 赠赞数量统计表Service
 * @author scarlett
 * @version 2018-10-22
 */
@Service
@Transactional(readOnly=true)
public class FzAppreciationReportService extends CrudService<FzAppreciationReportDao, FzAppreciationReport> {
	@Resource
	private RedisUtil<String, List> redisList;
	
	/**
	 * 获取单条数据
	 * @param fzAppreciationReport
	 * @return
	 */
	@Override
	public FzAppreciationReport get(FzAppreciationReport fzAppreciationReport) {
		return super.get(fzAppreciationReport);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param fzAppreciationReport
	 * @return
	 */
	@Override
	public Page<FzAppreciationReport> findPage(Page<FzAppreciationReport> page, FzAppreciationReport fzAppreciationReport) {
		//return super.findPage(page, fzAppreciationReport);
		Page<FzAppreciationReport> page1 = super.findPage(page, fzAppreciationReport);
		// 获取缓存中所有部门
		List<DepartmentData> departmentList = redisList.get("dingDepartment" + Variable.dataBase + Variable.RANDOMID);
		// 获取部门用户中间表的数据
		List<DingUserDepartment> dingUserDepartmentList = redisList.get("dingUserDepartment" + Variable.dataBase + Variable.RANDOMID);
		// 获取所有用户
		List<DingUser> dingUserList = redisList.get("dingUser" + Variable.dataBase + Variable.RANDOMID);
		for (FzAppreciationReport fzAppreciationReport1 : page1.getList()) {
			Optional<DingUser> optionalDingUser = dingUserList.stream().filter(s ->s.getUserid().equals(fzAppreciationReport1.getUserid())).findFirst();
			if (optionalDingUser.isPresent()) {
				DingUser dingUser = optionalDingUser.get();
				String department = DepartmentUtil.getDepartment(dingUser, dingUserDepartmentList, departmentList);
				fzAppreciationReport1.setDepartment(department);
			}
		}
		return page1;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param fzAppreciationReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(FzAppreciationReport fzAppreciationReport) {
		super.save(fzAppreciationReport);
	}
	
	/**
	 * 更新状态
	 * @param fzAppreciationReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FzAppreciationReport fzAppreciationReport) {
		super.updateStatus(fzAppreciationReport);
	}
	
	/**
	 * 删除数据
	 * @param fzAppreciationReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(FzAppreciationReport fzAppreciationReport) {
		super.delete(fzAppreciationReport);
	}
	
}