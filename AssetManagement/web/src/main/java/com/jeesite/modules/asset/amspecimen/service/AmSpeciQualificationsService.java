/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amspecimen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.amspecimen.entity.AmSpeciQualifications;
import com.jeesite.modules.asset.amspecimen.dao.AmSpeciQualificationsDao;

/**
 * 样品进度单文件表Service
 * @author dwh
 * @version 2018-07-10
 */
@Service
@Transactional(readOnly=true)
public class AmSpeciQualificationsService extends CrudService<AmSpeciQualificationsDao, AmSpeciQualifications> {

	@Autowired
	private AmSpeciQualificationsDao amSpeciQualificationsDao;
	/**
	 * 获取单条数据
	 * @param amSpeciQualifications
	 * @return
	 */
	@Override
	public AmSpeciQualifications get(AmSpeciQualifications amSpeciQualifications) {
		return super.get(amSpeciQualifications);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amSpeciQualifications
	 * @return
	 */
	@Override
	public Page<AmSpeciQualifications> findPage(Page<AmSpeciQualifications> page, AmSpeciQualifications amSpeciQualifications) {
		return super.findPage(page, amSpeciQualifications);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amSpeciQualifications
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmSpeciQualifications amSpeciQualifications) {
		super.save(amSpeciQualifications);
	}
	
	/**
	 * 更新状态
	 * @param amSpeciQualifications
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmSpeciQualifications amSpeciQualifications) {
		super.updateStatus(amSpeciQualifications);
	}
	
	/**
	 * 删除数据
	 * @param amSpeciQualifications
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmSpeciQualifications amSpeciQualifications) {
		super.delete(amSpeciQualifications);
	}

	@Transactional(readOnly=false)
	public void deleteByCodeAndType(String detailCode, String typeName) {

		 amSpeciQualificationsDao.deleteByCodeAndType(detailCode,typeName);
	}
}