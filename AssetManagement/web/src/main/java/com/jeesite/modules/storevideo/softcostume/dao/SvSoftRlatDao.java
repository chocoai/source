/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.softcostume.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.storevideo.softcostume.entity.SvSoftRlat;
import org.apache.ibatis.annotations.Delete;

/**
 * 店铺视频关系DAO接口
 * @author len
 * @version 2019-02-26
 */
@MyBatisDao
public interface SvSoftRlatDao extends CrudDao<SvSoftRlat> {
    @Delete("DELETE FROM sv_soft_rlat WHERE soft_costume_code = #{arg0}")    //AND (#{arg2} IS NULL OR card_code = #{arg2})
    void deleteByVideoId(String videoId);
}