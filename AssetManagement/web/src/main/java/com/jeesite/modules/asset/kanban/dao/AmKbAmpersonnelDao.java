/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.kanban.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.kanban.entity.AmKbAmpersonnel;

import java.util.List;

/**
 * 看板有效人员维护DAO接口
 * @author dwh
 * @version 2018-07-24
 */
@MyBatisDao
public interface AmKbAmpersonnelDao extends CrudDao<AmKbAmpersonnel> {

    AmKbAmpersonnel getAmKbAmpersonnelByPhone(String phone,String kanbanCode);
    int deleteDb(String kbAmPersonnelCode);  // 删除数据

    List<AmKbAmpersonnel> getInfoByTel(String tel,String kanbanCode);
}