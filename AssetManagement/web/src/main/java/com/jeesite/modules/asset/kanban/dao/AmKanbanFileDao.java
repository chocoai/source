/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.kanban.dao;

import com.jeesite.common.dao.TreeDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.kanban.entity.AmKanbanFile;

import java.util.List;

/**
 * 看板档案DAO接口
 * @author dwh
 * @version 2018-07-24
 */
@MyBatisDao
public interface AmKanbanFileDao extends TreeDao<AmKanbanFile> {

    List<AmKanbanFile> getKanbanFileByName(String kanbanName);
    int deleteDb(String kanbanCode);  // 删除数据
}