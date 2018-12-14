/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fault.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.fault.entity.BugRegistration;

/**
 * 线上bug登记单DAO接口
 * @author Scarlett
 * @version 2018-10-25
 */
@MyBatisDao
public interface BugRegistrationDao extends CrudDao<BugRegistration> {
    void deleteDataFromDb(String bugCode);
}