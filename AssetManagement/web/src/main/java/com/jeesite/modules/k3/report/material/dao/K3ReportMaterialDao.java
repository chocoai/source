/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.k3.report.material.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.k3.report.material.entity.K3ReportMaterial;

/**
 * 物料DAO接口
 * @author Albert
 * @version 2018-11-28
 */
@MyBatisDao
public interface K3ReportMaterialDao extends CrudDao<K3ReportMaterial> {
	
}