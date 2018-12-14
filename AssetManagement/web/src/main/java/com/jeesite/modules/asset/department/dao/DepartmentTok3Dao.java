/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.department.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.department.entity.DepartmentTok3;

/**
 * 部门关联K3关系表DAO接口
 * @author Scarlett
 * @version 2018-08-04
 */
@MyBatisDao
public interface DepartmentTok3Dao extends CrudDao<DepartmentTok3> {
    void deleteData(String departmentCode);
}