/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fgcqualitycheck.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.fgcqualitycheck.entity.FgcLog;
import org.apache.ibatis.annotations.Select;

/**
 * 梵工厂反写日志表DAO接口
 * @author len
 * @version 2018-10-16
 */
@MyBatisDao
public interface FgcLogDao extends CrudDao<FgcLog> {
    @Select("SELECT log_id FROM js_fgc_log WHERE fentityId=#{arg0}")
    FgcLog getByEntityId(String fentityId);

    @Select("SELECT log_id FROM js_fgc_log WHERE fid=#{arg0}")
    FgcLog getByFid(String fid);
}