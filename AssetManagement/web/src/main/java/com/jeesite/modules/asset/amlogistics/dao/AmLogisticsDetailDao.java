/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amlogistics.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.amlogistics.entity.AmLogisticsDetail;

import java.util.List;

/**
 * 物流车查询单DAO接口
 * @author dwh
 * @version 2018-06-07
 */
@MyBatisDao
public interface AmLogisticsDetailDao extends CrudDao<AmLogisticsDetail> {
    List<AmLogisticsDetail> getDetail(String entryId, String consumablesCode);
	
}