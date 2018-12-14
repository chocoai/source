/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ding.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.ding.entity.DingRole;
import com.jeesite.modules.asset.ding.entity.SyncPosition;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 人员管理DAO接口
 * @author scarlett
 * @version 2018-09-19
 */
@MyBatisDao
public interface DingRoleDao extends CrudDao<DingRole> {
    @Select("select role_name from js_ding_role  a left join  js_ding_user_role b on a.role_id=b.role_id where b.user_id=#{userid} ")
    List<String> getRoleNamesByUser(String userid);
}