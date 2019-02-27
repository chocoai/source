/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.ovopark.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.storevideo.ovopark.entity.SvOvoparkDevice;
import org.apache.ibatis.annotations.Delete;

/**
 * 万店掌设备DAO接口
 * @author Philip Guan
 * @version 2019-02-18
 */
@MyBatisDao
public interface SvOvoparkDeviceDao extends CrudDao<SvOvoparkDevice> {
    @Delete("DELETE FROM sv_ovopark_device")
    int deleteAll();
}