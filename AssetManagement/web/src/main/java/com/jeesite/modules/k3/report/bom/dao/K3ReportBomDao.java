/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.k3.report.bom.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.k3.report.bom.entity.K3ReportBom;

/**
 * 物料清单DAO接口
 * @author Albert
 * @version 2018-11-21
 */
@MyBatisDao
public interface K3ReportBomDao extends CrudDao<K3ReportBom> {
	
}