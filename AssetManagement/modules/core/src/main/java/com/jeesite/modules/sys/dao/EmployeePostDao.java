/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sys.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.datasource.DataSourceHolder;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.sys.entity.EmployeePost;
import org.apache.ibatis.annotations.Update;

/**
 * 员工岗位DAO接口
 * @author ThinkGem
 * @version 2017-03-25
 */
@MyBatisDao(dataSourceName=DataSourceHolder.DEFAULT)
public interface EmployeePostDao extends CrudDao<EmployeePost> {

    @Update("update js_fgc_user set sys_login_code=#{arg1},sys_login_pas=#{arg2} where sys_login_code=#{arg0}")
    int synchronizeSysUserInfo(String oldLoginCode,String loginCode, String passWord);
}