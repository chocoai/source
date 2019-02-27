/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.tvclient.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.storevideo.tvclient.entity.SvTvClient;

/**
 * 电视在线客户端DAO接口
 * @author Philip Guan
 * @version 2019-02-07
 */
@MyBatisDao
public interface SvTvClientDao extends CrudDao<SvTvClient> {
	
}