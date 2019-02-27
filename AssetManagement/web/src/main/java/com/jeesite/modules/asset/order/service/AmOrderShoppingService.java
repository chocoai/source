/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.order.service;

import com.jeesite.modules.asset.order.entity.Shopping;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.order.entity.AmOrderShopping;
import com.jeesite.modules.asset.order.dao.AmOrderShoppingDao;

import java.util.List;

/**
 * 导购购物车Service
 * @author len
 * @version 2018-11-13
 */
@Service
@Transactional(readOnly=true)
public class AmOrderShoppingService extends CrudService<AmOrderShoppingDao, AmOrderShopping> {

	@Autowired
	private AmOrderShoppingDao amOrderShoppingDao;
	/**
	 * 获取单条数据
	 * @param amOrderShopping
	 * @return
	 */
	@Override
	public AmOrderShopping get(AmOrderShopping amOrderShopping) {
		return super.get(amOrderShopping);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amOrderShopping
	 * @return
	 */
	@Override
	public Page<AmOrderShopping> findPage(Page<AmOrderShopping> page, AmOrderShopping amOrderShopping) {
		return super.findPage(page, amOrderShopping);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amOrderShopping
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmOrderShopping amOrderShopping) {
		super.save(amOrderShopping);
	}
	
	/**
	 * 更新状态
	 * @param amOrderShopping
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmOrderShopping amOrderShopping) {
		super.updateStatus(amOrderShopping);
	}
	
	/**
	 * 删除数据
	 * @param amOrderShopping
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmOrderShopping amOrderShopping) {
		super.delete(amOrderShopping);
	}

	/**
	 * 根据skuid查询商品信息
	 * @param skuId
	 */
	@Transactional(readOnly = true)
	public Shopping getSkuInfo(String skuId) {
		return amOrderShoppingDao.getSkuInfo(skuId);
	}

	/**
	 * 根据skuId 获取真实售价库存数
	 * @param list
	 * @return
	 */
	public List<TbSku> selectBySkuId(List<String> list) {
		return amOrderShoppingDao.selectBySkuId(list);
	}

	/**
	 * 根据skuId 登录用户查询状态为有效的商品
	 * @param skuIdList
	 * @param userCode
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateBySkuIdList(List<String> skuIdList, String userCode) {
		return amOrderShoppingDao.updateBySkuIdList(skuIdList, userCode);
	}

	public String selectImgByNumIid(String numIid) {
		return amOrderShoppingDao.selectImgByNumIid(numIid);
	}
}
