/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ding.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.ding.entity.DingUserBackups;

/**
 * 钉钉用户表备份DAO接口
 * @author len
 * @version 2018-10-31
 */
@MyBatisDao
public interface DingUserBackupsDao extends CrudDao<DingUserBackups> {
	
}