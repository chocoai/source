/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.screen.service;

import java.util.List;

import com.jeesite.modules.asset.screen.dao.ScreenEnterpriseDetailDao;
import com.jeesite.modules.asset.screen.entity.*;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import com.jeesite.modules.asset.util.service.AmSeqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.screen.dao.ScreenConfigDao;
import com.jeesite.modules.asset.screen.dao.ScreenConfigDetailDao;

/**
 * 屏幕配置Service
 * @author len
 * @version 2018-12-21
 */
@Service
@Transactional(readOnly=true)
public class ScreenConfigService extends CrudService<ScreenConfigDao, ScreenConfig> {
	
	@Autowired
	private ScreenConfigDetailDao screenConfigDetailDao;
	@Autowired
	private ScreenEnterpriseDetailDao screenEnterpriseDetailDao;
	@Autowired
	private ScreenConfigDao screenConfigDao;
	
	/**
	 * 获取单条数据
	 * @param screenConfig
	 * @return
	 */
	@Override
	public ScreenConfig get(ScreenConfig screenConfig) {
		ScreenConfig entity = super.get(screenConfig);
		if (entity != null){
			ScreenConfigDetail screenConfigDetail = new ScreenConfigDetail(entity);
			screenConfigDetail.setStatus(ScreenConfigDetail.STATUS_NORMAL);
			entity.setScreenConfigDetailList(screenConfigDetailDao.findList(screenConfigDetail));
			ScreenEnterpriseDetail screenEnterpriseDetail = new ScreenEnterpriseDetail(entity);
			screenEnterpriseDetail.setStatus(ScreenEnterpriseDetail.STATUS_NORMAL);
			entity.setScreenEnterpriseDetailList(screenEnterpriseDetailDao.findList(screenEnterpriseDetail));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param screenConfig
	 * @return
	 */
	@Override
	public Page<ScreenConfig> findPage(Page<ScreenConfig> page, ScreenConfig screenConfig) {
		return super.findPage(page, screenConfig);
	}

	@Autowired
	private AmSeqService amSeqService;
	/**
	 * 保存数据（插入或更新）
	 * @param screenConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ScreenConfig screenConfig) {
		super.save(screenConfig);
		// 保存 ScreenConfig子表
		for (ScreenConfigDetail screenConfigDetail : screenConfig.getScreenConfigDetailList()){
			if (!ScreenConfigDetail.STATUS_DELETE.equals(screenConfigDetail.getStatus())){
				screenConfigDetail.setConfigureCode(screenConfig);
				if (screenConfigDetail.getIsNewRecord()){
					screenConfigDetail.preInsert();
					screenConfigDetailDao.insert(screenConfigDetail);
				}else{
					screenConfigDetail.preUpdate();
					screenConfigDetailDao.update(screenConfigDetail);
				}
			}else{
				screenConfigDetailDao.delete(screenConfigDetail);
			}
		}
		for (ScreenEnterpriseDetail screenEnterpriseDetail : screenConfig.getScreenEnterpriseDetailList()){
			if (!ScreenConfigDetail.STATUS_DELETE.equals(screenEnterpriseDetail.getStatus())){
				screenEnterpriseDetail.setConfigureCode(screenConfig);
				if (screenEnterpriseDetail.getIsNewRecord()){
					screenEnterpriseDetail.preInsert();
					screenEnterpriseDetail.setDetailCode(amSeqService.getCode("QYBM", "yyyyMMdd", 4));
					screenEnterpriseDetailDao.insert(screenEnterpriseDetail);
				}else{
					screenEnterpriseDetail.preUpdate();
					screenEnterpriseDetailDao.update(screenEnterpriseDetail);
				}
			}else{
				screenEnterpriseDetailDao.delete(screenEnterpriseDetail);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param screenConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ScreenConfig screenConfig) {
		super.updateStatus(screenConfig);
	}
	
	/**
	 * 删除数据
	 * @param screenConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ScreenConfig screenConfig) {
		super.delete(screenConfig);
		screenConfigDetailDao.deleteDb(screenConfig.getConfigureCode());
	}

	public List<FirstPageData> getFirstPage(String flag) {
		return screenConfigDao.getFirstPage(flag);
	}

	public List<TbProduct> getGoods(String seriesName, int pageNo, int pageSize) {
		if (pageNo == 1) {
			pageNo = 0;
		} else {
			pageNo = pageSize * (pageNo-1);
		}
		return screenConfigDao.getGoods(seriesName, pageNo, pageSize);
	}

	public int getGoodsNum(String seriesName) {
		return screenConfigDao.getGoodsNum(seriesName);
	}

	/**
	 * 获取屏幕配置的企业
	 * @return
	 */
	public List<CoverData> getConfigEnterprise() {
		return screenEnterpriseDetailDao.getConfigEnterprise();
	}
}