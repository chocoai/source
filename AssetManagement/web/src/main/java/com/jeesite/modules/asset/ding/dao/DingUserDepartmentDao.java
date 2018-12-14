/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ding.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.ding.entity.DingUserDepartment;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * js_ding_user_departmentDAO接口
 * @author scarlett
 * @version 2018-09-22
 */
@MyBatisDao
public interface DingUserDepartmentDao extends CrudDao<DingUserDepartment> {
    @Select("SELECT a.* FROM js_ding_user_department a LEFT JOIN js_ding_user b on a.user_id = b.userid where b.`left`='0'")
    List<DingUserDepartment> selectByLeft();
}