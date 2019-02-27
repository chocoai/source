/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.expendrecord.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.fz.expendrecord.entity.FzExpenditureRecord;
import org.apache.ibatis.annotations.Select;

/**
 * 梵钻支出表DAO接口
 * @author len
 * @version 2018-12-18
 */
@MyBatisDao
public interface FzExpenditureRecordDao extends CrudDao<FzExpenditureRecord> {
    @Select("SELECT IFNULL(SUM(expend_num),0) from fz_expenditure_record where user_id =#{userId}")
    Double getExpendGold(String userId);
}