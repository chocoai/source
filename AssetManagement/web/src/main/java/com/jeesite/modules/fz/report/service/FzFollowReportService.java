/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.report.service;

import java.util.List;
import java.util.Optional;

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
import com.jeesite.modules.fz.report.entity.FzFollowReport;
import com.jeesite.modules.fz.report.dao.FzFollowReportDao;

import javax.annotation.Resource;

/**
 * 跟赞数量统计表Service
 * @author scarlett
 * @version 2018-10-22
 */
@Service
@Transactional(readOnly=true)
public class FzFollowReportService extends CrudService<FzFollowReportDao, FzFollowReport> {
	@Resource
	private RedisTemplate<String, List> redisTemplate;
	
	/**
	 * 获取单条数据
	 * @param fzFollowReport
	 * @return
	 */
	@Override
	public FzFollowReport get(FzFollowReport fzFollowReport) {
		return super.get(fzFollowReport);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param fzFollowReport
	 * @return
	 */
	@Override
	public Page<FzFollowReport> findPage(Page<FzFollowReport> page, FzFollowReport fzFollowReport) {
		//return super.findPage(page, fzFollowReport);
		Page<FzFollowReport> page1 = super.findPage(page, fzFollowReport);
		// 获取缓存中所有部门
		List<DepartmentData> departmentList = redisTemplate.opsForValue().get("dingDepartment" + Variable.dataBase + Variable.RANDOMID);
		// 获取部门用户中间表的数据
		List<DingUserDepartment> dingUserDepartmentList = redisTemplate.opsForValue().get("dingUserDepartment" + Variable.dataBase + Variable.RANDOMID);
		// 获取所有用户
		List<DingUser> dingUserList = redisTemplate.opsForValue().get("dingUser" + Variable.dataBase + Variable.RANDOMID);
		for (FzFollowReport fzFollowReport1 : page1.getList()) {
			Optional<DingUser> optionalDingUser = dingUserList.stream().filter(s ->s.getUserid().equals(fzFollowReport1.getUserid())).findFirst();
			if (optionalDingUser.isPresent()) {
				DingUser dingUser = optionalDingUser.get();
				String department = DepartmentUtil.getDepartment(dingUser, dingUserDepartmentList, departmentList);
				fzFollowReport1.setDepartment(department);
			}
		}
		return page1;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param fzFollowReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(FzFollowReport fzFollowReport) {
		super.save(fzFollowReport);
	}
	
	/**
	 * 更新状态
	 * @param fzFollowReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FzFollowReport fzFollowReport) {
		super.updateStatus(fzFollowReport);
	}
	
	/**
	 * 删除数据
	 * @param fzFollowReport
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(FzFollowReport fzFollowReport) {
		super.delete(fzFollowReport);
	}
	
}