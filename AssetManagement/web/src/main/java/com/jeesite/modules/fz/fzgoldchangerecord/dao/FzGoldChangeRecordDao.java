/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.fzgoldchangerecord.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.fz.fzgoldchangerecord.entity.FzGoldChangeRecord;
import org.apache.ibatis.annotations.Delete;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 梵钻变更记录DAO接口
 * @author dwh
 * @version 2018-09-21
 */
@MyBatisDao
public interface FzGoldChangeRecordDao extends CrudDao<FzGoldChangeRecord> {
    @Delete("delete from fz_gold_change_record where record_code=#{recordCode}")
    void deleteDB(String recordCode);

    void insetBatch(@Param("records")List<FzGoldChangeRecord> records);
}