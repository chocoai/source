/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userroleconfig.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.userroleconfig.entity.UserDataPermission;

import java.util.List;

/**
 * 用户数据权限DAO接口
 * @author dwh
 * @version 2018-07-18
 */
@MyBatisDao
public interface UserDataPermissionDao extends CrudDao<UserDataPermission> {
    List<UserDataPermission> getListByUserCode(String userCode);

    int deleteDbByInterFaceCode(String interFaceCode);

    List<UserDataPermission> getListByUserCodeAndIntUrl(String userCode, String interSql);
}