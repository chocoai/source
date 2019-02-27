/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.register.service;

import java.util.List;

import com.jeesite.common.config.Global;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.sys.entity.EmpUser;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.entity.UserRole;
import com.jeesite.modules.sys.service.EmpUserService;
import com.jeesite.modules.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.distribution.register.entity.DistrRegister;
import com.jeesite.modules.distribution.register.dao.DistrRegisterDao;

/**
 * 分销注册申请Service
 * @author len
 * @version 2019-01-03
 */
@Service
@Transactional(readOnly=true)
public class DistrRegisterService extends CrudService<DistrRegisterDao, DistrRegister> {
	
	/**
	 * 获取单条数据
	 * @param distrRegister
	 * @return
	 */
	@Override
	public DistrRegister get(DistrRegister distrRegister) {
		return super.get(distrRegister);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param distrRegister
	 * @return
	 */
	@Override
	public Page<DistrRegister> findPage(Page<DistrRegister> page, DistrRegister distrRegister) {
		return super.findPage(page, distrRegister);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param distrRegister
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(DistrRegister distrRegister) {
		super.save(distrRegister);
	}
	
	/**
	 * 更新状态
	 * @param distrRegister
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(DistrRegister distrRegister) {
		super.updateStatus(distrRegister);
	}
	
	/**
	 * 删除数据
	 * @param distrRegister
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(DistrRegister distrRegister) {
		super.delete(distrRegister);
	}
}