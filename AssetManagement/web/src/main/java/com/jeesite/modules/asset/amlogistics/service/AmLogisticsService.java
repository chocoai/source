/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amlogistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.amlogistics.entity.AmLogistics;
import com.jeesite.modules.asset.amlogistics.dao.AmLogisticsDao;
import com.jeesite.modules.asset.amlogistics.entity.AmLogisticsDetail;
import com.jeesite.modules.asset.amlogistics.dao.AmLogisticsDetailDao;

/**
 * 物流车查询单Service
 * @author dwh
 * @version 2018-06-07
 */
@Service
@Transactional(readOnly=true)
public class AmLogisticsService extends CrudService<AmLogisticsDao, AmLogistics> {
	
	@Autowired
	private AmLogisticsDetailDao amLogisticsDetailDao;
	
	/**
	 * 获取单条数据
	 * @param amLogistics
	 * @return
	 */
	@Override
	public AmLogistics get(AmLogistics amLogistics) {
		AmLogistics entity = super.get(amLogistics);
		if (entity != null){
			AmLogisticsDetail amLogisticsDetail = new AmLogisticsDetail(entity);
			amLogisticsDetail.setStatus(AmLogisticsDetail.STATUS_NORMAL);
			entity.setAmLogisticsDetailList(amLogisticsDetailDao.findList(amLogisticsDetail));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amLogistics
	 * @return
	 */
	@Override
	public Page<AmLogistics> findPage(Page<AmLogistics> page, AmLogistics amLogistics) {

		return super.findPage(page, amLogistics);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amLogistics
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmLogistics amLogistics) {
		super.save(amLogistics);
		// 保存 AmLogistics子表
//		for (AmLogisticsDetail amLogisticsDetail : amLogistics.getAmLogisticsDetailList()){
//			if (!AmLogisticsDetail.STATUS_DELETE.equals(amLogisticsDetail.getStatus())){
//				amLogisticsDetail.setDocumentCode(amLogistics);
//				if (amLogisticsDetail.getIsNewRecord()){
//					amLogisticsDetail.preInsert();
//					amLogisticsDetailDao.insert(amLogisticsDetail);
//				}else{
//					amLogisticsDetail.preUpdate();
//					amLogisticsDetailDao.update(amLogisticsDetail);
//				}
//			}else{
//				amLogisticsDetailDao.delete(amLogisticsDetail);
//			}
//		}
	}
	
	/**
	 * 更新状态
	 * @param amLogistics
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmLogistics amLogistics) {
		super.updateStatus(amLogistics);
	}
	
	/**
	 * 删除数据
	 * @param amLogistics
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmLogistics amLogistics) {
		super.delete(amLogistics);
		AmLogisticsDetail amLogisticsDetail = new AmLogisticsDetail();
		amLogisticsDetail.setDocumentCode(amLogistics);
		amLogisticsDetailDao.delete(amLogisticsDetail);
	}
	@Transactional(readOnly=false)
	public void saveDetail(AmLogisticsDetail amLogisticsDetail) {
		if (amLogisticsDetail.getIsNewRecord()) {
			amLogisticsDetail.preInsert();
			amLogisticsDetailDao.insert(amLogisticsDetail);
		} else {
			amLogisticsDetail.preUpdate();
			amLogisticsDetailDao.update(amLogisticsDetail);
		}
	}
	@Transactional(readOnly=false)
	public  List<AmLogisticsDetail> getDetail(String entryId,String consumablesCode) {
	return 	amLogisticsDetailDao.getDetail(entryId,consumablesCode);
	}
}