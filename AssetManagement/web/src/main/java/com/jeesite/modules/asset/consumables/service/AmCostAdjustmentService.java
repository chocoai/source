/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.service;

import java.util.List;

import com.jeesite.common.config.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.consumables.entity.AmCostAdjustment;
import com.jeesite.modules.asset.consumables.dao.AmCostAdjustmentDao;
import com.jeesite.modules.asset.consumables.entity.AmAdjustDetail;
import com.jeesite.modules.asset.consumables.dao.AmAdjustDetailDao;

/**
 * 耗材成本调整单Service
 * @author dwh
 * @version 2018-05-31
 */
@Service
@Transactional(readOnly=true)
public class AmCostAdjustmentService extends CrudService<AmCostAdjustmentDao, AmCostAdjustment> {
	
	@Autowired
	private AmAdjustDetailDao amAdjustDetailDao;
	@Autowired
	private AmCostAdjustmentDao amCostAdjustmentDao;
	
	/**
	 * 获取单条数据
	 * @param amCostAdjustment
	 * @return
	 */
	@Override
	public AmCostAdjustment get(AmCostAdjustment amCostAdjustment) {
		AmCostAdjustment entity = super.get(amCostAdjustment);
		if (entity != null){
			AmAdjustDetail amAdjustDetail = new AmAdjustDetail(entity);
			amAdjustDetail.setStatus(AmAdjustDetail.STATUS_NORMAL);
			entity.setAmAdjustDetailList(amAdjustDetailDao.findList(amAdjustDetail));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amCostAdjustment
	 * @return
	 */
	@Override
	public Page<AmCostAdjustment> findPage(Page<AmCostAdjustment> page, AmCostAdjustment amCostAdjustment) {
		return super.findPage(page, amCostAdjustment);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amCostAdjustment
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmCostAdjustment amCostAdjustment) {
		super.save(amCostAdjustment);
		// 保存 AmCostAdjustment子表
		for (AmAdjustDetail amAdjustDetail : amCostAdjustment.getAmAdjustDetailList()){
			if (!AmAdjustDetail.STATUS_DELETE.equals(amAdjustDetail.getStatus())){
				amAdjustDetail.setDocumentCode(amCostAdjustment);
				if (amAdjustDetail.getIsNewRecord()){
					amAdjustDetail.preInsert();
					amAdjustDetailDao.insert(amAdjustDetail);
				}else{
					amAdjustDetail.preUpdate();
					amAdjustDetailDao.update(amAdjustDetail);
				}
			}else{
				amAdjustDetailDao.delete(amAdjustDetail);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param amCostAdjustment
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmCostAdjustment amCostAdjustment) {
		super.updateStatus(amCostAdjustment);
	}
	
	/**
	 * 删除数据
	 * @param amCostAdjustment
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmCostAdjustment amCostAdjustment) {
		super.delete(amCostAdjustment);
		AmAdjustDetail amAdjustDetail = new AmAdjustDetail();
		amAdjustDetail.setDocumentCode(amCostAdjustment);
		amAdjustDetailDao.delete(amAdjustDetail);
	}

	/**
	 * 物理删除数据
	 * @param instorageCode
	 */
	@Transactional(readOnly=false)
	public void deleteDb(String instorageCode) {
		amCostAdjustmentDao.deleteDb(instorageCode);
		amAdjustDetailDao.deleteDb(instorageCode);
	}
	@Transactional
	public boolean deleteDbs(AmCostAdjustment amCostAdjustment, String ids) {
		boolean isShStutrs = false;
		if (ids != null && ids.length() > 0) {
			String[] codeList = ids.split(",");
			for (int i = 0; i < codeList.length; i++) {
				AmCostAdjustment adjustment = new AmCostAdjustment();
				adjustment.setDocumentCode(codeList[i]);
				adjustment = this.get(adjustment);
				if (adjustment != null && adjustment.getDocumentStatus().equals("1")) {
					isShStutrs = true;
				} else {
					this.deleteDb(codeList[i]);
				}
			}
		} else {
			this.deleteDb(amCostAdjustment.getDocumentCode());
		}
		return isShStutrs;
	}
}