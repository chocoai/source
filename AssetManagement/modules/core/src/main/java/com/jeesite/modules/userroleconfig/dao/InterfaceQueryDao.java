/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userroleconfig.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.userroleconfig.entity.InterfaceQuery;

/**
 * 接口查询条件DAO接口
 * @author dwh
 * @version 2018-07-18
 */
@MyBatisDao
public interface InterfaceQueryDao extends CrudDao<InterfaceQuery> {
    int deluserInfterfaceQuery(String userCode, String interFaceCode);
    int delRoleInfterfaceQuery(String roleCode, String interFaceCode);
    int deleteDbByInterFaceCode(String interFaceCode);

    int deleteQueryByField(String fieldCode);
}