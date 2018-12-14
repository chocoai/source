/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.basicroleinfoconfig.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.basicroleinfoconfig.dao.BasicRoleInfoDao;
import com.jeesite.modules.basicroleinfoconfig.entity.BasicRoleInfo;
import com.jeesite.modules.sys.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色表Service
 * @author dwh
 * @version 2018-07-26
 */
@Service
@Transactional(readOnly=true)
public class BasicRoleInfoService extends CrudService<BasicRoleInfoDao, BasicRoleInfo> {

	@Autowired
	private BasicRoleInfoDao roleInfoDao;
	/**
	 * 获取单条数据
	 * @param basicRoleInfo
	 * @return
	 */
	@Override
	public BasicRoleInfo get(BasicRoleInfo basicRoleInfo) {
		return super.get(basicRoleInfo);
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param basicRoleInfo
	 * @return
	 */
	@Override
	public Page<BasicRoleInfo> findPage(Page<BasicRoleInfo> page, BasicRoleInfo basicRoleInfo) {
		return super.findPage(page, basicRoleInfo);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param basicRoleInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(BasicRoleInfo basicRoleInfo) {
		super.save(basicRoleInfo);
	}

	/**
	 * 更新状态
	 * @param basicRoleInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(BasicRoleInfo basicRoleInfo) {
		super.updateStatus(basicRoleInfo);
	}

	/**
	 * 删除数据
	 * @param basicRoleInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(BasicRoleInfo basicRoleInfo) {
		super.delete(basicRoleInfo);
	}

    public List<BasicRoleInfo> getListByUserCode(String userCode) {
		return roleInfoDao.getListByUserCode(userCode);
    }
}