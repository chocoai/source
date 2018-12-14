/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.message.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.fz.message.entity.FzMessageManage;

/**
 * 梵赞消息推送DAO接口
 * @author scarlett
 * @version 2018-10-24
 */
@MyBatisDao
public interface FzMessageManageDao extends CrudDao<FzMessageManage> {
	
}