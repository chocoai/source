/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.track.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.track.entity.AmTrackDetail;

import java.util.List;

/**
 * 退货跟踪单DAO接口
 * @author czy
 * @version 2018-06-21
 */
@MyBatisDao
public interface AmTrackDetailDao extends CrudDao<AmTrackDetail> {
    List<AmTrackDetail> getDetail(String entryId, String consumablesCode);
}