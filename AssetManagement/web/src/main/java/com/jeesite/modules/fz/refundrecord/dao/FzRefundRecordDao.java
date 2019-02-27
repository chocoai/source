/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.refundrecord.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.fz.refundrecord.entity.FzRefundRecord;

/**
 * 商城退款记录DAO接口
 * @author len
 * @version 2019-01-04
 */
@MyBatisDao
public interface FzRefundRecordDao extends CrudDao<FzRefundRecord> {
	
}