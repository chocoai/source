/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ampersonnel.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.ampersonnel.entity.AmPersonnel;
import org.apache.ibatis.annotations.Param;

/**
 * 有效人员维护表DAO接口
 * @author mclaran
 * @version 2018-06-26
 */
@MyBatisDao
public interface AmPersonnelDao extends CrudDao<AmPersonnel> {

     int check(@Param("phone") String phone);
}