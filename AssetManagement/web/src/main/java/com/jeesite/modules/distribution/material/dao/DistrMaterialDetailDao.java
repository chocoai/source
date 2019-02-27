/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.material.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.distribution.material.entity.DistrMaterialDetail;
import org.apache.ibatis.annotations.Delete;

/**
 * 分销素材DAO接口
 * @author len
 * @version 2019-01-05
 */
@MyBatisDao
public interface DistrMaterialDetailDao extends CrudDao<DistrMaterialDetail> {
    /**
     * 根据素材编号删除明细数据
     * @param materialCode
     */
    @Delete("DELETE FROM distr_material_detail WHERE material_code=#{arg0]")
    void deleteDb(String materialCode);
}