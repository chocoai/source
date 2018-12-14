/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.fzlogin.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.fz.fzlogin.entity.FzNeigouLoginLog;

/**
 * 梵赞内购登陆日志DAO接口
 * @author easter
 * @version 2018-11-27
 */
@MyBatisDao
public interface FzNeigouLoginLogDao extends CrudDao<FzNeigouLoginLog> {
	
}