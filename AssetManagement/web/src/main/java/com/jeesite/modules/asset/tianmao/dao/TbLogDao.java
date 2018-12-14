/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.tianmao.entity.TbLog;
import org.apache.ibatis.annotations.Insert;

/**
 * tb_logDAO接口
 * @author jace
 * @version 2018-07-23
 */
@MyBatisDao
public interface TbLogDao extends CrudDao<TbLog> {
    @Insert("insert into tb_log values(#{skuId},#{sku},#{user},#{type},#{describe},#{logTime})")
	int insertLog(TbLog tbLog);
}