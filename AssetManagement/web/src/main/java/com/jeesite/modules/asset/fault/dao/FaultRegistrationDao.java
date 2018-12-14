/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fault.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.fault.entity.FaultRegistration;

/**
 * 故障登记单DAO接口
 * @author Scarlett
 * @version 2018-07-11
 */
@MyBatisDao
public interface FaultRegistrationDao extends CrudDao<FaultRegistration> {
    void deleteDataFromDb(String registrationCode);
	
}