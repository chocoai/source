/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.dispute.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.dispute.entity.AmDisputeDetail;

import java.util.List;

/**
 * 物流纠纷表DAO接口
 * @author czy
 * @version 2018-06-09
 */
@MyBatisDao
public interface AmDisputeDetailDao extends CrudDao<AmDisputeDetail> {
    AmDisputeDetail findDetil(String code, String consumablesCode);
    List<AmDisputeDetail> getDetail(String entryId, String consumablesCode);
}