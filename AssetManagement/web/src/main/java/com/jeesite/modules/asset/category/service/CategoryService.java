/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.category.service;

import java.util.List;

import com.jeesite.common.dao.CrudDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.service.TreeService;
import com.jeesite.modules.asset.category.entity.Category;
import com.jeesite.modules.asset.category.dao.CategoryDao;

/**
 * 耗材分类表Service
 *
 * @author czy
 * @version 2018-04-23
 */
@Service
@Transactional(readOnly = true)
public class CategoryService extends TreeService<CategoryDao, Category> {

    @Autowired
    private CategoryDao categoryDao;

    /**
     * 获取单条数据
     *
     * @param category
     * @return
     */
    @Override
    public Category get(Category category) {
        return super.get(category);
    }

    /**
     * 查询列表数据
     *
     * @param category
     * @return
     */
    @Override
    public List<Category> findList(Category category) {
        return super.findList(category);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param category
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Category category) {
        this.updateStatus(category);
        super.save(category);
    }

    /**
     * 更新状态
     *
     * @param category
     */
    @Transactional(readOnly = false)
    public void updateStatus(Category category) {
        categoryDao.updateStatus(category);
    }

    /**
     * 删除数据
     *
     * @param category
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(Category category) {
    //    super.delete(category);
        categoryDao.deleteDb(category.getCategoryCode());
        if (category != null && category.getParent() != null) {
            dao.updateTreeLeaf(category.getParent());
        }
    }

    @Transactional(readOnly = false)
    public boolean getCategoryByName(String categoryName, String categoryCode) {
        boolean rst = false;
        List<Category> list = categoryDao.getCategoryByName(categoryName);
        //查不到，新建，查得到判断名字和code是否是同一条数据
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (categoryCode != null && categoryCode.length() > 0 && list.get(i).getCategoryCode().equals(categoryCode)) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return rst;
    }


}