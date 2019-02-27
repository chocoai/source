/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.ovopark.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.storevideo.ovopark.entity.SvOvoparkUser;

/**
 * 万店掌用户DAO接口
 * @author Philip Guan
 * @version 2019-02-19
 */
@MyBatisDao
public interface SvOvoparkUserDao extends CrudDao<SvOvoparkUser> {
	
}