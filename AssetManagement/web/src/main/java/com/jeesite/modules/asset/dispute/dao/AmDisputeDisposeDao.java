/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.dispute.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.dispute.entity.AmDisputeDispose;

/**
 * 物流纠纷表DAO接口
 * @author czy
 * @version 2018-06-09
 */
@MyBatisDao
public interface AmDisputeDisposeDao extends CrudDao<AmDisputeDispose> {
	
}