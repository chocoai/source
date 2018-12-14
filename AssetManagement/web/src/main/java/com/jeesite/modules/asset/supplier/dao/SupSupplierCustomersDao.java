/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.supplier.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.supplier.entity.SupSupplierCustomers;

/**
 * 供应商资料DAO接口
 * @author Scarlett
 * @version 2018-07-04
 */
@MyBatisDao
public interface SupSupplierCustomersDao extends CrudDao<SupSupplierCustomers> {
    void deleteData(String supplierCode);
}