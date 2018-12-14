/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.service;

import java.util.ArrayList;
import java.util.List;

import com.jeesite.modules.asset.amlocation.dao.AmLocationDao;
import com.jeesite.modules.asset.amlocation.entity.AmLocation;
import com.jeesite.modules.asset.category.entity.Category;
import com.jeesite.modules.asset.consumables.entity.*;
import com.jeesite.modules.asset.util.service.AmUtilService;
import com.jeesite.modules.asset.warehouse.entity.AmWarehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.consumables.dao.AmTransferDao;
import com.jeesite.modules.asset.consumables.dao.AmTransferDetailsDao;

/**
 * 调拨单Service
 * @author dwh
 * @version 2018-05-21
 */
@Service
@Transactional(readOnly=true)
public class AmTransferService extends CrudService<AmTransferDao, AmTransfer> {
	
	@Autowired
	private AmTransferDetailsDao amTransferDetailsDao;
	@Autowired
	private AmTransferDao amTransferDao;
	@Autowired
	private AmLocationDao amLocationDao;
	@Autowired
	private AmConStockService amConStockService;
	@Autowired
	private AmConLocationStockService amConLocationStockService;
	@Autowired
	private AmUtilService amUtilService;
	@Autowired
	private AmLibIodetailsService amLibIodetailsService;
	@Autowired
	private AmWarehIodetailsService amWarehIodetailsService;
	
	/**
	 * 获取单条数据
	 * @param amTransfer
	 * @return
	 */
	@Override
	public AmTransfer get(AmTransfer amTransfer) {
		AmTransfer entity = super.get(amTransfer);
		if (entity != null){
			AmTransferDetails amTransferDetails = new AmTransferDetails(entity);
			amTransferDetails.setStatus(AmTransferDetails.STATUS_NORMAL);
			List<AmTransferDetails>  detailsList=amTransferDetailsDao.findList(amTransferDetails);
			AmLocation outLocation=null;
			AmLocation inLocation=null;
			for (int i=0;i<detailsList.size();i++){
				AmTransferDetails details=detailsList.get(i);
				String outLocationCode=details.getOutLocationCode();
				String inLocationCode=details.getInLocationCode();
				if (details!=null){
					//出库库位名字
					outLocation=new AmLocation();
					outLocation.setLocationCode(outLocationCode);
					outLocation=amLocationDao.get(outLocation);
					if (outLocation!=null){
						details.setOutLocationName(outLocation.getLocationName());
					}

					//入库库位名字
					inLocation=new AmLocation();
					inLocation.setLocationCode(inLocationCode);
					inLocation=amLocationDao.get(inLocation);
					if (inLocation!=null){
						details.setInLocationName(inLocation.getLocationName());
					}
					if (details.getCategory() != null) {
						details.setCategoryName(details.getCategory().getCategoryName());
					}


				}
			}
			entity.setAmTransferDetailsList(amTransferDetailsDao.findList(amTransferDetails));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amTransfer
	 * @return
	 */
	@Override
	public Page<AmTransfer> findPage(Page<AmTransfer> page, AmTransfer amTransfer) {
		return super.findPage(page, amTransfer);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amTransfer
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmTransfer amTransfer) {
		super.save(amTransfer);
		// 保存 AmTransfer子表
		for (AmTransferDetails amTransferDetails : amTransfer.getAmTransferDetailsList()){
			if (!AmTransferDetails.STATUS_DELETE.equals(amTransferDetails.getStatus())){
				amTransferDetails.setDocumentsCode(amTransfer);
				if (amTransferDetails.getIsNewRecord()){
					amTransferDetails.preInsert();
					amTransferDetailsDao.insert(amTransferDetails);
				}else{
					amTransferDetails.preUpdate();
					amTransferDetailsDao.update(amTransferDetails);
				}
			}else{
				amTransferDetailsDao.delete(amTransferDetails);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param amTransfer
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmTransfer amTransfer) {
		super.updateStatus(amTransfer);
	}
	
	/**
	 * 删除数据
	 * @param amTransfer
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmTransfer amTransfer) {
		super.delete(amTransfer);
		AmTransferDetails amTransferDetails = new AmTransferDetails();
		amTransferDetails.setDocumentsCode(amTransfer);
		amTransferDetailsDao.delete(amTransferDetails);
	}

	/**
	 * 物理删除数据
	 * @param instorageCode
	 */
	@Transactional(readOnly=false)
	public void deleteDb(String instorageCode) {
		amTransferDao.deleteDb(instorageCode);
		amTransferDetailsDao.deleteDb(instorageCode);
	}

	@Transactional(readOnly=false)
	public void updateDocumentStatus(AmTransfer amTransfer) {
		super.save(amTransfer);
	}

	/**
	 * 去重
	 * @param
	 * @return
	 */
	@Transactional(readOnly = false)
	public AmTransfer rmRepeatList(AmTransfer amTransfer) {
		if (amTransfer==null){
			return null;
		}

		AmTransfer rst=new AmTransfer();
		rst.setOutWarehouseName(amTransfer.getOutWarehouseName());
		rst.setAmTransferDetails(amTransfer.getAmTransferDetails());
		rst.setInWarehouseName(amTransfer.getInWarehouseName());
		rst.setDocumentsCode(amTransfer.getDocumentsCode());
		rst.setRoad(amTransfer.isRoad());
		rst.setDocumentStatus(amTransfer.getDocumentStatus());
		rst.setDocumentStatusName(amTransfer.getDocumentStatusName());
		rst.setDocumentType(amTransfer.getDocumentType());
		rst.setAmStaff(amTransfer.getAmStaff());
		rst.setInWarehouseCode(amTransfer.getInWarehouseCode());
		rst.setOutWarehouseCode(amTransfer.getOutWarehouseCode());
		rst.setStaffCode(amTransfer.getStaffCode());
		rst.setTransferDate(amTransfer.getTransferDate());
		List<AmTransferDetails> detailsList=new ArrayList<>();
		AmTransferDetails details=null;
		for (int i=0;i<amTransfer.getAmTransferDetailsList().size();i++){
			boolean isCf=false;
			String locationCode=amTransfer.getAmTransferDetailsList().get(i).getOutLocationCode();
			String consumablesCode=amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode();
			long count1=amTransfer.getAmTransferDetailsList().get(i).getTransferCount();
			details=addDetails(amTransfer.getAmTransferDetailsList().get(i));
			if (detailsList==null||detailsList.size()<=0){
				detailsList.add(details);
			}else {
				for (int j=0;j<detailsList.size();j++){
					long count2=detailsList.get(j).getTransferCount();
					if (locationCode.equals(detailsList.get(j).getOutLocationCode())
							&&consumablesCode.equals(detailsList.get(j).getConsumablesCode())){
						detailsList.get(j).setTransferCount(count1+count2);
						detailsList.get(j).setTransferAmount(detailsList.get(j).getTransferCount()*detailsList.get(j).getTransferPrice());
						break;
					}
					if (j==detailsList.size()-1){
						detailsList.add(details);
						break;
					}
				}
			}
		}
		rst.setAmTransferDetailsList(detailsList);
		return rst;
	}

	private AmTransferDetails addDetails( AmTransferDetails amTransferDetails) {
		AmTransferDetails details=new AmTransferDetails();
		details.setTransferAmount(amTransferDetails.getTransferAmount());
		details.setTransferCount(amTransferDetails.getTransferCount());
		details.setInLocationName(amTransferDetails.getInLocationName());
		details.setOutLocationName(amTransferDetails.getOutLocationName());
		details.setDocumentsCode(amTransferDetails.getDocumentsCode());
		details.setCategoryName(amTransferDetails.getCategoryName());
		details.setMeasureValue(amTransferDetails.getMeasureValue());
		details.setCategoryCode(amTransferDetails.getCategoryCode());
		details.setConsumablesCode(amTransferDetails.getConsumablesCode());
		details.setConsumablesName(amTransferDetails.getConsumablesName());
		details.setDetailsCode(amTransferDetails.getDetailsCode());
		details.setInLocationCode(amTransferDetails.getInLocationCode());
		details.setMeasureUnit(amTransferDetails.getMeasureUnit());
		details.setOutLocationCode(amTransferDetails.getOutLocationCode());
		details.setSpecifications(amTransferDetails.getSpecifications());
		details.setTransferPrice(amTransferDetails.getTransferPrice());
		return details;
	}
	@Transactional(readOnly=false)
	public void saveIds(AmTransfer amTransfer, boolean isReview,String documentStatusSH,AmWarehouse inAmWarehouse,   AmWarehouse outAmWarehouse) {
        //审核反审核
        AmConStock stock = null;
        AmConLocationStock amConLocationStock = null;
        AmInstorageDetails details = null;
        if (amTransfer != null && isReview) {
            for (int i = 0; i < amTransfer.getAmTransferDetailsList().size(); i++) {
                if (documentStatusSH.equals("1")) {        //审核先做出库操作，再做入库
                    /**
                     * 审核的入库操作更新数量
                     * **/
                    //耗材库存表出库,
                    stock = new AmConStock();
                    stock.setWarehouseCode(amTransfer.getOutWarehouseCode());
                    stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
                    stock = amConStockService.get(stock);     //上面已经验证，不会出现为空或者库存小于输入的
                    //保存出库耗材库存表
                    stock.setWarehouseCode(amTransfer.getOutWarehouseCode());
                    stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//				if (documentStatusSH.equals("1")){      //审核反审核都只改变数量和总金额，不改变单价
                    stock.setStockPrice(stock.getStockPrice());
                    stock.setStock(stock.getStock() - amTransfer.getAmTransferDetailsList().get(i).getTransferCount());
                    stock.setStockAtm(stock.getStock() * stock.getStockPrice());
//				}else if (documentStatusSH.equals("2")){
//					stock.setStockAtm(stock.getStockAtm());
//					stock.setStockPrice(stock.getStockPrice());
//					stock.setStock(amTransfer.getAmTransferDetailsList().get(i).getTransferCount());
//				}
                    stock.setIsNewRecord(false);
                    amConStockService.save(stock);

                    //库位库存表出库,更新数量
                    amConLocationStock = new AmConLocationStock();
                    amConLocationStock.setWarehouseCode(amTransfer.getOutWarehouseCode());
                    amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
                    amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getOutLocationCode());
                    amConLocationStock = amConLocationStockService.get(amConLocationStock);     //上面已经验证，不会出现为空或者库存小于输入的
                    //保存出库库位库存表
                    amConLocationStock.setWarehouseCode(amTransfer.getOutWarehouseCode());
                    amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
                    amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getOutLocationCode());
                    //审核反审核都只改变数量和总金额，不改变单价
                    amConLocationStock.setStockPrice(amConLocationStock.getStockPrice());
                    amConLocationStock.setStock(amConLocationStock.getStock() - amTransfer.getAmTransferDetailsList().get(i).getTransferCount());
                    amConLocationStock.setStockAtm(amConLocationStock.getStock() * amConLocationStock.getStockPrice());
                    amConLocationStock.setIsNewRecord(false);
                    amConLocationStockService.save(amConLocationStock);

                    /**
                     * 审核的入库操作
                     * **/
                    //耗材库存表入库,
                    stock = new AmConStock();
                    stock.setWarehouseCode(amTransfer.getInWarehouseCode());
                    stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
                    stock = amConStockService.get(stock);     //入库不用考虑存在不存在，有记录就更新，没有就新增
                    //带入入库数据保存
                    if (stock != null) {    //如果有这条数据，总金额相加，单价不变，数量相加
                        stock.setWarehouseCode(amTransfer.getInWarehouseCode());
                        stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
                        stock.setStock(amTransfer.getAmTransferDetailsList().get(i).getTransferCount() + stock.getStock());
                        stock.setStockAtm(stock.getStockAtm() + amTransfer.getAmTransferDetailsList().get(i).getTransferAmount());
                        stock.setStockPrice(stock.getStockAtm() / stock.getStock());
                        stock.setIsNewRecord(false);
                        amConStockService.save(stock);
                    } else {
                        stock = new AmConStock();
                        stock.setWarehouseCode(amTransfer.getInWarehouseCode());
                        stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
                        stock.setStock(amTransfer.getAmTransferDetailsList().get(i).getTransferCount());
                        stock.setStockAtm(amTransfer.getAmTransferDetailsList().get(i).getTransferAmount());
                        stock.setStockPrice(stock.getStockAtm() / stock.getStock());
                        stock.setIsNewRecord(true);
                        amConStockService.save(stock);
                    }

                    //库位库存表入库,
                    amConLocationStock = new AmConLocationStock();
                    amConLocationStock.setWarehouseCode(amTransfer.getInWarehouseCode());
                    amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
                    amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getInLocationCode());
                    amConLocationStock = amConLocationStockService.get(amConLocationStock);     //入库不用考虑存在不存在，有记录就更新，没有就新增
                    //带入入库数据保存
                    if (amConLocationStock != null) {    //如果有这条数据，总金额相加，单价不变，数量相加
                        amConLocationStock.setWarehouseCode(amTransfer.getInWarehouseCode());
                        amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
                        amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getInLocationCode());
                        amConLocationStock.setStock(amTransfer.getAmTransferDetailsList().get(i).getTransferCount() + amConLocationStock.getStock());
                        amConLocationStock.setStockAtm(amConLocationStock.getStockAtm() + amTransfer.getAmTransferDetailsList().get(i).getTransferAmount());
                        amConLocationStock.setStockPrice(amConLocationStock.getStockAtm() / amConLocationStock.getStock());
                        amConLocationStock.setIsNewRecord(false);
                        amConLocationStockService.save(amConLocationStock);
                    } else {
                        amConLocationStock = new AmConLocationStock();
                        amConLocationStock.setWarehouseCode(amTransfer.getInWarehouseCode());
                        amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
                        amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getInLocationCode());
                        amConLocationStock.setStock(amTransfer.getAmTransferDetailsList().get(i).getTransferCount());
                        amConLocationStock.setStockAtm(amTransfer.getAmTransferDetailsList().get(i).getTransferAmount());
                        amConLocationStock.setStockPrice(amConLocationStock.getStockAtm() / amConLocationStock.getStock());
                        amConLocationStock.setIsNewRecord(true);
                        amConLocationStockService.save(amConLocationStock);
                    }

                  //调拨单入库明细
                    amUtilService.saveDetail(amTransfer.getDocumentsCode(),amTransfer.getTransferDate(),amTransfer.getDocumentType(),inAmWarehouse.getWarehouseCode(),
                            amTransfer.getAmTransferDetailsList().get(i).getInLocationCode(),amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode(),
                            amTransfer.getAmTransferDetailsList().get(i).getTransferCount(),amTransfer.getAmTransferDetailsList().get(i).getTransferAmount(),
                            null,null,amTransfer.getAmTransferDetailsList().get(i).getTransferPrice(),"0");
                    //调拨单出库明细
                    amUtilService.saveDetail(amTransfer.getDocumentsCode(),amTransfer.getTransferDate(),amTransfer.getDocumentType(),outAmWarehouse.getWarehouseCode(),
                            amTransfer.getAmTransferDetailsList().get(i).getInLocationCode(),amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode(),
                            null,null,
                            amTransfer.getAmTransferDetailsList().get(i).getTransferCount(),amTransfer.getAmTransferDetailsList().get(i).getTransferAmount(),
                            amTransfer.getAmTransferDetailsList().get(i).getTransferPrice(),"1");
                }else if (documentStatusSH.equals("2")){                        //反审核，判断库位库存表和耗材库存表是否为空，
                    /**
                     * 反审核的出库操作更新数量(入库调到出库,入库仓库数量相减，出库仓库数量相加)
                     * **/
                    //耗材库存表入库,数量相减(做出库操作)
                    stock = new AmConStock();
                    stock.setWarehouseCode(amTransfer.getInWarehouseCode());
                    stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
                    stock = amConStockService.get(stock);     //上面已经验证，不会出现为空或者库存小于输入的数量
                    //保存出库耗材库存表
                    stock.setWarehouseCode(amTransfer.getInWarehouseCode());
                    stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//				if (documentStatusSH.equals("1")){      //审核反审核都只改变数量和总金额，不改变单价
                    stock.setStockPrice(amTransfer.getAmTransferDetailsList().get(i).getTransferPrice());
                    stock.setStock(stock.getStock() - amTransfer.getAmTransferDetailsList().get(i).getTransferCount());
                    stock.setStockAtm(stock.getStock() * stock.getStockPrice());
                    stock.setIsNewRecord(false);
                    amConStockService.save(stock);

                    //库位库存表入库,更新数量(做出库操作)
                    amConLocationStock = new AmConLocationStock();
                    amConLocationStock.setWarehouseCode(amTransfer.getInWarehouseCode());
                    amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
                    amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getInLocationCode());
                    amConLocationStock = amConLocationStockService.get(amConLocationStock);     //上面已经验证，不会出现为空或者库存小于输入的
                    //保存出库库位库存表
                    amConLocationStock.setWarehouseCode(amTransfer.getInWarehouseCode());
                    amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
                    amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getInLocationCode());
                    //审核反审核都只改变数量和总金额，不改变单价
                    amConLocationStock.setStockPrice(amTransfer.getAmTransferDetailsList().get(i).getTransferPrice());
                    amConLocationStock.setStock(amConLocationStock.getStock() - amTransfer.getAmTransferDetailsList().get(i).getTransferCount());
                    amConLocationStock.setStockAtm(amConLocationStock.getStock() * amConLocationStock.getStockPrice());
                    amConLocationStock.setIsNewRecord(false);
                    amConLocationStockService.save(amConLocationStock);

                    /**
                     * 反审核的入库操作更新数量(入库调到出库,入库仓库数量相减，出库仓库数量相加)
                     * **/
                    //耗材库存表出库,(做入库操作)
                    stock = new AmConStock();
                    stock.setWarehouseCode(amTransfer.getOutWarehouseCode());
                    stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
                    stock = amConStockService.get(stock);     //入库不用考虑存在不存在，有记录就更新，没有就新增
                    //带入入库数据保存
                    if (stock != null) {    //如果有这条数据，总金额相加，单价不变，数量相加
                        stock.setWarehouseCode(amTransfer.getOutWarehouseCode());
                        stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
                        stock.setStock(amTransfer.getAmTransferDetailsList().get(i).getTransferCount() + stock.getStock());
                        stock.setStockAtm(stock.getStockAtm() + amTransfer.getAmTransferDetailsList().get(i).getTransferAmount());
                        stock.setStockPrice(stock.getStockAtm() / stock.getStock());
                        stock.setIsNewRecord(false);
                        amConStockService.save(stock);
                    } else {
                        stock = new AmConStock();
                        stock.setWarehouseCode(amTransfer.getOutWarehouseCode());
                        stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
                        stock.setStock(amTransfer.getAmTransferDetailsList().get(i).getTransferCount());
                        stock.setStockAtm(amTransfer.getAmTransferDetailsList().get(i).getTransferAmount());
                        stock.setStockPrice(stock.getStockAtm() / stock.getStock());
                        stock.setIsNewRecord(true);
                        amConStockService.save(stock);
                    }

                    //库位库存表入库,
                    amConLocationStock = new AmConLocationStock();
                    amConLocationStock.setWarehouseCode(amTransfer.getOutWarehouseCode());
                    amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
                    amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getOutLocationCode());
                    amConLocationStock = amConLocationStockService.get(amConLocationStock);     //入库不用考虑存在不存在，有记录就更新，没有就新增
                    //带入入库数据保存
                    if (amConLocationStock != null) {    //如果有这条数据，总金额相加，单价不变，数量相加
                        amConLocationStock.setWarehouseCode(amTransfer.getOutWarehouseCode());
                        amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
                        amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getOutLocationCode());
                        amConLocationStock.setStock(amTransfer.getAmTransferDetailsList().get(i).getTransferCount() + amConLocationStock.getStock());
                        amConLocationStock.setStockAtm(amConLocationStock.getStockAtm() + amTransfer.getAmTransferDetailsList().get(i).getTransferAmount());
                        amConLocationStock.setStockPrice(amConLocationStock.getStockAtm() / amConLocationStock.getStock());
                        amConLocationStock.setIsNewRecord(false);
                        amConLocationStockService.save(amConLocationStock);
                    } else {
                        amConLocationStock = new AmConLocationStock();
                        amConLocationStock.setWarehouseCode(amTransfer.getOutWarehouseCode());
                        amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
                        amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getOutLocationCode());
                        amConLocationStock.setStock(amTransfer.getAmTransferDetailsList().get(i).getTransferCount());
                        amConLocationStock.setStockAtm(amTransfer.getAmTransferDetailsList().get(i).getTransferAmount());
                        amConLocationStock.setStockPrice(amConLocationStock.getStockAtm() / amConLocationStock.getStock());
                        amConLocationStock.setIsNewRecord(true);
                        amConLocationStockService.save(amConLocationStock);
                    }
                    //反审核删除除仓库数据
                    amLibIodetailsService.deleteDb(amTransfer.getDocumentsCode());
                    //反审核删除除库位数据
                    amWarehIodetailsService.deleteDb(amTransfer.getDocumentsCode());
                }

            }
            //更新状态
            amTransfer.setDocumentStatus(documentStatusSH);
            this.updateDocumentStatus(amTransfer);
        }
	}

	@Transactional
	public boolean deleteDbs(AmTransfer amTransfer, String ids) {
		boolean isShStutrs = false;
		if (ids != null && ids.length() > 0) {
			String[] codeList = ids.split(",");
			for (int i = 0; i < codeList.length; i++) {
				AmTransfer amTransfer1 = new AmTransfer();
				amTransfer1.setDocumentsCode(codeList[i]);
				amTransfer1 = this.get(amTransfer1);
				if (amTransfer1 != null && amTransfer1.getDocumentStatus().equals("1")) {
					isShStutrs = true;
				} else {
					this.deleteDb(codeList[i]);
				}
			}
		} else {
			this.deleteDb(amTransfer.getDocumentsCode());
		}
		return isShStutrs;
	}
}