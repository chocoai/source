/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.wish.service;

import com.jeesite.modules.asset.ding.entity.DingUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.wish.entity.WishPrimary;
import com.jeesite.modules.asset.wish.dao.WishPrimaryDao;
import com.jeesite.modules.asset.wish.entity.WishPrimaryDetail;
import com.jeesite.modules.asset.wish.dao.WishPrimaryDetailDao;

import java.util.List;

/**
 * 初选提名记录表Service
 * @author len
 * @version 2018-11-20
 */
@Service
@Transactional(readOnly=true)
public class WishPrimaryService extends CrudService<WishPrimaryDao, WishPrimary> {
	
	@Autowired
	private WishPrimaryDetailDao wishPrimaryDetailDao;
	@Autowired
	private WishPrimaryDao wishPrimaryDao;
	/**
	 * 获取单条数据
	 * @param wishPrimary
	 * @return
	 */
	@Override
	public WishPrimary get(WishPrimary wishPrimary) {
		WishPrimary entity = super.get(wishPrimary);
		if (entity != null){
			WishPrimaryDetail wishPrimaryDetail = new WishPrimaryDetail(entity);
			wishPrimaryDetail.setStatus(WishPrimaryDetail.STATUS_NORMAL);
			entity.setWishPrimaryDetailList(wishPrimaryDetailDao.findList(wishPrimaryDetail));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param wishPrimary
	 * @return
	 */
	@Override
	public Page<WishPrimary> findPage(Page<WishPrimary> page, WishPrimary wishPrimary) {
		return super.findPage(page, wishPrimary);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param wishPrimary
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(WishPrimary wishPrimary) {
		super.save(wishPrimary);
		// 保存 WishPrimary子表
		for (WishPrimaryDetail wishPrimaryDetail : wishPrimary.getWishPrimaryDetailList()){
			if (!WishPrimaryDetail.STATUS_DELETE.equals(wishPrimaryDetail.getStatus())){
				wishPrimaryDetail.setUserId(wishPrimary);
				if (wishPrimaryDetail.getIsNewRecord()){
					wishPrimaryDetail.preInsert();
					wishPrimaryDetailDao.insert(wishPrimaryDetail);
				}else{
					wishPrimaryDetail.preUpdate();
					wishPrimaryDetailDao.update(wishPrimaryDetail);
				}
			}else{
				wishPrimaryDetailDao.delete(wishPrimaryDetail);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param wishPrimary
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(WishPrimary wishPrimary) {
		super.updateStatus(wishPrimary);
	}
	
	/**
	 * 删除数据
	 * @param wishPrimary
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(WishPrimary wishPrimary) {
		super.delete(wishPrimary);
		WishPrimaryDetail wishPrimaryDetail = new WishPrimaryDetail();
		wishPrimaryDetail.setUserId(wishPrimary);
		wishPrimaryDetailDao.delete(wishPrimaryDetail);
	}

	/**
	 * 根据userId获取非离职用户
	 * @return
	 */
	public DingUser getDingUser(String userId) {
		return wishPrimaryDao.getDingUser(userId);
	}

	@Transactional(readOnly = false)
	public void saveData (WishPrimary wishPrimary) {
		wishPrimary.setIsNewRecord(true);
		super.save(wishPrimary);
		wishPrimaryDetailDao.insertBatch(wishPrimary.getWishPrimaryDetailList());
	}

	/**
	 * 导出提名记录表
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<WishPrimaryDetail> exportData() {
		return wishPrimaryDetailDao.exportData();
	}
}