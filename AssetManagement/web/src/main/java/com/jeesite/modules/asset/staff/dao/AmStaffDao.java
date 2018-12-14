/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.staff.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.staff.entity.AmStaff;


/**
 * 员工资料表DAO接口
 * @author czy
 * @version 2018-04-26
 */
@MyBatisDao
public interface AmStaffDao extends CrudDao<AmStaff> {
    int deleteDb(String staffCode);
}