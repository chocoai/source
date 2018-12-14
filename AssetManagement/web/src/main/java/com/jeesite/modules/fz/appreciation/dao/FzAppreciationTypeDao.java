/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.appreciation.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.fz.appreciation.entity.FzAppreciationType;
import org.apache.ibatis.annotations.Delete;

import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 赞赏类型DAO接口
 * @author dwh
 * @version 2018-09-19
 */
@MyBatisDao
public interface FzAppreciationTypeDao extends CrudDao<FzAppreciationType> {

    @Delete("delete from fz_appreciation_type where type_code=#{typeCode}")
    void deleteByDb(String typeCode);
    @Select("select * from fz_appreciation_type where privilege_phone=#{phon} ")
    List<FzAppreciationType> getListByPhon(String phon);

    List<FzAppreciationType> getListByPhons(@Param("phons") List<String> phons);
}