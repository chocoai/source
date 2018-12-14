/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.fzlogin.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.fz.fzlogin.entity.FzLoginRecord;

/**
 * 梵赞登录记录DAO接口
 * @author len
 * @version 2018-10-09
 */
@MyBatisDao
public interface FzLoginRecordDao extends CrudDao<FzLoginRecord> {
	
}