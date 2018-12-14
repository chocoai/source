/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.sale.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.sale.entity.SaleReception;

/**
 * 客户接待表DAO接口
 * @author Scarlett
 * @version 2018-07-26
 */
@MyBatisDao
public interface SaleReceptionDao extends CrudDao<SaleReception> {
	
}