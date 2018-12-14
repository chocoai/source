/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.staff.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.entity.Page;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.staff.entity.AmStaff;
import com.jeesite.modules.asset.staff.entity.AmStaffPost;

import java.util.List;

/**
 * 员工岗位表DAO接口
 * @author czy
 * @version 2018-04-27
 */
@MyBatisDao
public interface AmStaffPostDao extends CrudDao<AmStaffPost> {

    List<String> findPostName(String staffCode); // 岗位查询条件
    int deleteDb(String staffCode);
}