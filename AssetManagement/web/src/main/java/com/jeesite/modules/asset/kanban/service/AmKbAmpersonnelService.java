/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.kanban.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.kanban.entity.AmKbAmpersonnel;
import com.jeesite.modules.asset.kanban.dao.AmKbAmpersonnelDao;

/**
 * 看板有效人员维护Service
 * @author dwh
 * @version 2018-07-24
 */
@Service
@Transactional(readOnly=true)
public class AmKbAmpersonnelService extends CrudService<AmKbAmpersonnelDao, AmKbAmpersonnel> {
	@Autowired
	private AmKbAmpersonnelDao amKbAmpersonnelDao;
	
	/**
	 * 获取单条数据
	 * @param amKbAmpersonnel
	 * @return
	 */
	@Override
	public AmKbAmpersonnel get(AmKbAmpersonnel amKbAmpersonnel) {
		return super.get(amKbAmpersonnel);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amKbAmpersonnel
	 * @return
	 */
	@Override
	public Page<AmKbAmpersonnel> findPage(Page<AmKbAmpersonnel> page, AmKbAmpersonnel amKbAmpersonnel) {
		return super.findPage(page, amKbAmpersonnel);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amKbAmpersonnel
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmKbAmpersonnel amKbAmpersonnel) {
		super.save(amKbAmpersonnel);
	}
	
	/**
	 * 更新状态
	 * @param amKbAmpersonnel
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmKbAmpersonnel amKbAmpersonnel) {
		super.updateStatus(amKbAmpersonnel);
	}
	
	/**
	 * 删除数据
	 * @param amKbAmpersonnel
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmKbAmpersonnel amKbAmpersonnel) {
		super.delete(amKbAmpersonnel);
	}

	public AmKbAmpersonnel getAmKbAmpersonnelByPhone(String phone,String kanbanCode) {
		return amKbAmpersonnelDao.getAmKbAmpersonnelByPhone(phone,kanbanCode);
	}
	@Transactional(readOnly=false)
	public void deleteDb(String kbAmPersonnelCode) {
		amKbAmpersonnelDao.deleteDb(kbAmPersonnelCode);
	}

	public List<AmKbAmpersonnel> getInfoByTel(String tel,String kanbanCode) {
		return amKbAmpersonnelDao.getInfoByTel(tel,kanbanCode);
	}
}