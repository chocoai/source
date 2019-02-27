/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.order.service;

import java.util.List;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.modules.asset.order.dao.AmOrderShoppingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.distribution.order.entity.DistrOrder;
import com.jeesite.modules.distribution.order.dao.DistrOrderDao;
import com.jeesite.modules.distribution.order.entity.DistrOrderDetail;
import com.jeesite.modules.distribution.order.dao.DistrOrderDetailDao;

/**
 * 分销订单Service
 * @author len
 * @version 2019-01-07
 */
@Service
@Transactional(readOnly=true)
public class DistrOrderService extends CrudService<DistrOrderDao, DistrOrder> {
	
	@Autowired
	private DistrOrderDetailDao distrOrderDetailDao;
	@Autowired
	private DistrOrderDao distrOrderDao;
	
	/**
	 * 获取单条数据
	 * @param distrOrder
	 * @return
	 */
	@Override
	public DistrOrder get(DistrOrder distrOrder) {
		DistrOrder entity = super.get(distrOrder);
		if (entity != null){
			DistrOrderDetail distrOrderDetail = new DistrOrderDetail(entity);
			distrOrderDetail.setStatus(DistrOrderDetail.STATUS_NORMAL);
			entity.setDistrOrderDetailList(distrOrderDetailDao.findList(distrOrderDetail));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param distrOrder
	 * @return
	 */
	@Override
	public Page<DistrOrder> findPage(Page<DistrOrder> page, DistrOrder distrOrder) {
		return super.findPage(page, distrOrder);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param distrOrder
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(DistrOrder distrOrder) {
		super.save(distrOrder);
		// 保存 DistrOrder子表
		for (DistrOrderDetail distrOrderDetail : distrOrder.getDistrOrderDetailList()){
			if (!DistrOrderDetail.STATUS_DELETE.equals(distrOrderDetail.getStatus())){
				distrOrderDetail.setDocumentCode(distrOrder);
				if (distrOrderDetail.getIsNewRecord()){
					distrOrderDetail.preInsert();
					distrOrderDetailDao.insert(distrOrderDetail);
				}else{
					distrOrderDetail.preUpdate();
					distrOrderDetailDao.update(distrOrderDetail);
				}
			}else{
				distrOrderDetailDao.delete(distrOrderDetail);
			}
		}
	}

	@Autowired
	private AmOrderShoppingDao amOrderShoppingDao;
	/**
	 * 保存主表和子表
	 * @param distrOrder
	 */
	@Transactional(readOnly=false)
	public void saveOrder(DistrOrder distrOrder, List<String> skuIdList, String userCode) {
		super.save(distrOrder);
		distrOrderDetailDao.insertBatch(distrOrder.getDistrOrderDetailList());
		if (ListUtils.isNotEmpty(skuIdList)) {
			amOrderShoppingDao.updateBySkuIdList(skuIdList, userCode);
		}
	}

	/**
	 * 只更新主表
	 * @param distrOrder
	 */
	@Transactional(readOnly=false)
	public void saveData(DistrOrder distrOrder) {
		super.save(distrOrder);
	}
	/**
	 * 更新状态
	 * @param distrOrder
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(DistrOrder distrOrder) {
		super.updateStatus(distrOrder);
	}
	
	/**
	 * 删除数据
	 * @param distrOrder
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(DistrOrder distrOrder) {
		super.delete(distrOrder);
		DistrOrderDetail distrOrderDetail = new DistrOrderDetail();
		distrOrderDetail.setDocumentCode(distrOrder);
		distrOrderDetailDao.delete(distrOrderDetail);
	}

	/**
	 * 根据订单号查询明细信息
	 * @param orderList
	 * @return
	 */
	public List<DistrOrderDetail> selectById(List<String> orderList) {
		return distrOrderDetailDao.selectById(orderList);
	}

	/**
	 * 取消订单确认
	 * @param documentCode
	 */
	@Transactional(readOnly = false)
	public void cancleConform (String documentCode) {
		distrOrderDao.cancleConform(documentCode);
	}
}