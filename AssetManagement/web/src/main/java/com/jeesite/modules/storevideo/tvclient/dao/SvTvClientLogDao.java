/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.tvclient.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.storevideo.tvclient.entity.SvTvClientLog;

/**
 * 电视客户端日志DAO接口
 * @author Philip Guan
 * @version 2019-02-13
 */
@MyBatisDao
public interface SvTvClientLogDao extends CrudDao<SvTvClientLog> {
	
}