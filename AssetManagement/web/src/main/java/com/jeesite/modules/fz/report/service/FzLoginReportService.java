/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.report.service;


import com.jeesite.modules.asset.ding.entity.DepartmentData;
import com.jeesite.modules.asset.ding.entity.DepartmentUtil;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.entity.DingUserDepartment;
import com.jeesite.modules.fz.utils.common.Variable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.fz.report.entity.FzLoginReport;
import com.jeesite.modules.fz.report.dao.FzLoginReportDao;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 用户登录记录报表Service
 * @author len
 * @version 2018-10-19
 */
@Service
@Transactional(readOnly=true)
public class FzLoginReportService extends CrudService<FzLoginReportDao, FzLoginReport> {

	@Resource
	private RedisTemplate<String, List> redisTemplate;
	/**
	 * 获取单条数据
	 * @param fzLoginReport
	 * @return
	 */
	@Override
	public FzLoginReport get(FzLoginReport fzLoginReport) {
		return super.get(fzLoginReport);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param fzLoginReport
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page<FzLoginReport> findPage(Page<FzLoginReport> page, FzLoginReport fzLoginReport) {
		Page<FzLoginReport> page1 = super.findPage(page, fzLoginReport);
		// 获取缓存中所有部门
		List<DepartmentData> departmentList = redisTemplate.opsForValue().get("dingDepartment" + Variable.dataBase + Variable.RANDOMID);
		// 获取部门用户中间表的数据
		List<DingUserDepartment> dingUserDepartmentList = redisTemplate.opsForValue().get("dingUserDepartment" + Variable.dataBase + Variable.RANDOMID);
		// 获取所有用户
		List<DingUser> dingUserList = redisTemplate.opsForValue().get("dingUser" + Variable.dataBase + Variable.RANDOMID);
		for (FzLoginReport fzLoginReport1 : page1.getList()) {
			Optional<DingUser> optionalDingUser = dingUserList.stream().filter(s ->s.getUserid().equals(fzLoginReport1.getUserId())).findFirst();
			if (optionalDingUser.isPresent()) {
				DingUser dingUser = optionalDingUser.get();
				String department = DepartmentUtil.getDepartment(dingUser, dingUserDepartmentList, departmentList);
				fzLoginReport1.setDepartment(department);
			}
		}

		return page1;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param fzLoginReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(FzLoginReport fzLoginReport) {
		super.save(fzLoginReport);
	}
	
	/**
	 * 更新状态
	 * @param fzLoginReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FzLoginReport fzLoginReport) {
		super.updateStatus(fzLoginReport);
	}
	
	/**
	 * 删除数据
	 * @param fzLoginReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(FzLoginReport fzLoginReport) {
		super.delete(fzLoginReport);
	}
	
}