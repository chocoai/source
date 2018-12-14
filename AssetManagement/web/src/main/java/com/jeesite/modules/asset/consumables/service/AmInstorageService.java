/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.service;

import java.util.ArrayList;
import java.util.List;

import com.jeesite.modules.asset.amlocation.entity.AmLocation;
import com.jeesite.modules.asset.consumables.entity.AmConLocationStock;
import com.jeesite.modules.asset.consumables.entity.AmConStock;
import com.jeesite.modules.asset.util.service.AmUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.consumables.entity.AmInstorage;
import com.jeesite.modules.asset.consumables.dao.AmInstorageDao;
import com.jeesite.modules.asset.consumables.entity.AmInstorageDetails;
import com.jeesite.modules.asset.consumables.dao.AmInstorageDetailsDao;

/**
 * 入库表Service
 * @author dwh
 * @version 2018-05-03
 */
@Service
@Transactional(readOnly=true)
public class AmInstorageService extends CrudService<AmInstorageDao, AmInstorage> {

	@Autowired
	private AmInstorageDetailsDao amInstorageDetailsDao;
	@Autowired
	private  AmInstorageDao amInstorageDao;
	@Autowired
	private AmUtilService amUtilService;
	@Autowired
	private AmConLocationStockService amConLocationStockService;
	@Autowired
	private AmLibIodetailsService amLibIodetailsService;
	@Autowired
	private AmConStockService amConStockService;
	@Autowired
	private AmWarehIodetailsService amWarehIodetailsService;
	/**
	 * 获取单条数据
	 * @param amInstorage
	 * @return
	 */
	@Override
	public AmInstorage get(AmInstorage amInstorage) {
		AmInstorage entity = super.get(amInstorage);
		if (entity != null){
			AmInstorageDetails amInstorageDetails = new AmInstorageDetails(entity);
			amInstorageDetails.setStatus(AmInstorageDetails.STATUS_NORMAL);
			List<AmInstorageDetails>  detailsList=amInstorageDetailsDao.findList(amInstorageDetails);
			for (int i=0;i<detailsList.size();i++){
				AmInstorageDetails details=detailsList.get(i);
				if (details!=null){
					details.setLocationName(details.getAmLocation().getLocationName());
					String imgPath=amUtilService.getImgPath(details.getConsumablesCode());//获取照片路径
					details.setImgPath(imgPath);
				}
			}
			entity.setAmInstorageDetailsList(detailsList);
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amInstorage
	 * @return
	 */
	@Override
	public Page<AmInstorage> findPage(Page<AmInstorage> page, AmInstorage amInstorage) {
		return super.findPage(page, amInstorage);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amInstorage
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmInstorage amInstorage) {
		super.save(amInstorage);
		// 保存 AmInstorage子表
		for (AmInstorageDetails amInstorageDetails : amInstorage.getAmInstorageDetailsList()){
			if (!AmInstorageDetails.STATUS_DELETE.equals(amInstorageDetails.getStatus())){
				amInstorageDetails.setInstorageCode(amInstorage);
				if (amInstorageDetails.getIsNewRecord()){
					amInstorageDetails.preInsert();
					amInstorageDetailsDao.insert(amInstorageDetails);
				}else{
					amInstorageDetails.preUpdate();
					amInstorageDetailsDao.update(amInstorageDetails);
				}
			}else{
				amInstorageDetailsDao.delete(amInstorageDetails);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param amInstorage
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmInstorage amInstorage) {
		super.updateStatus(amInstorage);
	}
	
	/**
	 * 删除数据
	 * @param amInstorage
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmInstorage amInstorage) {
		super.delete(amInstorage);
		AmInstorageDetails amInstorageDetails = new AmInstorageDetails();
		amInstorageDetails.setInstorageCode(amInstorage);
		amInstorageDetailsDao.delete(amInstorageDetails);
	}

	public List<AmLocation> getAmLocationListByCode(String warehouseCode) {
		 List<AmLocation> locationList=amInstorageDao.getAmLocationListByCode(warehouseCode);
				return locationList;
	}

	/**
	 * 物理删除数据
	 * @param instorageCode
	 */
	@Transactional(readOnly=false)
	public void deleteDb(String instorageCode) {
		amInstorageDao.deleteDb(instorageCode);
		amInstorageDetailsDao.deleteDb(instorageCode);
	}
//	/**
//	 * 物理删除数据
//	 * @param instorageCode
//	 */
//	@Transactional(readOnly=false)
//	public void updataFlag(String instorageCode) {
//		amInstorageDao.updataFlag(instorageCode);
//	}
	@Transactional
	public List<AmInstorageDetails> getDetailList(AmInstorageDetails amInstorageDetails) {
		List<AmInstorageDetails>  detailsList=amInstorageDetailsDao.findList(amInstorageDetails);
		return detailsList;
	}
	@Transactional(readOnly=false)
	public void updateDocumentStatus(AmInstorage amInstorage) {
		super.save(amInstorage);
	}
	@Transactional(readOnly=false)
	public List<AmInstorageDetails> getDetailsByCategoryCode(String categoryCode) {
		String categoryCodeLike="%"+categoryCode+"%";
		List<AmInstorageDetails> list=  amInstorageDetailsDao.getDetailsByCategoryCode(categoryCode,categoryCodeLike);
		return list;
	}

	/**
	 * 判断是否有相同库位耗材的明细
	 * @param amInstorage
	 * @return
	 */
	public String getoutStock (AmInstorage amInstorage) {
		String message = "";
		List<String> detiList = new ArrayList<>();
		for (AmInstorageDetails amInstorageDetails : amInstorage.getAmInstorageDetailsList()){
			if (!AmInstorageDetails.STATUS_DELETE.equals(amInstorageDetails.getStatus())) {
				String key = amInstorageDetails.getLocationCode() + "/b" + amInstorageDetails.getConsumablesCode();
				if (detiList.contains(key)) {
					message = "明细中存在相同的库位耗材";
					return message;
				}
				detiList.add(key);
			}
		}
		return message;
	}
	@Transactional(readOnly=false)
	public void saveIds(AmInstorage amInstorage, boolean isReview, AmConStock stock, AmConLocationStock amConLocationStock, AmInstorageDetails details, String documentStatusSH) {
		if (amInstorage != null && isReview) {

			//更新插入库存表
//            stock = new AmConStock();
//            stock.setWarehouseCode(amInstorage.getWarehouseCode());
			//更新插入库位库存表

			for (int i = 0; i < amInstorage.getAmInstorageDetailsList().size(); i++) {
				stock = new AmConStock();
				stock.setWarehouseCode(amInstorage.getWarehouseCode());
				stock.setConsumablesCode(amInstorage.getAmInstorageDetailsList().get(i).getConsumablesCode());
				stock = amConStockService.get(stock);

				amConLocationStock = new AmConLocationStock();
				amConLocationStock.setWarehouseCode(amInstorage.getWarehouseCode());//仓库编码
				details = amInstorage.getAmInstorageDetailsList().get(i);
				amConLocationStock.setConsumablesCode(details.getConsumablesCode());
				amConLocationStock.setLocationCode(details.getLocationCode());
				amConLocationStock = amConLocationStockService.get(amConLocationStock); //根据三个条件查询数据库
				if (stock != null) {
//                    if (stock.getStock() + details.getInstorageCount() <= 0) {
//                        return renderResult(Global.FALSE, "审核失败，仓库不够库存冲减！");
//                    }
//                    AmConStock stockadd = new AmConStock();
					stock.setWarehouseCode(amInstorage.getWarehouseCode());
					stock.setConsumablesCode(details.getConsumablesCode());
					if ("1".equals(documentStatusSH)) {
						stock.setStock(stock.getStock() + details.getInstorageCount());
						stock.setStockAtm(stock.getStockAtm() + details.getInstorageAmount());
						stock.setStockPrice(stock.getStockAtm() / stock.getStock());
					} else if ("2".equals(documentStatusSH)) {
						long getInstorageCount = stock.getStock() - details.getInstorageCount();
						if (getInstorageCount <= 0) {                 //如果反审核时的库存数量为0时,总和和单价都为0
							stock.setStock((long) 0);
							stock.setStockPrice(0.0);
							stock.setStockAtm(0.0);
						} else {
							stock.setStock(stock.getStock() - details.getInstorageCount());
							stock.setStockAtm(stock.getStockAtm() - details.getInstorageAmount());
							stock.setStockPrice(stock.getStockAtm() / stock.getStock());
						}
					}
					stock.setIsNewRecord(false);
//                    stocks.add(stockadd);
					amConStockService.save(stock);
				} else {
					stock = new AmConStock();
					stock.setConsumablesCode(details.getConsumablesCode());
					stock.setWarehouseCode(amInstorage.getWarehouseCode());
					stock.setStock(details.getInstorageCount());
					stock.setStockPrice(details.getInstoragePrice());
					stock.setStockAtm(details.getInstorageAmount());
					stock.setIsNewRecord(true);
//                    stocks.add(stock);
					amConStockService.save(stock);
				}
				//更新插入库位库存表
				if (amConLocationStock != null) {
//                    if (amConLocationStock.getStock() + amInstorage.getAmInstorageDetailsList().get(i).getInstorageCount() <= 0) {
//                        return renderResult(Global.FALSE, "审核失败，仓位不够库存冲减！");
//                    }
//                    AmConLocationStock amConLocationStockadd = new AmConLocationStock();
					amConLocationStock.setLocationCode(details.getLocationCode());
					amConLocationStock.setConsumablesCode(details.getConsumablesCode());
					amConLocationStock.setWarehouseCode(amInstorage.getWarehouseCode());
					if ("1".equals(documentStatusSH)) {
						amConLocationStock.setStock(amConLocationStock.getStock() + details.getInstorageCount());
						amConLocationStock.setStockAtm(amConLocationStock.getStockAtm() + details.getInstorageAmount());
						amConLocationStock.setStockPrice(amConLocationStock.getStockAtm() / amConLocationStock.getStock());
					} else if ("2".equals(documentStatusSH)) {
						long InstorageCount = amConLocationStock.getStock() - details.getInstorageCount();
						if (InstorageCount <= 0) {//如果反审核时的库存数量为0时
							amConLocationStock.setStock((long) 0);
							amConLocationStock.setStockPrice(0.0);
							amConLocationStock.setStockAtm(0.0);
						} else {
							amConLocationStock.setStock(amConLocationStock.getStock() - details.getInstorageCount());
							amConLocationStock.setStockAtm(amConLocationStock.getStockAtm() - details.getInstorageAmount());
							amConLocationStock.setStockPrice(amConLocationStock.getStockAtm() / amConLocationStock.getStock());
						}
					}
					amConLocationStock.setIsNewRecord(false);
//                    amConLocationStocks.add(amConLocationStockadd);
					amConLocationStockService.save(amConLocationStock);

				} else {
					amConLocationStock = new AmConLocationStock();
					amConLocationStock.setLocationCode(details.getLocationCode());
					amConLocationStock.setConsumablesCode(details.getConsumablesCode());
					amConLocationStock.setWarehouseCode(amInstorage.getWarehouseCode());
					amConLocationStock.setStock(details.getInstorageCount());
					amConLocationStock.setStockPrice(details.getInstoragePrice());
					amConLocationStock.setStockAtm(details.getInstorageAmount());
					amConLocationStock.setIsNewRecord(true);
//                    amConLocationStocks.add(amConLocationStock);
					amConLocationStockService.save(amConLocationStock);
				}
				if ("1".equals(documentStatusSH)) {
					amUtilService.saveDetail(amInstorage.getInstorageCode(),amInstorage.getIncomingDate(),amInstorage.getDocumentType(),amInstorage.getWarehouseCode(),
							details.getLocationCode(),details.getConsumablesCode(),details.getInstorageCount(),details.getInstorageAmount(),
							null,null,details.getInstoragePrice(),"0");

				} else if ("2".equals(documentStatusSH)) {
					amLibIodetailsService.deleteDb(amInstorage.getInstorageCode());
					amWarehIodetailsService.deleteDb(amInstorage.getInstorageCode());
				}
			}
			//更新状态
			amInstorage.setDocumentStatus(documentStatusSH);
			this.updateDocumentStatus(amInstorage);
		}
	}
	@Transactional
	public boolean deleteDbs(AmInstorage amInstorage, String ids) {
		boolean isShStutrs = false;
		if (ids != null && ids.length() > 0) {
			String[] codeList = ids.split(",");
			for (int i = 0; i < codeList.length; i++) {
				AmInstorage instorage = new AmInstorage();
				instorage.setInstorageCode(codeList[i]);
				instorage = this.get(instorage);
				if (instorage != null && instorage.getDocumentStatus().equals("1")) {
					isShStutrs = true;
				} else {
					this.deleteDb(codeList[i]);
				}
			}
		} else {
			this.deleteDb(amInstorage.getInstorageCode());
		}
		return isShStutrs;
	}

}