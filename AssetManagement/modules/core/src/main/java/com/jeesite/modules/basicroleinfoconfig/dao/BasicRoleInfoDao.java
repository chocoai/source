/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.basicroleinfoconfig.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.basicroleinfoconfig.entity.BasicRoleInfo;
import com.jeesite.modules.sys.entity.Role;

import java.util.List;


/**
 * 角色表DAO接口
 * @author dwh
 * @version 2018-07-26
 */
@MyBatisDao
public interface BasicRoleInfoDao extends CrudDao<BasicRoleInfo> {

    List<BasicRoleInfo> getListByUserCode(String userCode);
}