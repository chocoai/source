/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.basicroleinfoconfig.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.basicroleinfoconfig.entity.RoleDataPermission;

import java.util.List;

/**
 * 角色数据权限DAO接口
 * @author dwh
 * @version 2018-07-26
 */
@MyBatisDao
public interface RoleDataPermissionDao extends CrudDao<RoleDataPermission> {
    List<RoleDataPermission> getListByRoleCode(String roleCode);


}