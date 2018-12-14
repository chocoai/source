/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.k3.report.inventory.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.k3.report.inventory.entity.K3ReportInventory;

/**
 * 即时库存DAO接口
 * @author Albert
 * @version 2018-11-21
 */
@MyBatisDao
public interface K3ReportInventoryDao extends CrudDao<K3ReportInventory> {
	
}