/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.service;

import java.util.ArrayList;
import java.util.List;

import com.jeesite.modules.asset.consumables.entity.AmConLocationStock;
import com.jeesite.modules.asset.consumables.entity.AmConStock;
import com.jeesite.modules.asset.util.dao.AmUtilDao;
import com.jeesite.modules.asset.util.service.AmUtilService;
import com.jeesite.modules.file.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.consumables.entity.AmOutStorage;
import com.jeesite.modules.asset.consumables.dao.AmOutStorageDao;
import com.jeesite.modules.asset.consumables.entity.AmOutStorageDetails;
import com.jeesite.modules.asset.consumables.dao.AmOutStorageDetailsDao;

/**
 * 耗材出库表Service
 * @author czy
 * @version 2018-05-07
 */
@Service
@Transactional(readOnly=true)
public class AmOutStorageService extends CrudService<AmOutStorageDao, AmOutStorage> {

	@Autowired
	private AmOutStorageDetailsDao amOutStorageDetailsDao;
	@Autowired
	private AmOutStorageDao amOutStorageDao;
	@Autowired
	private AmUtilDao amUtilDao;
	@Autowired
	private AmLibIodetailsService amLibIodetailsService;
	@Autowired
	private AmWarehIodetailsService amWarehIodetailsService;
	@Autowired
	private AmUtilService amUtilService;
	@Autowired
	private AmConLocationStockService amConLocationStockService;
	@Autowired
	private AmConStockService amConStockService;
	/**
	 * 获取单条数据
	 * @param amOutStorage
	 * @return
	 */
	@Override
	public AmOutStorage get(AmOutStorage amOutStorage) {
		AmOutStorage entity = super.get(amOutStorage);
		if (entity != null){
			AmOutStorageDetails amOutStorageDetails = new AmOutStorageDetails(entity);
			amOutStorageDetails.setStatus(AmOutStorageDetails.STATUS_NORMAL);
			List<AmOutStorageDetails> details = amOutStorageDetailsDao.findList(amOutStorageDetails);
			for (AmOutStorageDetails detail : details) {
				if (detail.getAmLocation() != null) {
					detail.setLocationName(detail.getAmLocation().getLocationName());
				}
				if (detail.getCategory() != null) {
					detail.setCategoryName(detail.getCategory().getCategoryName());
				}
				if (detail.getMeasureUnit() != null && !"".equals(detail.getMeasureUnit())) {
					String measureValue= amUtilDao.findDictLabel(detail.getMeasureUnit(),"sys_measure_unit");  // 计量单位编辑页面显示用
					detail.setMeasureValue(measureValue);
				}
			}
			entity.setAmOutStorageDetailsList(details);
		}
		return entity;
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amOutStorage
	 * @return
	 */
	@Override
	public Page<AmOutStorage> findPage(Page<AmOutStorage> page, AmOutStorage amOutStorage) {
		return super.findPage(page, amOutStorage);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param amOutStorage
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmOutStorage amOutStorage) {
		super.save(amOutStorage);
		// 保存 AmOutStorage子表
		for (AmOutStorageDetails amOutStorageDetails : amOutStorage.getAmOutStorageDetailsList()){
			if (!AmOutStorageDetails.STATUS_DELETE.equals(amOutStorageDetails.getStatus())){
				amOutStorageDetails.setOutstorageCode(amOutStorage);
				if (amOutStorageDetails.getIsNewRecord()){
					amOutStorageDetails.preInsert();
					amOutStorageDetailsDao.insert(amOutStorageDetails);
				}else {
					amOutStorageDetails.preUpdate();
					amOutStorageDetailsDao.update(amOutStorageDetails);
				}
			}else{
				amOutStorageDetailsDao.delete(amOutStorageDetails);
			}
		}
	}

	/**
	 * 更新状态
	 * @param amOutStorage
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmOutStorage amOutStorage) {
		super.updateStatus(amOutStorage);
	}

	/**
	 * 删除数据
	 * @param amOutStorage
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmOutStorage amOutStorage) {
		super.delete(amOutStorage);
		AmOutStorageDetails amOutStorageDetails = new AmOutStorageDetails();
		amOutStorageDetails.setOutstorageCode(amOutStorage);
		amOutStorageDetailsDao.delete(amOutStorageDetails);
	}
	/**
	 * 查询仓库名称
	 * @param warehouseCode
	 * @return
	 */
	public String findWarehouseName (String warehouseCode) {
		return amOutStorageDao.findWarehouseName(warehouseCode);
	}

	/**
	 * 删除数据
	 * @param outStorageCode
	 */
	@Transactional(readOnly=false)
	public String deleteDB(AmOutStorage amOutStorage, String outStorageCode) {
		String message = "";
		if (outStorageCode != null || !"".equals(outStorageCode)) {
			String[] str = outStorageCode.split(",");
			for (int i = 0; i < str.length; i++) {
				amOutStorage.setOutstorageCode(str[i]);
				amOutStorage = this.get(amOutStorage);
				if ("1".equals(amOutStorage.getBillStatus())) {    // 已审核的单据不能被删除
					message = "删除成功！已审核的单据未被删除!";
					continue;
				}
				amOutStorageDao.delStorage(str[i]);  // 删除主表
				amOutStorageDetailsDao.delStorage(str[i]); // 删除明细表
			}
		}
		return message;
	}

	@Transactional
	public List<AmOutStorageDetails> getDetailList(AmOutStorageDetails amOutStorageDetails) {
			List<AmOutStorageDetails> detailsList = amOutStorageDetailsDao.findList(amOutStorageDetails);
		return detailsList;
	}

	@Transactional(readOnly=false)
	public List<AmOutStorageDetails> getDetailsByCategoryCode(String categoryCode) {
		String categoryCodeLike="%"+categoryCode+"%";
		List<AmOutStorageDetails> list=  amOutStorageDetailsDao.getDetailsByCategoryCode(categoryCode,categoryCodeLike);
		return list;
	}
	/**
	 * 保存数据（插入或更新）
	 * @param amOutStorage
	 */
	@Transactional(readOnly=false)
	public void updateData(AmOutStorage amOutStorage) {
		super.save(amOutStorage);
	}

	/**
	 * 判断是否有相同库位耗材的明细
	 * @param amOutStorage
	 * @return
	 */
	public String getoutStock (AmOutStorage amOutStorage) {
		String message = "";
		List<String> detiList = new ArrayList();
		for (AmOutStorageDetails amOutStorageDetails : amOutStorage.getAmOutStorageDetailsList()) {
			if (!AmOutStorageDetails.STATUS_DELETE.equals(amOutStorageDetails.getStatus())) {
				String key = amOutStorageDetails.getLocationCode() + "/b" + amOutStorageDetails.getConsumablesCode();
				if (detiList.contains(key)) {
					message = "明细中存在相同的库位耗材";
					return message;
				}
				detiList.add(key);
			}
		}
		return message;
	}

	/**
	 * 保存数据报库存表库位表
	 * @param amOutStorage
	 * @param warehouseCode
	 * @param examine
	 * @param isNewRecord
	 */
	@Transactional(readOnly = false)
	public void saveData(AmOutStorage amOutStorage, String warehouseCode, String examine, boolean isNewRecord) {
		for (AmOutStorageDetails amOutStorageDetails : amOutStorage.getAmOutStorageDetailsList()) {
			AmConStock amConStock = new AmConStock();     // // 耗材库存表
			AmConLocationStock amConLocationStock = new AmConLocationStock();   // 库存库位表
			// 耗材编码
			String consumablesCode = amOutStorageDetails.getConsumablesCode();
			// 出库数量
			long outstorageCount = amOutStorageDetails.getOutstorageCount();
			amConStock.setWarehouseCode(warehouseCode);
			amConStock.setConsumablesCode(consumablesCode);
			amConStock = amConStockService.get(amConStock);
			// 耗材库存表
			if (amConStock != null) {
				if ("1".equals(examine)) {
					amConStock.setStock(amConStock.getStock() - outstorageCount);
				} else {
					amConStock.setStock(amConStock.getStock() + outstorageCount);
				}
				amConStock.setIsNewRecord(false);
				amConStockService.save(amConStock);
			}
			amConLocationStock.setWarehouseCode(warehouseCode);
			// 库存库位表
			amConLocationStock.setConsumablesCode(consumablesCode);
			// 库位
			String locationCode = amOutStorageDetails.getLocationCode();
			amConLocationStock.setLocationCode(locationCode);
			amConLocationStock = amConLocationStockService.get(amConLocationStock);
			if (amConLocationStock != null) {
				if ("1".equals(examine)) {
					amConLocationStock.setStock(amConLocationStock.getStock() - outstorageCount);
				} else {
					amConLocationStock.setStock(amConLocationStock.getStock() + outstorageCount);
				}
				amConLocationStock.setIsNewRecord(false);
				amConLocationStockService.save(amConLocationStock);
			}
			if ("1".equals(examine)) {
				amUtilService.saveDetail(amOutStorage.getOutstorageCode(), amOutStorage.getOutDate(), amOutStorage.getBillType(), warehouseCode,
						locationCode, amOutStorageDetails.getConsumablesCode(), null, null,
						amOutStorageDetails.getOutstorageCount(), null, null, "1");
			}
		}
		if ("2".equals(examine)) {
			amWarehIodetailsService.deleteDb(amOutStorage.getOutstorageCode());
			amLibIodetailsService.deleteDb(amOutStorage.getOutstorageCode());
		}
		if (examine != null) {
			amOutStorage.setBillStatus(examine);
		}

		if (!isNewRecord) {
			this.updateData(amOutStorage);
		}
	}
}
