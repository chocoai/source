/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.rookie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.rookie.entity.AmRookie;
import com.jeesite.modules.asset.rookie.dao.AmRookieDao;
import com.jeesite.modules.asset.rookie.entity.AmRookieDetail;
import com.jeesite.modules.asset.rookie.dao.AmRookieDetailDao;

/**
 * 菜鸟对接记录Service
 * @author czy
 * @version 2018-07-03
 */
@Service
@Transactional(readOnly=true)
public class AmRookieService extends CrudService<AmRookieDao, AmRookie> {
	
	@Autowired
	private AmRookieDetailDao amRookieDetailDao;
	
	/**
	 * 获取单条数据
	 * @param amRookie
	 * @return
	 */
	@Override
	public AmRookie get(AmRookie amRookie) {
		AmRookie entity = super.get(amRookie);
		if (entity != null){
			AmRookieDetail amRookieDetail = new AmRookieDetail(entity);
			amRookieDetail.setStatus(AmRookieDetail.STATUS_NORMAL);
			entity.setAmRookieDetailList(amRookieDetailDao.findList(amRookieDetail));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amRookie
	 * @return
	 */
	@Override
	public Page<AmRookie> findPage(Page<AmRookie> page, AmRookie amRookie) {
		return super.findPage(page, amRookie);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amRookie
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmRookie amRookie) {
		super.save(amRookie);
		// 保存 AmRookie子表
		for (AmRookieDetail amRookieDetail : amRookie.getAmRookieDetailList()){
			if (!AmRookieDetail.STATUS_DELETE.equals(amRookieDetail.getStatus())){
				amRookieDetail.setDocumentCode(amRookie);
				if (amRookieDetail.getIsNewRecord()){
					amRookieDetail.preInsert();
					amRookieDetailDao.insert(amRookieDetail);
				}else{
					amRookieDetail.preUpdate();
					amRookieDetailDao.update(amRookieDetail);
				}
			}else{
				amRookieDetailDao.delete(amRookieDetail);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param amRookie
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmRookie amRookie) {
		super.updateStatus(amRookie);
	}
	
	/**
	 * 删除数据
	 * @param amRookie
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmRookie amRookie) {
		super.delete(amRookie);
		AmRookieDetail amRookieDetail = new AmRookieDetail();
		amRookieDetail.setDocumentCode(amRookie);
		amRookieDetailDao.delete(amRookieDetail);
	}
	
}