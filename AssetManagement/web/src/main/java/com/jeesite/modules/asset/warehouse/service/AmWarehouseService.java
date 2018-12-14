/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.warehouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.TreeService;
import com.jeesite.modules.asset.warehouse.entity.AmWarehouse;
import com.jeesite.modules.asset.warehouse.dao.AmWarehouseDao;

/**
 * 仓库Service
 * @author dwh
 * @version 2018-04-27
 */
@Service
@Transactional(readOnly=true)
public class AmWarehouseService extends TreeService<AmWarehouseDao, AmWarehouse> {

	@Autowired
	private AmWarehouseDao dao;
	
	/**
	 * 获取单条数据
	 * @param amWarehouse
	 * @return
	 */
	@Override
	public AmWarehouse get(AmWarehouse amWarehouse) {
		return super.get(amWarehouse);
	}
	
	/**
	 * 查询列表数据
	 * @param amWarehouse
	 * @return
	 */
	@Override
	public List<AmWarehouse> findList(AmWarehouse amWarehouse) {
		return super.findList(amWarehouse);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amWarehouse
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmWarehouse amWarehouse) {
		super.save(amWarehouse);
	}
	
	/**
	 * 更新状态
	 * @param amWarehouse
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmWarehouse amWarehouse) {
		super.updateStatus(amWarehouse);
	}
	
	/**
	 * 删除数据
	 * @param amWarehouse
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmWarehouse amWarehouse) {
//		super.delete(amWarehouse);
		dao.deleteDb(amWarehouse.getWarehouseCode());
		if (amWarehouse != null && amWarehouse.getParent() != null) {
			dao.updateTreeLeaf(amWarehouse.getParent());
		}
	}

	@Transactional(readOnly=true)
	public List<AmWarehouse> getWarehouseListByLeaf(String treeLeaf) {
		return dao.getWarehouseListByLeaf( treeLeaf);
	}
}