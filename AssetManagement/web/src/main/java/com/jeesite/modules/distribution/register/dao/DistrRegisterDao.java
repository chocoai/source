/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.register.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.distribution.register.entity.DistrRegister;

/**
 * 分销注册申请DAO接口
 * @author len
 * @version 2019-01-03
 */
@MyBatisDao
public interface DistrRegisterDao extends CrudDao<DistrRegister> {
	
}