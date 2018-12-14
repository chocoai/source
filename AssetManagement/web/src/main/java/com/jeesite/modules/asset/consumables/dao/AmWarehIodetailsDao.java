/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.consumables.entity.AmWarehIodetails;

/**
 * 耗材仓库进出明细表DAO接口
 * @author czy
 * @version 2018-05-24
 */
@MyBatisDao
public interface AmWarehIodetailsDao extends CrudDao<AmWarehIodetails> {
	int deleteDb(String billCode);
}