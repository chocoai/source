/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userroleconfig.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.userroleconfig.entity.BasicUserInfo;


/**
 * 用户表DAO接口
 * @author dwh
 * @version 2018-07-18
 */
@MyBatisDao
public interface BasicUserInfoDao extends CrudDao<BasicUserInfo> {
	
}