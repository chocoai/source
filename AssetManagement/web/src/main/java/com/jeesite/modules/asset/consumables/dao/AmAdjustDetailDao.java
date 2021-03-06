/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.consumables.entity.AmAdjustDetail;

/**
 * 耗材成本调整单DAO接口
 * @author dwh
 * @version 2018-05-31
 */
@MyBatisDao
public interface AmAdjustDetailDao extends CrudDao<AmAdjustDetail> {
    int deleteDb(String documentCode);
	
}